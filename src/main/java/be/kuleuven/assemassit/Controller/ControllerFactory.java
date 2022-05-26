package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;
import be.kuleuven.assemassit.Domain.GarageHolder;
import be.kuleuven.assemassit.Domain.ProductionStatistics;
import be.kuleuven.assemassit.Repositories.GarageHolderRepository;

public class ControllerFactory {

  /**
   * Genearte an instance of the login controller
   *
   * @param controllerFactoryMiddleWare the controller factory middleware for changing the state
   * @param garageHolderRepository the repository to get the garage holders list
   * @return the newly created login controller
   * @creates | result
   */
  public LoginController createLoginController(GarageHolderRepository garageHolderRepository, ControllerFactoryMiddleWare controllerFactoryMiddleWare) {
    return new LoginController(garageHolderRepository, controllerFactoryMiddleWare);
  }

  /**
   * Generate an instance of the order controller
   *
   * @param carManufactoringCompany The car manufactoring company instance that should be used for creating the controller
   * @param loggedInGarageHolder    The logged in garage holder
   * @return a new instance of the order controller
   * @creates | this
   */
  public OrderNewCarController createOrderNewCarController(CarManufactoringCompany carManufactoringCompany, GarageHolder loggedInGarageHolder) {
    return new OrderNewCarController(carManufactoringCompany, loggedInGarageHolder);
  }

  /**
   * Generate an instance of the checkOrderDetailsController
   *
   * @param loggedInGarageHolder The logged in garage holder
   * @return a new instance of the checkOrderDetailsController
   * @creates | result
   */
  public CheckOrderDetailsController createCheckOrderDetailsController(GarageHolder loggedInGarageHolder) {
    return new CheckOrderDetailsController(loggedInGarageHolder);
  }

  /**
   * Generate an instance of the performAssemblyTasksController
   *
   * @param assemblyLine            The assembly line instance that should be used for creating the controller
   * @param carManufactoringCompany The car manufactoring company instance that should be used for creating the controller
   * @return a new instance of the performAssemblyTasksController
   * @creates | result
   */
  public PerformAssemblyTasksController createPerformAssemblyTasksController(AssemblyLine assemblyLine, CarManufactoringCompany carManufactoringCompany) {
    return new PerformAssemblyTasksController(assemblyLine, carManufactoringCompany);
  }

  /**
   * Generate an instance of the checkAssemblyLineStatusController
   *
   * @param assemblyLine The assembly line instance that should be used for creating the controller
   * @return a new instance of the checkAssemblyLineStatusController
   * @creates | result
   */
  public CheckAssemblyLineStatusController createCheckAssemblyLineStatusController(AssemblyLine assemblyLine) {
    return new CheckAssemblyLineStatusController(assemblyLine);
  }

  /**
   * Generate an instance of the checkProductionStatisticsController
   *
   * @param assemblyLine The assembly line instance that should be used for creating the controller
   * @return a new instance of the checkProductionStatisticsController
   * @creates | result
   */
  public CheckProductionStatisticsController createCheckProductionStatisticsController(AssemblyLine assemblyLine) {
    return new CheckProductionStatisticsController(new ProductionStatistics(assemblyLine.getFinishedCars()));
  }

  /**
   * Generate an instance of the checkProductionStatisticsController
   *
   * @param assemblyLine The assembly line instance that should be used for creating the controller
   * @return a new instance of the checkProductionStatisticsController
   * @creates | result
   */
  public AdaptSchedulingAlgorithmController createAdaptSchedulingAlgorithmController(AssemblyLine assemblyLine) {
    return new AdaptSchedulingAlgorithmController(assemblyLine);
  }
}
