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

  public OrderController(CarManufactoringCompany carManufactoringCompany, GarageHolderRepository garageHolderRepository) {
    this.carManufactoringCompany = carManufactoringCompany;
    this.garageHolderRepository = garageHolderRepository;
    garageHolders = garageHolderRepository.getGarageHolders();
  }

  public void logInGarageHolder(int garageHolderId) {
    if (garageHolderId < 0)
      throw new IllegalArgumentException("GarageHolderId cannot be smaller than 0");

    try {
      this.loggedInGarageHolder = garageHolders.get(garageHolderId);
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("There is no garage holder with the given id");
    }
  }

  public void logOffGarageHolder() {
    this.loggedInGarageHolder = null;
  }

  public String giveLoggedInGarageHolderName() {
    if (this.loggedInGarageHolder == null)
      throw new IllegalStateException();
    return loggedInGarageHolder.getName();
  }

  public Map<Integer, String> giveGarageHolders() {
    return this.garageHolders
      .stream()
      .collect(Collectors.toMap(GarageHolder::getId, GarageHolder::getName));
  }

  public LocalDateTime getCompletionDate(int orderId) {
    if (orderId < 0)
      throw new IllegalArgumentException("OrderId cannot be smaller than 0");
    if (loggedInGarageHolder == null)
      throw new IllegalStateException();

    return loggedInGarageHolder.getCompletionTimeFromOrder(orderId);
  }

  public CarOrder chooseOrder(int orderId) {
    if (orderId < 0)
      throw new IllegalArgumentException("OrderId cannot be smaller than 0");
    if (loggedInGarageHolder == null)
      throw new IllegalStateException();

    return loggedInGarageHolder.getOrder(orderId);
  }

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

  public Map<Integer, String> giveListOfCarModels() {
    return this.carManufactoringCompany
      .getCarModels()
      .stream()
      .collect(Collectors.toMap(CarModel::getId, CarModel::getName));
  }

  public Map<String, List<String>> givePossibleOptionsOfCarModel(int carModelId) {
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

  public LocalDateTime placeCarOrder(int carModelId, String body, String color, String engine, String gearbox, String seats, String airco, String wheels) {
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
          Wheel.valueOf(wheels)
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

    return estimatedCompletionTime;
  }
}
