package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;

public class Car {
    CarModel carModel;
    Body body;
    Color color;
    Engine engine;
    Gearbox gearbox;
    Seat seats;
    Airco airco;
    Wheel wheels;

    public Car(CarModel model, Body body, Color color, Engine engine, Gearbox gearbox,Seat seats, Airco airco, Wheel wheels ){
        this.carModel = model;
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
      String result = "Body: " + body.toString() + "\n";

      result += "Color: " + color.toString() + "\n";
      result += "Engine: " + engine.toString() + "\n";
      result += "Gearbox: " + gearbox.toString() + "\n";
      result += "Airco: " + airco.toString() + "\n";
      result += "Wheels: " + wheels.toString();

      return result;
    }
}
