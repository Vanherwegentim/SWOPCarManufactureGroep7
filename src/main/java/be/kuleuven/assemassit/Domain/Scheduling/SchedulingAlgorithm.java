package be.kuleuven.assemassit.Domain.Scheduling;

import be.kuleuven.assemassit.Domain.CarAssemblyProcess;
import be.kuleuven.assemassit.Domain.WorkPost;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Queue;

public interface SchedulingAlgorithm {
  int moveAssemblyLine(
    int minutes,
    int previousOvertimeInMinutes,
    LocalTime endTime,
    Queue<CarAssemblyProcess> carAssemblyProcessesQueue,
    List<CarAssemblyProcess> finishedCars,
    List<WorkPost> workPostsInOrder
  );

  LocalDateTime giveEstimatedDeliveryTime(Queue<CarAssemblyProcess> carAssemblyProcessesQueue, int manufacturingTimeInMinutes, LocalTime endTime, LocalTime startTime, int maxTimeNeededForWorkPostOnLine);
}
