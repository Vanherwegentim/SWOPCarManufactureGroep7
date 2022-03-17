package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;

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
 */
public class Car {
  private static int idRunner = 0;

  private int id;
  /**
   * @representationObject
   */
  private CarModel carModel;
  private Body body;
  private Color color;
  private Engine engine;
  private Gearbox gearbox;
  private Seat seats;
  private Airco airco;
  private Wheel wheels;


  /**
   * @param carModel the corresponding car model that the car is based on
   * @param body
   * @param color
   * @param engine
   * @param gearbox
   * @param seats
   * @param airco
   * @param wheels`
   * @throws IllegalArgumentException | carModel == null
   * @throws IllegalArgumentException | body == null || color == null || engine == null || gearbox == null || seats == null || airco == null || wheels == null
   * @post | this.carModel = carModel
   * @post | this.body = body
   * @post | this.color = Color
   * @post | this.engine = engine
   * @post | this.gearbox = gearbox
   * @post | this.seats = seats
   * @post | this.airco = airco
   * @post | this.wheels = wheels
   */
  public Car(CarModel carModel, Body body, Color color, Engine engine, Gearbox gearbox, Seat seats, Airco airco, Wheel wheels) {
    if (carModel == null)
      throw new IllegalArgumentException("Car Model cannot be null");
    if (body == null || color == null || engine == null || gearbox == null || seats == null || airco == null || wheels == null)
      throw new IllegalArgumentException("Assembly tasks cannot be null");
    if (!carModel.isValidConfiguration(body, color, engine, gearbox, seats, airco, wheels))
      throw new IllegalArgumentException("Invalid car configuration");

    this.carModel = carModel;
    this.body = body;
    this.color = color;
    this.engine = engine;
    this.gearbox = gearbox;
    this.seats = seats;
    this.airco = airco;
    this.wheels = wheels;
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

  public int getId() {
    return this.id;
  }
}
