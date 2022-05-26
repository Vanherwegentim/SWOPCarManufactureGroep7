package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;

import java.util.List;
import java.util.Objects;

/**
 * @mutable
 * @invar | getWheelOptions() != null && !getWheelsOptions().contains(null) && !getWheelOptions().isEmpty()
 * @invar | getGearboxOptions() != null && !getGearboxOptions().contains(null) && !getGearboxOptions().isEmpty()
 * @invar | getSeatOptions() != null && !getSeatOptions().contains(null) && !getSeatOptions().isEmpty()
 * @invar | getBodyOptions() != null && !getBodyOptions().contains(null) && !getBodyOptions().isEmpty()
 * @invar | getColorOptions() != null && !getColorOptions().contains(null) && !getColorOptions().isEmpty()
 * @invar | getEngineOptions() != null && !getEngineOptions().contains(null) && !getEngineOptions().isEmpty()
 * @invar | getAircoOptions() != null && !getAircoOptions().contains(null) && !getAircoOptions().isEmpty()
 * @invar | getSpoilerOptions() != null && !getSpoilerOptions().contains(null) && !getSpoilerOptions().isEmpty()
 */
public class CarModel {
  private final int id;
  private final String name;
  private final int workPostDuration;

  /**
   * @invar | wheelOptions != null && !wheelsOptions.contains(null) && !wheelOptions.isEmpty()
   * @invar | gearboxOptions != null && !gearboxOptions.contains(null) && !gearboxOptions.isEmpty()
   * @invar | seatOptions != null && !seatOptions.contains(null) && !seatOptions.isEmpty()
   * @invar | bodyOptions != null && !bodyOptions.contains(null) && !bodyOptions.isEmpty()
   * @invar | colorOptions != null && !colorOptions.contains(null) && !colorOptions.isEmpty()
   * @invar | engineOptions != null && !engineOptions.contains(null) && !engineOptions.isEmpty()
   * @invar | aircoOptions != null && !aircoOptions.contains(null) && !aircoOptions.isEmpty()
   * @invar | spoilerOptions != null && !spoilerOptions.contains(null) && !spoilerOptions.isEmpty()
   * @representationObject
   */
  private final List<Wheel> wheelOptions;
  /**
   * @representationObject
   */
  private final List<Gearbox> gearboxOptions;
  /**
   * @representationObject
   */
  private final List<Seat> seatOptions;
  /**
   * @representationObject
   */
  private final List<Body> bodyOptions;
  /**
   * @representationObject
   */
  private final List<Color> colorOptions;
  /**
   * @representationObject
   */
  private final List<Engine> engineOptions;
  /**
   * @representationObject
   */
  private final List<Airco> aircoOptions;

  /**
   * @representationObject
   */
  private final List<Spoiler> spoilerOptions;

  /**
   * @param id             car model ID
   * @param name           name of the car model
   * @param wheelOptions   list of acceptable wheel options for the car model
   * @param gearboxOptions list of acceptable gearbox options for the car model
   * @param seatOptions    list of acceptable seat options for the car model
   * @param bodyOptions    list of acceptable body options for the car model
   * @param colorOptions   list of acceptable color options for the car model
   * @param engineOptions  list of acceptable engine options for the car model
   * @param aircoOptions   list of acceptable airco options for the car model
   * @param spoilerOptions list of acceptable spoiler options for the car model
   * @throws IllegalArgumentException name can not be null or empty
   *                                  | name == null || name.equals("")
   * @throws IllegalArgumentException options can not be null or empty
   *                                  | (wheelOptions == null || gearboxOptions == null || seatOptions == null || bodyOptions == null || colorOptions == null || engineOptions == null || aircoOptions == null ||
   *                                  wheelOptions.isEmpty() || gearboxOptions.isEmpty() || seatOptions.isEmpty() || bodyOptions.isEmpty() || colorOptions.isEmpty() || engineOptions.isEmpty() || aircoOptions.isEmpty())
   * @throws IllegalArgumentException options can not contain null
   *                                  | (wheelOptions.contains(null) || gearboxOptions.contains(null) || seatOptions.contains(null) || bodyOptions.contains(null) || colorOptions.contains(null) || engineOptions.contains(null) || aircoOptions.contains(null))
   * @mutates | this
   */
  public CarModel(int id, String name, List<Wheel> wheelOptions, List<Gearbox> gearboxOptions, List<Seat> seatOptions, List<Body> bodyOptions, List<Color> colorOptions, List<Engine> engineOptions, List<Airco> aircoOptions, List<Spoiler> spoilerOptions) {
    this(id, name, wheelOptions, gearboxOptions, seatOptions, bodyOptions, colorOptions, engineOptions, aircoOptions, spoilerOptions, 60);
  }

  /**
   * @param id               car model ID
   * @param name             name of the car model
   * @param wheelOptions     list of acceptable wheel options for the car model
   * @param gearboxOptions   list of acceptable gearbox options for the car model
   * @param seatOptions      list of acceptable seat options for the car model
   * @param bodyOptions      list of acceptable body options for the car model
   * @param colorOptions     list of acceptable color options for the car model
   * @param engineOptions    list of acceptable engine options for the car model
   * @param aircoOptions     list of acceptable airco options for the car model
   * @param workPostDuration the duration of the model on every workpost
   * @throws IllegalArgumentException name can not be null or empty
   *                                  | name == null || name.equals("")
   * @throws IllegalArgumentException options can not be null or empty
   *                                  | (wheelOptions == null || gearboxOptions == null || seatOptions == null || bodyOptions == null || colorOptions == null || engineOptions == null || aircoOptions == null ||
   *                                  wheelOptions.isEmpty() || gearboxOptions.isEmpty() || seatOptions.isEmpty() || bodyOptions.isEmpty() || colorOptions.isEmpty() || engineOptions.isEmpty() || aircoOptions.isEmpty())
   * @throws IllegalArgumentException options can not contain null
   *                                  | (wheelOptions.contains(null) || gearboxOptions.contains(null) || seatOptions.contains(null) || bodyOptions.contains(null) || colorOptions.contains(null) || engineOptions.contains(null) || aircoOptions.contains(null))
   * @mutates | this
   */
  public CarModel(int id, String name, List<Wheel> wheelOptions, List<Gearbox> gearboxOptions, List<Seat> seatOptions, List<Body> bodyOptions, List<Color> colorOptions, List<Engine> engineOptions, List<Airco> aircoOptions, List<Spoiler> spoilerOptions, int workPostDuration) {
    if (Objects.equals(name, "") || name == null)
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
    this.workPostDuration = workPostDuration;
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

  public List<Spoiler> getSpoilerOptions() {
    return spoilerOptions;
  }

  public int getWorkPostDuration() {
    return workPostDuration;
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
      spoilerOptions.contains(spoiler);
  }

  @Override
  public String toString() {
    return id + ": " + name;
  }


}
