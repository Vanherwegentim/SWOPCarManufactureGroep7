package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.*;
import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.TaskTypes.CarBodyAssemblyTask;
import be.kuleuven.assemassit.Domain.TaskTypes.InsertEngineAssemblyTask;
import be.kuleuven.assemassit.Domain.TaskTypes.InsertGearboxAssemblyTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PerformAssemblyTasksControllerTest {
  private PerformAssemblyTasksController controller;

  private CarManufactoringCompany mockedCarManufacturingCompany;

  private AssemblyLine mockedAssemblyLine;

  private WorkPost mockedCarBodyPost;
  private WorkPost mockedDrivetrainPost;
  private WorkPost mockedAccessoriesPost;
  private AssemblyTask mockedCarBodyAssemblyTask;
  private AssemblyTask mockedInsertEngineAssemblyTask;
  private AssemblyTask mockedDrivetrainAssemblyTask;
  private AssemblyTask mockedAccessoriesAssemblyTask;


  @BeforeEach
  public void beforeEach() {
    mockedCarManufacturingCompany = mock(CarManufactoringCompany.class);
    mockedAssemblyLine = mock(AssemblyLine.class);

    mockedAssemblyLine = mock(AssemblyLine.class);
    mockedCarBodyPost = mock(WorkPost.class);
    mockedDrivetrainPost = mock(WorkPost.class);
    mockedAccessoriesPost = mock(WorkPost.class);
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
    when(mockedCarBodyAssemblyTask.getActions()).thenReturn(Arrays.asList("Installing the dead body"));

    controller = new PerformAssemblyTasksController(mockedAssemblyLine, mockedCarManufacturingCompany);
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
  void setActiveTaskTest_succeeds() {
    int workPostId = 0;
    int assemblyTaskId = 0;

    when(mockedAssemblyLine.findWorkPost(workPostId)).thenReturn(mockedCarBodyPost);
    controller.setActiveTask(workPostId, assemblyTaskId);
    //todo assert schrijven?
  }

  @Test
  void setActiveTaskTest_throws() {
    assertThrows(IllegalArgumentException.class, () -> controller.setActiveTask(-1, 0));
    assertThrows(IllegalArgumentException.class, () -> controller.setActiveTask(0, -1));
  }

  @Test
  void giveAssemblyTaskActionsTest_succeeds() {
    List<String> actions = controller.giveAssemblyTaskActions(0, 0);

    assertEquals(actions, Arrays.asList("Installing the dead body"));
  }

  @Test
  void giveAssemblyTaskActionsTest_throws() {
    assertThrows(IllegalArgumentException.class, () -> controller.giveAssemblyTaskActions(-1, 0));
    assertThrows(IllegalArgumentException.class, () -> controller.giveAssemblyTaskActions(0, -1));
  }

  @Test
  void completeAssemblyTaskAndMoveIfPossibleTest_succeeds() {

    controller.completeAssemblyTaskAndMoveIfPossible(0, 30);

    //todo test
  }

  @Test
  void completeAssemblyTaskAndMoveIfPossibleTest_throws() {
    assertThrows(IllegalArgumentException.class, () -> controller.completeAssemblyTaskAndMoveIfPossible(-1, 30));
    assertThrows(IllegalArgumentException.class, () -> controller.completeAssemblyTaskAndMoveIfPossible(0, -1));

  }


}
