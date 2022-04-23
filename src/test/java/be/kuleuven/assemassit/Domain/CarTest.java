package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {

  private CarModel carModel;
  private Car car;

  @BeforeEach
  public void BeforeEach() {
    carModel = new CarModel(
      0,
      "testmodel",
      Arrays.asList(Wheel.COMFORT, Wheel.SPORT),
      Arrays.asList(Gearbox.AUTOMATIC, Gearbox.MANUAL),
      Arrays.asList(Seat.LEATHER_BLACK),
      Arrays.asList(Body.BREAK),
      Arrays.asList(Color.BLACK, Color.BLUE, Color.RED),
      Arrays.asList(Engine.PERFORMANCE, Engine.STANDARD),
      Arrays.asList(Airco.MANUAL, Airco.AUTOMATIC)
    );

    car = new Car(carModel, Body.BREAK, Color.BLACK, Engine.PERFORMANCE, Gearbox.MANUAL, Seat.LEATHER_BLACK, Airco.AUTOMATIC, Wheel.COMFORT);
  }


  @Test
  public void carTest_succeeds() {
    assertAll(() ->
      new Car(carModel, Body.BREAK, Color.BLACK, Engine.PERFORMANCE, Gearbox.MANUAL, Seat.LEATHER_BLACK, Airco.AUTOMATIC, Wheel.COMFORT)
    );
  }

  @Test
  public void carTest_throws() {
    assertThrows(IllegalArgumentException.class, () ->
      new Car(carModel, Body.SEAD, Color.BLACK, Engine.PERFORMANCE, Gearbox.MANUAL, Seat.LEATHER_BLACK, Airco.AUTOMATIC, Wheel.COMFORT)
    );
    assertThrows(IllegalArgumentException.class, () ->
      new Car(null, Body.BREAK, Color.BLACK, Engine.PERFORMANCE, Gearbox.MANUAL, Seat.LEATHER_BLACK, Airco.AUTOMATIC, Wheel.COMFORT)
    );
    assertThrows(IllegalArgumentException.class, () ->
      new Car(carModel, Body.BREAK, Color.BLACK, null, Gearbox.MANUAL, Seat.LEATHER_BLACK, Airco.AUTOMATIC, Wheel.COMFORT)
    );
  }

  @Test
  public void carEqualsTest_True() {
    Car car = new Car(carModel, Body.BREAK, Color.BLACK, Engine.PERFORMANCE, Gearbox.MANUAL, Seat.LEATHER_BLACK, Airco.AUTOMATIC, Wheel.COMFORT);
    Car car2 = new Car(carModel, Body.BREAK, Color.BLACK, Engine.PERFORMANCE, Gearbox.MANUAL, Seat.LEATHER_BLACK, Airco.AUTOMATIC, Wheel.COMFORT);
    assertTrue(car.equals(car2));
    assertTrue(car2.equals(car));
  }

  @Test
  public void carEqualsTest_False() {
    Car car = new Car(carModel, Body.BREAK, Color.BLACK, Engine.PERFORMANCE, Gearbox.MANUAL, Seat.LEATHER_BLACK, Airco.MANUAL, Wheel.COMFORT);
    Car car2 = new Car(carModel, Body.BREAK, Color.BLACK, Engine.PERFORMANCE, Gearbox.MANUAL, Seat.LEATHER_BLACK, Airco.AUTOMATIC, Wheel.COMFORT);
    assertFalse(car.equals(car2));
    assertFalse(car2.equals(car));
  }
}
