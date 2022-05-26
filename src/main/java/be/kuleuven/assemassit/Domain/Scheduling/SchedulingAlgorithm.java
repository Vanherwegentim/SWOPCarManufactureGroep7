package be.kuleuven.assemassit.Domain.Scheduling;

import be.kuleuven.assemassit.Domain.CarAssemblyProcess;
import be.kuleuven.assemassit.Domain.WorkPost;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Queue;

public interface SchedulingAlgorithm {
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
  int moveAssemblyLine(
    int previousOvertimeInMinutes,
    LocalTime endTime,
    Queue<CarAssemblyProcess> carAssemblyProcessesQueue,
    List<CarAssemblyProcess> finishedCars,
    List<WorkPost> workPostsInOrder
  );

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
  LocalDateTime giveEstimatedDeliveryTime(
    Queue<CarAssemblyProcess> carAssemblyProcessesQueue,
    int manufacturingTimeInMinutes,
    LocalTime endTime,
    LocalTime startTime,
    int maxTimeNeededForWorkPostOnLine
  );
}
