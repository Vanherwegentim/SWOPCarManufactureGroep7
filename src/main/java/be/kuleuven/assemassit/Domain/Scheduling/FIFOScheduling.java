package be.kuleuven.assemassit.Domain.Scheduling;

import be.kuleuven.assemassit.Domain.CarAssemblyProcess;
import be.kuleuven.assemassit.Domain.WorkPost;

import java.util.List;
import java.util.Queue;

public class FIFOScheduling implements SchedulingAlgorithm {
  @Override
  public void moveAssemblyLine(Queue<CarAssemblyProcess> carAssemblyProcesses, List<CarAssemblyProcess> finishedCars, List<WorkPost> workPostsInOrder) {

  }
}
