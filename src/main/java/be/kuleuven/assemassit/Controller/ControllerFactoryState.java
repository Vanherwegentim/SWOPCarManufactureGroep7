package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;
import be.kuleuven.assemassit.Domain.GarageHolder;
import be.kuleuven.assemassit.Domain.Repositories.GarageHolderRepository;

public abstract class ControllerFactoryState {
  public AdaptSchedulingAlgorithmController createAdaptSchedulingAlgorithmController(AssemblyLine assemblyLine) {
    throw new IllegalStateException();
  }

  //todo remove
//  public AssemblyLineController createAssemblyLineController(AssemblyLine assemblyLine) {
//    throw new IllegalStateException();
//  }

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

  public LoginController createLoginController(GarageHolderRepository garageHolderRepository, ControllerFactory controllerFactory) {
    throw new IllegalStateException();
  }

  @Override
  public abstract boolean equals(Object o);
}
