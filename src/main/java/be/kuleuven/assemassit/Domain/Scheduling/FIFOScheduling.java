package be.kuleuven.assemassit.Domain.Scheduling;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.CarAssemblyProcess;
import be.kuleuven.assemassit.Domain.Helper.EnhancedIterator;
import be.kuleuven.assemassit.Domain.Helper.MyEnhancedIterator;
import be.kuleuven.assemassit.Domain.WorkPost;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class FIFOScheduling implements SchedulingAlgorithm {

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
    CarAssemblyProcess nextProcess = carAssemblyProcessesQueue.peek();
    if (
      !carAssemblyProcessesQueue.isEmpty() &&
        LocalTime.now()
          .plusMinutes(nextProcess.giveManufacturingDurationInMinutes())
          .plusMinutes(previousOvertimeInMinutes)
          .isBefore(endTime)
    ) {
      // add a new work process to the first work post
      workPost.addProcessToWorkPost(carAssemblyProcessesQueue.poll());
    }
  }
}
