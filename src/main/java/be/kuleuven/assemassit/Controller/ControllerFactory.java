package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;
import be.kuleuven.assemassit.Domain.GarageHolder;
import be.kuleuven.assemassit.Domain.Repositories.GarageHolderRepository;

import java.time.LocalTime;

public class ControllerFactory {
  private final AssemblyLine assemblyLine;
  private final CarManufactoringCompany carManufactoringCompany;
  private GarageHolder loggedInGarageHolder;

  /**
   * @peerObject
   */
  private ControllerFactoryState controllerFactoryState;

  public ControllerFactory() {
    this.assemblyLine = new AssemblyLine();
    this.carManufactoringCompany = new CarManufactoringCompany(LocalTime.of(6, 0), LocalTime.of(22, 0), assemblyLine);
    this.controllerFactoryState = new ControllerFactoryLoginState(this);
  }

  public LoginController createLoginController() {
    return new LoginController(new GarageHolderRepository(), this);
  }

  protected void setLoggedInGarageHolder(GarageHolder loggedInGarageHolder) {
    this.loggedInGarageHolder = loggedInGarageHolder;
  }

  /**
   * Generate an instance of the order controller
   *
   * @return a new instance of the order controller
   */
  public OrderNewCarController createOrderNewCarController() {
    return controllerFactoryState.createOrderNewCarController(carManufactoringCompany, loggedInGarageHolder);
  }

  public CheckOrderDetailsController createCheckOrderDetailsController() {
    return new CheckOrderDetailsController(loggedInGarageHolder);
  }

  /**
   * Generate an instance of the order controller
   *
   * @param carManufactoringCompany can be used for mocking
   * @return a new instance of the order controller
   */
  public OrderNewCarController createOrderNewCarController(CarManufactoringCompany carManufactoringCompany, GarageHolder loggedInGarageHolder) {
    return controllerFactoryState.createOrderNewCarController(carManufactoringCompany, loggedInGarageHolder);
  }

  /**
   * Generate an instance of the assembly line controller
   *
   * @return a new instance of the assembly controller
   */
  public AssemblyLineController createAssemblyLineController() {
    return controllerFactoryState.createAssemblyLineController(assemblyLine);
  }

  /**
   * Generate an instance of the assembly line controller
   *
   * @param assemblyLine | can be used for mocking
   * @return a new instance of the assembly controller
   */
  public AssemblyLineController createAssemblyLineController(AssemblyLine assemblyLine) {
    return controllerFactoryState.createAssemblyLineController(assemblyLine);
  }

  public PerformAssemblyTasksController createPerformAssemblyTasksController() {
    return controllerFactoryState.createPerformAssemblyTasksController(assemblyLine, carManufactoringCompany);
  }

  public CheckAssemblyLineStatusController createCheckAssemblyLineStatusController() {
    return controllerFactoryState.createCheckAssemblyLineStatusController(assemblyLine);
  }

  public CheckProductionStatisticsController createCheckProductionStatisticsController() {
    return controllerFactoryState.createCheckProductionStatisticsController(assemblyLine);
  }

}
