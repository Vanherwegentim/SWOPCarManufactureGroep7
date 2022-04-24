package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;

public class ControllerFactoryManagerState {

  protected ControllerFactoryManagerState() {
  }

  public AdaptSchedulingAlgorithmController createAdaptSchedulingAlgorithmController(AssemblyLine assemblyLine) {
    return new AdaptSchedulingAlgorithmController(assemblyLine);
  }

  public CheckProductionStatisticsController createCheckProductionStatisticsController(AssemblyLine assemblyLine) {
    return new CheckProductionStatisticsController(assemblyLine);
  }
}
