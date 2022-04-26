package be.kuleuven.assemassit;

import be.kuleuven.assemassit.Controller.CheckAssemblyLineStatusController;
import be.kuleuven.assemassit.Controller.ControllerFactory;
import be.kuleuven.assemassit.Domain.*;
import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.Scheduling.FIFOScheduling;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckAssemblyLineStatusTest {

  private AssemblyLine assemblyLine;
  private FIFOScheduling fifoScheduling;
  private LocalTime endTime = LocalTime.of(23, 59);
  private CarAssemblyProcess carAssemblyProcess1;
  private CarAssemblyProcess carAssemblyProcess2;
  private CheckAssemblyLineStatusController controller;

  @BeforeEach
  public void beforeEach() {
    ControllerFactory controllerFactory = new ControllerFactory();
    controllerFactory.loginCarMechanic();
    controller = controllerFactory.createCheckAssemblyLineStatusController();
    fifoScheduling = new FIFOScheduling();
    assemblyLine = controllerFactory.getAssemblyLine();
    assemblyLine.setEndTime(LocalTime.of(22, 0));
    assemblyLine.setStartTime(LocalTime.of(6, 0));
    carAssemblyProcess1 = new CarAssemblyProcess(
      new CarOrder(
        new Car(
          new CarModel(0, "Tolkswagen Rolo", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values()), Arrays.asList(Spoiler.values())),
          Body.BREAK,
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
          new CarModel(0, "Tolkswagen Rolo", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values()), Arrays.asList(Spoiler.values())),
          Body.BREAK,
          Color.BLACK,
          Engine.PERFORMANCE,
          Gearbox.FIVE_SPEED_MANUAL,
          Seat.LEATHER_BLACK,
          Airco.MANUAL,
          Wheel.SPORT,
          Spoiler.NO_SPOILER)));
    assemblyLine.addCarAssemblyProcess(carAssemblyProcess1);
    assemblyLine.addCarToFinishedCars(carAssemblyProcess2);

  }

  @Test
  public void checkAssemblyLineStatusIntegrationTest_MainScenario_Success() {
    //MAIN SUCCESS SCENARIO

    // Step 1

    // executed in UI tests

    // Step 2

    fifoScheduling.moveAssemblyLine(0, endTime, assemblyLine.getCarAssemblyProcessesQueueAsQueue(), assemblyLine.getFinishedCars(), assemblyLine.getWorkPosts());

    assertEquals(Map.of("Car Body Post", List.of("Assembly car body (pending)", "Paint car (pending)"), "Drivetrain Post", List.of(), "Accessories Post", List.of()), controller.giveAssemblyLineStatusOverview());
    assertEquals(Map.of("Car Body Post", List.of(), "Drivetrain Post", List.of("Insert gearbox (pending)", "Insert engine (pending)"), "Accessories Post", List.of()), controller.giveFutureAssemblyLineStatusOverview());
    assertEquals(List.of("Assembly car body", "Paint car"), controller.givePendingAssemblyTasks(0).values().stream().toList());
    assertEquals(Map.of(), controller.givePendingAssemblyTasks(1));
    assertEquals(Map.of(), controller.givePendingAssemblyTasks(2));
    assertEquals(Map.of(), controller.giveFinishedAssemblyTasks(0));
    assertEquals(Map.of(), controller.giveFinishedAssemblyTasks(1));
    assertEquals(Map.of(), controller.giveFinishedAssemblyTasks(2));

    //extra testing
    fifoScheduling.moveAssemblyLine(0, endTime, assemblyLine.getCarAssemblyProcessesQueueAsQueue(), assemblyLine.getFinishedCars(), assemblyLine.getWorkPosts());
    assertEquals(Map.of("Car Body Post", List.of(), "Drivetrain Post", List.of("Insert gearbox (pending)", "Insert engine (pending)"), "Accessories Post", List.of()), controller.giveAssemblyLineStatusOverview());
    assertEquals(Map.of("Car Body Post", List.of(), "Drivetrain Post", List.of(), "Accessories Post", List.of("Install airco (pending)", "Mount wheels (pending)", "Install seats (pending)")), controller.giveFutureAssemblyLineStatusOverview());
    assertEquals(Map.of(), controller.givePendingAssemblyTasks(0));
    assertEquals(Map.of(1, "Insert gearbox", 2, "Insert engine"), controller.givePendingAssemblyTasks(1));
    assertEquals(Map.of(), controller.givePendingAssemblyTasks(2));
    assertEquals(Map.of(), controller.giveFinishedAssemblyTasks(0));
    assertEquals(Map.of(), controller.giveFinishedAssemblyTasks(1));
    assertEquals(Map.of(), controller.giveFinishedAssemblyTasks(2));

    //Done

  }
}
