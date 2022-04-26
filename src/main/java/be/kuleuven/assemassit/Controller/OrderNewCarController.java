package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.*;
import be.kuleuven.assemassit.Domain.Enums.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderNewCarController {

  private final CarManufactoringCompany carManufactoringCompany;
  private final GarageHolder loggedInGarageHolder;

  /**
   * @param carManufactoringCompany
   * @post | this.carManufactoringCompany == carManufactoringCompany
   */
  public OrderNewCarController(CarManufactoringCompany carManufactoringCompany, GarageHolder loggedInGarageHolder) {
    if (carManufactoringCompany == null)
      throw new IllegalArgumentException("CarManufactoring company can not be null");
    if (loggedInGarageHolder == null)
      throw new IllegalStateException("Cannot order a new car without a logged in garage holder");


    this.carManufactoringCompany = carManufactoringCompany;
    this.loggedInGarageHolder = loggedInGarageHolder;
  }

  /**
   * @return the name of the logged in garage holder
   */
  public String giveLoggedInGarageHolderName() {
    if (this.loggedInGarageHolder == null)
      throw new IllegalStateException();
    return loggedInGarageHolder.getName();
  }

  /**
   * @param orderId to get the completion date from
   * @return the completion date
   * @throws IllegalArgumentException OrderId is below 0 | orderId < 0
   * @throws IllegalStateException    loggedInGarageHolder is null | loggedInGarageHolder == null
   */
  public LocalDateTime getCompletionDate(int orderId) {
    if (orderId < 0)
      throw new IllegalArgumentException("OrderId cannot be smaller than 0");
    if (loggedInGarageHolder == null)
      throw new IllegalStateException();

    return loggedInGarageHolder.getCompletionTimeFromOrder(orderId);
  }

  /**
   * get an order from the garage holder
   *
   * @param orderId the id of the order
   * @return the car order
   * @throws IllegalArgumentException orderId is below 0 | orderId < 0
   * @throws IllegalStateException    loggedInGarageHolder is null | loggedInGarageHolder == null
   */
  public Optional<CarOrder> chooseOrder(int orderId) {
    if (orderId < 0)
      throw new IllegalArgumentException("OrderId cannot be smaller than 0");
    if (loggedInGarageHolder == null)
      throw new IllegalStateException();

    return loggedInGarageHolder.findCarOrder(orderId);
  }

  /**
   * A list of all car models that can be manufactured by the company is returned
   *
   * @return the list of all car models
   */
  public Map<Integer, String> giveListOfCarModels() {
    return this.carManufactoringCompany
      .getCarModels()
      .stream()
      .collect(Collectors.toMap(CarModel::getId, CarModel::getName));
  }

  /**
   * A list of all possible options from a specific car model is returned
   *
   * @param carModelId the id of the car model
   * @return the list of possible options
   * @throws IllegalArgumentException carModelId is below 0 | carModelId < 0
   */
  public Map<String, List<String>> givePossibleOptionsOfCarModel(int carModelId) {
    if (carModelId < 0)
      throw new IllegalArgumentException("carModelId can not be lower than 0");

    Map<String, List<String>> carModelOptions = new HashMap<>();
    CarModel carModel = carManufactoringCompany.giveCarModelWithId(carModelId);

    carModelOptions.put("Body", carModel.getBodyOptions().stream().map(v -> v.name()).collect(Collectors.toList()));
    carModelOptions.put("Color", carModel.getColorOptions().stream().map(v -> v.name()).collect(Collectors.toList()));
    carModelOptions.put("Engine", carModel.getEngineOptions().stream().map(v -> v.name()).collect(Collectors.toList()));
    carModelOptions.put("GearBox", carModel.getGearboxOptions().stream().map(v -> v.name()).collect(Collectors.toList()));
    carModelOptions.put("Seats", carModel.getSeatOptions().stream().map(v -> v.name()).collect(Collectors.toList()));
    carModelOptions.put("Airco", carModel.getAircoOptions().stream().map(v -> v.name()).collect(Collectors.toList()));
    carModelOptions.put("Wheels", carModel.getWheelOptions().stream().map(v -> v.name()).collect(Collectors.toList()));
    carModelOptions.put("Spoiler", carModel.getSpoilerOptions().stream().map(v -> v.name()).collect(Collectors.toList()));
    return carModelOptions;
  }

  /**
   * A new car order is made and the estimated delivery time is calculated
   *
   * @param carModelId
   * @param body
   * @param color
   * @param engine
   * @param gearbox
   * @param seats
   * @param airco
   * @param wheels
   * @return the id of the new car order
   * @throws IllegalStateException no garage holder is logged in | loggedInGarageHolder == null
   */
  public int placeCarOrder(int carModelId, String body, String color, String engine, String gearbox, String seats, String airco, String wheels, String spoiler) {
    if (loggedInGarageHolder == null)
      throw new IllegalStateException();

    CarModel carModel = carManufactoringCompany.giveCarModelWithId(carModelId);
    Car car;

    try {
      car = new Car
        (
          carModel,
          Body.valueOf(body),
          Color.valueOf(color),
          Engine.valueOf(engine),
          Gearbox.valueOf(gearbox),
          Seat.valueOf(seats),
          Airco.valueOf(airco),
          Wheel.valueOf(wheels),
          Spoiler.valueOf(spoiler)
        );
    } catch (IllegalArgumentException e) {
      if (e.getLocalizedMessage().startsWith("No enum constant")) {
        throw new IllegalArgumentException("One or more invalid car options were provided");
      }
      throw new IllegalArgumentException(e.getMessage());
    }

    CarOrder carOrder = new CarOrder(car);
    loggedInGarageHolder.addCarOrder(carOrder);

    CarAssemblyProcess carAssemblyProcess = new CarAssemblyProcess(carOrder);

    carManufactoringCompany.addCarAssemblyProcess(carAssemblyProcess);
    LocalDateTime estimatedCompletionTime = carManufactoringCompany.giveEstimatedCompletionDateOfLatestProcess();
    carOrder.setEstimatedCompletionTime(estimatedCompletionTime);

    return carOrder.getId();
  }

  /**
   * The estimated delivery time is requested from a given carOrderId
   *
   * @param carOrderId
   * @return the id of the new car order
   * @throws IllegalStateException no garage holder is logged in | loggedInGarageHolder == null
   */
  public LocalDateTime getCarOrderEstimatedCompletionTime(int carOrderId) {
    if (loggedInGarageHolder == null)
      throw new IllegalStateException();
    Optional<CarOrder> carOrder = loggedInGarageHolder.findCarOrder(carOrderId);
    if (carOrder.isPresent()) {
      return carOrder.get().getEstimatedCompletionTime();
    }
    throw new IllegalArgumentException("CarOrder with given ID not found");
  }

  public LocalDateTime placeCarOrderAndReturnEstimatedCompletionTime(int carModelId, String body, String color, String engine, String gearbox, String seats, String airco, String wheels, String spoiler) {
    int carOrderId = placeCarOrder(carModelId, body, color, engine, gearbox, seats, airco, wheels, spoiler);
    return getCarOrderEstimatedCompletionTime(carOrderId);
  }

}
