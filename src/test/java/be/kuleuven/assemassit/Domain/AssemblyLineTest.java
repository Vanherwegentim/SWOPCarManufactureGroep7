package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssemblyLineTest {

  private AssemblyLine assemblyLine;
  private CarAssemblyProcess carAssemblyProcess;


  @BeforeEach
  public void beforeEach() {
    this.assemblyLine = new AssemblyLine();
    assemblyLine.setEndTime(LocalTime.of(22, 0));
    assemblyLine.setStartTime(LocalTime.of(6, 0));
    carAssemblyProcess = new CarAssemblyProcess(
      new CarOrder(
        new Car(
          new CarModel(0, "Tolkswagen Rolo", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values())),
          Body.SEDAN,
          Color.BLACK,
          Engine.PERFORMANCE,
          Gearbox.MANUAL,
          Seat.LEATHER_BLACK,
          Airco.MANUAL,
          Wheel.SPORT)));
  }

  @Test
  public void checkCorrectAssemblyTasksPerWorkpost() {
    assertEquals(this.assemblyLine.getCarBodyPost().getAssemblyTaskTypes(), List.of(AssemblyTaskType.ASSEMBLE_CAR_BODY, AssemblyTaskType.PAINT_CAR));
    assertEquals(this.assemblyLine.getDrivetrainPost().getAssemblyTaskTypes(), List.of(AssemblyTaskType.INSERT_ENGINE, AssemblyTaskType.INSERT_GEARBOX));
    assertEquals(this.assemblyLine.getAccessoriesPost().getAssemblyTaskTypes(), List.of(AssemblyTaskType.INSTALL_AIRCO, AssemblyTaskType.INSTALL_SEATS, AssemblyTaskType.MOUNT_WHEELS));
  }

  @Test
  public void givePendingAssemblyTasksFromWorkPostTest() {
    assemblyLine.addCarAssemblyProcess(carAssemblyProcess);
    assemblyLine.move(2);

    List<AssemblyTask> actual = assemblyLine.givePendingAssemblyTasksFromWorkPost(0);
    assertArrayEquals(new String[]{"Assembly car body", "Paint car"}, actual.stream().map(AssemblyTask::getName).toArray());
  }


  @Test
  public void giveStatusTest_WorkpostsEmpty() {
    Map<String, AssemblyTask> workPostStatusses = new HashMap<>();
    workPostStatusses.put("Car Body Post", null);
    workPostStatusses.put("Drivetrain Post", null);
    workPostStatusses.put("Accessories Post", null);

    assertEquals(workPostStatusses, assemblyLine.giveActiveTasksOverview());
  }
}
