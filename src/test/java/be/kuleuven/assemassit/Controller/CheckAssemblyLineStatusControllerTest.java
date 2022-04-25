package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Enums.WorkPostType;
import be.kuleuven.assemassit.Domain.TaskTypes.CarBodyAssemblyTask;
import be.kuleuven.assemassit.Domain.TaskTypes.InsertEngineAssemblyTask;
import be.kuleuven.assemassit.Domain.TaskTypes.InsertGearboxAssemblyTask;
import be.kuleuven.assemassit.Domain.WorkPost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckAssemblyLineStatusControllerTest {
  private CheckAssemblyLineStatusController controller;
  private AssemblyLine mockedAssemblyLine;
  private WorkPost mockedDrivetrainPost;
  private AssemblyTask mockedCarBodyAssemblyTask;
  private AssemblyTask mockedInsertEngineAssemblyTask;
  private AssemblyTask mockedDrivetrainAssemblyTask;
  private AssemblyTask mockedAccessoriesAssemblyTask;


  @BeforeEach
  public void beforeEach() {
    mockedAssemblyLine = mock(AssemblyLine.class);
    WorkPost mockedCarBodyPost = mock(WorkPost.class);
    mockedDrivetrainPost = mock(WorkPost.class);
    WorkPost mockedAccessoriesPost = mock(WorkPost.class);
    mockedCarBodyAssemblyTask = mock(CarBodyAssemblyTask.class);
    mockedInsertEngineAssemblyTask = mock(InsertEngineAssemblyTask.class);
    mockedDrivetrainAssemblyTask = mock(InsertGearboxAssemblyTask.class);
    mockedAccessoriesAssemblyTask = mock(CarBodyAssemblyTask.class);


    when(mockedAssemblyLine.getAccessoriesPost()).thenReturn(mockedAccessoriesPost);
    when(mockedAssemblyLine.getDrivetrainPost()).thenReturn(mockedDrivetrainPost);
    when(mockedAssemblyLine.getCarBodyPost()).thenReturn(mockedCarBodyPost);
    when(mockedAssemblyLine.getWorkPosts()).thenReturn(Arrays.asList(mockedCarBodyPost, mockedDrivetrainPost, mockedAccessoriesPost));
    when(mockedAssemblyLine.giveCarAssemblyTask(0, 0)).thenReturn(mockedCarBodyAssemblyTask);

    when(mockedCarBodyPost.getWorkPostType()).thenReturn(WorkPostType.CAR_BODY_POST);
    when(mockedCarBodyPost.getId()).thenReturn(0);

    when(mockedDrivetrainPost.getWorkPostType()).thenReturn(WorkPostType.DRIVETRAIN_POST);
    when(mockedDrivetrainPost.getId()).thenReturn(1);

    when(mockedAccessoriesPost.getWorkPostType()).thenReturn(WorkPostType.ACCESSORIES_POST);
    when(mockedAccessoriesPost.getId()).thenReturn(2);

    when(mockedCarBodyAssemblyTask.getName()).thenReturn("mockedCarBodyAssemblyTaskName");
    when(mockedCarBodyAssemblyTask.getActions()).thenReturn(Arrays.asList("Installing the sead body"));

    when(mockedInsertEngineAssemblyTask.getName()).thenReturn("mockedEngineAssemblyTaskName");
    when(mockedInsertEngineAssemblyTask.getPending()).thenReturn(true);

    when(mockedDrivetrainAssemblyTask.getName()).thenReturn("mockedDrivetrainAssemblyTaskName");

    when(mockedAccessoriesAssemblyTask.getName()).thenReturn("mockedAccessoriesAssemblyTaskName");


    controller = new CheckAssemblyLineStatusController(mockedAssemblyLine);
  }

  @Test
  public void CheckAssemblyLineStatusControllerConstructor_throws() {
    assertThrows(IllegalArgumentException.class, () -> controller = new CheckAssemblyLineStatusController(null));

  }

  @Test
  public void giveAllWorkPostsTest() {
    String expected = "0: Car Body Post" + System.lineSeparator() + "1: Drivetrain Post" + System.lineSeparator() + "2: Accessories Post" + System.lineSeparator();

    Map<Integer, String> allWorkPosts = controller.giveAllWorkPosts();
    StringBuilder actual = new StringBuilder();
    for (int key : allWorkPosts.keySet()) {
      actual.append(key).append(": ").append(allWorkPosts.get(key)).append(System.lineSeparator());
    }
    assertEquals(expected, actual.toString());
  }

  @Test
  public void givePendingAssemblyTasksTest_succeeds() {
    AssemblyTask mockedAssemblyTask = mock(CarBodyAssemblyTask.class);

    when(mockedAssemblyTask.getId()).thenReturn(0);
    when(mockedAssemblyTask.getName()).thenReturn("mockedAssemblyTaskName");

    when(mockedAssemblyLine.givePendingAssemblyTasksFromWorkPost(0)).thenReturn(Arrays.asList(mockedAssemblyTask));

    Map<Integer, String> actual = controller.givePendingAssemblyTasks(0);
    assertTrue(actual.size() == 1 && actual.containsKey(0));
    assertEquals("mockedAssemblyTaskName", actual.get(0));
  }

  @Test
  public void givePendingAssemblyTaskTest_throws() {
    assertThrows(IllegalArgumentException.class, () -> controller.givePendingAssemblyTasks(-1));

    when(mockedAssemblyLine.givePendingAssemblyTasksFromWorkPost(0)).thenThrow(IllegalArgumentException.class);
    assertThrows(IllegalArgumentException.class, () -> controller.givePendingAssemblyTasks(0));
  }

  @Test
  public void giveFinishedAssemblyTaskTest_throws() {
    assertThrows(IllegalArgumentException.class, () -> controller.giveFinishedAssemblyTasks(-1));

    when(mockedAssemblyLine.giveFinishedAssemblyTasksFromWorkPost(0)).thenThrow(IllegalArgumentException.class);
    assertThrows(IllegalArgumentException.class, () -> controller.giveFinishedAssemblyTasks(0));
  }

  @Test
  public void giveAssemblyLineStatusAndOverviewTest() {
    HashMap<String, AssemblyTask> mockedWorkPostStatuses = new LinkedHashMap<>();

    mockedWorkPostStatuses.put("Car Body Post", mockedCarBodyAssemblyTask);
    mockedWorkPostStatuses.put("Drivetrain Post", mockedDrivetrainAssemblyTask);
    mockedWorkPostStatuses.put("Accessories Post", mockedAccessoriesAssemblyTask);

    when(mockedAssemblyLine.giveActiveTasksOverview()).thenReturn(mockedWorkPostStatuses);

    HashMap<String, List<AssemblyTask>> mockedTasksOverview = new LinkedHashMap<>();

    mockedTasksOverview.put("Car Body Post", Arrays.asList(mockedCarBodyAssemblyTask, mockedInsertEngineAssemblyTask, mockedDrivetrainAssemblyTask, mockedAccessoriesAssemblyTask));
    mockedTasksOverview.put("Drivetrain Post", Arrays.asList(mockedDrivetrainAssemblyTask, mockedAccessoriesAssemblyTask));
    mockedTasksOverview.put("Accessories Post", Arrays.asList(mockedAccessoriesAssemblyTask));

    when(mockedAssemblyLine.giveTasksOverview()).thenReturn(mockedTasksOverview);

    String expected = "Car Body Post:" + System.getProperty("line.separator") +
      " mockedCarBodyAssemblyTaskName (active)" + System.getProperty("line.separator") +
      " mockedEngineAssemblyTaskName (pending)" + System.getProperty("line.separator") +
      " mockedDrivetrainAssemblyTaskName" + System.getProperty("line.separator") +
      " mockedAccessoriesAssemblyTaskName" + System.getProperty("line.separator") +
      "Drivetrain Post:" + System.getProperty("line.separator") +
      " mockedDrivetrainAssemblyTaskName (active)" + System.getProperty("line.separator") +
      " mockedAccessoriesAssemblyTaskName" + System.getProperty("line.separator") +
      "Accessories Post:" + System.getProperty("line.separator") +
      " mockedAccessoriesAssemblyTaskName (active)" + System.getProperty("line.separator");

    Map<String, List<String>> assemblyLineStatusAndOverview = controller.giveAssemblyLineStatusOverview();
    String actual = "";
    for (String key : assemblyLineStatusAndOverview.keySet()) {
      actual += String.format("%s:%n", key);
      for (String task : assemblyLineStatusAndOverview.get(key)) {
        actual += String.format(" %s%n", task);
      }
    }

    assertEquals(expected, actual);
  }

  @Test
  public void giveFutureAssemblyLineStatusOverviewTest() {
    HashMap<String, AssemblyTask> mockedWorkPostStatuses = new LinkedHashMap<>();

    mockedWorkPostStatuses.put("Car Body Post", mockedCarBodyAssemblyTask);
    mockedWorkPostStatuses.put("Drivetrain Post", mockedDrivetrainAssemblyTask);
    mockedWorkPostStatuses.put("Accessories Post", mockedAccessoriesAssemblyTask);

    when(mockedAssemblyLine.giveActiveTasksOverview()).thenReturn(mockedWorkPostStatuses);

    HashMap<String, List<AssemblyTask>> mockedTasksOverview = new LinkedHashMap<>();

    mockedTasksOverview.put("Car Body Post", Arrays.asList(mockedCarBodyAssemblyTask, mockedInsertEngineAssemblyTask, mockedDrivetrainAssemblyTask, mockedAccessoriesAssemblyTask));
    mockedTasksOverview.put("Drivetrain Post", Arrays.asList(mockedDrivetrainAssemblyTask, mockedAccessoriesAssemblyTask));
    mockedTasksOverview.put("Accessories Post", Arrays.asList(mockedAccessoriesAssemblyTask));

    when(mockedAssemblyLine.giveFutureTasksOverview()).thenReturn(mockedTasksOverview);

    HashMap<String, List<String>> futureAssemblyLineStatusOverview = controller.giveFutureAssemblyLineStatusOverview();

    String actual = "";
    for (String key : futureAssemblyLineStatusOverview.keySet()) {
      actual += String.format("%s:%n", key);
      for (String task : futureAssemblyLineStatusOverview.get(key)) {
        actual += String.format(" %s%n", task);
      }
    }
  }
}
