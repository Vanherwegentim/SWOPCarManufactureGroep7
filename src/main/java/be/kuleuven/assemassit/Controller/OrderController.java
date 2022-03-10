package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.CarModel;
import be.kuleuven.assemassit.Domain.CarOrder;
import be.kuleuven.assemassit.Domain.GarageHolder;

import java.util.List;

public class OrderController {
  //assemblyLine moet nog meegegeven worden in de constructor omdat we carModels nodig hebben om weer te geven in de ui
  public OrderController(){}
  private List<GarageHolder> garageHolders;
  private GarageHolder loggedInGarageHolder;

  public List<CarOrder> giveNewCarOrders(GarageHolder garageHolder){
    return List.copyOf(garageHolder.getCarOrders());
  }

  //should carModel be String or CarModel object?

  public void placeCarOrder(CarModel carModel, String body, String color, String engine, String gearbox, String seats, String airco, String wheels){
    // ik heb hier geen logica gezet die het car object aanmaakt met deze variabelen door low-coupling, klopt dit?
    loggedInGarageHolder.addCarOrder(carModel, body, color, engine, gearbox, seats, airco, wheels);
  }
}
