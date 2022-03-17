package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;

import java.time.LocalTime;

public class ControllerFactory {
  private AssemblyLine assemblyLine;
  private CarManufactoringCompany carManufactoringCompany;

  public ControllerFactory() {
    this.assemblyLine = new AssemblyLine();
    this.carManufactoringCompany = new CarManufactoringCompany(LocalTime.of(6,0), LocalTime.of(22,0), assemblyLine);
  }

  public OrderController createOrderController() {
    return new OrderController(carManufactoringCompany, assemblyLine);
  }

  public AssemblyLineController createAssemblyLineController() {
    return new AssemblyLineController(assemblyLine);
  }
}
