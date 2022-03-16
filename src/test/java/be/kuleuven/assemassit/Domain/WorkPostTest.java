package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.TaskTypes.CarBodyAssemblyTask;
import be.kuleuven.assemassit.Domain.TaskTypes.PaintCarAssemblyTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WorkPostTest {

  List<AssemblyTaskType> list;
  WorkPostType type;
  CarAssemblyProcess carAssemblyProcess;
  CarOrder carOrder;


  @BeforeEach
  public void beforeEach() {

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
    for (AssemblyTask assemblyTask : workPost.getAllAssemblyTasks()) {
      System.out.println(assemblyTask.getId());
    }
    // TODO nakijken of dit een correcte test is
    workPost.setActiveAssemblyTask(workPost.getAllAssemblyTasks().get(0).getId());
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

    workPost.setActiveAssemblyTask(9);
    assertFalse(workPost.getActiveAssemblyTask() == null);
    workPost.completeAssemblyTask();
    assertTrue(workPost.getActiveAssemblyTask() == null);
  }

  //TODO
//  @Test
//  public void remainingTimeInMinutesTest(){
//    workPost.addProcessToWorkPost(carAssemblyProcess);
//    workPost.setActiveAssemblyTask(0);
//    workPost.completeAssemblyTask();
//    workPost.setActiveAssemblyTask(1);
//    workPost.completeAssemblyTask();
//    System.out.println(workPost.remainingTimeInMinutes());
//    assert workPost.remainingTimeInMinutes() == 0;
//  }

  @Test
  public void findAssemblyTaskTest() {
    WorkPost workPost = new WorkPost(0, list, type, 60);

    workPost.addProcessToWorkPost(carAssemblyProcess);
    for (AssemblyTask assemblyTask : workPost.getAllAssemblyTasks()) {
      System.out.println(assemblyTask.getId());
    }
    assertTrue(workPost.findAssemblyTask(38).equals(new CarBodyAssemblyTask(Body.SEAD)));
  }
}
