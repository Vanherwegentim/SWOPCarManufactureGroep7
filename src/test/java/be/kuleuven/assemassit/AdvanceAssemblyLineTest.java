package be.kuleuven.assemassit;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.ControllerFactory;
import be.kuleuven.assemassit.Controller.OrderNewCarController;
import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;
import be.kuleuven.assemassit.Domain.CarModel;
import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.GarageHolder;
import be.kuleuven.assemassit.Domain.Repositories.CarModelRepository;
import be.kuleuven.assemassit.Domain.Repositories.GarageHolderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class AdvanceAssemblyLineTest {


  private AssemblyLineController assemblyLineController;
  private OrderNewCarController orderNewCarController;
  private GarageHolderRepository mockedGarageHolderRepository;
  private CarModelRepository mockedCarModelRepository;
  private CarManufactoringCompany carManufactoringCompany;
  private CarModel carModel;
  private GarageHolder garageHolder;

  @BeforeEach
  public void beforeEach() {
    ControllerFactory controllerFactory = new ControllerFactory();
    AssemblyLine assemblyLine = new AssemblyLine();
    garageHolder = new GarageHolder(0, "John");

    mockedGarageHolderRepository = mock(GarageHolderRepository.class);
    mockedCarModelRepository = mock(CarModelRepository.class);

    mockGarageHolders();
    mockCarModels();

    carManufactoringCompany = new CarManufactoringCompany(mockedCarModelRepository, LocalTime.of(6, 0), LocalTime.of(22, 0), assemblyLine);
    orderNewCarController = controllerFactory.createOrderNewCarController(carManufactoringCompany, mockedGarageHolderRepository);
    assemblyLineController = controllerFactory.createAssemblyLineController(assemblyLine);

    orderNewCarController.logInGarageHolder(garageHolder.getId());

    fillTheSystemWithTasks();
  }

  private void mockGarageHolders() {
    when(mockedGarageHolderRepository.getGarageHolders()).thenReturn(Arrays.asList(garageHolder));
  }

  private void mockCarModels() {
    carModel = new CarModel(0, "Tolkswagen Rolo", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values()));
    when(mockedCarModelRepository.getCarModels()).thenReturn(Arrays.asList(carModel));
  }

  private void fillTheSystemWithTasks() {
    orderNewCarController.placeCarOrder(0, "BREAK", "BLACK", "PERFORMANCE", "MANUAL", "LEATHER_BLACK", "AUTOMATIC", "COMFORT");
    orderNewCarController.placeCarOrder(0, "SEAD", "RED", "STANDARD", "AUTOMATIC", "LEATHER_WHITE", "MANUAL", "SPORT");
    orderNewCarController.placeCarOrder(0, "BREAK", "WHITE", "STANDARD", "MANUAL", "VINYL_GREY", "AUTOMATIC", "COMFORT");
  }

  private void moveTheAssemblyLine() {
    Map<Integer, String> workPosts = assemblyLineController.giveAllWorkPosts();

    assemblyLineController.moveAssemblyLine(0);
  }

  @Test
  public void advanceAssemblyLineIntegrationTest_MainScenario_Success() {
    //MAIN SUCCESS SCENARIO

    // Step 1

    // executed in UI tests

    // Step 2
    HashMap<String, List<String>> assemblyLineStatusOverview = assemblyLineController.giveAssemblyLineStatusOverview();
    assertTrue(assemblyLineStatusOverview.containsKey("Car Body Post"));
    assertTrue(assemblyLineStatusOverview.containsKey("Drivetrain Post"));
    assertTrue(assemblyLineStatusOverview.containsKey("Accessories Post"));

    assertEquals(assemblyLineStatusOverview.get("Car Body Post").size(), 0);
    assertEquals(assemblyLineStatusOverview.get("Drivetrain Post").size(), 0);
    assertEquals(assemblyLineStatusOverview.get("Accessories Post").size(), 0);

    HashMap<String, List<String>> futureAssemblyLineStatusOverview = assemblyLineController.giveFutureAssemblyLineStatusOverview();
    assertTrue(futureAssemblyLineStatusOverview.containsKey("Car Body Post"));
    assertTrue(futureAssemblyLineStatusOverview.containsKey("Drivetrain Post"));
    assertTrue(futureAssemblyLineStatusOverview.containsKey("Accessories Post"));

    assertEquals(futureAssemblyLineStatusOverview.get("Car Body Post").size(), 2);
    assertEquals(futureAssemblyLineStatusOverview.get("Drivetrain Post").size(), 0);
    assertEquals(futureAssemblyLineStatusOverview.get("Accessories Post").size(), 0);

    // Step 3

    // executed in UI tests

    // Step 4

    List<String> blockingWorkPosts = assemblyLineController.moveAssemblyLine(60);
    assertEquals(0, blockingWorkPosts.size());

    // Step 5

    HashMap<String, List<String>> newAssemblyLineStatusOverview = assemblyLineController.giveAssemblyLineStatusOverview();
    assertTrue(newAssemblyLineStatusOverview.containsKey("Car Body Post"));
    assertTrue(newAssemblyLineStatusOverview.containsKey("Drivetrain Post"));
    assertTrue(newAssemblyLineStatusOverview.containsKey("Accessories Post"));

    assertEquals(newAssemblyLineStatusOverview.get("Car Body Post").size(), 2);
    assertEquals(newAssemblyLineStatusOverview.get("Drivetrain Post").size(), 0);
    assertEquals(newAssemblyLineStatusOverview.get("Accessories Post").size(), 0);
  }

  @Test
  public void advanceAssemblyLineIntegrationTest_MainScenario_IllegalArgumentException() {
    assertThrows(IllegalArgumentException.class, () ->
      assemblyLineController.moveAssemblyLine(-60));
  }

  @Test
  public void advanceAssemblyLineIntegrationTest_AlternateFlow() {
    moveTheAssemblyLine();

    // Step 4 (a)

    List<String> blockingWorkPosts = assemblyLineController.moveAssemblyLine(60);
    assertEquals(blockingWorkPosts.get(0), "CAR_BODY_POST");
  }

}
