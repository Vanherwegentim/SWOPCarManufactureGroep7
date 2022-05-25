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
  
  @Override
  public abstract boolean equals(Object o);
}
