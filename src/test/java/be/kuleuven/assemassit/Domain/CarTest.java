package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    Car car = new Car(carModel, Body.BREAK, Color.BLACK, Engine.PERFORMANCE, Gearbox.FIVE_SPEED_MANUAL, Seat.LEATHER_BLACK, Airco.AUTOMATIC, Wheel.COMFORT, Spoiler.NO_SPOILER);
    Car car2 = new Car(carModel, Body.BREAK, Color.BLACK, Engine.PERFORMANCE, Gearbox.FIVE_SPEED_MANUAL, Seat.LEATHER_BLACK, Airco.AUTOMATIC, Wheel.COMFORT, Spoiler.NO_SPOILER);
    assertEquals(car, car2);
    assertEquals(car2, car);
  }

  @Test
  public void carEqualsTest_False() {
    Car car = new Car(carModel, Body.BREAK, Color.BLACK, Engine.PERFORMANCE, Gearbox.FIVE_SPEED_MANUAL, Seat.LEATHER_BLACK, Airco.MANUAL, Wheel.COMFORT, Spoiler.NO_SPOILER);
    Car car2 = new Car(carModel, Body.BREAK, Color.BLACK, Engine.PERFORMANCE, Gearbox.FIVE_SPEED_MANUAL, Seat.LEATHER_BLACK, Airco.AUTOMATIC, Wheel.COMFORT, Spoiler.NO_SPOILER);
    assertNotEquals(car, car2);
    assertNotEquals(car2, car);
  }

  @Test
  void getCarModel() {
    assertEquals(carModel, car.getCarModel());
  }

  @Test
  void testHashCode() {
    List<CarOption> carOptions = new ArrayList<>();

    carOptions.add(car.getBody());
    carOptions.add(car.getColor());
    carOptions.add(car.getEngine());
    carOptions.add(car.getGearbox());
    carOptions.add(car.getSeats());
    carOptions.add(car.getAirco());
    carOptions.add(car.getWheels());
    carOptions.add(car.getSpoiler());

    assertEquals(carOptions.stream().map(Object::hashCode).mapToInt(Integer::intValue).sum(), car.hashCode());
  }

  @Test
  void getId() {
    int id = car.getId();
    assertTrue(id >= 0);
  }
}
