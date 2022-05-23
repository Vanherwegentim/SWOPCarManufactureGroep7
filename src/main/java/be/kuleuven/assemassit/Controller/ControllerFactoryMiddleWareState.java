package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;
import be.kuleuven.assemassit.Domain.GarageHolder;
import be.kuleuven.assemassit.Repositories.GarageHolderRepository;

public abstract class ControllerFactoryMiddleWareState {

  public AdaptSchedulingAlgorithmController createAdaptSchedulingAlgorithmController(AssemblyLine assemblyLine) {
    throw new IllegalStateException();
  }

  public CheckAssemblyLineStatusController createCheckAssemblyLineStatusController(AssemblyLine assemblyLine) {
    throw new IllegalStateException();
  }

  public CheckOrderDetailsController createCheckOrderDetailsController(GarageHolder loggedInGarageHolder) {
    throw new IllegalStateException();
  }

  public OrderNewCarController createOrderNewCarController(CarManufactoringCompany carManufactoringCompany, GarageHolder loggedInGarageHolder) {
    throw new IllegalStateException();
  }

  public PerformAssemblyTasksController createPerformAssemblyTasksController(AssemblyLine assemblyLine, CarManufactoringCompany carManufactoringCompany) {
    throw new IllegalStateException();
  }

  public CheckProductionStatisticsController createCheckProductionStatisticsController(AssemblyLine assemblyLine) {
    throw new IllegalStateException();
  }

  public LoginController createLoginController(GarageHolderRepository garageHolderRepository, ControllerFactoryMiddleWare controllerFactoryMiddleWare) {
    throw new IllegalStateException();
  }

  public AdaptSchedulingAlgorithmController createAdaptSchedulingAlgorithmController() {
    throw new IllegalStateException();
  }

  public CheckAssemblyLineStatusController createCheckAssemblyLineStatusController() {
    throw new IllegalStateException();
  }

  public CheckOrderDetailsController createCheckOrderDetailsController() {
    throw new IllegalStateException();
  }

  public OrderNewCarController createOrderNewCarController() {
    throw new IllegalStateException();
  }

  public PerformAssemblyTasksController createPerformAssemblyTasksController() {
    throw new IllegalStateException();
  }

  public CheckProductionStatisticsController createCheckProductionStatisticsController() {
    throw new IllegalStateException();
  }

  public LoginController createLoginController() {
    throw new IllegalStateException();
  }

  @Override
  public abstract boolean equals(Object o);
}
