package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.TaskTypes.CarBodyAssemblyTask;
import be.kuleuven.assemassit.Domain.TaskTypes.PaintCarAssemblyTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static be.kuleuven.assemassit.Domain.AssemblyTask.resetRunningId;
import static org.junit.jupiter.api.Assertions.*;

public class WorkPostTest {

  List<AssemblyTaskType> list;
  WorkPostType type;
  CarAssemblyProcess carAssemblyProcess;
  CarOrder carOrder;
  WorkPost workPost;


  @BeforeEach
  public void beforeEach() {
    resetRunningId();
    list = Arrays.asList(AssemblyTaskType.ASSEMBLE_CAR_BODY, AssemblyTaskType.PAINT_CAR);
    type = WorkPostType.CAR_BODY_POST;
    this.carOrder = new CarOrder(new Car(new CarModel(0, "Test", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values()), Arrays.asList(Spoiler.values())), Body.SEDAN, Color.BLACK, Engine.PERFORMANCE, Gearbox.FIVE_SPEED_MANUAL, Seat.LEATHER_BLACK, Airco.MANUAL, Wheel.SPORT, Spoiler.NO_SPOILER));
    carAssemblyProcess = new CarAssemblyProcess(carOrder);
    workPost = new WorkPost(0, list, type, 60);
  }

  @Test
  public void constructorTest() {

    assertEquals(workPost.getId(), 0);
    assertEquals(workPost.getAssemblyTaskTypes(), list);
    assertEquals(workPost.getWorkPostType(), type);
    assertEquals(workPost.getExpectedWorkPostDurationInMinutes(), 60);
  }

  @Test
  public void addProcessToWorkPostTest() {

    workPost.addProcessToWorkPost(carAssemblyProcess);
    assertEquals(workPost.getCarAssemblyProcess(), carAssemblyProcess);
  }

  @Test
  public void removeProcessFromWorkPost() {

    workPost.addProcessToWorkPost(carAssemblyProcess);
    assertEquals(carAssemblyProcess, workPost.removeProcessFromWorkPost());
  }

  @Test
  public void setActiveAssemblyTaskTest() {

    assertThrows(NullPointerException.class, () -> workPost.setActiveAssemblyTask(100000));
    workPost.addProcessToWorkPost(carAssemblyProcess);

    workPost.setActiveAssemblyTask(workPost.getWorkPostAssemblyTasks().get(0).getId());
    assertEquals(workPost.getActiveAssemblyTask().getActions(), new CarBodyAssemblyTask(Body.SEDAN).getActions());
  }


  @Test
  public void givePendingAssemblyTasks() {

    workPost.addProcessToWorkPost(carAssemblyProcess);

    assertEquals(workPost.givePendingAssemblyTasks().get(0).getActions(), new CarBodyAssemblyTask(carOrder.getCar().getBody()).getActions());
    assertEquals(workPost.givePendingAssemblyTasks().get(1).getActions(), new PaintCarAssemblyTask(carOrder.getCar().getColor()).getActions());
  }

  @Test
  public void completeAssemblyTaskTest() {


    workPost.addProcessToWorkPost(carAssemblyProcess);

    workPost.setActiveAssemblyTask(0);
    assertNotNull(workPost.getActiveAssemblyTask());
    workPost.completeAssemblyTask(15, LocalDateTime.now());
    assertNull(workPost.getActiveAssemblyTask());
  }


  @Test
  public void findAssemblyTaskTest() {

    workPost.addProcessToWorkPost(carAssemblyProcess);

    assertEquals(workPost.findAssemblyTask(0).getActions(), new CarBodyAssemblyTask(Body.SEDAN).getActions());
  }

  @Test
  void removeCarAssemblyProcessTest() {
    assertNull(workPost.getCarAssemblyProcess());
    workPost.addProcessToWorkPost(carAssemblyProcess);
    assertEquals(carAssemblyProcess, workPost.getCarAssemblyProcess());
    workPost.removeCarAssemblyProcess();
    assertNull(workPost.getCarAssemblyProcess());
  }

  @Test
  void getExpectedWorkPostDurationInMinutesTest() {
    List<AssemblyTaskType> list = Arrays.asList(AssemblyTaskType.INSTALL_AIRCO, AssemblyTaskType.PAINT_CAR);
    CarOrder order = new CarOrder(new Car(new CarModel(0, "Ik heb honger", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values()), Arrays.asList(Spoiler.values()), 99), Body.SEDAN, Color.BLACK, Engine.PERFORMANCE, Gearbox.FIVE_SPEED_MANUAL, Seat.LEATHER_BLACK, Airco.MANUAL, Wheel.SPORT, Spoiler.NO_SPOILER));
    CarAssemblyProcess process = new CarAssemblyProcess(order);
    WorkPost post = new WorkPost(0, list, WorkPostType.ACCESSORIES_POST, 60);
    assertEquals(post.getExpectedWorkPostDurationInMinutes(), 60);
    post.addProcessToWorkPost(process);
    assertEquals(post.getExpectedWorkPostDurationInMinutes(), 99);
  }

  @Test
  void testEquals() {
    List<AssemblyTaskType> list = Arrays.asList(AssemblyTaskType.INSTALL_AIRCO, AssemblyTaskType.PAINT_CAR);
    CarOrder order = new CarOrder(new Car(new CarModel(0, "Ik heb honger", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values()), Arrays.asList(Spoiler.values()), 99), Body.SEDAN, Color.BLACK, Engine.PERFORMANCE, Gearbox.FIVE_SPEED_MANUAL, Seat.LEATHER_BLACK, Airco.MANUAL, Wheel.SPORT, Spoiler.NO_SPOILER));
    CarAssemblyProcess process = new CarAssemblyProcess(order);
    WorkPost post = new WorkPost(0, list, WorkPostType.ACCESSORIES_POST, 60);
    assertNotEquals(post, workPost);
  }

  @Test
  void giveFinishedAssemblyTasksTest() {
    assertEquals(workPost.giveFinishedAssemblyTasks().size(), 0);
    workPost.addProcessToWorkPost(carAssemblyProcess);
    int id = workPost.getWorkPostAssemblyTasks().stream().findFirst().get().getId();
    workPost.setActiveAssemblyTask(id);
    assertEquals(workPost.getActiveAssemblyTask(), workPost.findAssemblyTask(id));
    workPost.completeAssemblyTask(40, LocalDateTime.now());
    assertEquals(workPost.giveFinishedAssemblyTasks().size(), 1);
    assertEquals(workPost.giveFinishedAssemblyTasks().get(0), workPost.findAssemblyTask(id));
  }
}
