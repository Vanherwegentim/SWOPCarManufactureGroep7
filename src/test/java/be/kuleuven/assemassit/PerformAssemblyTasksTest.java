package be.kuleuven.assemassit;

import be.kuleuven.assemassit.Controller.ControllerFactory;
import be.kuleuven.assemassit.Controller.OrderNewCarController;
import be.kuleuven.assemassit.Controller.PerformAssemblyTasksController;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;
import be.kuleuven.assemassit.Domain.CarModel;
import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.GarageHolder;
import be.kuleuven.assemassit.Domain.Repositories.CarModelRepository;
import be.kuleuven.assemassit.Domain.Repositories.GarageHolderRepository;
import be.kuleuven.assemassit.Domain.WorkPost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PerformAssemblyTasksTest {

  private OrderNewCarController orderNewCarController;
  private PerformAssemblyTasksController performAssemblyTasksController;
  private GarageHolderRepository mockedGarageHolderRepository;
  private CarModelRepository mockedCarModelRepository;
  private GarageHolder garageHolder;
  private CarManufactoringCompany carManufactoringCompany;

  @BeforeEach
  public void beforeEach() {
    ControllerFactory controllerFactory = new ControllerFactory();
    garageHolder = new GarageHolder(0, "Ward");

    mockedGarageHolderRepository = mock(GarageHolderRepository.class);
    mockedCarModelRepository = mock(CarModelRepository.class);

    mockGarageHolders();
    mockCarModels();

    carManufactoringCompany = new CarManufactoringCompany(mockedCarModelRepository, LocalTime.of(6, 0), LocalTime.of(22, 0), controllerFactory.getAssemblyLine());
    controllerFactory.loginGarageHolder(garageHolder);
    orderNewCarController = controllerFactory.createOrderNewCarController(carManufactoringCompany, garageHolder);
    controllerFactory.loginCarMechanic();
    performAssemblyTasksController = controllerFactory.createPerformAssemblyTasksController();

    fillTheSystemWithCarOrders();
    //first move
    //carManufactoringCompany.moveAssemblyLine();
  }

  private void mockGarageHolders() {
    when(mockedGarageHolderRepository.getGarageHolders()).thenReturn(Arrays.asList(garageHolder));
  }

  private void mockCarModels() {
    CarModel carModel = new CarModel(0, "Tolkswagen Rolo", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values()), Arrays.asList(Spoiler.values()));
    when(mockedCarModelRepository.getCarModels()).thenReturn(Arrays.asList(carModel));
  }

  private void fillTheSystemWithCarOrders() {
    orderNewCarController.placeCarOrder(0, "BREAK", "WHITE", "STANDARD", "FIVE_SPEED_MANUAL", "VINYL_GREY", "AUTOMATIC", "COMFORT", "NO_SPOILER");
    orderNewCarController.placeCarOrder(0, "BREAK", "BLACK", "PERFORMANCE", "FIVE_SPEED_MANUAL", "LEATHER_BLACK", "AUTOMATIC", "COMFORT", "NO_SPOILER");
    orderNewCarController.placeCarOrder(0, "SEDAN", "RED", "STANDARD", "FIVE_SPEED_AUTOMATIC", "LEATHER_WHITE", "MANUAL", "SPORT", "NO_SPOILER");
  }


  @Test
  public void mainSuccessScenarioTest() {
    // Step 1
    Map<Integer, String> allWorkPosts = performAssemblyTasksController.giveAllWorkPosts();
    assertEquals(allWorkPosts.size(), 3);
    int firstWorkPostId = allWorkPosts.entrySet().stream()
      .filter(entry -> Objects.equals(entry.getValue(), "Car Body Post"))
      .map(Map.Entry::getKey).findFirst().get();

    WorkPost workPost1 = carManufactoringCompany.getAssemblyLine().findWorkPost(firstWorkPostId);
    assertNotNull(workPost1.getCarAssemblyProcess());

    int secondWorkPostId = allWorkPosts.entrySet().stream()
      .filter(entry -> Objects.equals(entry.getValue(), "Drivetrain Post"))
      .map(Map.Entry::getKey).findFirst().get();
    WorkPost workPost2 = carManufactoringCompany.getAssemblyLine().findWorkPost(secondWorkPostId);
    assertNull(workPost2.getCarAssemblyProcess());


    // Step 2 and 3
    List<String> bodyTasks = Arrays.asList("Paint car", "Assembly car body");
    Map<Integer, String> pendingAssemblyTasks = performAssemblyTasksController.givePendingAssemblyTasks(0);
    assertTrue(bodyTasks.containsAll(pendingAssemblyTasks.values()));
    assertEquals(pendingAssemblyTasks.values().size(), bodyTasks.size());

    // Step 4
    int id = performAssemblyTasksController.givePendingAssemblyTasks(0).keySet().stream().findFirst().get();
    performAssemblyTasksController.setActiveTask(0, id);

    // Step 5
    List<String> actions = Arrays.asList("Installing the BREAK body");
    List<String> actionsAlternative = Arrays.asList("Painting the car WHITE");

    assertTrue(performAssemblyTasksController.giveAssemblyTaskActions(0, id).stream().allMatch(actions::contains) || performAssemblyTasksController.giveAssemblyTaskActions(0, id).stream().allMatch(actionsAlternative::contains));
    assertEquals(performAssemblyTasksController.giveAssemblyTaskActions(0, id).size(), actions.size());

    // Step 6
    performAssemblyTasksController.completeAssemblyTaskAndMoveIfPossible(0, 35);

    // Step 7
    assertEquals(performAssemblyTasksController.givePendingAssemblyTasks(0).size(), 1);
  }

  @Test
  public void mainSuccessScenarioTest_WithAutoMoveAssemblyLine() {

    //task1: Step 2 and 3
    List<String> bodyTasks = Arrays.asList("Paint car", "Assembly car body");
    Map<Integer, String> pendingAssemblyTasks = performAssemblyTasksController.givePendingAssemblyTasks(0);
    assertTrue(bodyTasks.containsAll(pendingAssemblyTasks.values()));
    assertEquals(pendingAssemblyTasks.values().size(), bodyTasks.size());

    //task1: Step 4
    int taskId1 = performAssemblyTasksController.givePendingAssemblyTasks(0).keySet().stream().findFirst().get();
    performAssemblyTasksController.setActiveTask(0, taskId1);

    //task1: Step 5
    List<String> actions = Arrays.asList("Installing the BREAK body");
    List<String> actionsAlternative = Arrays.asList("Painting the car WHITE");

    assertTrue(actions.containsAll(performAssemblyTasksController.giveAssemblyTaskActions(0, taskId1)) || actionsAlternative.containsAll(performAssemblyTasksController.giveAssemblyTaskActions(0, taskId1)));
    assertEquals(performAssemblyTasksController.giveAssemblyTaskActions(0, taskId1).size(), actions.size());

    //task1: Step 6
    performAssemblyTasksController.completeAssemblyTaskAndMoveIfPossible(0, 35);

    //task1: Step 7
    assertFalse(performAssemblyTasksController.givePendingAssemblyTasks(0).containsValue("Installing the BREAK body"));

    //task2: Step 4
    int taskId2 = performAssemblyTasksController.givePendingAssemblyTasks(0).keySet().stream().findFirst().get();
    performAssemblyTasksController.setActiveTask(0, taskId2);

    //task2: Step 5
    List<String> task2actions = performAssemblyTasksController.giveAssemblyTaskActions(0, taskId2);
    assertEquals(task2actions, actionsAlternative);
    assertEquals(task2actions.size(), actionsAlternative.size());

    //task2: Step 6
    performAssemblyTasksController.completeAssemblyTaskAndMoveIfPossible(0, 35);

    //task2: Step 7
    Map<Integer, String> allWorkPosts = performAssemblyTasksController.giveAllWorkPosts();
    assertEquals(allWorkPosts.size(), 3);
    int firstWorkPostId = allWorkPosts.entrySet().stream()
      .filter(entry -> Objects.equals(entry.getValue(), "Car Body Post"))
      .map(Map.Entry::getKey).findFirst().get();

    WorkPost workPost1 = carManufactoringCompany.getAssemblyLine().findWorkPost(firstWorkPostId);
    assertNotNull(workPost1.getCarAssemblyProcess());

    int secondWorkPostId = allWorkPosts.entrySet().stream()
      .filter(entry -> Objects.equals(entry.getValue(), "Drivetrain Post"))
      .map(Map.Entry::getKey).findFirst().get();
    WorkPost workPost2 = carManufactoringCompany.getAssemblyLine().findWorkPost(secondWorkPostId);
    assertNotNull(workPost2.getCarAssemblyProcess());


  }

  @Test
  public void mainSuccessScenario_throwsIllegalArgumentException() {
    // Step 1
    performAssemblyTasksController.giveAllWorkPosts();

    // Step 2 and 3
    assertThrows(IllegalArgumentException.class, () -> performAssemblyTasksController.givePendingAssemblyTasks(-1));

    // Step 4
    int id = performAssemblyTasksController.givePendingAssemblyTasks(0).keySet().stream().findFirst().get();

    assertThrows(IllegalArgumentException.class, () -> performAssemblyTasksController.setActiveTask(-1, id));
    assertThrows(IllegalArgumentException.class, () -> performAssemblyTasksController.setActiveTask(0, -1));
    performAssemblyTasksController.setActiveTask(0, id);

    // Step 5
    assertThrows(IllegalArgumentException.class, () -> performAssemblyTasksController.giveAssemblyTaskActions(-1, id));
    assertThrows(IllegalArgumentException.class, () -> performAssemblyTasksController.giveAssemblyTaskActions(0, -1));

    // Step 6
    assertThrows(IllegalArgumentException.class, () -> performAssemblyTasksController.completeAssemblyTaskAndMoveIfPossible(-1, 15));
    assertThrows(IllegalArgumentException.class, () -> performAssemblyTasksController.completeAssemblyTaskAndMoveIfPossible(1, -15));

    // Step 7
    assertThrows(IllegalArgumentException.class, () -> performAssemblyTasksController.givePendingAssemblyTasks(-1));
  }
}
