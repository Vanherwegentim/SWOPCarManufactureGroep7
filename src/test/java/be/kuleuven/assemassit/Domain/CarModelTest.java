package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarModelTest {

  @Test
  public void CarModelTest_succeeds() {
    assertAll(() ->
      new CarModel(
        0,
        "testmodel",
        Arrays.asList(Wheel.COMFORT, Wheel.SPORT),
        Arrays.asList(Gearbox.FIVE_SPEED_AUTOMATIC, Gearbox.SIX_SPEED_MANUAL),
        Arrays.asList(Seat.LEATHER_BLACK),
        Arrays.asList(Body.BREAK),
        Arrays.asList(Color.BLACK, Color.BLUE, Color.RED),
        Arrays.asList(Engine.PERFORMANCE, Engine.STANDARD),
        Arrays.asList(Airco.MANUAL, Airco.AUTOMATIC)
      ));
  }

  @Test
  public void CarModelTest_throws() {
    assertThrows(IllegalArgumentException.class, () ->
      new CarModel(
        0,
        "",
        Arrays.asList(Wheel.COMFORT, Wheel.SPORT),
        Arrays.asList(Gearbox.AUTOMATIC, Gearbox.MANUAL),
        Arrays.asList(Seat.LEATHER_BLACK),
        Arrays.asList(Body.BREAK),
        Arrays.asList(Color.BLACK, Color.BLUE, Color.RED),
        Arrays.asList(Engine.PERFORMANCE, Engine.STANDARD),
        Arrays.asList(Airco.MANUAL, Airco.AUTOMATIC)
      ));
    assertThrows(IllegalArgumentException.class, () ->
      new CarModel(
        0,
        "testmodel",
        null,
        Arrays.asList(Gearbox.AUTOMATIC, Gearbox.MANUAL),
        Arrays.asList(Seat.LEATHER_BLACK),
        Arrays.asList(Body.BREAK),
        Arrays.asList(Color.BLACK, Color.BLUE, Color.RED),
        Arrays.asList(Engine.PERFORMANCE, Engine.STANDARD),
        Arrays.asList(Airco.MANUAL, Airco.AUTOMATIC)
      ));
    assertThrows(IllegalArgumentException.class, () ->
      new CarModel(
        0,
        "testmodel",
        Arrays.asList(),
        Arrays.asList(Gearbox.AUTOMATIC, Gearbox.MANUAL),
        Arrays.asList(Seat.LEATHER_BLACK),
        Arrays.asList(Body.BREAK),
        Arrays.asList(Color.BLACK, Color.BLUE, Color.RED),
        Arrays.asList(Engine.PERFORMANCE, Engine.STANDARD),
        Arrays.asList(Airco.MANUAL, Airco.AUTOMATIC)
      ));

    List<Wheel> test = new ArrayList<>();
    test.add(null);
    assertThrows(IllegalArgumentException.class, () ->
      new CarModel(
        0,
        "testmodel",
        test,
        Arrays.asList(Gearbox.AUTOMATIC, Gearbox.MANUAL),
        Arrays.asList(Seat.LEATHER_BLACK),
        Arrays.asList(Body.BREAK),
        Arrays.asList(Color.BLACK, Color.BLUE, Color.RED),
        Arrays.asList(Engine.PERFORMANCE, Engine.STANDARD),
        Arrays.asList(Airco.MANUAL, Airco.AUTOMATIC)
      ));
  }

  @Test
  public void carModelToString() {
    String expected = "0: testmodel";
    CarModel carModel = new CarModel(
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

    assertEquals(expected, carModel.toString());
  }
}
