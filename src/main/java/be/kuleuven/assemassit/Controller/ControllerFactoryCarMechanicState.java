package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;

public class ControllerFactoryCarMechanicState extends ControllerFactoryState {
  protected ControllerFactoryCarMechanicState() {
  }

  // todo REMOVE
//  public AssemblyLineController createAssemblyLineController(AssemblyLine assemblyLine) {
//    return new AssemblyLineController(assemblyLine);
//  }

  public CheckAssemblyLineStatusController createCheckAssemblyLineStatusController(AssemblyLine assemblyLine) {
    return new CheckAssemblyLineStatusController(assemblyLine);
  }

  public PerformAssemblyTasksController createPerformAssemblyTasksController(AssemblyLine assemblyLine, CarManufactoringCompany carManufactoringCompany) {
    return new PerformAssemblyTasksController(assemblyLine, carManufactoringCompany);
  }
}
