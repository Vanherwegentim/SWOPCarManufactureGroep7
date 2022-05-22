package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.ProductionStatistics;

public class ControllerFactoryMiddleWareManagerState extends ControllerFactoryMiddleWareState {

  public AdaptSchedulingAlgorithmController createAdaptSchedulingAlgorithmController(AssemblyLine assemblyLine) {
    return new AdaptSchedulingAlgorithmController(assemblyLine);
  }

  public CheckProductionStatisticsController createCheckProductionStatisticsController(AssemblyLine assemblyLine) {
    return new CheckProductionStatisticsController(new ProductionStatistics(assemblyLine.getFinishedCars()));
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof ControllerFactoryMiddleWareManagerState;
  }
}