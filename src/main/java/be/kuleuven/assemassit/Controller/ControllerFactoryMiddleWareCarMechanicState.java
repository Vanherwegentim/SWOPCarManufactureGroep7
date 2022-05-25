package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;

public class ControllerFactoryMiddleWareCarMechanicState extends ControllerFactoryMiddleWareState {


  public CheckAssemblyLineStatusController createCheckAssemblyLineStatusController(AssemblyLine assemblyLine) {
    return factory.createCheckAssemblyLineStatusController(assemblyLine);
  }

  public PerformAssemblyTasksController createPerformAssemblyTasksController(AssemblyLine assemblyLine, CarManufactoringCompany carManufactoringCompany) {
    return factory.createPerformAssemblyTasksController(assemblyLine, carManufactoringCompany);
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof ControllerFactoryMiddleWareCarMechanicState;
  }
}
