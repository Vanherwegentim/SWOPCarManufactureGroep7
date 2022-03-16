package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Enums.Airco;
import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.Body;
import be.kuleuven.assemassit.Domain.Enums.Engine;
import be.kuleuven.assemassit.Domain.TaskTypes.CarBodyAssemblyTask;
import be.kuleuven.assemassit.Domain.TaskTypes.InsertEngineAssemblyTask;
import be.kuleuven.assemassit.Domain.TaskTypes.InsertGearboxAssemblyTask;
import be.kuleuven.assemassit.Domain.TaskTypes.InstallAircoAssemblyTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AssemblyLineControllerTest {

  private AssemblyLine mockedAssemblyLine;
  private AssemblyLineController assemblyLineController;

  @BeforeEach
  public void beforeEach() {
    mockedAssemblyLine = mock(AssemblyLine.class);
    assemblyLineController = new AssemblyLineController(mockedAssemblyLine);
  }

  @Test
  public void givePendingAssemblyTasksTest_succeeds() {
    AssemblyTask mockedAssemblyTask = mock(CarBodyAssemblyTask.class);

    when(mockedAssemblyTask.getId()).thenReturn(0);
    when(mockedAssemblyTask.getName()).thenReturn("mockedAssemblyTaskName");

    when(mockedAssemblyLine.givePendingAssemblyTasksFromWorkPost(0)).thenReturn(Arrays.asList(mockedAssemblyTask));

    Map<Integer, String> actual = assemblyLineController.givePendingAssemblyTasks(0);
    assertTrue(actual.size() == 1 && actual.containsKey(0));
    assertEquals("mockedAssemblyTaskName", actual.get(0));
  }

  @Test
  public void givePendingAssemblyTaskTest_throws() {
    assertThrows(IllegalArgumentException.class, () -> assemblyLineController.givePendingAssemblyTasks(-1));

    when(mockedAssemblyLine.givePendingAssemblyTasksFromWorkPost(0)).thenThrow(NullPointerException.class);
    assertThrows(IllegalArgumentException.class, () -> assemblyLineController.givePendingAssemblyTasks(0));
  }

  @Test
  public void giveAssemblyLineStatusAndOverviewTest() {
    Map<String, AssemblyTask> mockedWorkPostStatuses = new HashMap<>();

    AssemblyTask mockedCarBodyAssemblyTask = mock(CarBodyAssemblyTask.class);
    when(mockedCarBodyAssemblyTask.getName()).thenReturn("mockedCarBodyAssemblyTaskName");

    AssemblyTask mockedInsertEngineAssemblyTask = mock(InsertEngineAssemblyTask.class);
    when(mockedInsertEngineAssemblyTask.getName()).thenReturn("mockedEngineAssemblyTaskName");
    when(mockedInsertEngineAssemblyTask.getPending()).thenReturn(true);

    AssemblyTask mockedDrivetrainAssemblyTask = mock(InsertGearboxAssemblyTask.class);
    when(mockedDrivetrainAssemblyTask.getName()).thenReturn("mockedDrivetrainAssemblyTaskName");

    AssemblyTask mockedAccessoriesAssemblyTask = mock(CarBodyAssemblyTask.class);
    when(mockedAccessoriesAssemblyTask.getName()).thenReturn("mockedAccessoriesAssemblyTaskName");

    mockedWorkPostStatuses.put("Car Body Post", mockedCarBodyAssemblyTask);
    mockedWorkPostStatuses.put("Drivetrain Post", mockedDrivetrainAssemblyTask);
    mockedWorkPostStatuses.put("Accessories Post", mockedAccessoriesAssemblyTask);

    when(mockedAssemblyLine.giveStatus()).thenReturn(mockedWorkPostStatuses);

    Map<String, List<AssemblyTask>> mockedTasksOverview = new HashMap<>();

    mockedTasksOverview.put("Car Body Post", Arrays.asList(mockedCarBodyAssemblyTask, mockedInsertEngineAssemblyTask, mockedDrivetrainAssemblyTask, mockedAccessoriesAssemblyTask));
    mockedTasksOverview.put("Drivetrain Post", Arrays.asList(mockedDrivetrainAssemblyTask, mockedAccessoriesAssemblyTask));
    mockedTasksOverview.put("Accessories Post", Arrays.asList(mockedAccessoriesAssemblyTask));

    when(mockedAssemblyLine.giveTasksOverview()).thenReturn(mockedTasksOverview);

    String expected = """
      Drivetrain Post:\r
       mockedDrivetrainAssemblyTaskName (active)\r
       mockedAccessoriesAssemblyTaskName\r
      Car Body Post:\r
       mockedCarBodyAssemblyTaskName (active)\r
       mockedEngineAssemblyTaskName (pending)\r
       mockedDrivetrainAssemblyTaskName\r
       mockedAccessoriesAssemblyTaskName\r
      Accessories Post:\r
       mockedAccessoriesAssemblyTaskName (active)\r
      """;

    Map<String, List<String>> assemblyLineStatusAndOverview = assemblyLineController.giveAssemblyLineStatusAndOverview(0);
    String actual = "";
    for (String key : assemblyLineStatusAndOverview.keySet()) {
      actual += String.format("%s:%n", key);
      for (String task : assemblyLineStatusAndOverview.get(key)) {
        actual += String.format(" %s%n", task);
      }
    }

    assertEquals(expected, actual);
  }
}
