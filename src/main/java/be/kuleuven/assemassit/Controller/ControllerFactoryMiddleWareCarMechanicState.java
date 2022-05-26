package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;

public class ControllerFactoryMiddleWareCarMechanicState extends ControllerFactoryMiddleWareState {

  /**
   * Get a new instance of the check assembly line status controller
   *
   * @param assemblyLine the instance of the assembly line used for creation
   * @return the new instance of the check assembly line status controller
   */
  public CheckAssemblyLineStatusController createCheckAssemblyLineStatusController(AssemblyLine assemblyLine) {
    return factory.createCheckAssemblyLineStatusController(assemblyLine);
  }

  /**
   * Get a new instance of the perform assembly tasks controller
   *
   * @param assemblyLine            the instance of the assembly line used for creation
   * @param carManufactoringCompany the instance of the car manufactoring company used for creation
   * @return the new instance of the perform assemlby tasks controller
   */
  public PerformAssemblyTasksController createPerformAssemblyTasksController(AssemblyLine assemblyLine, CarManufactoringCompany carManufactoringCompany) {
    return factory.createPerformAssemblyTasksController(assemblyLine, carManufactoringCompany);
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof ControllerFactoryMiddleWareCarMechanicState;
  }
}
