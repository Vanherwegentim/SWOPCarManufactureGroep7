
package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.*;
import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.Enums.Color;
import be.kuleuven.assemassit.Domain.Repositories.GarageHolderRepository;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderController {

  private List<GarageHolder> garageHolders;
  private GarageHolder loggedInGarageHolder;
  private AssemblyLine assemblyLine;
  private GarageHolderRepository garageHolderRepository;
  private CarManufactoringCompany carManufactoringCompany;

  //TODO: still needed?
  public OrderController(AssemblyLine assemblyLine){
    this.assemblyLine = assemblyLine;
  }

  public OrderController(CarManufactoringCompany carManufactoringCompany, AssemblyLine assemblyLine) {
    this.carManufactoringCompany = carManufactoringCompany;
    this.assemblyLine = assemblyLine;
    garageHolderRepository = new GarageHolderRepository();
    garageHolders = garageHolderRepository.getGarageHolders();
  }

  //TODO: this constructor can be removed when code is refactored
  public OrderController(CarManufactoringCompany carManufactoringCompany) {
    this.carManufactoringCompany = carManufactoringCompany;
    garageHolderRepository = new GarageHolderRepository();
    garageHolders = garageHolderRepository.getGarageHolders();
  }

  public List<String> givePendingCarOrders() {
    if (loggedInGarageHolder == null)
      throw new IllegalStateException();

    return loggedInGarageHolder
      .getCarOrders()
      .stream()
      .filter(co -> co.isPending())
      .map(co -> co.toString())
      .collect(Collectors.toList());
  }

  public List<String> giveCompletedCarOrders(){
    if (loggedInGarageHolder == null)
      throw new IllegalStateException();

    return loggedInGarageHolder.getCarOrders()
      .stream()
      .filter(co -> !co.isPending())
      .map(co -> co.toString())
      .collect(Collectors.toList());
  }

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

    carManufactoringCompany.addCarAssemblyProcess(new CarAssemblyProcess(carOrder));
    return carManufactoringCompany.giveEstimatedCompletionDateOfLatestProcess();
  }

  //TODO: will not be used
  public LocalDateTime getCompletionDate(int orderId) {
    if (loggedInGarageHolder == null)
      throw new IllegalStateException();

    return loggedInGarageHolder.getCompletionTimeFromOrder(orderId);
  }

  public CarOrder chooseOrder(int orderId) {
    if (loggedInGarageHolder == null)
      throw new IllegalStateException();

    return loggedInGarageHolder.getOrder(orderId);
  }

  public void logOffGarageHolder() {
    this.loggedInGarageHolder = null;
  }

  public void logInGarageHolder(int garageHolderId) {
    this.loggedInGarageHolder = garageHolders.get(garageHolderId);
  }

  public String giveLoggedInGarageHolderName() {
    return loggedInGarageHolder.getName();
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

  public Map<Integer, String> giveGarageHolders() {
    return this.garageHolders
      .stream()
      .collect(Collectors.toMap(GarageHolder::getId, GarageHolder::getName));
  }
}
