package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @mutable
 * @invar | getCarModel() != null
 * @invar | getBody() != null
 * @invar | getColor() != null
 * @invar | getEngine() != null
 * @invar | getGearbox() != null
 * @invar | getSeats() != null
 * @invar | getAirco() != null
 * @invar | getWheels() != null
 * @invar | getCarOptionRestrictions() != null
 */
public class Car {
  private static int idRunner = 0;

  /**
   * @invar | carModel != null
   * @invar | body != null
   * @invar | color != null
   * @invar | engine != null
   * @invar | gearbox != null
   * @invar | seats != null
   * @invar | airco != null
   * @invar | wheels != null
   * @invar | spoiler != null
   * @invar | carOptionRestrictions != null
   */
  private final int id;
  private final CarModel carModel;
  private final Body body;
  private final Color color;
  private final Engine engine;
  private final Gearbox gearbox;
  private final Seat seats;
  private final Airco airco;
  private final Wheel wheels;
  private final Spoiler spoiler;

  /**
   * @representationObject
   */
  private List<CarOptionRestriction> carOptionRestrictions;

  /**
   * @param carModel the corresponding car model that the car is based on
   * @param body     the body car option
   * @param color    the color car option
   * @param engine   the engine car option
   * @param gearbox  the gearbox car option
   * @param seats    the seat car option
   * @param airco    the airco car option
   * @param wheels   the wheel car option
   * @param spoiler  the spoiler car option
   * @throws IllegalArgumentException | carModel == null
   * @throws IllegalArgumentException | body == null || color == null || engine == null || gearbox == null || seats == null || airco == null || wheels == null
   * @post | this.carModel == carModel
   * @post | this.body == body
   * @post | this.color == Color
   * @post | this.engine == engine
   * @post | this.gearbox == gearbox
   * @post | this.seats == seats
   * @post | this.airco == airco
   * @post | this.wheels == wheels
   * @post | this.spoiler == spoiler
   */
  public Car(CarModel carModel, Body body, Color color, Engine engine, Gearbox gearbox, Seat seats, Airco airco, Wheel wheels, Spoiler spoiler) {
    if (carModel == null)
      throw new IllegalArgumentException("Car Model cannot be null");
    if (body == null || color == null || engine == null || gearbox == null || seats == null || wheels == null || airco == null || spoiler == null)
      throw new IllegalArgumentException("Assembly tasks cannot be null");
    if (!carModel.isValidConfiguration(body, color, engine, gearbox, seats, airco, wheels, spoiler))
      throw new IllegalArgumentException("Invalid car configuration");

    this.carModel = carModel;
    this.body = body;
    this.color = color;
    this.engine = engine;
    this.gearbox = gearbox;
    this.seats = seats;
    this.airco = airco;
    this.wheels = wheels;
    this.spoiler = spoiler;

    for (CarOptionRestriction restriction : getCarOptionsRestrictions()) {
      if (restriction.getRestrictedCarOptions().stream().allMatch(co -> this.giveListOfCarOptions().contains(co)))
        throw new IllegalArgumentException("Invalid car configuration");
    }

    this.id = Car.idRunner++;
  }

  public CarModel getCarModel() {
    return carModel;
  }

  public Body getBody() {
    return body;
  }

  public Color getColor() {
    return color;
  }

  public Engine getEngine() {
    return engine;
  }

  public Gearbox getGearbox() {
    return gearbox;
  }

  public Seat getSeats() {
    return seats;
  }

  public Airco getAirco() {
    return airco;
  }

  public Wheel getWheels() {
    return wheels;
  }

  public Spoiler getSpoiler() {
    return spoiler;
  }

  public int getId() {
    return this.id;
  }

  /**
   * @return All the car options in one list
   * @inspects | this
   */
  public List<CarOption> giveListOfCarOptions() {
    List<CarOption> carOptions = new ArrayList<>();

    carOptions.add(this.getBody());
    carOptions.add(this.getColor());
    carOptions.add(this.getEngine());
    carOptions.add(this.getGearbox());
    carOptions.add(this.getSeats());
    carOptions.add(this.getAirco());
    carOptions.add(this.getWheels());
    carOptions.add(this.getSpoiler());

    return carOptions;
  }

  private List<CarOptionRestriction> getCarOptionsRestrictions() {
    if (carOptionRestrictions == null) {
      carOptionRestrictions = new ArrayList<>();

      carOptionRestrictions.add(new CarOptionRestriction(Arrays.asList(Body.SPORT, Spoiler.NO_SPOILER)));
      carOptionRestrictions.add(new CarOptionRestriction(Arrays.asList(Body.SPORT, Engine.STANDARD)));
      carOptionRestrictions.add(new CarOptionRestriction(Arrays.asList(Engine.ULTRA, Airco.AUTOMATIC)));
    }

    return carOptionRestrictions;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Car) {
      Car car = (Car) o;
      return
        (car.giveListOfCarOptions().stream().allMatch(co ->
          this.giveListOfCarOptions().stream().anyMatch(co::equals)
        ))
          &&
          (this.giveListOfCarOptions().stream().allMatch(co ->
            car.giveListOfCarOptions().stream().anyMatch(co::equals)
          ));
    }
    return false;
  }

  @Override
  public int hashCode() {
    return giveListOfCarOptions().stream().map(Object::hashCode).mapToInt(Integer::intValue).sum();
  }
}
