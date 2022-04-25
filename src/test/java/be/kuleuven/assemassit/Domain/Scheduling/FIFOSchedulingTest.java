package be.kuleuven.assemassit.Domain.Scheduling;

import be.kuleuven.assemassit.Domain.*;
import be.kuleuven.assemassit.Domain.Enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class FIFOSchedulingTest {
  private AssemblyLine assemblyLine;
  private FIFOScheduling fifoScheduling;
  private LocalTime endTime = LocalTime.of(23, 59);
  private CarAssemblyProcess carAssemblyProcess1;
  private CarAssemblyProcess carAssemblyProcess2;

  @BeforeEach
  public void beforeEach() {
    fifoScheduling = new FIFOScheduling();
    assemblyLine = new AssemblyLine();
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
  public void moveAssemblyLineTest_succeeds() {
    assertEquals(-1, fifoScheduling.moveAssemblyLine(0, endTime, assemblyLine.getCarAssemblyProcessesQueueAsQueue(), assemblyLine.getFinishedCars(), assemblyLine.getWorkPosts()));
    assertFalse(assemblyLine.getCarAssemblyProcessesQueue().contains(carAssemblyProcess1));
    assertTrue(assemblyLine.getCarBodyPost().getCarAssemblyProcess().equals(carAssemblyProcess1));

    assertEquals(assemblyLine.getFinishedCars(), List.of(carAssemblyProcess2));
    fifoScheduling.moveAssemblyLine(0, endTime, assemblyLine.getCarAssemblyProcessesQueueAsQueue(), assemblyLine.getFinishedCars(), assemblyLine.getWorkPosts());
    fifoScheduling.moveAssemblyLine(0, endTime, assemblyLine.getCarAssemblyProcessesQueueAsQueue(), assemblyLine.getFinishedCars(), assemblyLine.getWorkPosts());
    //TODO If we have time, create test to check the overtime
    //https://dzone.com/articles/mock-java-datetime-for-testing
    assertEquals(2, assemblyLine.getFinishedCars().size());
  }


}
