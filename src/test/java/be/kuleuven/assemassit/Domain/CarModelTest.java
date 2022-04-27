package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarModelTest {

  List<Wheel> wheels1 = Arrays.asList(Wheel.WINTER, Wheel.COMFORT, Wheel.SPORT);
  List<Wheel> wheels2 = Arrays.asList(Wheel.WINTER, Wheel.SPORT);

  List<Gearbox> gearboxes1 = Arrays.asList(Gearbox.SIX_SPEED_MANUAL, Gearbox.FIVE_SPEED_MANUAL, Gearbox.FIVE_SPEED_AUTOMATIC);
  List<Gearbox> gearboxes2 = Arrays.asList(Gearbox.SIX_SPEED_MANUAL, Gearbox.FIVE_SPEED_AUTOMATIC);
  List<Gearbox> gearboxes3 = Arrays.asList(Gearbox.SIX_SPEED_MANUAL);

  List<Seat> seats1 = Arrays.asList(Seat.LEATHER_WHITE, Seat.LEATHER_BLACK, Seat.VINYL_GREY);
  List<Seat> seats2 = Arrays.asList(Seat.LEATHER_WHITE, Seat.LEATHER_BLACK);

  List<Body> body1 = Arrays.asList(Body.SEDAN, Body.BREAK);
  List<Body> body2 = Arrays.asList(Body.SEDAN, Body.BREAK, Body.SPORT);
  List<Body> body3 = Arrays.asList(Body.SPORT);

  List<Color> color1 = Arrays.asList(Color.RED, Color.BLUE, Color.BLACK, Color.WHITE);
  List<Color> color2 = Arrays.asList(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW);
  List<Color> color3 = Arrays.asList(Color.BLACK, Color.WHITE);

  List<Engine> engines1 = Arrays.asList(Engine.STANDARD, Engine.PERFORMANCE);
  List<Engine> engines2 = Arrays.asList(Engine.STANDARD, Engine.PERFORMANCE, Engine.ULTRA);
  List<Engine> engines3 = Arrays.asList(Engine.PERFORMANCE, Engine.ULTRA);

  List<Airco> aircos1 = Arrays.asList(Airco.MANUAL, Airco.AUTOMATIC, Airco.NO_AIRCO);

  List<Spoiler> spoilers1 = Arrays.asList(Spoiler.NO_SPOILER);
  List<Spoiler> spoilers2 = Arrays.asList(Spoiler.LOW, Spoiler.NO_SPOILER);
  List<Spoiler> spoilers3 = Arrays.asList(Spoiler.LOW, Spoiler.HIGH);

  CarModel carModelA;
  CarModel carModelB;
  CarModel carModelC;

  @BeforeEach
  public void setup() {

    carModelA = new CarModel(
      0,
      "Model A",
      wheels1,
      gearboxes1,
      seats1,
      body1,
      color1,
      engines1,
      aircos1,
      spoilers1,
      50);
    carModelB = new CarModel(
      1,
      "Model B",
      wheels1,
      gearboxes2,
      seats1,
      body2,
      color2,
      engines2,
      aircos1,
      spoilers2,
      70);
    carModelC = new CarModel(2,
      "Model C",
      wheels2,
      gearboxes3,
      seats2,
      body3,
      color3,
      engines3,
      aircos1,
      spoilers3,
      60);
  }

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
        Arrays.asList(Airco.MANUAL, Airco.AUTOMATIC),
        Arrays.asList(Spoiler.NO_SPOILER)
      ));
  }


  @Test
  public void CarModelTest_throws() {
    assertThrows(IllegalArgumentException.class, () ->
      new CarModel(
        0,
        "",
        Arrays.asList(Wheel.COMFORT, Wheel.SPORT),
        Arrays.asList(Gearbox.FIVE_SPEED_AUTOMATIC, Gearbox.FIVE_SPEED_MANUAL),
        Arrays.asList(Seat.LEATHER_BLACK),
        Arrays.asList(Body.BREAK),
        Arrays.asList(Color.BLACK, Color.BLUE, Color.RED),
        Arrays.asList(Engine.PERFORMANCE, Engine.STANDARD),
        Arrays.asList(Airco.MANUAL, Airco.AUTOMATIC),
        Arrays.asList(Spoiler.NO_SPOILER)
      ));
    assertThrows(IllegalArgumentException.class, () ->
      new CarModel(
        0,
        "testmodel",
        null,
        Arrays.asList(Gearbox.FIVE_SPEED_AUTOMATIC, Gearbox.FIVE_SPEED_MANUAL),
        Arrays.asList(Seat.LEATHER_BLACK),
        Arrays.asList(Body.BREAK),
        Arrays.asList(Color.BLACK, Color.BLUE, Color.RED),
        Arrays.asList(Engine.PERFORMANCE, Engine.STANDARD),
        Arrays.asList(Airco.MANUAL, Airco.AUTOMATIC),
        Arrays.asList(Spoiler.NO_SPOILER)
      ));
    assertThrows(IllegalArgumentException.class, () ->
      new CarModel(
        0,
        "testmodel",
        Arrays.asList(),
        Arrays.asList(Gearbox.FIVE_SPEED_AUTOMATIC, Gearbox.FIVE_SPEED_MANUAL),
        Arrays.asList(Seat.LEATHER_BLACK),
        Arrays.asList(Body.BREAK),
        Arrays.asList(Color.BLACK, Color.BLUE, Color.RED),
        Arrays.asList(Engine.PERFORMANCE, Engine.STANDARD),
        Arrays.asList(Airco.MANUAL, Airco.AUTOMATIC),
        Arrays.asList(Spoiler.NO_SPOILER)
      ));

    List<Wheel> test = new ArrayList<>();
    test.add(null);
    assertThrows(IllegalArgumentException.class, () ->
      new CarModel(
        0,
        "testmodel",
        test,
        Arrays.asList(Gearbox.FIVE_SPEED_AUTOMATIC, Gearbox.FIVE_SPEED_MANUAL),
        Arrays.asList(Seat.LEATHER_BLACK),
        Arrays.asList(Body.BREAK),
        Arrays.asList(Color.BLACK, Color.BLUE, Color.RED),
        Arrays.asList(Engine.PERFORMANCE, Engine.STANDARD),
        Arrays.asList(Airco.MANUAL, Airco.AUTOMATIC),
        Arrays.asList(Spoiler.NO_SPOILER)
      ));
  }

  @Test
  public void carModelToString() {
    String expected = "0: testmodel";
    CarModel carModel = new CarModel(
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

    assertEquals(expected, carModel.toString());
  }

  @Test
  void properties() {


    assertEquals(carModelA.getWheelOptions(), wheels1);
    assertEquals(carModelA.getGearboxOptions(), gearboxes1);
    assertEquals(carModelA.getSeatOptions(), seats1);
    assertEquals(carModelA.getBodyOptions(), body1);
    assertEquals(carModelA.getColorOptions(), color1);
    assertEquals(carModelA.getEngineOptions(), engines1);
    assertEquals(carModelA.getAircoOptions(), aircos1);
    assertEquals(carModelA.getSpoilerOptions(), spoilers1);

    assertEquals(1, carModelB.getId());

    assertEquals("Model C", carModelC.getName());

    assertEquals(50, carModelA.getWorkPostDuration());
  }


  @Test
  void isValidConfigurationTest() {
    assertTrue(carModelA.isValidConfiguration(Body.BREAK, Color.BLACK, Engine.PERFORMANCE, Gearbox.FIVE_SPEED_MANUAL, Seat.LEATHER_BLACK, Airco.AUTOMATIC, Wheel.COMFORT, Spoiler.NO_SPOILER));
    assertFalse(carModelC.isValidConfiguration(Body.BREAK, Color.BLACK, Engine.PERFORMANCE, Gearbox.FIVE_SPEED_MANUAL, Seat.LEATHER_BLACK, Airco.AUTOMATIC, Wheel.COMFORT, Spoiler.NO_SPOILER));
  }
}
