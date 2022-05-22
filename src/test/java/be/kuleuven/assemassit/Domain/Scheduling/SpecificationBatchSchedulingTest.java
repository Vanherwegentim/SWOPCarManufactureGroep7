package be.kuleuven.assemassit.Domain.Scheduling;

import be.kuleuven.assemassit.Domain.*;
import be.kuleuven.assemassit.Domain.Enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class SpecificationBatchSchedulingTest {
  private AssemblyLine assemblyLine;
  private SpecificationBatchScheduling specificationBatchScheduling;
  private LocalTime endTime = LocalTime.of(22, 00);
  private CarAssemblyProcess carAssemblyProcess1;
  private CarAssemblyProcess carAssemblyProcess2;
  private CarAssemblyProcess carAssemblyProcess3;
  private CarAssemblyProcess carAssemblyProcess4;


  @BeforeEach
  public void beforeEach() {

    assemblyLine = new AssemblyLine();
    assemblyLine.setClosingTime(LocalTime.of(22, 0));
    assemblyLine.setOpeningTime(LocalTime.of(6, 0));
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
    carAssemblyProcess3 = new CarAssemblyProcess(
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
    carAssemblyProcess4 = new CarAssemblyProcess(
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
    assemblyLine.addCarAssemblyProcess(carAssemblyProcess4);
    assemblyLine.addCarToFinishedCars(carAssemblyProcess2);
    assemblyLine.addCarAssemblyProcess(carAssemblyProcess3);
    List<CarOption> carOptions = List.of(Body.BREAK,
      Color.BLACK,
      Engine.PERFORMANCE,
      Gearbox.FIVE_SPEED_MANUAL,
      Seat.LEATHER_BLACK,
      Airco.MANUAL,
      Wheel.SPORT,
      Spoiler.NO_SPOILER);
    specificationBatchScheduling = new SpecificationBatchScheduling(carOptions);
    assemblyLine.setSchedulingAlgorithm(specificationBatchScheduling);

  }


  @Test
  public void moveAssemblyLine_succeeds() {
    assertEquals(-1, specificationBatchScheduling.moveAssemblyLine(0, endTime, assemblyLine.getCarAssemblyProcessesQueueAsQueue(), assemblyLine.getFinishedCars(), assemblyLine.getWorkPosts()));
    assertEquals(carAssemblyProcess1, assemblyLine.getCarBodyPost().getCarAssemblyProcess());
    specificationBatchScheduling.moveAssemblyLine(0, endTime, assemblyLine.getCarAssemblyProcessesQueueAsQueue(), assemblyLine.getFinishedCars(), assemblyLine.getWorkPosts());
    specificationBatchScheduling.moveAssemblyLine(0, endTime, assemblyLine.getCarAssemblyProcessesQueueAsQueue(), assemblyLine.getFinishedCars(), assemblyLine.getWorkPosts());

    assertFalse(assemblyLine.getCarBodyPost().getCarAssemblyProcess().equals(carAssemblyProcess1));
    //if this next assertTrue fails, it means that must be something wrong with the batches
    assertTrue(assemblyLine.getCarBodyPost().getCarAssemblyProcess().equals(carAssemblyProcess3));
    specificationBatchScheduling.moveAssemblyLine(0, endTime, assemblyLine.getCarAssemblyProcessesQueueAsQueue(), assemblyLine.getFinishedCars(), assemblyLine.getWorkPosts());
    assertEquals(2, assemblyLine.getFinishedCars().size());
  }
}
