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

public class AssemblyLineControllerTest {

  private AssemblyLineController assemblyLineController;

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

    assemblyLineController = new AssemblyLineController(mockedAssemblyLine);
  }

  @Test
  public void giveAllWorkPostsTest() {
    String expected = """
      0: CAR_BODY_POST
      1: DRIVETRAIN_POST
      2: ACCESSORIES_POST
      """;

    Map<Integer, String> allWorkPosts = assemblyLineController.giveAllWorkPosts();
    String actual = "";
    for (int key : allWorkPosts.keySet()) {
      actual += key + ": " + allWorkPosts.get(key) + "\n";
    }
    assertEquals(expected, actual);
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

    when(mockedAssemblyLine.givePendingAssemblyTasksFromWorkPost(0)).thenThrow(IllegalArgumentException.class);
    assertThrows(IllegalArgumentException.class, () -> assemblyLineController.givePendingAssemblyTasks(0));
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

    String expected = """
      Car Body Post:\r
       mockedCarBodyAssemblyTaskName (active)\r
       mockedEngineAssemblyTaskName (pending)\r
       mockedDrivetrainAssemblyTaskName\r
       mockedAccessoriesAssemblyTaskName\r
      Drivetrain Post:\r
       mockedDrivetrainAssemblyTaskName (active)\r
       mockedAccessoriesAssemblyTaskName\r
      Accessories Post:\r
       mockedAccessoriesAssemblyTaskName (active)\r
      """;

    Map<String, List<String>> assemblyLineStatusAndOverview = assemblyLineController.giveAssemblyLineStatusOverview();
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

    HashMap<String, List<String>> futureAssemblyLineStatusOverview = assemblyLineController.giveFutureAssemblyLineStatusOverview();

    String actual = "";
    for (String key : futureAssemblyLineStatusOverview.keySet()) {
      actual += String.format("%s:%n", key);
      for (String task : futureAssemblyLineStatusOverview.get(key)) {
        actual += String.format(" %s%n", task);
      }
    }
  }

  @Test
  public void moveAssemblyLineTest_canMove_succeeds() {
    when(mockedAssemblyLine.canMove()).thenReturn(true);
    assertArrayEquals(new String[]{}, assemblyLineController.moveAssemblyLine(1).toArray());
  }

  @Test
  public void moveAssemblyLineTest_cannotMove_succeeds() {
    when(mockedAssemblyLine.canMove()).thenReturn(false);
    when(mockedDrivetrainPost.givePendingAssemblyTasks()).thenReturn(Arrays.asList(mockedDrivetrainAssemblyTask));

    assertArrayEquals(new String[]{WorkPostType.DRIVETRAIN_POST.toString()}, assemblyLineController.moveAssemblyLine(1).toArray());
  }

  @Test
  public void moveAssemblyLineTest_throws() {
    assertThrows(IllegalArgumentException.class, () -> assemblyLineController.moveAssemblyLine(-1));
  }

//  @Test
//  public void completeAssemblyTaskTest_throws() {
//    assertThrows(IllegalArgumentException.class, () -> assemblyLineController.completeAssemblyTask(-1));
//  }

  @Test
  public void setActiveTaskTest_throws() {
    assertThrows(IllegalArgumentException.class, () -> assemblyLineController.setActiveTask(-1, 2));
    assertThrows(IllegalArgumentException.class, () -> assemblyLineController.setActiveTask(2, -1));
  }

  @Test
  public void giveAssemblyTaskActionsTest_succeeds() {
    assertArrayEquals(new String[]{"Installing the sead body"}, assemblyLineController.giveAssemblyTaskActions(0, 0).toArray());
  }

  @Test
  public void giveAssemblyTaskActionsTest_throws() {
    assertThrows(IllegalArgumentException.class, () -> assemblyLineController.giveAssemblyTaskActions(-1, 2));
    assertThrows(IllegalArgumentException.class, () -> assemblyLineController.giveAssemblyTaskActions(2, -1));
  }

}
