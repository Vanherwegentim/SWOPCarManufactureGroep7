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

import static org.junit.jupiter.api.Assertions.*;

public class AssemblyLineTest {

  private AssemblyLine assemblyLine;

  private CarAssemblyProcess carAssemblyProcess1;
  private CarAssemblyProcess carAssemblyProcess2;
  private CarAssemblyProcess carAssemblyProcess3;


  @BeforeEach
  public void beforeEach() {
    this.assemblyLine = new AssemblyLine();
    assemblyLine.setStartTime(LocalTime.of(6, 0));
    assemblyLine.setEndTime(LocalTime.of(22, 0));
    carAssemblyProcess1 = new CarAssemblyProcess(
      new CarOrder(
        new Car(
          new CarModel(0, "Tolkswagen Rolo", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values()), Arrays.asList(Spoiler.values())),
          Body.SEDAN,
          Color.BLACK,
          Engine.PERFORMANCE,
          Gearbox.FIVE_SPEED_MANUAL,
          Seat.LEATHER_BLACK,
          Airco.MANUAL,
          Wheel.SPORT,
          Spoiler.NO_SPOILER)));
    carAssemblyProcess2 = new CarAssemblyProcess(
      new CarOrder(
        new Car(
          new CarModel(0, "Limoen C4", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values()), Arrays.asList(Spoiler.values())),
          Body.SEDAN,
          Color.BLACK,
          Engine.PERFORMANCE,
          Gearbox.FIVE_SPEED_MANUAL,
          Seat.LEATHER_BLACK,
          Airco.MANUAL,
          Wheel.SPORT,
          Spoiler.LOW)));

  }

  @Test
  public void checkCorrectAssemblyTasksPerWorkpostTest() {
    assertEquals(this.assemblyLine.getCarBodyPost().getAssemblyTaskTypes(), List.of(AssemblyTaskType.ASSEMBLE_CAR_BODY, AssemblyTaskType.PAINT_CAR));
    assertEquals(this.assemblyLine.getDrivetrainPost().getAssemblyTaskTypes(), List.of(AssemblyTaskType.INSERT_ENGINE, AssemblyTaskType.INSERT_GEARBOX));
    assertEquals(this.assemblyLine.getAccessoriesPost().getAssemblyTaskTypes(), List.of(AssemblyTaskType.INSTALL_AIRCO, AssemblyTaskType.INSTALL_SEATS, AssemblyTaskType.MOUNT_WHEELS));
  }

  @Test
  public void givePendingAssemblyTasksFromWorkPostTest() {
    assemblyLine.addCarAssemblyProcess(carAssemblyProcess1);

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

    carAssemblyProcess2.complete();
    carAssemblyProcess2.getCarOrder().setCompletionTime(LocalDateTime.now());
    carAssemblyProcess2.getCarOrder().setEstimatedCompletionTime(LocalDateTime.now());
    carAssemblyProcess3.complete();
    carAssemblyProcess2.getCarOrder().setCompletionTime(LocalDateTime.now().minusDays(1));
    carAssemblyProcess2.getCarOrder().setEstimatedCompletionTime(LocalDateTime.now().minusDays(1).plusHours(3));

    assemblyLine.addCarToFinishedCars(carAssemblyProcess2);
    assemblyLine.addCarToFinishedCars(carAssemblyProcess2);
    assemblyLine.addCarToFinishedCars(carAssemblyProcess2);

    assemblyLine.addCarToFinishedCars(carAssemblyProcess3);
    assemblyLine.addCarToFinishedCars(carAssemblyProcess3);

    assertEquals(Map.of(carAssemblyProcessTest.getCarOrder().getCompletionTime().toLocalDate(), 3.0, carAssemblyProcessTestPlusDay.getCarOrder().getCompletionTime().toLocalDate(), 2.0), assemblyLine.createCarsPerDayMap());

  }

  @Test
  public void averageCarsInADayTest() {
    carAssemblyProcess2.complete();
    carAssemblyProcess2.getCarOrder().setCompletionTime(LocalDateTime.now());
    carAssemblyProcess2.getCarOrder().setEstimatedCompletionTime(LocalDateTime.now());

    assemblyLine.addCarToFinishedCars(carAssemblyProcess2);
    assertEquals(assemblyLine.averageCarsInADay(), 1);
  }

  @Test
  public void averageCarsInADayTest2() {
    assertEquals(assemblyLine.averageCarsInADay(), 0);
  }


  @Test
  public void medianCarsInADayTest() {
    assertEquals(2.5, assemblyLine.medianCarsInADay());
  }

  @Test
  public void exactCarsIn2DaysTest() {
    assertEquals(2.0, assemblyLine.exactCarsIn2Days());
  }

  @Test
  public void averageDelayPerOrderTest() {
    assertEquals(1.2, assemblyLine.averageDelayPerOrder());
  }

  @Test
  public void medianDelayPerOrderTest() {
    System.out.println();
    assertEquals(0, assemblyLine.medianDelayPerOrder());
  }

  @Test
  public void last2DelaysTest() {
    assertEquals(assemblyLine.last2Delays(), Map.of(carAssemblyProcessTest.getCarOrder().getCompletionTime().toLocalDate(), 0));
  }

  @Test
  public void givePossibleBatchCars_ReturnsBatch() {
    CarModel carModel = new CarModel(0, "Tolkswagen Rolo", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values()), Arrays.asList(Spoiler.values()));

    Car car1 = new Car(carModel, Body.BREAK, Color.BLACK, Engine.ULTRA, Gearbox.FIVE_SPEED_MANUAL, Seat.LEATHER_BLACK, Airco.MANUAL, Wheel.SPORT, Spoiler.NO_SPOILER);
    Car car2 = new Car(carModel, Body.BREAK, Color.BLACK, Engine.ULTRA, Gearbox.FIVE_SPEED_MANUAL, Seat.LEATHER_BLACK, Airco.MANUAL, Wheel.SPORT, Spoiler.NO_SPOILER);
    Car car3 = new Car(carModel, Body.BREAK, Color.BLACK, Engine.ULTRA, Gearbox.FIVE_SPEED_MANUAL, Seat.LEATHER_BLACK, Airco.MANUAL, Wheel.SPORT, Spoiler.NO_SPOILER);

    CarOrder carOrder1 = new CarOrder(car1);
    CarOrder carOrder2 = new CarOrder(car2);
    CarOrder carOrder3 = new CarOrder(car3);

    assemblyLine.addCarAssemblyProcess(new CarAssemblyProcess(carOrder1));
    assemblyLine.addCarAssemblyProcess(new CarAssemblyProcess(carOrder2));
    assemblyLine.addCarAssemblyProcess(new CarAssemblyProcess(carOrder3));

    List<Car> cars = assemblyLine.givePossibleBatchCars();
    assertTrue(cars.size() == 1);
  }

  @Test
  void giveSchedulingAlgorithmNames() {
  }

  @Test
  void detach() {
  }

  @Test
  void notifyObservers() {
  }

  @Test
  void setStartTime() {
  }

  @Test
  void setEndTime() {
  }

  @Test
  void getSchedulingAlgorithm() {
  }

  @Test
  void setSchedulingAlgorithm() {
  }

  @Test
  void addCarAssemblyProcess() {
  }

  @Test
  void getCarBodyPost() {
  }

  @Test
  void getDrivetrainPost() {
  }

  @Test
  void getAccessoriesPost() {
  }

  @Test
  void getWorkPosts() {
  }

  @Test
  void getFinishedCars() {
  }

  @Test
  void givePendingAssemblyTasksFromWorkPost() {
  }

  @Test
  void giveFinishedAssemblyTasksFromWorkPost() {
  }

  @Test
  void completeAssemblyTask() {
  }

  @Test
  void giveActiveTasksOverview() {
  }

  @Test
  void giveTasksOverview() {
  }

  @Test
  void giveFutureTasksOverview() {
  }

  @Test
  void findWorkPost() {
  }

  @Test
  void canMove() {
  }

  @Test
  void move() {
  }

  @Test
  void testMove() {
  }

  @Test
  void giveEstimatedCompletionDateOfLatestProcess() {
  }

  @Test
  void giveCarAssemblyTask() {
  }

  @Test
  void setActiveTask() {
  }

  @Test
  void getCarAssemblyProcessesQueue() {
  }

  @Test
  void getCarAssemblyProcessesQueueAsQueue() {
  }

  @Test
  void createCarsPerDayMap() {
  }

  @Test
  void averageCarsInADay() {
  }

  @Test
  void medianCarsInADay() {
  }

  @Test
  void exactCarsIn2Days() {
  }

  @Test
  void averageDelayPerOrder() {
  }

  @Test
  void medianDelayPerOrder() {
  }

  @Test
  void last2Delays() {
  }

  @Test
  void addCarToFinishedCars() {
  }

  @Test
  void testGiveSchedulingAlgorithmNames() {
  }

  @Test
  void givePossibleBatchCars() {
  }

  @Test
  void attach() {
  }

  @Test
  void testDetach() {
  }

  @Test
  void testNotifyObservers() {
  }
}
