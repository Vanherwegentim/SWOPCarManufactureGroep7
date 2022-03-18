package be.kuleuven.assemassit.Integration;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.ControllerFactory;
import be.kuleuven.assemassit.Controller.OrderController;
import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;
import be.kuleuven.assemassit.Domain.CarModel;
import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.GarageHolder;
import be.kuleuven.assemassit.Domain.Repositories.CarModelRepository;
import be.kuleuven.assemassit.Domain.Repositories.GarageHolderRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalTime;
import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PerformAssemblyTasksTest {

  private AssemblyLineController assemblyLineController;
  private OrderController orderController;
  private GarageHolderRepository mockedGarageHolderRepository;
  private CarModelRepository mockedCarModelRepository;
  private CarModelRepository carModelRepository;
  private CarManufactoringCompany carManufactoringCompany;

  @BeforeEach
  public void beforeEach() {
    ControllerFactory controllerFactory = new ControllerFactory();
    AssemblyLine assemblyLine = new AssemblyLine();

    mockedGarageHolderRepository = mock(GarageHolderRepository.class);
    mockedCarModelRepository = mock(CarModelRepository.class);

    CarManufactoringCompany carManufactoringCompany = new CarManufactoringCompany(mockedCarModelRepository, LocalTime.of(6, 0), LocalTime.of(22, 0), assemblyLine);
    assemblyLineController = controllerFactory.createAssemblyLineController();
    orderController = controllerFactory.createOrderController(carManufactoringCompany, mockedGarageHolderRepository);
    assemblyLineController = controllerFactory.createAssemblyLineController();

    fillTheSystemWithGarageHolders();
    fillTheSystemWithTasks();
  }

  private void fillTheSystemWithGarageHolders() {
    GarageHolder garageHolder = new GarageHolder(0, "John");
    when(mockedGarageHolderRepository.getGarageHolders()).thenReturn(Arrays.asList(garageHolder));
    orderController.logInGarageHolder(garageHolder.getId());
  }

  private void fillTheSystemWithCarModels() {
    CarModel carModel = new CarModel(0, "Tolkswagen Rolo", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values()));
    when(mockedCarModelRepository.getCarModels()).thenReturn(Arrays.asList(carModel));
  }

  private void fillTheSystemWithTasks() {

  }

  @Test
  public void idealFlow() {
    // step 1

  }
}
