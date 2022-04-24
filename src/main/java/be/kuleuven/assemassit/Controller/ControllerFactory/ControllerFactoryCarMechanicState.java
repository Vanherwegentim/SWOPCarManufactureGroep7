package be.kuleuven.assemassit.Controller.ControllerFactory;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.CheckAssemblyLineStatusController;
import be.kuleuven.assemassit.Controller.PerformAssemblyTasksController;
import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;

public class ControllerFactoryCarMechanicState extends ControllerFactoryState {
  protected ControllerFactoryCarMechanicState() {
  }

  public AssemblyLineController createAssemblyLineController(AssemblyLine assemblyLine) {
    return new AssemblyLineController(assemblyLine);
  }

  public CheckAssemblyLineStatusController createCheckAssemblyLineStatusController(AssemblyLine assemblyLine) {
    return new CheckAssemblyLineStatusController(assemblyLine);
  }

  public PerformAssemblyTasksController createPerformAssemblyTasksController(AssemblyLine assemblyLine, CarManufactoringCompany carManufactoringCompany) {
    return new PerformAssemblyTasksController(assemblyLine, carManufactoringCompany);
  }
}
