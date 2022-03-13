package be.kuleuven.assemassit.Domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GarageHolder {

  private int id;
  private String name;
  private List<CarOrder> carOrders = new ArrayList<>();

  public GarageHolder(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public List<CarOrder> getCarOrders(){
    return List.copyOf(carOrders);
  }

  public void addCarOrder(CarOrder carOrder) {
    carOrders.add(carOrder);
  }

  public CarOrder getOrder(int id){
    return findCarOrder(id);
  }

  public LocalDateTime getCompletionTimeFromOrder(int orderId){
    return findCarOrder(orderId).getCompletionTime();
  }

  private CarOrder findCarOrder(int id) {
    Optional<CarOrder> carOrder = carOrders.stream()
      .filter(wp -> wp.getId() == id)
      .findFirst();

    if (carOrder.isEmpty())
      throw new IllegalArgumentException("Car order not found");

    return carOrder.get();
  }
}
