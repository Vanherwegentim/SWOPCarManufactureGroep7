package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;

public class ControllerFactoryMiddleWareManagerState extends ControllerFactoryMiddleWareState {

  public AdaptSchedulingAlgorithmController createAdaptSchedulingAlgorithmController(AssemblyLine assemblyLine) {
    return new AdaptSchedulingAlgorithmController(assemblyLine);
  }

  public CheckProductionStatisticsController createCheckProductionStatisticsController(AssemblyLine assemblyLine) {
    return new CheckProductionStatisticsController(assemblyLine);
  }

  public AdaptSchedulingAlgorithmController createAdaptSchedulingAlgorithmController() {
    //return new AdaptSchedulingAlgorithmController(assemblyLine);
    return null;
  }

  public CheckProductionStatisticsController createCheckProductionStatisticsController() {
    //return new CheckProductionStatisticsController(assemblyLine);
    return null;
  }


  @Override
  public boolean equals(Object o) {
    return o instanceof ControllerFactoryMiddleWareManagerState;
  }
}
