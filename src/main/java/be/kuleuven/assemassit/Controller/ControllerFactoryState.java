package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;
import be.kuleuven.assemassit.Domain.Repositories.GarageHolderRepository;

public abstract class ControllerFactoryState {
  public AdaptSchedulingAlgorithmController createAdaptSchedulingAlgorithmController(AssemblyLine assemblyLine) {
    throw new IllegalStateException();
  }

  public AssemblyLineController createAssemblyLineController(AssemblyLine assemblyLine) {
    throw new IllegalStateException();
  }

  public CheckAssemblyLineStatusController createCheckAssemblyLineStatusController(AssemblyLine assemblyLine) {
    throw new IllegalStateException();
  }

  public CheckOrderDetailsController createCheckOrderDetailsController() {
    throw new IllegalStateException();
  }

  public OrderNewCarController createOrderNewCarController(CarManufactoringCompany carManufactoringCompany, GarageHolderRepository garageHolderRepository) {
    throw new IllegalStateException();
  }

  public PerformAssemblyTasksController createPerformAssemblyTasksController(AssemblyLine assemblyLine, CarManufactoringCompany carManufactoringCompany) {
    throw new IllegalStateException();
  }

  public CheckProductionStatisticsController createCheckProductionStatisticsController(AssemblyLine assemblyLine) {
    throw new IllegalStateException();
  }
}
