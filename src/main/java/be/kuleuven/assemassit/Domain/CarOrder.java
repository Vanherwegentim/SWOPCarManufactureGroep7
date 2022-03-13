package be.kuleuven.assemassit.Domain;

import java.time.LocalDateTime;

public class CarOrder {

    private int id;
    private Car car;
    //private CarAssemblyProcess
    private boolean pending;
    private LocalDateTime completionTime;
    private LocalDateTime estimatedCompletionTime;
    private LocalDateTime deliveryTime;

    public CarOrder(Car car) {
        this.car = car;
        this.pending = true;
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

    @Override
    public String toString() {
      String result = "";

      if (pending)
        result += "Pending order " + Integer.toString(id) + "(estimation time: " + getEstimatedCompletionTime().toString() + ")";
      else
        result += "Order" + Integer.toString(id) + "(completed at: " + getCompletionTime() + ")";

      result += " with configuration: ";
      result += "\n" + car.toString();

      return result;
    }
}
