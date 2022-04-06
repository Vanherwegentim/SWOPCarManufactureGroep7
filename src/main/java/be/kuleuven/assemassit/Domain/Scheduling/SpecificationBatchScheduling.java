package be.kuleuven.assemassit.Domain.Scheduling;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Car;
import be.kuleuven.assemassit.Domain.CarAssemblyProcess;
import be.kuleuven.assemassit.Domain.Helper.EnhancedIterator;
import be.kuleuven.assemassit.Domain.Helper.MyEnhancedIterator;
import be.kuleuven.assemassit.Domain.WorkPost;

import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

public class SpecificationBatchScheduling implements SchedulingAlgorithm {
  @Override
  public void moveAssemblyLine(
    int minutes,
    int previousOvertimeInMinutes,
    LocalTime endTime,
    Queue<CarAssemblyProcess> carAssemblyProcessesQueue,
    List<CarAssemblyProcess> finishedCars,
    List<WorkPost> workPostsInOrder
  ) {
    if (minutes < 0)
      throw new IllegalArgumentException("Minutes can not be below 0");

    Collections.reverse(workPostsInOrder);
    EnhancedIterator<WorkPost> iterator = new MyEnhancedIterator<>(workPostsInOrder);

    WorkPost workPost = iterator.next();

    do {
      if (workPost != null && workPost.getCarAssemblyProcess() != null) {
        for (AssemblyTask assemblyTask : workPost.getWorkPostAssemblyTasks()) {
          assemblyTask.setCompletionTime(minutes);
        }

        CarAssemblyProcess carAssemblyProcess = workPost.getCarAssemblyProcess();
        carAssemblyProcess.complete();

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
    CarAssemblyProcess nextProcess = giveNextCarAssemblyProcess(carAssemblyProcessesQueue, workPostsInOrder.get(0).getCarAssemblyProcess());
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
  }

  private CarAssemblyProcess giveNextCarAssemblyProcess(Queue<CarAssemblyProcess> carAssemblyProcessesQueue, CarAssemblyProcess carAssemblyProcessOnFirstWorkPost) {
    return Collections.max(carAssemblyProcessesQueue.stream().toList(), (p1, p2) -> {
      Integer amount1 = amountOfEqualSpecifications(p1, carAssemblyProcessOnFirstWorkPost);
      Integer amount2 = amountOfEqualSpecifications(p2, carAssemblyProcessOnFirstWorkPost);
      int difference = amount1.compareTo(amount2);

      if (difference == 0) difference = p2.getCarOrder().getOrderTime().compareTo(p1.getCarOrder().getOrderTime());
      return difference;
    });
  }

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
  }

  private class SpecificationComparator implements Comparator<CarAssemblyProcess> {
    @Override
    public int compare(CarAssemblyProcess o1, CarAssemblyProcess o2) {
      return 0;
    }
  }
}
