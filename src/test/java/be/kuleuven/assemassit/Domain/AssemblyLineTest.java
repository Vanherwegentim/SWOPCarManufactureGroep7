package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
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
  private CarAssemblyProcess carAssemblyProcessTest;


  @BeforeEach
  public void beforeEach() {
    this.assemblyLine = new AssemblyLine();
    assemblyLine.setEndTime(LocalTime.of(22, 0));
    assemblyLine.setStartTime(LocalTime.of(6, 0));
    carAssemblyProcess = new CarAssemblyProcess(
      new CarOrder(
        new Car(
          new CarModel(0, "Tolkswagen Rolo", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values()), Arrays.asList(Spoiler.values())),
          Body.SEDAN,
          Color.BLACK,
          Engine.PERFORMANCE,
          Gearbox.FIVE_SPEED_MANUAL,
          Seat.LEATHER_BLACK,
          Airco.MANUAL,
          Wheel.SPORT)));
    carAssemblyProcessTest = new CarAssemblyProcess(
      new CarOrder(
        new Car(
          new CarModel(0, "Tolkswagen Rolo", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values())),
          Body.SEAD,
          Color.BLACK,
          Engine.PERFORMANCE,
          Gearbox.MANUAL,
          Seat.LEATHER_BLACK,
          Airco.MANUAL,
          Wheel.SPORT,
          Spoiler.LOW)));
    //Probably not correct
    carAssemblyProcessTest.complete();
    assemblyLine.addCarToFinishedCars(carAssemblyProcessTest);
    carAssemblyProcessTest.getCarOrder().setCompletionTime(LocalDateTime.now());
    carAssemblyProcessTest.getCarOrder().setEstimatedCompletionTime(LocalDateTime.now());
    carAssemblyProcess.getCarOrder().setCompletionTime(LocalDateTime.now());
    carAssemblyProcess.getCarOrder().setEstimatedCompletionTime(LocalDateTime.now());


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

  @Test
  public void createCarsPerDayMapTest() {
    assertEquals(Map.of(carAssemblyProcessTest.getCarOrder().getCompletionTime().toLocalDate(), 1), assemblyLine.createCarsPerDayMap());

  }

  //Needs more verbose testing
  @Test
  public void averageCarsInADayTest() {
    assertEquals(assemblyLine.averageCarsInADay(), 1);
  }

  @Test
  public void medianCarsInADayTest() {
    assertEquals(assemblyLine.medianCarsInADay(), 1);
  }

  @Test
  public void exactCarsIn2DaysTest() {
    assertEquals(assemblyLine.exactCarsIn2Days(), 1);
  }

  //This is probably going to error because of the problem with the estimatedCompletionTime algorithm
  //TODO fix this
  @Test
  public void averageDelayPerOrderTest() {
    assertEquals(assemblyLine.averageDelayPerOrder(), 0);
  }

  @Test
  public void medianDelayPerOrderTest() {
    System.out.println();
    assertEquals(assemblyLine.medianDelayPerOrder(), 0);
  }

  //probably going to error because we are only adding one CarAssemblyProcess to the finishedcars list at the moment.
  //which will try to set an element but the list will be smaller then 2 elements -> OutOfBoundsException
  @Test
  public void last2DelaysTest() {
    assertEquals(assemblyLine.last2Delays(), Map.of(carAssemblyProcessTest.getCarOrder().getCompletionTime().toLocalDate(), 0));
  }

}
