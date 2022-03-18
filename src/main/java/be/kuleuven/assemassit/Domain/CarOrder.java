package be.kuleuven.assemassit.Domain;

import java.time.LocalDateTime;

/**
 * @mutable
 * @invar | getCar() != null
 * @invar order time can not be after the completion time
 * | (getCompletionTime() == null || getOrderTime() == null) || getCompletionTime().isAfter(getOrderTime())
 * @invar order time can not be null | getOrderTime() != null
 */
public class CarOrder {
  private static int idRunner = 0;

  private final int id;
  /**
   * @invar | car != null
   * @invar order time can not be after the completion time
   * | (completionTime == null || orderTime == null) || completionTime.isAfter(orderTime)
   * @invar order time can not be null | orderTime != null
   * @representationObject
   */
  private final Car car;
  //private CarAssemblyProcess
  private boolean pending;
  private LocalDateTime completionTime;
  private LocalDateTime estimatedCompletionTime;
  private final LocalDateTime orderTime;
  /**
   * @param car the car model that is ordered
   * @throws IllegalArgumentException car can not be null | car == null
   * @post the order time can not be null and should be equal to the current date time
   * | this.orderTime != null
   * @post | this.pending == true
   */
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

  public void setPending(boolean pending) {
    this.pending = pending;
  }

  // TODO: this value returns null if the order is not completed, do we keep it this way?
  public LocalDateTime getCompletionTime() {
    return this.completionTime;
  }

  /**
   * @param completionTime
   * @post | getCompletionTime() == completionTime
   */
  public void setCompletionTime(LocalDateTime completionTime) {
    this.completionTime = completionTime;
  }

  public Car getCar() {
    return car;
  }

  public LocalDateTime getEstimatedCompletionTime() {
    return estimatedCompletionTime;
  }

  /**
   * Set the estimated completion time of an order which is calculated by the assembly line
   *
   * @param estimatedCompletionTime
   * @post | getEstimatedCompletionTime() == estimatedCompletionTime
   */
  public void setEstimatedCompletionTime(LocalDateTime estimatedCompletionTime) {
    this.estimatedCompletionTime = estimatedCompletionTime;
  }

  public int getId() {
    return id;
  }

  public LocalDateTime getOrderTime() {
    return this.orderTime;
  }
}
