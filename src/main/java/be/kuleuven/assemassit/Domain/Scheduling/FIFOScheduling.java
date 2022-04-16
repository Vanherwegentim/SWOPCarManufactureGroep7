package be.kuleuven.assemassit.Domain.Scheduling;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.CarAssemblyProcess;
import be.kuleuven.assemassit.Domain.Helper.EnhancedIterator;
import be.kuleuven.assemassit.Domain.Helper.MyEnhancedIterator;
import be.kuleuven.assemassit.Domain.WorkPost;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

public class FIFOScheduling implements SchedulingAlgorithm {

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
          assemblyTask.setCompletionTime(minutes);
        }

        CarAssemblyProcess carAssemblyProcess = workPost.getCarAssemblyProcess();
        carAssemblyProcess.complete();

        if (!iterator.hasPrevious()) {
          finishedCars.add(workPost.getCarAssemblyProcess());
          workPost.removeCarAssemblyProcess();

          int overtimeInMinutes = differenceInMinutes(endTime, LocalTime.now());
          if (overtimeInMinutes >= 0)
            overtime = overtimeInMinutes; // only set the overtime when it is greater than or equal to zero
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

    return overtime;
  }

  @Override
  public LocalDateTime giveEstimatedDeliveryTime(Queue<CarAssemblyProcess> carAssemblyProcessesQueue, int manufacturingTimeInMinutes, LocalTime endTime, LocalTime startTime, int maxTimeNeededForWorkPostOnLine) {
// calculate remaining cars for this day (1)
    double remaningCarsForTodayDouble = ((double) ((endTime.getHour() * 60 + endTime.getMinute()) - // end time
      manufacturingTimeInMinutes - // time needed to manufacture a car
      (LocalTime.now().getHour() * 60 + LocalTime.now().getMinute()) - // current time
      maxTimeNeededForWorkPostOnLine + // time needed for the slowest work post
      60) / (double) 60);
    int remainingCarsForToday =
      (int) Math.ceil(((double) ((endTime.getHour() * 60 + endTime.getMinute()) - // end time
        manufacturingTimeInMinutes - // time needed to manufacture a car
        (LocalTime.now().getHour() * 60 + LocalTime.now().getMinute()) - // current time
        maxTimeNeededForWorkPostOnLine + // time needed for the slowest work post
        60) / (double) 60));

    // calculate cars for a whole day (2)
    int amountOfCarsWholeDay =
      (int) ((double) ((endTime.getHour() * 60 + endTime.getMinute()) - // end time
        manufacturingTimeInMinutes - // time needed to manufacture a car
        (startTime.getHour() * 60 + startTime.getMinute()) - // opening time
        maxTimeNeededForWorkPostOnLine + // time needed for the slowest work post
        60) / (double) 60);

    // car can still be manufactured today
    if (carAssemblyProcessesQueue.size() <= remainingCarsForToday) {
      // total duration - max duration of work post + max duration * amount
      return LocalDateTime.now().plusMinutes(manufacturingTimeInMinutes - maxTimeNeededForWorkPostOnLine).plusMinutes((long) maxTimeNeededForWorkPostOnLine * carAssemblyProcessesQueue.size());
    }

    // car can not be manufactured today
    // Math.ceil(list - (1) / (2)) = days needed
    int daysNeeded = Math.max(0, (carAssemblyProcessesQueue.size() - remainingCarsForToday) / amountOfCarsWholeDay - 1);


    // return date of tomorrow + days needed + minutes needed
    LocalDateTime today = LocalDateTime.now();
    int remainingMinutesForLastDay = (((carAssemblyProcessesQueue.size() - Math.abs(remainingCarsForToday)) % amountOfCarsWholeDay) + 1) * maxTimeNeededForWorkPostOnLine;
    return LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), startTime.getHour(), startTime.getMinute()).plusDays(1).plusDays(daysNeeded).plusMinutes(manufacturingTimeInMinutes - maxTimeNeededForWorkPostOnLine).plusMinutes(remainingMinutesForLastDay);
  }

  private int differenceInMinutes(LocalTime dateTime1, LocalTime dateTime2) {
    int timeInMinutes1 = dateTime1.getHour() * 60 + dateTime1.getMinute();
    int timeInMinutes2 = dateTime2.getHour() * 60 + dateTime2.getMinute();

    return timeInMinutes2 - timeInMinutes1;
  }
}
