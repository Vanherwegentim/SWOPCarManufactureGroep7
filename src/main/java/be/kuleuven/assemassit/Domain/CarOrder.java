package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Helper.CustomTime;

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
  private final LocalDateTime orderTime;
  private boolean pending;
  private LocalDateTime completionTime;
  private LocalDateTime estimatedCompletionTime;

  /**
   * @param car the car model that is ordered
   * @throws IllegalArgumentException car can not be null | car == null
   * @post the order time can not be null and should be equal to the current date time
   * | this.getOrderTime() != null
   * @post | this.isPending() == true
   */
  public CarOrder(Car car) {
    if (car == null)
      throw new IllegalArgumentException("Car cannot be null");
    this.car = car;
    this.pending = true;
    this.orderTime = (CustomTime.getInstance().customLocalDateTimeNow());
    this.id = CarOrder.idRunner++;
  }

  /**
   * Reset the counter of automatic IDs, this is for testing purposes
   */
  public static void resetIdRunner() {
    CarOrder.idRunner = 0;
  }

  public boolean isPending() {
    return pending;
  }

  public void setPending(boolean pending) {
    this.pending = pending;
  }

  public LocalDateTime getCompletionTime() {
    return this.completionTime;
  }

  /**
   * @param completionTime the time when the car order is completed
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
   * @param estimatedCompletionTime the time when the car order is estimated to be completed
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
