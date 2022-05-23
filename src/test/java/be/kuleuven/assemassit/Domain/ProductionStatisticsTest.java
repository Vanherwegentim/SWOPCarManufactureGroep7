package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.Helper.CustomTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductionStatisticsTest {

  private AssemblyLine assemblyLine;
  private ProductionStatistics productionStatistics;
  private CarAssemblyProcess carAssemblyProcess1;
  private CarAssemblyProcess carAssemblyProcess2;
  private CarAssemblyProcess carAssemblyProcess3;


  @BeforeEach
  public void beforeEach() {
    this.assemblyLine = new AssemblyLine();
    assemblyLine.setOpeningTime(LocalTime.of(6, 0));
    assemblyLine.setClosingTime(LocalTime.of(22, 0));
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
    productionStatistics = new ProductionStatistics(assemblyLine.getFinishedCars());
  }


  private void extraSetup() {

    carAssemblyProcess2.complete();
    carAssemblyProcess2.getCarOrder().setCompletionTime((CustomTime.getInstance().customLocalDateTimeNow()));
    carAssemblyProcess2.getCarOrder().setEstimatedCompletionTime((CustomTime.getInstance().customLocalDateTimeNow()));
    assemblyLine.addCarToFinishedCars(carAssemblyProcess2);
    assemblyLine.addCarToFinishedCars(carAssemblyProcess2);
    assemblyLine.addCarToFinishedCars(carAssemblyProcess2);


    carAssemblyProcess3.complete();
    carAssemblyProcess3.getCarOrder().setCompletionTime((CustomTime.getInstance().customLocalDateTimeNow()).minusDays(1));
    carAssemblyProcess3.getCarOrder().setEstimatedCompletionTime((CustomTime.getInstance().customLocalDateTimeNow()).minusDays(1).plusHours(3));
    assemblyLine.addCarToFinishedCars(carAssemblyProcess3);
    assemblyLine.addCarToFinishedCars(carAssemblyProcess3);
    productionStatistics = new ProductionStatistics(assemblyLine.getFinishedCars());

  }

  @Test
  public void createCarsPerDayMapTest() {
    extraSetup();
    assertEquals(Map.of(carAssemblyProcess2.getCarOrder().getCompletionTime().toLocalDate(), 3.0, carAssemblyProcess3.getCarOrder().getCompletionTime().toLocalDate(), 2.0), productionStatistics.createCarsPerDayMap());

  }

  @Test
  public void averageCarsInADayTest() {
    extraSetup();
    assertEquals(productionStatistics.averageCarsInADay(), 2.5);
  }

  @Test
  public void averageCarsInADayTest2() {
    assertEquals(productionStatistics.averageCarsInADay(), 0);
  }


  @Test
  public void medianCarsInADayTest() {
    extraSetup();
    assertEquals(2.5, productionStatistics.medianCarsInADay());
  }

  @Test
  public void exactCarsIn2DaysTest() {
    extraSetup();
    assertEquals(2.0, productionStatistics.exactCarsIn2Days());
  }

  @Test
  public void averageDelayPerOrderTest() {
    extraSetup();
    assertEquals(1.2, productionStatistics.averageDelayPerOrder());
  }

  @Test
  public void medianDelayPerOrderTest() {
    assertEquals(0, productionStatistics.medianDelayPerOrder());
  }

  @Test
  public void last2DelaysTest() {
    extraSetup();
    assertEquals(productionStatistics.last2Delays(), Map.of(carAssemblyProcess2.getCarOrder().getCompletionTime().toLocalDate(), 0));
  }

}
