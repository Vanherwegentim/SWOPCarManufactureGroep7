package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.*;
import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.Repositories.GarageHolderRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderController {

  private final List<GarageHolder> garageHolders;
  private final GarageHolderRepository garageHolderRepository;
  private final CarManufactoringCompany carManufactoringCompany;
  private GarageHolder loggedInGarageHolder;

  /**
   * @param carManufactoringCompany
   * @post | this.carManufactoringCompany = carManufactoringCompany
   */
  public OrderController(CarManufactoringCompany carManufactoringCompany) {
    if (carManufactoringCompany == null)
      throw new IllegalArgumentException("CarManufactoring company can not be null");

    this.carManufactoringCompany = carManufactoringCompany;
    garageHolderRepository = new GarageHolderRepository();
    garageHolders = garageHolderRepository.getGarageHolders();
  }

  /**
   * log in a garage holder
   *
   * @param garageHolderId
   * @throws IllegalArgumentException garageHolderId is below 0 | garageHolderId < 0
   */
  public void logInGarageHolder(int garageHolderId) {
    if (garageHolderId < 0)
      throw new IllegalArgumentException("GarageHolderId cannot be smaller than 0");

    this.loggedInGarageHolder = garageHolders.get(garageHolderId);
  }

  /**
   * Log off the garage holder
   */
  public void logOffGarageHolder() {
    this.loggedInGarageHolder = null;
  }

  /**
   * @return the name of the logged in garage holder
   */
  public String giveLoggedInGarageHolderName() {
    return loggedInGarageHolder.getName();
  }

  /**
   * @return a map with garage holders, the key is the id and de value is the name of the garage holder
   */
  public Map<Integer, String> giveGarageHolders() {
    return this.garageHolders
      .stream()
      .collect(Collectors.toMap(GarageHolder::getId, GarageHolder::getName));
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
  public CarOrder chooseOrder(int orderId) {
    if (orderId < 0)
      throw new IllegalArgumentException("OrderId cannot be smaller than 0");
    if (loggedInGarageHolder == null)
      throw new IllegalStateException();

    return loggedInGarageHolder.getOrder(orderId);
  }

  /**
   * Give the pending car orders from the logged in garage holder
   *
   * @return a list of pending car orders from the logged in garage holder
   * @throws IllegalStateException loggedInGarageHolder is null | loggedInGarageHolder == null
   */
  public List<String> givePendingCarOrders() {
    if (loggedInGarageHolder == null)
      throw new IllegalStateException();

    return loggedInGarageHolder
      .getCarOrders()
      .stream()
      .filter(CarOrder::isPending)
      .map(this::carOrderFormattedString)
      .collect(Collectors.toList());
  }

  /**
   * Give the completed car orders from the logged in garage holder
   *
   * @return a list of completed car orders from the logged in garage holder
   * @throws IllegalStateException loggedInGarageHolder is null | loggedInGarageHolder == null
   */
  public List<String> giveCompletedCarOrders() {
    if (loggedInGarageHolder == null)
      throw new IllegalStateException();

    return loggedInGarageHolder.getCarOrders()
      .stream()
      .filter(co -> !co.isPending())
      .map(this::carOrderFormattedString)
      .collect(Collectors.toList());
  }

  private String carOrderFormattedString(CarOrder carOrder) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' H:mm");
    String spacer = " ".repeat(4);
    StringBuilder result = new StringBuilder();
    result
      .append("Order ID: ")
      .append(carOrder.getId())
      .append(spacer);

    if (carOrder.isPending())
      result
        .append("[Estimation time: ")
        .append(carOrder.getEstimatedCompletionTime().format(formatter))
        .append("]");
    else
      result
        .append("[Completed at: ")
        .append(carOrder.getCompletionTime().format(formatter))
        .append("]");
    result.append("\n");

    result
      .append(spacer)
      .append("Car model: ")
      .append(carOrder.getCar().getCarModel().getName())
      .append("\n");


    Map<String, String> parts = new LinkedHashMap<>();
    parts.put("Body", carOrder.getCar().getBody().name());
    parts.put("Color", carOrder.getCar().getColor().name());
    parts.put("Engine", carOrder.getCar().getEngine().name());
    parts.put("Gearbox", carOrder.getCar().getGearbox().name());
    parts.put("Airco", carOrder.getCar().getAirco().name());
    parts.put("Wheels", carOrder.getCar().getWheels().name());
    parts.put("Seats", carOrder.getCar().getSeats().name());

    for (Map.Entry<String, String> partWithOption : parts.entrySet()) {
      result
        .append(spacer.repeat(2))
        .append(partWithOption.getKey())
        .append(": ")
        .append(partWithOption.getValue())
        .append("\n");
    }

    return result.toString();
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
   * @return the estimated delivery time of the new car order
   * @throws IllegalStateException no garage holder is logged in | loggedInGarageHolder == null
   */
  public LocalDateTime placeCarOrder(int carModelId, String body, String color, String engine, String gearbox, String seats, String airco, String wheels) {
    if (loggedInGarageHolder == null)
      throw new IllegalStateException();

    CarModel carModel = carManufactoringCompany.giveCarModelWithId(carModelId);
    Car car = new Car
      (
        carModel,
        Body.valueOf(body),
        Color.valueOf(color),
        Engine.valueOf(engine),
        Gearbox.valueOf(gearbox),
        Seat.valueOf(seats),
        Airco.valueOf(airco),
        Wheel.valueOf(wheels)
      );

    CarOrder carOrder = new CarOrder(car);
    loggedInGarageHolder.addCarOrder(carOrder);

    CarAssemblyProcess carAssemblyProcess = new CarAssemblyProcess(carOrder);

    carManufactoringCompany.addCarAssemblyProcess(carAssemblyProcess);
    LocalDateTime estimatedCompletionTime = carManufactoringCompany.giveEstimatedCompletionDateOfLatestProcess();
    carOrder.setEstimatedCompletionTime(estimatedCompletionTime);

    return estimatedCompletionTime;
  }
}
