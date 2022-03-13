package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;

import java.util.List;

public class CarModel {
  private int id;
  private String name;

  private List<Wheel> wheelOptions;
  private List<Gearbox> gearboxOptions;
  private List<Seat> seatOptions;
  private List<Body> bodyOptions;
  private List<Color> colorOptions;
  private List<Engine> engineOptions;
  private List<Airco> aircoOptions;

  public CarModel(int id, String name, List<Wheel> wheelOptions, List<Gearbox> gearboxOptions, List<Seat> seatOptions, List<Body> bodyOptions, List<Color> colorOptions, List<Engine> engineOptions, List<Airco> aircoOptions) {
    this.id = id;
    this.name = name;
    this.wheelOptions = List.copyOf(wheelOptions);
    this.gearboxOptions = List.copyOf(gearboxOptions);
    this.seatOptions = List.copyOf(seatOptions);
    this.bodyOptions = List.copyOf(bodyOptions);
    this.colorOptions = List.copyOf(colorOptions);
    this.engineOptions = List.copyOf(engineOptions);
    this.aircoOptions = List.copyOf(aircoOptions);
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return name;
  }

  public List<Wheel> getWheelOptions() {
    return wheelOptions;
  }

  public List<Gearbox> getGearboxOptions() {
    return gearboxOptions;
  }

  public List<Seat> getSeatOptions() {
    return seatOptions;
  }

  public List<Body> getBodyOptions() {
    return bodyOptions;
  }

  public List<Color> getColorOptions() {
    return colorOptions;
  }

  public List<Engine> getEngineOptions() {
    return engineOptions;
  }

  public List<Airco> getAircoOptions() {
    return aircoOptions;
  }

  public boolean isValidConfiguration(Body body, Color color, Engine engine, Gearbox gearbox, Seat seats, Airco airco, Wheel wheels) {
    return
      bodyOptions.contains(body) &&
      colorOptions.contains(color) &&
      engineOptions.contains(engine) &&
      gearboxOptions.contains(gearbox) &&
      seatOptions.contains(seats) &&
      aircoOptions.contains(airco);
  }

  @Override
  public String toString() {
    return Integer.toString(id) + ": " + name;
  }
}
