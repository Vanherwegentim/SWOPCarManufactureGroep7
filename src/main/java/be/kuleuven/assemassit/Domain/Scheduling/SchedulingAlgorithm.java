package be.kuleuven.assemassit.Domain.Scheduling;

import be.kuleuven.assemassit.Domain.CarAssemblyProcess;
import be.kuleuven.assemassit.Domain.WorkPost;

import java.time.LocalTime;
import java.util.List;
import java.util.Queue;

public interface SchedulingAlgorithm {
  void moveAssemblyLine(
    int minutes,
    int previousOvertimeInMinutes,
    LocalTime endTime,
    Queue<CarAssemblyProcess> carAssemblyProcesses,
    List<CarAssemblyProcess> finishedCars,
    List<WorkPost> workPostsInOrder
  );
}
