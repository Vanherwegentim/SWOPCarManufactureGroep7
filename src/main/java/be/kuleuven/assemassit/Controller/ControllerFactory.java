package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;
import be.kuleuven.assemassit.Domain.GarageHolder;
import be.kuleuven.assemassit.Repositories.GarageHolderRepository;

import java.time.LocalTime;

public class ControllerFactory {
  private final AssemblyLine assemblyLine;
  private final CarManufactoringCompany carManufactoringCompany;
  private GarageHolder loggedInGarageHolder;

  public ControllerFactory() {
    this.assemblyLine = new AssemblyLine();
    this.carManufactoringCompany = new CarManufactoringCompany(LocalTime.of(6, 0), LocalTime.of(22, 0), assemblyLine);
  }

  public String giveLoggedInGarageHolderName() {
    return loggedInGarageHolder.getName();
  }

  public LoginController createLoginController(ControllerFactoryMiddleWare controllerFactoryMiddleWare) {
    return new LoginController(new GarageHolderRepository(), controllerFactoryMiddleWare);
  }

  /**
   * Generate an instance of the order controller
   *
   * @return a new instance of the order controller
   */
  public OrderNewCarController createOrderNewCarController() {
    return new OrderNewCarController(carManufactoringCompany, loggedInGarageHolder);
  }

  /**
   * Generate an instance of the order controller
   *
   * @param carManufactoringCompany The car manufactoring company instance that should be used for creating the controller
   * @param loggedInGarageHolder    The logged in garage holder
   * @return a new instance of the order controller
   */
  public OrderNewCarController createOrderNewCarController(CarManufactoringCompany carManufactoringCompany, GarageHolder loggedInGarageHolder) {
    return new OrderNewCarController(carManufactoringCompany, loggedInGarageHolder);
  }

  /**
   * Generate an instance of the checkOrderDetailsController
   *
   * @return a new instance of the checkOrderDetailsController
   */
  public CheckOrderDetailsController createCheckOrderDetailsController() {
    return new CheckOrderDetailsController(loggedInGarageHolder);
  }

  /**
   * Generate an instance of the checkOrderDetailsController
   *
   * @param loggedInGarageHolder The logged in garage holder
   * @return a new instance of the checkOrderDetailsController
   */
  public CheckOrderDetailsController createCheckOrderDetailsController(GarageHolder loggedInGarageHolder) {
    return new CheckOrderDetailsController(loggedInGarageHolder);
  }

  /**
   * Generate an instance of the performAssemblyTasksController
   *
   * @return a new instance of the performAssemblyTasksController
   */
  public PerformAssemblyTasksController createPerformAssemblyTasksController() {
    return new PerformAssemblyTasksController(assemblyLine, carManufactoringCompany);
  }

  /**
   * Generate an instance of the performAssemblyTasksController
   *
   * @param assemblyLine            The assembly line instance that should be used for creating the controller
   * @param carManufactoringCompany The car manufactoring company instance that should be used for creating the controller
   * @return a new instance of the performAssemblyTasksController
   */
  public PerformAssemblyTasksController createPerformAssemblyTasksController(AssemblyLine assemblyLine, CarManufactoringCompany carManufactoringCompany) {
    return new PerformAssemblyTasksController(assemblyLine, carManufactoringCompany);
  }

  /**
   * Generate an instance of the checkAssemblyLineStatusController
   *
   * @return a new instance of the checkAssemblyLineStatusController
   */
  public CheckAssemblyLineStatusController createCheckAssemblyLineStatusController() {
    return new CheckAssemblyLineStatusController(assemblyLine);
  }

  /**
   * Generate an instance of the checkAssemblyLineStatusController
   *
   * @param assemblyLine The assembly line instance that should be used for creating the controller
   * @return a new instance of the checkAssemblyLineStatusController
   */
  public CheckAssemblyLineStatusController createCheckAssemblyLineStatusController(AssemblyLine assemblyLine) {
    return new CheckAssemblyLineStatusController(assemblyLine);
  }

  /**
   * Generate an instance of the checkProductionStatisticsController
   *
   * @return a new instance of the checkProductionStatisticsController
   */
  public CheckProductionStatisticsController createCheckProductionStatisticsController() {
    return new CheckProductionStatisticsController(assemblyLine);
  }

  /**
   * Generate an instance of the checkProductionStatisticsController
   *
   * @param assemblyLine The assembly line instance that should be used for creating the controller
   * @return a new instance of the checkProductionStatisticsController
   */
  public CheckProductionStatisticsController createCheckProductionStatisticsController(AssemblyLine assemblyLine) {
    return new CheckProductionStatisticsController(assemblyLine);
  }

  /**
   * Generate an instance of the checkProductionStatisticsController
   *
   * @return a new instance of the checkProductionStatisticsController
   */
  public AdaptSchedulingAlgorithmController createAdaptSchedulingAlgorithmController() {
    return new AdaptSchedulingAlgorithmController(assemblyLine);
  }

  /**
   * Generate an instance of the checkProductionStatisticsController
   *
   * @param assemblyLine The assembly line instance that should be used for creating the controller
   * @return a new instance of the checkProductionStatisticsController
   */
  public AdaptSchedulingAlgorithmController createAdaptSchedulingAlgorithmController(AssemblyLine assemblyLine) {
    return new AdaptSchedulingAlgorithmController(assemblyLine);
  }


  public CarManufactoringCompany getCarManufactoringCompany() {
    return carManufactoringCompany;
  }

  public GarageHolder getLoggedInGarageHolder() {
    return loggedInGarageHolder;
  }

  public AssemblyLine getAssemblyLine() {
    return assemblyLine;
  }
}
