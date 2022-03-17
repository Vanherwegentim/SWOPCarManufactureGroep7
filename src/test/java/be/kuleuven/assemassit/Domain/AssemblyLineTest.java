package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class AssemblyLineTest {

  private AssemblyLine assemblyLine;
  private CarAssemblyProcess carAssemblyProcess;




  @BeforeEach
  public void beforeEach(){
    this.assemblyLine = new AssemblyLine();
    carAssemblyProcess = new CarAssemblyProcess(
      new CarOrder(
        new Car(
          new CarModel(0, "Tolkswagen Rolo", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values())),
          Body.SEAD,
          Color.BLACK,
          Engine.PERFORMANCE,
          Gearbox.MANUAL,
          Seat.LEATHER_BLACK,
          Airco.MANUAL,
          Wheel.SPORT)));
  }
  @Test
  public void checkCorrectAssemblyTasksPerWorkpost(){
    assertEquals(this.assemblyLine.getCarBodyPost().getAssemblyTaskTypes(), List.of(AssemblyTaskType.ASSEMBLE_CAR_BODY, AssemblyTaskType.PAINT_CAR));
    assertEquals(this.assemblyLine.getDrivetrainPost().getAssemblyTaskTypes(), List.of(AssemblyTaskType.INSERT_ENGINE, AssemblyTaskType.INSERT_GEARBOX));
    assertEquals(this.assemblyLine.getAccessoriesPost().getAssemblyTaskTypes(), List.of(AssemblyTaskType.INSTALL_AIRCO, AssemblyTaskType.INSTALL_SEATS, AssemblyTaskType.MOUNT_WHEELS));
  }
  @Test
  public void addCarAssemblyProcessTest(){
    assemblyLine.addCarAssemblyProcess(carAssemblyProcess);
    assert assemblyLine.getCarAssemblyProcessesQueue().contains(carAssemblyProcess);
  }

  @Test
  public void givePendingAssemblyTasksFromWorkPostTest(){
    assemblyLine.addCarAssemblyProcess(carAssemblyProcess);
    assemblyLine.move(2);

    List<AssemblyTask> actual = assemblyLine.givePendingAssemblyTasksFromWorkPost(0);
    assertArrayEquals(new String[]{"Assembly car body", "Paint car"}, actual.stream().map(AssemblyTask::getName).toArray());
  }


//  @Test
//  public void completeAssemblyTaskTest(){
//    //TODO: fix nullpointerexception bij ActiveAssemblyTask
//
//    assemblyLine.addCarAssemblyProcess(carAssemblyProcess);
//    assemblyLine.move(2);
//    assemblyLine.completeAssemblyTask(0);
//
//    assemblyLine.givePendingAssemblyTasksFromWorkPost(0).stream().forEach(t-> System.out.println(t.getName()));
//
//  }

  /*
  Voorlopig wordt er nog nooit een assemblytask op active gezet dus vandaar de null.
   */
  @Test
  public void giveStatusTest_WorkpostsEmpty(){
    Map<String, AssemblyTask> workPostStatusses = new HashMap<>();
    workPostStatusses.put("Car Body Post",null);
    workPostStatusses.put("Drivetrain Post", null);
    workPostStatusses.put("Accessories Post", null);

    assertEquals(workPostStatusses, assemblyLine.giveStatus());
  }

//  @Test
//  public void giveTasksOverviewTest(){
//    //TODO: ouwe test, moet nog geüpdated worden met de nieuwe code
//    Map<String, List<AssemblyTask>> expected = new HashMap<>();
//    expected.put("Car Body Post", carBodyPost.getWorkPostAssemblyTasks());
//    expected.put("Drivetrain Post", drivetrainPost.getWorkPostAssemblyTasks());
//    expected.put("Accessories Post", accessoriesPost.getWorkPostAssemblyTasks());
//
//    Map<String, List<AssemblyTask>> actual = assemblyLine.giveTasksOverview();
//    assertEquals(expected.size(), actual.size());
//
//    for(String key : expected.keySet()){
//      assertArrayEquals(
//        expected.get(key).stream().map(AssemblyTask::getName).toArray(),
//        actual.get(key).stream().map(AssemblyTask::getName).toArray()
//      );
//    }
//  }
}
