package be.kuleuven.assemassit.Domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @mutable
 * @invar | getName() != null && !getName().isEmpty()
 * @invar | getCarOrders() != null
 */
public class GarageHolder {

  private int id;
  private String name;
  /**
   * @invar | name != null && !name.isEmpty()
   * @invar | carOrders != null
   * @representationObject
   * @representationObjects
   */
  private List<CarOrder> carOrders = new ArrayList<>();

  /**
   * @param id
   * @param name
   * @throws IllegalArgumentException name can not be null or empty | (name == null || name.isEmpty())
   */
  public GarageHolder(int id, String name) {
    this.id = id;
    if (name == null || name.isEmpty())
      throw new IllegalArgumentException("Name can not be null or empty");
    this.name = name;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  /**
   * @return the list of car orders
   * @inspects | this
   * @creates | result
   */
  public List<CarOrder> getCarOrders() {
    return List.copyOf(carOrders);
  }

  /**
   * @param carOrder
   * @mutates | this
   * @inspects | carOrder
   */
  public void addCarOrder(CarOrder carOrder) {
    carOrders.add(carOrder);
  }

  public Optional<CarOrder> getOrder(int id) {
    return findCarOrder(id);
  }

  /**
   * @param orderId
   * @return the completion time of the order
   * @throws IllegalArgumentException ID can not be lower than 0
   *                                  | orderId < 0
   */
  public LocalDateTime getCompletionTimeFromOrder(int orderId) {
    if (id < 0)
      throw new IllegalArgumentException("ID can not be lower than 0");
    Optional<CarOrder> order = findCarOrder(orderId);
    if (order.isPresent()) {
      return order.get().getCompletionTime();
    }
    throw new IllegalArgumentException("No order was found with the given ID");
  }

  /**
   * @param id
   * @return the car order
   * @throws IllegalArgumentException ID can not be lower than 0
   *                                  | id < 0
   * @inspects | this
   */
  public Optional<CarOrder> findCarOrder(int id) {
    if (id < 0)
      throw new IllegalArgumentException("ID can not be lower than 0");

    Optional<CarOrder> carOrder = carOrders.stream()
      .filter(wp -> wp.getId() == id)
      .findFirst();

    if (carOrder.isEmpty())
      return Optional.empty();

    return Optional.of(carOrder.get());
  }
}
