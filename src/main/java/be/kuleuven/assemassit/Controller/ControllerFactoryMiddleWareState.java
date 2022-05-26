package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;
import be.kuleuven.assemassit.Domain.GarageHolder;
import be.kuleuven.assemassit.Repositories.GarageHolderRepository;

public abstract class ControllerFactoryMiddleWareState {

    protected ControllerFactory factory;

  public ControllerFactoryMiddleWareState() {
    this.factory = new ControllerFactory();
  }

  /**
   * Create an instance of the adapt scheduling algorithm controller
   *
   * @param assemblyLine the instance of the assembly line used for creation
   * @return the new instance of the adapt scheduling algorithm controller
   * @throws IllegalStateException
   */
  public AdaptSchedulingAlgorithmController createAdaptSchedulingAlgorithmController(AssemblyLine assemblyLine) {
    throw new IllegalStateException();
  }

  /**
   * Create an instance of check assembly line status controller
   *
   * @param assemblyLine the instance of the assembly line used for creation
   * @return the new instance of the check assembly line status controller
   * @throws IllegalStateException
   */
  public CheckAssemblyLineStatusController createCheckAssemblyLineStatusController(AssemblyLine assemblyLine) {
    throw new IllegalStateException();
  }

  /**
   * Create an instance of check order details controller
   *
   * @param loggedInGarageHolder the instance of the garage holder used for creation
   * @return the new instance of the check order details controller
   * @throws IllegalStateException
   */
  public CheckOrderDetailsController createCheckOrderDetailsController(GarageHolder loggedInGarageHolder) {
    throw new IllegalStateException();
  }

  /**
   * Create an instance of order new car controller
   *
   * @param carManufactoringCompany the instance of the car manufactoring company used for creation
   * @param loggedInGarageHolder    the instance of the garage holder that is currently logged in
   * @return the new instance of the order new car controller
   * @throws IllegalStateException
   */
  public OrderNewCarController createOrderNewCarController(CarManufactoringCompany carManufactoringCompany, GarageHolder loggedInGarageHolder) {
    throw new IllegalStateException();
  }

  /**
   * Create an instance of perform assembly task controller
   *
   * @param carManufactoringCompany the instance of the car manufactoring company used for creation
   * @param assemblyLine            the instance of the assembly line used for creation
   * @return the new instance of the perform assembly task controller
   * @throws IllegalStateException
   */
  public PerformAssemblyTasksController createPerformAssemblyTasksController(AssemblyLine assemblyLine, CarManufactoringCompany carManufactoringCompany) {
    throw new IllegalStateException();
  }

  /**
   * Create an instance of check production statistics controller
   *
   * @param assemblyLine the instance of assembly line used for creation
   * @return the new instance of the production statistics controller
   * @throws IllegalStateException
   */
  public CheckProductionStatisticsController createCheckProductionStatisticsController(AssemblyLine assemblyLine) {
    throw new IllegalStateException();
  }

  /**
   * Create an instance of login controller
   *
   * @param garageHolderRepository      the instance of garage holder repository used for creation
   * @param controllerFactoryMiddleWare the instance of the controller factory middleware used for creation
   * @return the new instance of the login controller
   * @throws IllegalStateException
   */
  public LoginController createLoginController(GarageHolderRepository garageHolderRepository, ControllerFactoryMiddleWare controllerFactoryMiddleWare) {
    throw new IllegalStateException();
  }
  
  @Override
  public abstract boolean equals(Object o);
}
