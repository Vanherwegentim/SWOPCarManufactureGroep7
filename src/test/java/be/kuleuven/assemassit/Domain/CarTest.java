package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CarTest {

  private CarModel carModel;
  private Car car;

  @BeforeEach
  public void BeforeEach() {
    carModel = new CarModel(
      0,
      "testmodel",
      Arrays.asList(Wheel.COMFORT, Wheel.SPORT),
      Arrays.asList(Gearbox.FIVE_SPEED_AUTOMATIC, Gearbox.FIVE_SPEED_MANUAL),
      Arrays.asList(Seat.LEATHER_BLACK),
      Arrays.asList(Body.BREAK),
      Arrays.asList(Color.BLACK, Color.BLUE, Color.RED),
      Arrays.asList(Engine.PERFORMANCE, Engine.STANDARD),
      Arrays.asList(Airco.MANUAL, Airco.AUTOMATIC),
      Arrays.asList(Spoiler.NO_SPOILER)
    );

    car = new Car(carModel, Body.BREAK, Color.BLACK, Engine.PERFORMANCE, Gearbox.FIVE_SPEED_MANUAL, Seat.LEATHER_BLACK, Airco.AUTOMATIC, Wheel.COMFORT, Spoiler.NO_SPOILER);
  }


  @Test
  public void carTest_succeeds() {
    assertAll(() ->
      new Car(carModel, Body.BREAK, Color.BLACK, Engine.PERFORMANCE, Gearbox.FIVE_SPEED_MANUAL, Seat.LEATHER_BLACK, Airco.AUTOMATIC, Wheel.COMFORT, Spoiler.NO_SPOILER)
    );
  }

  @Test
  public void carTest_throws() {
    assertThrows(IllegalArgumentException.class, () ->
      new Car(carModel, Body.SEDAN, Color.BLACK, Engine.PERFORMANCE, Gearbox.FIVE_SPEED_MANUAL, Seat.LEATHER_BLACK, Airco.AUTOMATIC, Wheel.COMFORT, Spoiler.NO_SPOILER)
    );
    assertThrows(IllegalArgumentException.class, () ->
      new Car(null, Body.BREAK, Color.BLACK, Engine.PERFORMANCE, Gearbox.FIVE_SPEED_MANUAL, Seat.LEATHER_BLACK, Airco.AUTOMATIC, Wheel.COMFORT, Spoiler.NO_SPOILER)
    );
    assertThrows(IllegalArgumentException.class, () ->
      new Car(carModel, Body.BREAK, Color.BLACK, null, Gearbox.FIVE_SPEED_MANUAL, Seat.LEATHER_BLACK, Airco.AUTOMATIC, Wheel.COMFORT, Spoiler.NO_SPOILER)
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
