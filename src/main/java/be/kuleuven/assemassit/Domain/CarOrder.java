package be.kuleuven.assemassit.Domain;

import java.time.LocalDateTime;

public class CarOrder {

    private int id;
    private Car car;
    //private CarAssemblyProcess
    private boolean pending = true;
    private LocalDateTime completionTime;
    private LocalDateTime estimatedCompletionTime;
    private LocalDateTime deliveryTime;

    public CarOrder(Car car){
        this.car = car;
    }

    public boolean isPending(){
        return pending;
    }

    public LocalDateTime getCompletionTime(){
        return completionTime;
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

    public int getId(){
      return id;
    }
}
