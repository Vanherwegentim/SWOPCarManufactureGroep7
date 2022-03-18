package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;
import be.kuleuven.assemassit.Domain.Repositories.GarageHolderRepository;

import java.time.LocalTime;

public class ControllerFactory {
  private final AssemblyLine assemblyLine;
  private final CarManufactoringCompany carManufactoringCompany;

  public ControllerFactory() {
    this.assemblyLine = new AssemblyLine();
    this.carManufactoringCompany = new CarManufactoringCompany(LocalTime.of(6, 0), LocalTime.of(22, 0), assemblyLine);
  }

  /**
   * Generate an instance of the order controller
   *
   * @return a new instance of the order controller
   */
  public OrderController createOrderController() {
    return new OrderController(carManufactoringCompany, new GarageHolderRepository());
  }

  public OrderController createOrderController(CarManufactoringCompany carManufactoringCompany, GarageHolderRepository garageHolderRepository) {
    return new OrderController(carManufactoringCompany, garageHolderRepository);
  }

  /**
   * Generate an instance of the assembly line controller
   *
   * @return a new instance of the assembly controller
   */
  public AssemblyLineController createAssemblyLineController() {
    return new AssemblyLineController(assemblyLine);
  }

  public AssemblyLineController createAssemblyLineController(AssemblyLine assemblyLine) {
    return new AssemblyLineController(assemblyLine);
  }

}
