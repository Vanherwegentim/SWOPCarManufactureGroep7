package be.kuleuven.assemassit.Domain;

import java.time.LocalDateTime;

public class CarOrder {
  private static int idRunner = 0;

  private int id;
  private Car car;
  //private CarAssemblyProcess
  private boolean pending;
  private LocalDateTime completionTime;
  private LocalDateTime estimatedCompletionTime;
  private LocalDateTime deliveryTime;
  private LocalDateTime orderTime;

  public CarOrder(Car car) {
    if (car == null)
      throw new IllegalArgumentException("Car cannot be null");
    this.car = car;
    this.pending = true;
    this.orderTime = LocalDateTime.now();
    this.id = CarOrder.idRunner++;
  }

  public boolean isPending() {
    return pending;
  }

  //TODO: calculate completionTime!
  public LocalDateTime getCompletionTime() {
    //return completionTime;
    return LocalDateTime.now(); // THIS IS VERY TEMPORARY
  }

  public Car getCar() {
    return car;
  }

  public LocalDateTime getEstimatedCompletionTime() {
    return estimatedCompletionTime;
  }

  public LocalDateTime getDeliveryTime() {
    return deliveryTime;
  }

  public int getId() {
    return id;
  }

  public LocalDateTime getOrderTime() {
    return this.orderTime;
  }

  public void setEstimatedCompletionTime(LocalDateTime estimatedCompletionTime) {
    this.estimatedCompletionTime = LocalDateTime.of(estimatedCompletionTime.getYear(),
      estimatedCompletionTime.getMonth(),
      estimatedCompletionTime.getDayOfMonth(),
      estimatedCompletionTime.getHour(),
      estimatedCompletionTime.getMinute());
  }
}
