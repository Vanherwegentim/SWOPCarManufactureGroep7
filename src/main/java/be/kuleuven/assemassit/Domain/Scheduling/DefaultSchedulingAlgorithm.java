package be.kuleuven.assemassit.Domain.Scheduling;

import be.kuleuven.assemassit.Domain.CarAssemblyProcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Queue;

public abstract class DefaultSchedulingAlgorithm implements SchedulingAlgorithm {

  DefaultSchedulingAlgorithm() {
  }

  @Override
  public LocalDateTime giveEstimatedDeliveryTime(
    Queue<CarAssemblyProcess> carAssemblyProcessesQueue,
    int manufacturingTimeInMinutes,
    LocalTime endTime,
    LocalTime startTime,
    int maxTimeNeededForWorkPostOnLine
  ) {
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
}
