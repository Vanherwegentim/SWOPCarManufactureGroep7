package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarModel;
import be.kuleuven.assemassit.Domain.CarOrder;
import be.kuleuven.assemassit.Domain.GarageHolder;

import java.time.LocalDateTime;
import java.util.List;

public class OrderController {
  public OrderController(AssemblyLine assemblyLine){
    this.assemblyLine = assemblyLine;
  }

  private List<GarageHolder> garageHolders;
  private GarageHolder loggedInGarageHolder;
  private AssemblyLine assemblyLine;

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

}
