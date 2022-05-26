package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;

public class ControllerFactoryMiddleWareManagerState extends ControllerFactoryMiddleWareState {

  /**
   * Get a new instance of the create adapt scheduling algorithm controller
   *
   * @param assemblyLine the instance of the assembly line used for creation
   * @return the new instance of the create adapt sched
   */
  public AdaptSchedulingAlgorithmController createAdaptSchedulingAlgorithmController(AssemblyLine assemblyLine) {
    return factory.createAdaptSchedulingAlgorithmController(assemblyLine);
  }

  /**
   * Get a new instance of the check production statistics controller
   *
   * @param assemblyLine the instance of assembly line used for creation
   * @return the new instance of the check production statistics controller
   */
  public CheckProductionStatisticsController createCheckProductionStatisticsController(AssemblyLine assemblyLine) {
    return factory.createCheckProductionStatisticsController(assemblyLine);
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof ControllerFactoryMiddleWareManagerState;
  }
}
