package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.TaskTypes.CarBodyAssemblyTask;
import be.kuleuven.assemassit.Domain.TaskTypes.PaintCarAssemblyTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static be.kuleuven.assemassit.Domain.AssemblyTask.resetRunningId;
import static org.junit.jupiter.api.Assertions.*;

public class WorkPostTest {

  List<AssemblyTaskType> list;
  WorkPostType type;
  CarAssemblyProcess carAssemblyProcess;
  CarOrder carOrder;


  @BeforeEach
  public void beforeEach() {
    resetRunningId();
    list = Arrays.asList(AssemblyTaskType.ASSEMBLE_CAR_BODY, AssemblyTaskType.PAINT_CAR);
    type = WorkPostType.CAR_BODY_POST;
    this.carOrder = new CarOrder(
      new Car(
        new CarModel(0, "Test", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values())),
        Body.SEAD,
        Color.BLACK,
        Engine.PERFORMANCE,
        Gearbox.MANUAL,
        Seat.LEATHER_BLACK,
        Airco.MANUAL,
        Wheel.SPORT));
    carAssemblyProcess = new CarAssemblyProcess(carOrder);
  }

  @Test
  public void contructorTest() {
    WorkPost workPost = new WorkPost(0, list, type, 60);

    assert workPost.getId() == 0;
    assertEquals(workPost.getAssemblyTaskTypes(), list);
    assertEquals(workPost.getWorkPostType(), type);
    assert workPost.getExpectedWorkPostDurationInMinutes() == 60;
  }

  @Test
  public void addProcessToWorkPostTest() {
    WorkPost workPost = new WorkPost(0, list, type, 60);

    workPost.addProcessToWorkPost(carAssemblyProcess);
    assertEquals(workPost.getCarAssemblyProcess(), carAssemblyProcess);
  }

  @Test
  public void removeProcessFromWorkPost() {
    WorkPost workPost = new WorkPost(0, list, type, 60);

    workPost.addProcessToWorkPost(carAssemblyProcess);
    assertEquals(carAssemblyProcess, workPost.removeProcessFromWorkPost());
  }

  @Test
  public void setActiveAssemblyTaskTest() {
    WorkPost workPost = new WorkPost(0, list, type, 60);

    assertThrows(NullPointerException.class, () -> workPost.setActiveAssemblyTask(100000));
    workPost.addProcessToWorkPost(carAssemblyProcess);

    for (AssemblyTask assemblyTask : workPost.getWorkPostAssemblyTasks()) {
      System.out.println(assemblyTask.getId());
    }

    workPost.setActiveAssemblyTask(workPost.getWorkPostAssemblyTasks().get(0).getId());
    assertEquals(workPost.getActiveAssemblyTask(), new CarBodyAssemblyTask(Body.SEAD));
  }


  @Test
  public void givePendingAssemblyTasks() {
    WorkPost workPost = new WorkPost(0, list, type, 60);

    workPost.addProcessToWorkPost(carAssemblyProcess);
    System.out.println(workPost.givePendingAssemblyTasks());
    assertEquals(workPost.givePendingAssemblyTasks(), Arrays.asList(new CarBodyAssemblyTask(carOrder.getCar().getBody()), new PaintCarAssemblyTask(carOrder.getCar().getColor())));
  }

  @Test
  public void completeAssemblyTaskTest() {
    WorkPost workPost = new WorkPost(0, list, type, 60);


    workPost.addProcessToWorkPost(carAssemblyProcess);
    for (AssemblyTask assemblyTask : carAssemblyProcess.getAssemblyTasks()) {
      System.out.println(assemblyTask.getId());
    }
    workPost.setActiveAssemblyTask(0);
    assertNotNull(workPost.getActiveAssemblyTask());
    workPost.completeAssemblyTask();
    assertNull(workPost.getActiveAssemblyTask());
  }


  @Test
  public void findAssemblyTaskTest() {
    WorkPost workPost = new WorkPost(0, list, type, 60);

    workPost.addProcessToWorkPost(carAssemblyProcess);
    for (AssemblyTask assemblyTask : workPost.getWorkPostAssemblyTasks()) {
      System.out.println(assemblyTask.getId());
    }

    assertTrue(workPost.findAssemblyTask(0).equals(new CarBodyAssemblyTask(Body.SEAD)));
  }
}
