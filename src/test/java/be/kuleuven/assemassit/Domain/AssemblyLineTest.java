package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.Scheduling.FIFOScheduling;
import be.kuleuven.assemassit.Domain.Scheduling.SchedulingAlgorithm;
import be.kuleuven.assemassit.Domain.Scheduling.SpecificationBatchScheduling;
import be.kuleuven.assemassit.Domain.TaskTypes.CarBodyAssemblyTask;
import be.kuleuven.assemassit.Domain.TaskTypes.InsertEngineAssemblyTask;
import be.kuleuven.assemassit.Domain.TaskTypes.InstallAircoAssemblyTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    carAssemblyProcess3 = new CarAssemblyProcess(
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
    assertArrayEquals(new String[]{}, actual.stream().map(AssemblyTask::getName).toArray());
  }


  @Test
  public void giveStatusTest_WorkpostsEmpty() {
    Map<String, AssemblyTask> workPostStatusses = new HashMap<>();
    workPostStatusses.put("Car Body Post", null);
    workPostStatusses.put("Drivetrain Post", null);
    workPostStatusses.put("Accessories Post", null);

    assertEquals(workPostStatusses, assemblyLine.giveActiveTasksOverview());
  }

  private void extraSetup() {

    carAssemblyProcess2.complete();
    carAssemblyProcess2.getCarOrder().setCompletionTime(LocalDateTime.now());
    carAssemblyProcess2.getCarOrder().setEstimatedCompletionTime(LocalDateTime.now());
    assemblyLine.addCarToFinishedCars(carAssemblyProcess2);
    assemblyLine.addCarToFinishedCars(carAssemblyProcess2);
    assemblyLine.addCarToFinishedCars(carAssemblyProcess2);


    carAssemblyProcess3.complete();
    carAssemblyProcess3.getCarOrder().setCompletionTime(LocalDateTime.now().minusDays(1));
    carAssemblyProcess3.getCarOrder().setEstimatedCompletionTime(LocalDateTime.now().minusDays(1).plusHours(3));
    assemblyLine.addCarToFinishedCars(carAssemblyProcess3);
    assemblyLine.addCarToFinishedCars(carAssemblyProcess3);
  }

  @Test
  public void createCarsPerDayMapTest() {
    extraSetup();
    assertEquals(Map.of(carAssemblyProcess2.getCarOrder().getCompletionTime().toLocalDate(), 3.0, carAssemblyProcess3.getCarOrder().getCompletionTime().toLocalDate(), 2.0), assemblyLine.createCarsPerDayMap());

  }

  @Test
  public void averageCarsInADayTest() {
    extraSetup();
    assertEquals(assemblyLine.averageCarsInADay(), 2.5);
  }

  @Test
  public void averageCarsInADayTest2() {
    assertEquals(assemblyLine.averageCarsInADay(), 0);
  }


  @Test
  public void medianCarsInADayTest() {
    extraSetup();
    assertEquals(2.5, assemblyLine.medianCarsInADay());
  }

  @Test
  public void exactCarsIn2DaysTest() {
    extraSetup();
    assertEquals(2.0, assemblyLine.exactCarsIn2Days());
  }

  @Test
  public void averageDelayPerOrderTest() {
    extraSetup();
    assertEquals(1.2, assemblyLine.averageDelayPerOrder());
  }

  @Test
  public void medianDelayPerOrderTest() {
    assertEquals(0, assemblyLine.medianDelayPerOrder());
  }

  @Test
  public void last2DelaysTest() {
    extraSetup();
    assertEquals(assemblyLine.last2Delays(), Map.of(carAssemblyProcess2.getCarOrder().getCompletionTime().toLocalDate(), 0));
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
    assertEquals(1, cars.size());
  }

  @Test
  public void giveSchedulingAlgorithmNames() {
    assertEquals(List.of("FIFOScheduling", "SpecificationBatchScheduling"), assemblyLine.giveSchedulingAlgorithmNames());
  }

  @Test
  public void detach() {
    CarManufactoringCompany company = mock(CarManufactoringCompany.class);
    assemblyLine.attach(company);
    assemblyLine.detach(company);
    assertEquals(List.of(), assemblyLine.getObservers());
  }

  @Test
  public void notifyObservers() {
    CarManufactoringCompany company = new CarManufactoringCompany(LocalTime.of(6, 0), LocalTime.of(22, 0), assemblyLine);
    assemblyLine.attach(company);
    assemblyLine.notifyObservers(3);
    assertEquals(3, company.getOvertime());
    assertEquals(3, company.getOverTimeRepository().getOverTime());
    company.getOverTimeRepository().clearFile();
  }

  @Test
  public void setStartTime() {
    assertThrows(IllegalArgumentException.class, () -> assemblyLine.setStartTime(null));
    assemblyLine.setStartTime(LocalTime.of(6, 0));
    assertEquals(LocalTime.of(6, 0), assemblyLine.getStartTime());

  }

  @Test
  public void setEndTime() {
    assertThrows(IllegalArgumentException.class, () -> assemblyLine.setEndTime(null));
    assemblyLine.setStartTime(LocalTime.of(22, 0));
    assertEquals(LocalTime.of(22, 0), assemblyLine.getEndTime());
  }

  @Test
  public void getSchedulingAlgorithm() {
    assertEquals("FIFOScheduling", assemblyLine.getSchedulingAlgorithm().getClass().getSimpleName());
  }

  @Test
  public void setSchedulingAlgorithm() {
    assertThrows(IllegalArgumentException.class, () -> assemblyLine.setSchedulingAlgorithm(null));
    SpecificationBatchScheduling scheduling = mock(SpecificationBatchScheduling.class);
    assemblyLine.setSchedulingAlgorithm(scheduling);
    assertEquals(scheduling, assemblyLine.getSchedulingAlgorithm());

  }

  @Test
  public void addCarAssemblyProcess() {
    assertThrows(IllegalArgumentException.class, () -> assemblyLine.addCarAssemblyProcess(null));
    CarAssemblyProcess carAssemblyProcess = mock(CarAssemblyProcess.class);
    assemblyLine.addCarAssemblyProcess(carAssemblyProcess);
    assertTrue(assemblyLine.getCarAssemblyProcessesQueue().contains(carAssemblyProcess));
  }

  @Test
  public void getCarBodyPost() {
    WorkPost workPost = new WorkPost(0, Arrays.asList(AssemblyTaskType.ASSEMBLE_CAR_BODY, AssemblyTaskType.PAINT_CAR), WorkPostType.CAR_BODY_POST, 60);
    assertEquals(workPost, assemblyLine.getCarBodyPost());
  }

  @Test
  void getDrivetrainPost() {
    WorkPost workPost = new WorkPost(1, Arrays.asList(AssemblyTaskType.INSERT_ENGINE, AssemblyTaskType.INSERT_GEARBOX), WorkPostType.DRIVETRAIN_POST, 60);
    assertEquals(workPost, assemblyLine.getDrivetrainPost());

  }

  @Test
  void getAccessoriesPost() {
    WorkPost workPost = new WorkPost(2, Arrays.asList(AssemblyTaskType.INSTALL_AIRCO, AssemblyTaskType.INSTALL_SEATS, AssemblyTaskType.MOUNT_WHEELS), WorkPostType.ACCESSORIES_POST, 60);
    assertEquals(workPost, assemblyLine.getAccessoriesPost());
  }

  @Test
  void getWorkPosts() {
    WorkPost workPost1 = new WorkPost(0, Arrays.asList(AssemblyTaskType.ASSEMBLE_CAR_BODY, AssemblyTaskType.PAINT_CAR), WorkPostType.CAR_BODY_POST, 60);
    WorkPost workPost2 = new WorkPost(1, Arrays.asList(AssemblyTaskType.INSERT_ENGINE, AssemblyTaskType.INSERT_GEARBOX), WorkPostType.DRIVETRAIN_POST, 60);
    WorkPost workPost3 = new WorkPost(2, Arrays.asList(AssemblyTaskType.INSTALL_AIRCO, AssemblyTaskType.INSTALL_SEATS, AssemblyTaskType.MOUNT_WHEELS), WorkPostType.ACCESSORIES_POST, 60);
    assertEquals(List.of(workPost1, workPost2, workPost3), assemblyLine.getWorkPosts());
  }

  @Test
  void getFinishedCars() {
    CarAssemblyProcess carAssemblyProcess = mock(CarAssemblyProcess.class);
    assemblyLine.addCarToFinishedCars(carAssemblyProcess);
    assertTrue(assemblyLine.getFinishedCars().contains(carAssemblyProcess));
  }

  @Test
  void givePendingAssemblyTasksFromWorkPost() {
    CarAssemblyProcess carAssemblyProcess = mock(CarAssemblyProcess.class);
    when(carAssemblyProcess.getAssemblyTasks()).thenReturn(List.of(new CarBodyAssemblyTask(Body.BREAK)));
    assemblyLine.getCarBodyPost().addProcessToWorkPost(carAssemblyProcess);
    WorkPost workPost = new WorkPost(0, Arrays.asList(AssemblyTaskType.ASSEMBLE_CAR_BODY, AssemblyTaskType.PAINT_CAR), WorkPostType.CAR_BODY_POST, 60);
    workPost.addProcessToWorkPost(carAssemblyProcess);
    assertEquals(carAssemblyProcess.getAssemblyTasks(), assemblyLine.givePendingAssemblyTasksFromWorkPost(0));
  }

  @Test
  void giveFinishedAssemblyTasksFromWorkPost() {
    CarAssemblyProcess carAssemblyProcess = mock(CarAssemblyProcess.class);
    when(carAssemblyProcess.getAssemblyTasks()).thenReturn(List.of(new CarBodyAssemblyTask(Body.BREAK)));
    assemblyLine.getCarBodyPost().addProcessToWorkPost(carAssemblyProcess);
    WorkPost workPost = new WorkPost(0, Arrays.asList(AssemblyTaskType.ASSEMBLE_CAR_BODY, AssemblyTaskType.PAINT_CAR), WorkPostType.CAR_BODY_POST, 60);
    workPost.addProcessToWorkPost(carAssemblyProcess);
    for (AssemblyTask assemblyTask : workPost.getWorkPostAssemblyTasks()) {
      assemblyTask.complete();
    }
    assertEquals(carAssemblyProcess.getAssemblyTasks(), assemblyLine.giveFinishedAssemblyTasksFromWorkPost(0));

  }

  @Test
  void completeAssemblyTask() {
    assertThrows(IllegalArgumentException.class, () -> assemblyLine.completeAssemblyTask(-1, 5));
    assertThrows(IllegalArgumentException.class, () -> assemblyLine.completeAssemblyTask(5, -1));
    CarAssemblyProcess carAssemblyProcess = mock(CarAssemblyProcess.class);
    when(carAssemblyProcess.getAssemblyTasks()).thenReturn(List.of(new CarBodyAssemblyTask(Body.BREAK)));
    assemblyLine.getCarBodyPost().addProcessToWorkPost(carAssemblyProcess);
    assemblyLine.getCarBodyPost().setActiveAssemblyTask(assemblyLine.getCarBodyPost().getWorkPostAssemblyTasks().get(0).getId());
    assemblyLine.completeAssemblyTask(0, 60);
    assertEquals(carAssemblyProcess.getAssemblyTasks(), assemblyLine.giveFinishedAssemblyTasksFromWorkPost(0));
  }

  @Test
  void giveActiveTasksOverview() {
    CarAssemblyProcess carAssemblyProcess = mock(CarAssemblyProcess.class);
    when(carAssemblyProcess.getAssemblyTasks()).thenReturn(List.of(new CarBodyAssemblyTask(Body.BREAK)));
    assemblyLine.getCarBodyPost().addProcessToWorkPost(carAssemblyProcess);
    assemblyLine.setActiveTask(assemblyLine.getCarBodyPost(), carAssemblyProcess.getAssemblyTasks().get(0).getId());
    HashMap<String, AssemblyTask> workPostStatuses = new LinkedHashMap<>();
    workPostStatuses.put("Car Body Post", carAssemblyProcess.getAssemblyTasks().get(0));
    workPostStatuses.put("Drivetrain Post", null);
    workPostStatuses.put("Accessories Post", null);
    assertEquals(workPostStatuses, assemblyLine.giveActiveTasksOverview());
  }

  @Test
  void giveTasksOverview() {
    CarAssemblyProcess carAssemblyProcess = mock(CarAssemblyProcess.class);
    when(carAssemblyProcess.getAssemblyTasks()).thenReturn(List.of(new CarBodyAssemblyTask(Body.BREAK), new InsertEngineAssemblyTask(Engine.STANDARD), new InstallAircoAssemblyTask(Airco.MANUAL)));
    assemblyLine.getCarBodyPost().addProcessToWorkPost(carAssemblyProcess);
    assemblyLine.getDrivetrainPost().addProcessToWorkPost(carAssemblyProcess);
    assemblyLine.getAccessoriesPost().addProcessToWorkPost(carAssemblyProcess);
    HashMap<String, List<AssemblyTask>> workPostPairs = new LinkedHashMap<>();
    workPostPairs.put("Car Body Post", List.of(carAssemblyProcess.getAssemblyTasks().get(0)));
    workPostPairs.put("Drivetrain Post", List.of(carAssemblyProcess.getAssemblyTasks().get(1)));
    workPostPairs.put("Accessories Post", List.of(carAssemblyProcess.getAssemblyTasks().get(2)));
    assertEquals(workPostPairs, assemblyLine.giveTasksOverview());
  }

  @Test
  void giveFutureTasksOverview() {
    CarAssemblyProcess carAssemblyProcess = mock(CarAssemblyProcess.class);
    when(carAssemblyProcess.getAssemblyTasks()).thenReturn(List.of(new CarBodyAssemblyTask(Body.BREAK), new InsertEngineAssemblyTask(Engine.STANDARD), new InstallAircoAssemblyTask(Airco.MANUAL)));
    assemblyLine.addCarAssemblyProcess(carAssemblyProcess);
    assemblyLine.getCarBodyPost().addProcessToWorkPost(carAssemblyProcess);
    assemblyLine.getDrivetrainPost().addProcessToWorkPost(carAssemblyProcess);
    assemblyLine.getAccessoriesPost().addProcessToWorkPost(carAssemblyProcess);
    HashMap<String, List<AssemblyTask>> workPostPairs = new LinkedHashMap<>();
    workPostPairs.put("Car Body Post", List.of(carAssemblyProcess.getAssemblyTasks().get(0)));
    workPostPairs.put("Drivetrain Post", List.of(carAssemblyProcess.getAssemblyTasks().get(1)));
    workPostPairs.put("Accessories Post", List.of(carAssemblyProcess.getAssemblyTasks().get(2)));
    assertEquals(workPostPairs, assemblyLine.giveFutureTasksOverview());
  }

  @Test
  void findWorkPost() {
    assertThrows(IllegalArgumentException.class, () -> assemblyLine.findWorkPost(-1));
    assertThrows(IllegalArgumentException.class, () -> assemblyLine.findWorkPost(5));
    WorkPost workPost = new WorkPost(0, Arrays.asList(AssemblyTaskType.ASSEMBLE_CAR_BODY, AssemblyTaskType.PAINT_CAR), WorkPostType.CAR_BODY_POST, 60);
    assertEquals(workPost, assemblyLine.findWorkPost(0));

  }

  @Test
  void canMove() {
    assertTrue(assemblyLine.canMove());
    CarAssemblyProcess carAssemblyProcess = mock(CarAssemblyProcess.class);
    when(carAssemblyProcess.getAssemblyTasks()).thenReturn(List.of(new CarBodyAssemblyTask(Body.BREAK), new InsertEngineAssemblyTask(Engine.STANDARD), new InstallAircoAssemblyTask(Airco.MANUAL)));
    assemblyLine.addCarAssemblyProcess(carAssemblyProcess);
    assemblyLine.getCarBodyPost().addProcessToWorkPost(carAssemblyProcess);
    assertFalse(assemblyLine.canMove());
  }

  @Test
  void move() {


    assemblyLine.getCarBodyPost().addProcessToWorkPost(carAssemblyProcess1);
    assertThrows(IllegalArgumentException.class, () -> assemblyLine.move(LocalTime.of(12, 0), LocalTime.of(13, 0), 0));
    assemblyLine.getCarBodyPost().removeProcessFromWorkPost();

    assemblyLine.addCarAssemblyProcess(carAssemblyProcess1);
    SchedulingAlgorithm schedulingAlgorithm = new FIFOScheduling();
    assemblyLine.setSchedulingAlgorithm(schedulingAlgorithm);
    assemblyLine.move(LocalTime.of(12, 0), LocalTime.of(13, 0), 0);
    assemblyLine.move(LocalTime.of(12, 0), LocalTime.of(13, 0), 0);
    System.out.println(assemblyLine.getCarAssemblyProcessesQueue().size());
    System.out.println(assemblyLine.getCarBodyPost());
    assertEquals(carAssemblyProcess1.getAssemblyTasks().get(0), assemblyLine.getCarBodyPost().getCarAssemblyProcess().getAssemblyTasks().get(0));
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
  void addCarToFinishedCars() {
  }

  @Test
  void attach() {
  }

}
