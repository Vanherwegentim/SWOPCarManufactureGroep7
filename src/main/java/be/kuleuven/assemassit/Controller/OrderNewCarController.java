package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.CarManufactoringCompany;
import be.kuleuven.assemassit.Domain.CarModel;
import be.kuleuven.assemassit.Domain.CarOrder;
import be.kuleuven.assemassit.Domain.GarageHolder;

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
   * Get the name of the logged in garage holder
   *
   * @return the name of the logged in garage holder
   */
  public String giveLoggedInGarageHolderName() {
    if (this.loggedInGarageHolder == null)
      throw new IllegalStateException();
    return loggedInGarageHolder.getName();
  }

  /**
   * Get the completion date of the order with given order ID
   *
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
   * Get a specific order with given order ID from the logged in garage holder
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
   * @param carModelId the id of the car model
   * @param body
   * @param color
   * @param engine
   * @param gearbox
   * @param seats
   * @param airco
   * @param wheels
   * @return the id of the new car order
   * @throws IllegalStateException loggedInGarageHolder == null
   */
  public int placeCarOrder(int carModelId, String body, String color, String engine, String gearbox, String seats, String airco, String wheels, String spoiler) {
    if (loggedInGarageHolder == null)
      throw new IllegalStateException();

    return carManufactoringCompany.designCarOrder(loggedInGarageHolder, carModelId, body, color, engine, gearbox, seats, airco, wheels, spoiler);
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

  /**
   * Place a car order and get the estimated completion time of that order
   *
   * @param carModelId
   * @param body
   * @param color
   * @param engine
   * @param gearbox
   * @param seats
   * @param airco
   * @param wheels
   * @param spoiler
   * @return the estimated completion time of the new car order
   */
  public LocalDateTime placeCarOrderAndReturnEstimatedCompletionTime(int carModelId, String body, String color, String engine, String gearbox, String seats, String airco, String wheels, String spoiler) {
    int carOrderId = placeCarOrder(carModelId, body, color, engine, gearbox, seats, airco, wheels, spoiler);
    return getCarOrderEstimatedCompletionTime(carOrderId);
  }

}
