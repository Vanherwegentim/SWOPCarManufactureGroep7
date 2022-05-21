package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;

public class ControllerFactoryMiddleWareCarMechanicState extends ControllerFactoryMiddleWareState {

  public CheckAssemblyLineStatusController createCheckAssemblyLineStatusController(AssemblyLine assemblyLine) {
    return new CheckAssemblyLineStatusController(assemblyLine);
  }

  public PerformAssemblyTasksController createPerformAssemblyTasksController(AssemblyLine assemblyLine, CarManufactoringCompany carManufactoringCompany) {
    return new PerformAssemblyTasksController(assemblyLine, carManufactoringCompany);
  }

  public CheckAssemblyLineStatusController createCheckAssemblyLineStatusController() {
    //return new CheckAssemblyLineStatusController(assemblyLine);
    return null;
  }

  public PerformAssemblyTasksController createPerformAssemblyTasksController() {
    //return new PerformAssemblyTasksController(assemblyLine, carManufactoringCompany);
    return null;
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof ControllerFactoryMiddleWareCarMechanicState;
  }
}
