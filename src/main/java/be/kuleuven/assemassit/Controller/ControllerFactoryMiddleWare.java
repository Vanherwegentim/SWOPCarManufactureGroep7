package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;
import be.kuleuven.assemassit.Domain.GarageHolder;
import be.kuleuven.assemassit.Repositories.GarageHolderRepository;

import java.time.LocalTime;

public class ControllerFactoryMiddleWare {

  /**
   * @peerObject
   */
  private ControllerFactoryMiddleWareState controllerFactoryMiddleWareState;
  private GarageHolder loggedInGarageHolder;
  private AssemblyLine assemblyLine;
  private CarManufactoringCompany carManufactoringCompany;

  /**
   * @mutates | this
   * @post | getAssemblyLine() != null
   * @post | getControllerFactoryMiddleWareState() != null
   * @post | getCarManufactoringCompany() != null
   */
  public ControllerFactoryMiddleWare() {

    LocalTime openingTime = LocalTime.of(6, 0);
    LocalTime closingTime = LocalTime.of(22, 0);
    this.assemblyLine = new AssemblyLine(openingTime, closingTime);
    this.controllerFactoryMiddleWareState = new ControllerFactoryMiddleWareLoginState();
    this.carManufactoringCompany = new CarManufactoringCompany(assemblyLine);
  }

  protected ControllerFactoryMiddleWareState getControllerFactoryMiddleWareState() {
    return this.controllerFactoryMiddleWareState;
  }

  public AssemblyLine getAssemblyLine() {
    return this.assemblyLine;
  }

  protected GarageHolder getLoggedInGarageHolder() {
    return this.loggedInGarageHolder;
  }

  /**
   * Get the name of the logged in garage holder
   *
   * @return the name of the logged in garage holder
   */
  public String giveLoggedInGarageHolderName() {
    return this.loggedInGarageHolder.getName();
  }

  protected CarManufactoringCompany getCarManufactoringCompany() {
    return this.carManufactoringCompany;
  }

  /**
   * Get an instance of the login controller
   *
   * @return the new instance of the login controller
   * @creates | result
   */
  public LoginController createLoginController() {
    return createLoginController(new GarageHolderRepository());
  }

  /**
   * Get an instance of the login controller
   *
   * @param garageHolderRepository create the instance with a specific garage holder repository
   * @return the new instance of the login controller
   * @creates | result
   */
  public LoginController createLoginController(GarageHolderRepository garageHolderRepository) {
    return controllerFactoryMiddleWareState.createLoginController(garageHolderRepository, this);
  }

  /**
   * Get an instance of the adapt scheduling algorithm controller
   *
   * @return the new instance of the adapt scheduling controller
   * @creates | result
   */
  public AdaptSchedulingAlgorithmController createAdaptSchedulingAlgorithmController() {
    return controllerFactoryMiddleWareState.createAdaptSchedulingAlgorithmController(assemblyLine);
  }

  /**
   * Get an instance of the check assembly line status controller
   *
   * @return the new instance of the check assembly line status controller
   * @creates | result
   */
  public CheckAssemblyLineStatusController createCheckAssemblyLineStatusController() {
    return controllerFactoryMiddleWareState.createCheckAssemblyLineStatusController(assemblyLine);
  }

  /**
   * Get an instance of the check order details controller
   *
   * @return the new instance of the check order details controller
   * @creates | result
   */
  public CheckOrderDetailsController createCheckOrderDetailsController() {
    return controllerFactoryMiddleWareState.createCheckOrderDetailsController(loggedInGarageHolder);
  }

  /**
   * Get an instance of the order new car controller
   *
   * @return the new instance of the order new car controller
   * @creates | result
   */
  public OrderNewCarController createOrderNewCarController() {
    return controllerFactoryMiddleWareState.createOrderNewCarController(carManufactoringCompany, loggedInGarageHolder);
  }

  /**
   * Get an instance of the order new car controller
   *
   * @param carManufactoringCompany this instance will be used for creation
   * @param garageHolder            this instance will be used for creation
   * @return the new instance of the order new car controller
   * @creates | result
   */
  public OrderNewCarController createOrderNewCarController(CarManufactoringCompany carManufactoringCompany, GarageHolder garageHolder) {
    return controllerFactoryMiddleWareState.createOrderNewCarController(carManufactoringCompany, garageHolder);
  }

  /**
   * Get an instance of the perform assembly task controller
   *
   * @return the new instance of the order new car controller
   * @creates | result
   */
  public PerformAssemblyTasksController createPerformAssemblyTasksController() {
    return controllerFactoryMiddleWareState.createPerformAssemblyTasksController(assemblyLine, carManufactoringCompany);
  }

  /**
   * Get an instance of the check production statistics controller
   *
   * @return the new instance of the check production statistics controller
   * @creates | result
   */
  public CheckProductionStatisticsController createCheckProductionStatisticsController() {
    return controllerFactoryMiddleWareState.createCheckProductionStatisticsController(assemblyLine);
  }

  /**
   * Log in a new garage holder
   *
   * @param loggedInGarageHolder the garage holder to be logged in
   * @throws IllegalArgumentException | loggedInGarageHolder == null
   * @post | getLoggedInGarageHolder().equals(loggedInGarageHolder)
   * @mutates | this
   */
  public void loginGarageHolder(GarageHolder loggedInGarageHolder) {
    if (loggedInGarageHolder == null)
      throw new IllegalArgumentException("The garage holder can not be null");

    this.loggedInGarageHolder = loggedInGarageHolder;
    this.controllerFactoryMiddleWareState = new ControllerFactoryMiddleWareGarageHolderState();
  }

  /**
   * Log out the currently logged in garage holder
   *
   * @throws IllegalStateException | getLoggedInGarageHolder() == null
   * @mutates | this
   * @post | getLoggedInGarageHolder() == null
   */
  public void logoutGarageHolder() {
    if (this.loggedInGarageHolder == null)
      throw new IllegalArgumentException("There is no garage holder logged in");

    this.loggedInGarageHolder = null;
    this.controllerFactoryMiddleWareState = new ControllerFactoryMiddleWareLoginState();
  }

  /**
   * Log in as a manager
   *
   * @mutates | this
   */
  public void loginManager() {
    this.controllerFactoryMiddleWareState = new ControllerFactoryMiddleWareManagerState();
  }

  /**
   * Log out the manager
   *
   * @mutates | this
   */
  public void logoutManager() {
    this.controllerFactoryMiddleWareState = new ControllerFactoryMiddleWareLoginState();
  }

  /**
   * Log in as a car mechanic
   *
   * @mutates | this
   */
  public void loginCarMechanic() {
    this.controllerFactoryMiddleWareState = new ControllerFactoryMiddleWareCarMechanicState();
  }

  /**
   * Log out the car mechanic
   *
   * @mutates | this
   */
  public void logoutCarMechanic() {
    this.controllerFactoryMiddleWareState = new ControllerFactoryMiddleWareLoginState();
  }
}
