package be.kuleuven.assemassit.Domain.Scheduling;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Car;
import be.kuleuven.assemassit.Domain.CarAssemblyProcess;
import be.kuleuven.assemassit.Domain.Enums.CarOption;
import be.kuleuven.assemassit.Domain.Helper.CustomTime;
import be.kuleuven.assemassit.Domain.Helper.EnhancedIterator;
import be.kuleuven.assemassit.Domain.Helper.MyEnhancedIterator;
import be.kuleuven.assemassit.Domain.WorkPost;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class SpecificationBatchScheduling extends DefaultSchedulingAlgorithm {
  /**
   * @representationObject
   * @invar | batchCarOptions != null
   */
  private final List<CarOption> batchCarOptions;

  /**
   * @param batchCarOptions the list of car options that are handled as a batch in the scheduling algorithm
   */
  public SpecificationBatchScheduling(List<CarOption> batchCarOptions) {
    super();
    if (batchCarOptions == null)
      throw new IllegalArgumentException();
    this.batchCarOptions = batchCarOptions;
  }

  /**
   * Move the assembly line forward
   *
   * @param previousOvertimeInMinutes the over time that was done by the crew in the previous work day
   * @param endTime                   the closing time of the company
   * @param carAssemblyProcessesQueue the queue of scheduled cars to be manufactured
   * @param finishedCars              the list of cars that are already finished
   * @param workPostsInOrder          the list of work posts in the correct order
   * @return the newly spend over time (-1 if day is not finished, 0 if day ended within working hours)
   */
  @Override
  public int moveAssemblyLine(
    int previousOvertimeInMinutes,
    LocalTime endTime,
    Queue<CarAssemblyProcess> carAssemblyProcessesQueue,
    List<CarAssemblyProcess> finishedCars,
    List<WorkPost> workPostsInOrder
  ) {

    int overtime = -1; // return -1 if the end of the day is not reached yet


    workPostsInOrder = new ArrayList<>(workPostsInOrder);
    Collections.reverse(workPostsInOrder);

    EnhancedIterator<WorkPost> iterator = new MyEnhancedIterator<>(workPostsInOrder);

    WorkPost workPost;

    do {
      workPost = iterator.next();

      if (workPost != null) {
        for (AssemblyTask assemblyTask : workPost.getWorkPostAssemblyTasks()) {
          //assemblyTask.setCompletionTime(minutes);
        }

        // last one
        if (!iterator.hasPrevious() && workPost.getCarAssemblyProcess() != null) {
          CarAssemblyProcess carAssemblyProcess = workPost.getCarAssemblyProcess();
          carAssemblyProcess.complete();
          finishedCars.add(workPost.getCarAssemblyProcess());
          workPost.removeCarAssemblyProcess();

          int overtimeInMinutes = differenceInMinutes(endTime.minusMinutes(previousOvertimeInMinutes), (CustomTime.getInstance().customLocalTimeNow()));
          if (overtimeInMinutes >= 0)
            overtime = overtimeInMinutes; // only set the overtime when it is greater than or equal to zero
        }
      }

      if (iterator.hasNext()) {
        WorkPost previousWorkPost = iterator.peek();
        workPost.addProcessToWorkPost(previousWorkPost.getCarAssemblyProcess());
        previousWorkPost.removeCarAssemblyProcess();
      }
    } while (iterator.hasNext());


    // check if the next process can still be produced today
    CarAssemblyProcess nextProcess = giveNextCarAssemblyProcess(carAssemblyProcessesQueue);
    if (
      !carAssemblyProcessesQueue.isEmpty() &&
        (CustomTime.getInstance().customLocalTimeNow())
          .plusMinutes(nextProcess.giveManufacturingDurationInMinutes())
          .plusMinutes(previousOvertimeInMinutes)
          .isBefore(endTime)
    ) {
      // add a new work process to the first work post
      carAssemblyProcessesQueue.remove(nextProcess);
      workPost.addProcessToWorkPost(nextProcess);
    }

    return overtime;
  }

  /**
   * Return the estimated delivery date of the last order in the queue
   *
   * @param carAssemblyProcessesQueue      the queue of planned cars that need to be manufactured
   * @param manufacturingTimeInMinutes     the duration of the build of exactly one car
   * @param endTime                        the closing time of the company
   * @param startTime                      the opening time of the company
   * @param maxTimeNeededForWorkPostOnLine the max time needed to finish one task on a work post
   * @return the estimated delivery time of the last order in the queue
   */
  @Override
  public LocalDateTime giveEstimatedDeliveryTime(Queue<CarAssemblyProcess> carAssemblyProcessesQueue, int manufacturingTimeInMinutes, LocalTime endTime, LocalTime startTime, int maxTimeNeededForWorkPostOnLine) {

    List<CarAssemblyProcess> carAssemblyProcessesList = carAssemblyProcessesQueue.stream().collect(Collectors.toList());

    // this is not the most optimal way of doing this, but it is an option and it works
    Collections.sort(carAssemblyProcessesList, (p1, p2) -> {
      Car car1 = p1.getCarOrder().getCar();
      Car car2 = p2.getCarOrder().getCar();

      boolean car1Batch = car1.giveListOfCarOptions().containsAll(batchCarOptions) && batchCarOptions.containsAll(car1.giveListOfCarOptions());
      boolean car2Batch = car2.giveListOfCarOptions().containsAll(batchCarOptions) && batchCarOptions.containsAll(car1.giveListOfCarOptions());

      if (car1Batch && car2Batch) return 0;
      if (car1Batch) return 1;
      if (car2Batch) return -1;

      return 0;
    });

    carAssemblyProcessesQueue = new ArrayDeque<>(carAssemblyProcessesList);

    return super.giveEstimatedDeliveryTime(carAssemblyProcessesQueue, manufacturingTimeInMinutes, endTime, startTime, maxTimeNeededForWorkPostOnLine);
  }

  private CarAssemblyProcess giveNextCarAssemblyProcess(Queue<CarAssemblyProcess> carAssemblyProcesses) {
    return carAssemblyProcesses.stream().filter(p -> {
      Car car = p.getCarOrder().getCar();
      return car.giveListOfCarOptions().containsAll(batchCarOptions) && batchCarOptions.containsAll(car.giveListOfCarOptions());
    }).findFirst().orElse(carAssemblyProcesses.peek()); // if none found, peek the first one from queue
  }

  private int differenceInMinutes(LocalTime dateTime1, LocalTime dateTime2) {
    int timeInMinutes1 = dateTime1.getHour() * 60 + dateTime1.getMinute();
    int timeInMinutes2 = dateTime2.getHour() * 60 + dateTime2.getMinute();

    return timeInMinutes2 - timeInMinutes1;
  }

}
