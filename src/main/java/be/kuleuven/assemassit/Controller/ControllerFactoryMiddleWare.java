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

  public ControllerFactoryMiddleWare() {
    this.assemblyLine = new AssemblyLine();
    this.controllerFactoryMiddleWareState = new ControllerFactoryMiddleWareLoginState();
    this.carManufactoringCompany = new CarManufactoringCompany(LocalTime.of(6, 0), LocalTime.of(22, 0), assemblyLine);
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

  public String giveLoggedInGarageHolderName() {
    return this.loggedInGarageHolder.getName();
  }

  protected CarManufactoringCompany getCarManufactoringCompany() {
    return this.carManufactoringCompany;
  }

  public LoginController createLoginController() {
    return controllerFactoryMiddleWareState.createLoginController(new GarageHolderRepository(), this);
  }

  public LoginController createLoginController(GarageHolderRepository garageHolderRepository) {
    return controllerFactoryMiddleWareState.createLoginController(garageHolderRepository, this);
  }

  public AdaptSchedulingAlgorithmController createAdaptSchedulingAlgorithmController() {
    return controllerFactoryMiddleWareState.createAdaptSchedulingAlgorithmController(assemblyLine);
  }

  public CheckAssemblyLineStatusController createCheckAssemblyLineStatusController() {
    return controllerFactoryMiddleWareState.createCheckAssemblyLineStatusController(assemblyLine);
  }

  public CheckOrderDetailsController createCheckOrderDetailsController() {
    return controllerFactoryMiddleWareState.createCheckOrderDetailsController(loggedInGarageHolder);
  }

  public OrderNewCarController createOrderNewCarController() {
    return controllerFactoryMiddleWareState.createOrderNewCarController(carManufactoringCompany, loggedInGarageHolder);
  }

  public OrderNewCarController createOrderNewCarController(CarManufactoringCompany carManufactoringCompany, GarageHolder garageHolder) {
    return controllerFactoryMiddleWareState.createOrderNewCarController(carManufactoringCompany, garageHolder);
  }

  public PerformAssemblyTasksController createPerformAssemblyTasksController() {
    return controllerFactoryMiddleWareState.createPerformAssemblyTasksController(assemblyLine, carManufactoringCompany);
  }

  public CheckProductionStatisticsController createCheckProductionStatisticsController() {
    return controllerFactoryMiddleWareState.createCheckProductionStatisticsController(assemblyLine);
  }

  public void loginGarageHolder(GarageHolder loggedInGarageHolder) {
    if (loggedInGarageHolder == null)
      throw new IllegalArgumentException("The garage holder can not be null");

    this.loggedInGarageHolder = loggedInGarageHolder;
    this.controllerFactoryMiddleWareState = new ControllerFactoryMiddleWareGarageHolderState();
  }

  public void logoutGarageHolder() {
    if (this.loggedInGarageHolder == null)
      throw new IllegalArgumentException("There is no garage holder logged in");

    this.loggedInGarageHolder = null;
    this.controllerFactoryMiddleWareState = new ControllerFactoryMiddleWareLoginState();
  }

  public void loginManager() {
    this.controllerFactoryMiddleWareState = new ControllerFactoryMiddleWareManagerState();
  }

  public void logoutManager() {
    this.controllerFactoryMiddleWareState = new ControllerFactoryMiddleWareLoginState();
  }

  public void loginCarMechanic() {
    this.controllerFactoryMiddleWareState = new ControllerFactoryMiddleWareCarMechanicState();
  }

  public void logoutCarMechanic() {
    this.controllerFactoryMiddleWareState = new ControllerFactoryMiddleWareLoginState();
  }
}
