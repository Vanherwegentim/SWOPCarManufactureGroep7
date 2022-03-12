package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarModel;
import be.kuleuven.assemassit.Domain.CarOrder;
import be.kuleuven.assemassit.Domain.GarageHolder;
import be.kuleuven.assemassit.Domain.Repositories.GarageHolderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderController {

  private List<GarageHolder> garageHolders;
  private GarageHolder loggedInGarageHolder;
  private AssemblyLine assemblyLine;
  private GarageHolderRepository garageHolderRepository;

  public OrderController(AssemblyLine assemblyLine){
    this.assemblyLine = assemblyLine;
  }

  public OrderController() {
    garageHolderRepository = new GarageHolderRepository();
    garageHolders = garageHolderRepository.getGarageHolders();
  }

  public List<CarOrder> giveNewCarOrders(GarageHolder garageHolder){
    return List.copyOf(garageHolder.getCarOrders());
  }

  //TODO ask sander what we should we with the persistence of garageholder
  public void placeCarOrder(CarModel carModel, String body, String color, String engine, String gearbox, String seats, String airco, String wheels){
    loggedInGarageHolder.addCarOrder(carModel, body, color, engine, gearbox, seats, airco, wheels);
  }

  public LocalDateTime getCompletionDate(int orderId){
    return loggedInGarageHolder.getCompletionTimeFromOrder(orderId);
  }

  public CarOrder chooseOrder(int orderId){
    return loggedInGarageHolder.getOrder(orderId);
  }

  public void logOffGarageHolder() {
    this.loggedInGarageHolder = null;
  }

  public void setLoggedInGarageHolder(int garageHolderId) {
    this.loggedInGarageHolder = garageHolders.get(garageHolderId);
  }

  public String giveLoggedInGarageHolderName() {
    return loggedInGarageHolder.getName();
  }

  public Map<Integer, String> giveGarageHolders() {
    return this.garageHolders
      .stream()
      .collect(Collectors.toMap(GarageHolder::getId, GarageHolder::getName));
  }
}
