package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarModel;
import be.kuleuven.assemassit.Domain.CarOrder;
import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.GarageHolder;
import be.kuleuven.assemassit.Domain.Repositories.GarageHolderRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderController {

  private List<GarageHolder> garageHolders;
  private GarageHolder loggedInGarageHolder;
  private AssemblyLine assemblyLine;
  private GarageHolderRepository garageHolderRepository;

  //TODO: still needed?
  public OrderController(AssemblyLine assemblyLine){
    this.assemblyLine = assemblyLine;
  }

  public OrderController() {
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

  public List<String> giveCompletedCarOrders(GarageHolder garageHolder){
    return garageHolder.getCarOrders()
      .stream()
      .filter(co -> !co.isPending())
      .map(co -> co.toString())
      .collect(Collectors.toList());
  }

  //TODO ask sander what we should we with the persistence of garageholder
  public void placeCarOrder(CarModel carModel, String body, String color, String engine, String gearbox, String seats, String airco, String wheels) {
    if (loggedInGarageHolder == null)
      throw new IllegalStateException();

    loggedInGarageHolder.addCarOrder(carModel, body, color, engine, gearbox, seats, airco, wheels);
  }

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

  //TODO: this is temp code purely written for UI development
  public List<String> giveListOfCarModels() {
    return Arrays.asList(new CarModel(
      "Porsche Super Duper Turbo Go Go",
      List.of(Wheel.values()),
      List.of(Gearbox.values()),
      List.of(Seat.values()),
      List.of(Body.values()),
      List.of(Color.values()),
      List.of(Engine.values())
    ).toString());
  }

  /*
  public void placeNewCarOrder() {
    if (loggedInGarageHolder == null)
      throw new IllegalStateException();

    //TODO: implement method
  }
  */


  public Map<Integer, String> giveGarageHolders() {
    return this.garageHolders
      .stream()
      .collect(Collectors.toMap(GarageHolder::getId, GarageHolder::getName));
  }
}
