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
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PerformAssemblyTasksTest {

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
    controllerFactory.loginGarageHolder(garageHolder);
    orderNewCarController = controllerFactory.createOrderNewCarController(carManufactoringCompany, garageHolder);
    controllerFactory.loginCarMechanic();
    assemblyLineController = controllerFactory.createAssemblyLineController(assemblyLine);


    fillTheSystemWithTasks();
    moveTheAssemblyLine();
  }

  private void mockGarageHolders() {
    when(mockedGarageHolderRepository.getGarageHolders()).thenReturn(Arrays.asList(garageHolder));
  }

  private void mockCarModels() {
    carModel = new CarModel(0, "Tolkswagen Rolo", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values()), Arrays.asList(Spoiler.values()));
    when(mockedCarModelRepository.getCarModels()).thenReturn(Arrays.asList(carModel));
  }

  private void fillTheSystemWithTasks() {
    orderNewCarController.placeCarOrder(0, "BREAK", "BLACK", "PERFORMANCE", "FIVE_SPEED_MANUAL", "LEATHER_BLACK", "AUTOMATIC", "COMFORT", "NO_SPOILER");
    orderNewCarController.placeCarOrder(0, "SEDAN", "RED", "STANDARD", "FIVE_SPEED_AUTOMATIC", "LEATHER_WHITE", "MANUAL", "SPORT", "NO_SPOILER");
    orderNewCarController.placeCarOrder(0, "BREAK", "WHITE", "STANDARD", "FIVE_SPEED_MANUAL", "VINYL_GREY", "AUTOMATIC", "COMFORT", "NO_SPOILER");
  }

  private void moveTheAssemblyLine() {
    Map<Integer, String> workPosts = assemblyLineController.giveAllWorkPosts();

    assemblyLineController.moveAssemblyLine(0);

    for (int i = 0; i < workPosts.size() - 1; i++) {
      for (int id : workPosts.keySet()) {
        Map<Integer, String> tasks = assemblyLineController.givePendingAssemblyTasks(id);

        for (int taskId : tasks.keySet()) {
          assemblyLineController.setActiveTask(id, taskId);
          assemblyLineController.completeAssemblyTask(id);
        }
      }
      assemblyLineController.moveAssemblyLine(60);
    }
  }

  @Test
  public void mainSuccessScenarioTest() {
    // Step 1
    assemblyLineController.giveAllWorkPosts();

    // Step 2 and 3
    List<String> bodyTasks = Arrays.asList("Paint car", "Assembly car body");
    Map<Integer, String> pendingAssemblyTasks = assemblyLineController.givePendingAssemblyTasks(0);
    assertTrue(bodyTasks.containsAll(pendingAssemblyTasks.values()));
    assertEquals(pendingAssemblyTasks.values().size(), bodyTasks.size());

    // Step 4

    int id = assemblyLineController.givePendingAssemblyTasks(0).keySet().stream().findFirst().get();
    assemblyLineController.setActiveTask(0, id);

    // Step 5
    List<String> actions = Arrays.asList("Installing the BREAK body");
    List<String> actionsAlternative = Arrays.asList("Painting the car WHITE");

    assertTrue(
      assemblyLineController.giveAssemblyTaskActions(0, id).stream().allMatch(actions::contains) ||
        assemblyLineController.giveAssemblyTaskActions(0, id).stream().allMatch(actionsAlternative::contains)
    );
    assertTrue(assemblyLineController.giveAssemblyTaskActions(0, id).size() == actions.size());

    // Step 6
    assemblyLineController.completeAssemblyTask(0);

    // Step 7
    assertFalse(assemblyLineController.givePendingAssemblyTasks(0).containsValue("Installing the BREAK body"));
  }

  @Test
  public void mainSuccessScenario_throwsIllegalArgumentException() {
    // Step 1
    assemblyLineController.giveAllWorkPosts();

    // Step 2 and 3
    assertThrows(IllegalArgumentException.class, () -> assemblyLineController.givePendingAssemblyTasks(-1));

    // Step 4
    int id = assemblyLineController.givePendingAssemblyTasks(0).keySet().stream().findFirst().get();

    assertThrows(IllegalArgumentException.class, () -> assemblyLineController.setActiveTask(-1, id));
    assertThrows(IllegalArgumentException.class, () -> assemblyLineController.setActiveTask(0, -1));
    assemblyLineController.setActiveTask(0, id);

    // Step 5
    assertThrows(IllegalArgumentException.class, () -> assemblyLineController.giveAssemblyTaskActions(-1, id));
    assertThrows(IllegalArgumentException.class, () -> assemblyLineController.giveAssemblyTaskActions(0, -1));

    // Step 6
    assertThrows(IllegalArgumentException.class, () -> assemblyLineController.completeAssemblyTask(-1));

    // Step 7
    assertThrows(IllegalArgumentException.class, () -> assemblyLineController.givePendingAssemblyTasks(-1));
  }

  @Test
  public void mainSuccessScenario_throwsIllegalStateException() {
    assertThrows(IllegalStateException.class, () -> assemblyLineController.completeAssemblyTask(0));
  }
}
