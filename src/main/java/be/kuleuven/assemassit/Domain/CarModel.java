package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;

import java.util.List;

public class CarModel {
  private String name;

  private List<Wheel> wheelOptions;
  private List<Gearbox> gearboxOptions;
  private List<Seat> seatOptions;
  private List<Body> bodyOptions;
  private List<Color> colorOptions;
  private List<Engine> engineOptions;

  public CarModel(String name, List<Wheel> wheelOptions, List<Gearbox> gearboxOptions, List<Seat> seatOptions, List<Body> bodyOptions, List<Color> colorOptions, List<Engine> engineOptions) {
    this.name = name;
    this.wheelOptions = List.copyOf(wheelOptions);
    this.gearboxOptions = List.copyOf(gearboxOptions);
    this.seatOptions = List.copyOf(seatOptions);
    this.bodyOptions = List.copyOf(bodyOptions);
    this.colorOptions = List.copyOf(colorOptions);
    this.engineOptions = List.copyOf(engineOptions);
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

  @Override
  public String toString() {
    return "Model " + name;
  }
}
