package be.kuleuven.assemassit.Domain.Scheduling;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Car;
import be.kuleuven.assemassit.Domain.CarAssemblyProcess;
import be.kuleuven.assemassit.Domain.Enums.CarOption;
import be.kuleuven.assemassit.Domain.Helper.EnhancedIterator;
import be.kuleuven.assemassit.Domain.Helper.MyEnhancedIterator;
import be.kuleuven.assemassit.Domain.WorkPost;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class SpecificationBatchScheduling extends DefaultSchedulingAlgorithm {
  private List<CarOption> batchCarOptions;

  public SpecificationBatchScheduling(List<CarOption> batchCarOptions) {
    super();
    this.batchCarOptions = batchCarOptions;
  }

  @Override
  public int moveAssemblyLine(
    int minutes,
    int previousOvertimeInMinutes,
    LocalTime endTime,
    Queue<CarAssemblyProcess> carAssemblyProcessesQueue,
    List<CarAssemblyProcess> finishedCars,
    List<WorkPost> workPostsInOrder
  ) {

    int overtime = -1; // return -1 if the end of the day is not reached yet

    if (minutes < 0)
      throw new IllegalArgumentException("Minutes can not be below 0");

    Collections.reverse(workPostsInOrder);
    EnhancedIterator<WorkPost> iterator = new MyEnhancedIterator<>(workPostsInOrder);

    WorkPost workPost = iterator.next();

    do {
      if (workPost != null && workPost.getCarAssemblyProcess() != null) {
        for (AssemblyTask assemblyTask : workPost.getWorkPostAssemblyTasks()) {
          //assemblyTask.setCompletionTime(minutes);
        }

        CarAssemblyProcess carAssemblyProcess = workPost.getCarAssemblyProcess();
        carAssemblyProcess.complete();

        int overtimeInMinutes = differenceInMinutes(endTime, LocalTime.now());
        if (overtimeInMinutes >= 0)
          overtime = overtimeInMinutes; // only set the overtime when it is greater than or equal to zero

        if (!iterator.hasPrevious()) {
          finishedCars.add(workPost.getCarAssemblyProcess());
          workPost.removeCarAssemblyProcess();
        } else {
          WorkPost nextWorkPost = iterator.peek();
          nextWorkPost.addProcessToWorkPost(workPost.getCarAssemblyProcess());
          workPost.removeCarAssemblyProcess();
        }
      }

      workPost = iterator.next();
    } while (iterator.hasNext());

    // check if the next process can still be produced today
    CarAssemblyProcess nextProcess = giveNextCarAssemblyProcess(carAssemblyProcessesQueue);
    if (
      !carAssemblyProcessesQueue.isEmpty() &&
        LocalTime.now()
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

  @Override
  public LocalDateTime giveEstimatedDeliveryTime(Queue<CarAssemblyProcess> carAssemblyProcessesQueue, int manufacturingTimeInMinutes, LocalTime endTime, LocalTime startTime, int maxTimeNeededForWorkPostOnLine) {

    List<CarAssemblyProcess> carAssemblyProcessesList = carAssemblyProcessesQueue.stream().toList();

    // this is not the most optimal way of doing this, but it is an option and it works
    Collections.sort(carAssemblyProcessesList, (p1, p2) -> {
      Car car1 = p1.getCarOrder().getCar();
      Car car2 = p2.getCarOrder().getCar();

      boolean car1Batch = car1.giveListOfCarOptions().containsAll(batchCarOptions) && batchCarOptions.containsAll(car1.giveListOfCarOptions());
      boolean car2Batch = car2.giveListOfCarOptions().containsAll(batchCarOptions) && batchCarOptions.containsAll(car1.giveListOfCarOptions());

      if (car1Batch == true && car2Batch == true) return 0;
      if (car1Batch == true) return 1;
      if (car2Batch == true) return -1;

      return 0;
    });

    carAssemblyProcessesQueue = new ArrayDeque<>(carAssemblyProcessesList);

    return super.giveEstimatedDeliveryTime(carAssemblyProcessesQueue, manufacturingTimeInMinutes, endTime, startTime, maxTimeNeededForWorkPostOnLine);
  }

  /*private CarAssemblyProcess giveNextCarAssemblyProcess(Queue<CarAssemblyProcess> carAssemblyProcessesQueue, CarAssemblyProcess carAssemblyProcessOnFirstWorkPost) {
    return Collections.max(carAssemblyProcessesQueue.stream().toList(), (p1, p2) -> {
      Integer amount1 = amountOfEqualSpecifications(p1, carAssemblyProcessOnFirstWorkPost);
      Integer amount2 = amountOfEqualSpecifications(p2, carAssemblyProcessOnFirstWorkPost);
      int difference = amount1.compareTo(amount2);

      if (difference == 0) difference = p2.getCarOrder().getOrderTime().compareTo(p1.getCarOrder().getOrderTime());
      return difference;
    });
  }*/

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

  /*
  private int amountOfEqualSpecifications(CarAssemblyProcess carAssemblyProcess1, CarAssemblyProcess carAssemblyProcess2) {
    Car car1 = carAssemblyProcess1.getCarOrder().getCar();
    Car car2 = carAssemblyProcess2.getCarOrder().getCar();

    return (int) car1.giveListOfCarOptions()
      .stream()
      .filter(op1 -> car2
        .giveListOfCarOptions()
        .stream()
        .anyMatch(op2 -> op1.equals(op2)))
      .count();
  }*/

  /*
  private class SpecificationComparator implements Comparator<CarAssemblyProcess> {
    @Override
    public int compare(CarAssemblyProcess o1, CarAssemblyProcess o2) {
      return 0;
    }
  }*/
}
