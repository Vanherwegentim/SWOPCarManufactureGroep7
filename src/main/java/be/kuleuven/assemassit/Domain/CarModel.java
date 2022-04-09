package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;

import java.util.List;

/**
 * @mutable
 * @invar | getWheelOptions() != null && !getWheelsOptions().contains(null) && !getWheelOptions().isEmpty()
 * @invar | getGearboxOptions() != null && !getGearboxOptions().contains(null) && !getGearboxOptions().isEmpty()
 * @invar | getSeatOptions() != null && !getSeatOptions().contains(null) && !getSeatOptions().isEmpty()
 * @invar | getBodyOptions() != null && !getBodyOptions().contains(null) && !getBodyOptions().isEmpty()
 * @invar | getColorOptions() != null && !getColorOptions().contains(null) && !getColorOptions().isEmpty()
 * @invar | getEngineOptions() != null && !getEngineOptions().contains(null) && !getEngineOptions().isEmpty()
 * @invar | getAircoOptions() != null && !getAircoOptions().contains(null) && !getAircoOptions().isEmpty()
 */
public class CarModel {
  private int id;
  private String name;

  /**
   * @invar | wheelOptions != null && !wheelsOptions.contains(null) && !wheelOptions.isEmpty()
   * @invar | gearboxOptions != null && !gearboxOptions.contains(null) && !gearboxOptions.isEmpty()
   * @invar | seatOptions != null && !seatOptions.contains(null) && !seatOptions.isEmpty()
   * @invar | bodyOptions != null && !bodyOptions.contains(null) && !bodyOptions.isEmpty()
   * @invar | colorOptions != null && !colorOptions.contains(null) && !colorOptions.isEmpty()
   * @invar | engineOptions != null && !engineOptions.contains(null) && !engineOptions.isEmpty()
   * @invar | aircoOptions != null && !aircoOptions.contains(null) && !aircoOptions.isEmpty()
   * @representationObject
   */
  private List<Wheel> wheelOptions;
  /**
   * @representationObject
   */
  private List<Gearbox> gearboxOptions;
  /**
   * @representationObject
   */
  private List<Seat> seatOptions;
  /**
   * @representationObject
   */
  private List<Body> bodyOptions;
  /**
   * @representationObject
   */
  private List<Color> colorOptions;
  /**
   * @representationObject
   */
  private List<Engine> engineOptions;
  /**
   * @representationObject
   */
  private List<Airco> aircoOptions;

  private List<Spoiler> spoilerOptions;

  /**
   * @param id
   * @param name
   * @param wheelOptions
   * @param gearboxOptions
   * @param seatOptions
   * @param bodyOptions
   * @param colorOptions
   * @param engineOptions
   * @param aircoOptions
   * @throws IllegalArgumentException name can not be null or empty
   *                                  | name == null || name.equals("")
   * @throws IllegalArgumentException options can not be null or empty
   *                                  | (wheelOptions == null || gearboxOptions == null || seatOptions == null || bodyOptions == null || colorOptions == null || engineOptions == null || aircoOptions == null ||
   *                                  wheelOptions.isEmpty() || gearboxOptions.isEmpty() || seatOptions.isEmpty() || bodyOptions.isEmpty() || colorOptions.isEmpty() || engineOptions.isEmpty() || aircoOptions.isEmpty())
   * @throws IllegalArgumentException options can not contain null
   *                                  | (wheelOptions.contains(null) || gearboxOptions.contains(null) || seatOptions.contains(null) || bodyOptions.contains(null) || colorOptions.contains(null) || engineOptions.contains(null) || aircoOptions.contains(null))
   * @mutates | this
   */
//  public CarModel(int id, String name, List<Wheel> wheelOptions, List<Gearbox> gearboxOptions, List<Seat> seatOptions, List<Body> bodyOptions, List<Color> colorOptions, List<Engine> engineOptions, List<Airco> aircoOptions) {
//    if (name == "" || name.equals(null))
//      throw new IllegalArgumentException("Car model name cannot be empty or null");
//    if (wheelOptions == null || gearboxOptions == null || seatOptions == null || bodyOptions == null || colorOptions == null || engineOptions == null || aircoOptions == null ||
//      wheelOptions.isEmpty() || gearboxOptions.isEmpty() || seatOptions.isEmpty() || bodyOptions.isEmpty() || colorOptions.isEmpty() || engineOptions.isEmpty() || aircoOptions.isEmpty())
//      throw new IllegalArgumentException("Car model options cannot be empty or null");
//    if (wheelOptions.contains(null) || gearboxOptions.contains(null) || seatOptions.contains(null) || bodyOptions.contains(null) || colorOptions.contains(null) || engineOptions.contains(null) || aircoOptions.contains(null))
//      throw new IllegalArgumentException("Car model options cannot contain null");
//    this.id = id;
//    this.name = name;
//    this.wheelOptions = List.copyOf(wheelOptions);
//    this.gearboxOptions = List.copyOf(gearboxOptions);
//    this.seatOptions = List.copyOf(seatOptions);
//    this.bodyOptions = List.copyOf(bodyOptions);
//    this.colorOptions = List.copyOf(colorOptions);
//    this.engineOptions = List.copyOf(engineOptions);
//    this.aircoOptions = List.copyOf(aircoOptions);
//  }
  public CarModel(int id, String name, List<Wheel> wheelOptions, List<Gearbox> gearboxOptions, List<Seat> seatOptions, List<Body> bodyOptions, List<Color> colorOptions, List<Engine> engineOptions, List<Airco> aircoOptions, List<Spoiler> spoilerOptions) {
    if (name == "" || name.equals(null))
      throw new IllegalArgumentException("Car model name cannot be empty or null");
    if (wheelOptions == null || gearboxOptions == null || seatOptions == null || bodyOptions == null || colorOptions == null || engineOptions == null || aircoOptions == null || spoilerOptions == null ||
      wheelOptions.isEmpty() || gearboxOptions.isEmpty() || seatOptions.isEmpty() || bodyOptions.isEmpty() || colorOptions.isEmpty() || engineOptions.isEmpty() || aircoOptions.isEmpty() || spoilerOptions.isEmpty())
      throw new IllegalArgumentException("Car model options cannot be empty or null");
    if (wheelOptions.contains(null) || gearboxOptions.contains(null) || seatOptions.contains(null) || bodyOptions.contains(null) || colorOptions.contains(null) || engineOptions.contains(null) || aircoOptions.contains(null) || spoilerOptions.contains(null))
      throw new IllegalArgumentException("Car model options cannot contain null");
    this.id = id;
    this.name = name;
    this.wheelOptions = List.copyOf(wheelOptions);
    this.gearboxOptions = List.copyOf(gearboxOptions);
    this.seatOptions = List.copyOf(seatOptions);
    this.bodyOptions = List.copyOf(bodyOptions);
    this.colorOptions = List.copyOf(colorOptions);
    this.engineOptions = List.copyOf(engineOptions);
    this.aircoOptions = List.copyOf(aircoOptions);
    this.spoilerOptions = List.copyOf(spoilerOptions);
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

  /**
   * Method checks if a give configuration of options is valid for the car model
   *
   * @param body
   * @param color
   * @param engine
   * @param gearbox
   * @param seats
   * @param airco
   * @param wheels
   * @return true or false according to the validity of the configuration
   * @inspects | this, body, color, engine, gearbox, seats, airco, wheels
   * @post | result == (getBodyOptions.contains(body) &&
   * |getColorOptions.contains(color) &&
   * |getEngineOptions.contains(engine) &&
   * |getGearboxOptions.contains(gearbox) &&
   * |getSeatOptions.contains(seats) &&
   * |getAircoOptions.contains(airco) &&
   * |getWheelOptions.contains(wheels))
   */
  public boolean isValidConfiguration(Body body, Color color, Engine engine, Gearbox gearbox, Seat seats, Airco airco, Wheel wheels, Spoiler spoiler) {
    return bodyOptions.contains(body) &&
      colorOptions.contains(color) &&
      engineOptions.contains(engine) &&
      gearboxOptions.contains(gearbox) &&
      seatOptions.contains(seats) &&
      aircoOptions.contains(airco) &&
      wheelOptions.contains(wheels) &&
      //spoiler can be null, contains returns true if spoiler is null
      spoilerOptions.contains(spoiler);


  }

  @Override
  public String toString() {
    return id + ": " + name;
  }
}
