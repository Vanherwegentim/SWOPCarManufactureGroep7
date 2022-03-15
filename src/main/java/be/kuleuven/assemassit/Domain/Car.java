package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.Enums.Color;

import java.awt.*;

public class Car {
  private CarModel carModel;
  private Body body;
  private Color color;
  private Engine engine;
  private Gearbox gearbox;
  private Seat seats;
  private Airco airco;
  private Wheel wheels;


  public Car(CarModel carModel, Body body, Color color, Engine engine, Gearbox gearbox, Seat seats, Airco airco, Wheel wheels ) {
    if(carModel == null)
      throw new IllegalArgumentException("Car Model cannot be null");
    if(body == null || color == null || engine == null || gearbox == null || seats == null || airco == null || wheels == null)
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

  public String toString() {
    String result = "Car with model: " + carModel.getName() + "\n";
    result = "Body: " + body.toString() + "\n";

    result += "Color: " + color.toString() + "\n";
    result += "Engine: " + engine.toString() + "\n";
    result += "Gearbox: " + gearbox.toString() + "\n";
    result += "Airco: " + airco.toString() + "\n";
    result += "Wheels: " + wheels.toString();

    return result;
  }
}
