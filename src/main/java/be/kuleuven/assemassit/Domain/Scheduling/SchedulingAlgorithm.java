package be.kuleuven.assemassit.Domain.Scheduling;

import be.kuleuven.assemassit.Domain.CarAssemblyProcess;
import be.kuleuven.assemassit.Domain.WorkPost;

import java.util.List;
import java.util.Queue;

public interface SchedulingAlgorithm {
  void moveAssemblyLine(Queue<CarAssemblyProcess> carAssemblyProcesses,
                        List<CarAssemblyProcess> finishedCars,
                        List<WorkPost> workPostsInOrder);
}
