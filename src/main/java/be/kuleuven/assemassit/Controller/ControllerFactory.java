package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;

public class ControllerFactory {
  private AssemblyLine assemblyLine;
  private CarManufactoringCompany carManufactoringCompany;

  public ControllerFactory() {
    this.assemblyLine = new AssemblyLine();
    this.carManufactoringCompany = new CarManufactoringCompany();
  }

  public OrderController createOrderController() {
    return new OrderController(carManufactoringCompany, assemblyLine);
  }

  public AssemblyLineController createAssembyLineController() {
    return new AssemblyLineController(assemblyLine);
  }
}
