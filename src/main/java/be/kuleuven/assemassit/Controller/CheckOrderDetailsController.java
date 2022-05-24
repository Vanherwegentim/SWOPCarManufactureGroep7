package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.CarOrder;
import be.kuleuven.assemassit.Domain.GarageHolder;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class CheckOrderDetailsController {

  /**
   * @invar logged in garage holder can not bu null | loggedInGarageHolder != null
   */
  private final GarageHolder loggedInGarageHolder;

  /**
   * @throws IllegalStateException loggedInGarageHolder is null | loggedInGarageHolder == null
   * @mutates | this
   * @post | getLoggedInGarageHolder().equals(loggedInGarageHolder)
   */
  public CheckOrderDetailsController(GarageHolder loggedInGarageHolder) {
    if (loggedInGarageHolder == null)
      throw new IllegalStateException();
    this.loggedInGarageHolder = loggedInGarageHolder;
  }

  protected GarageHolder getLoggedInGarageHolder() {
    return this.loggedInGarageHolder;
  }

  /**
   * Give the pending car orders from the logged in garage holder
   *
   * @return a list of pending car orders from the logged in garage holder
   * @throws IllegalStateException loggedInGarageHolder is null | loggedInGarageHolder == null
   */
  public List<String> givePendingCarOrders() {
    if (loggedInGarageHolder == null)
      throw new IllegalStateException();

    return loggedInGarageHolder
      .getCarOrders()
      .stream()
      .filter(CarOrder::isPending)
      .map(this::carOrderFormattedString)
      .collect(Collectors.toList());
  }

  /**
   * Give the completed car orders from the logged in garage holder
   *
   * @return a list of completed car orders from the logged in garage holder
   * @throws IllegalStateException loggedInGarageHolder is null | loggedInGarageHolder == null
   */
  public List<String> giveCompletedCarOrders() {
    if (loggedInGarageHolder == null)
      throw new IllegalStateException();

    return loggedInGarageHolder.getCarOrders()
      .stream()
      .filter(co -> !co.isPending())
      .sorted(Comparator.comparing(CarOrder::getCompletionTime))
      .map(this::carOrderFormattedString)
      .collect(Collectors.toList());
  }

  /**
   * Returns the order details of a given orderId
   *
   * @param orderId the ID of the order
   * @return a list of order details from the order with given orderId
   */
  public Optional<String> giveOrderDetails(int orderId) {

    Optional<CarOrder> order = loggedInGarageHolder.findCarOrder(orderId);
    return order.map(this::carOrderDetailedFormattedString);
  }

  private String carOrderFormattedString(CarOrder carOrder) {
    DateTimeFormatter estimatedDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter completionTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' H:mm");
    String spacer = " ".repeat(4);
    StringBuilder result = new StringBuilder();
    result
      .append("Order ID: ")
      .append(carOrder.getId())
      .append(spacer);

    if (carOrder.isPending())
      result
        .append("[Estimated date: ")
        .append(carOrder.getEstimatedCompletionTime().format(estimatedDateFormatter))
        .append("]");
    else
      result
        .append("[Completed at: ")
        .append(carOrder.getCompletionTime().format(completionTimeFormatter))
        .append("]");
    result.append(spacer);
    result
      .append("[Car model: ")
      .append(carOrder.getCar().getCarModel().getName())
      .append("]");
    result.append(System.lineSeparator());


    return result.toString();
  }

  private String carOrderDetailedFormattedString(CarOrder carOrder) {
    DateTimeFormatter estimatedDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter completionTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' H:mm");
    String spacer = " ".repeat(4);
    StringBuilder result = new StringBuilder();
    result
      .append("Order ID: ")
      .append(carOrder.getId())
      .append(spacer);

    if (carOrder.isPending())
      result
        .append("[Estimated date: ")
        .append(carOrder.getEstimatedCompletionTime().format(estimatedDateFormatter))
        .append("]");
    else
      result
        .append("[Completed at: ")
        .append(carOrder.getCompletionTime().format(completionTimeFormatter))
        .append("]");
    result.append(System.lineSeparator());

    result
      .append(spacer)
      .append("Car model: ")
      .append(carOrder.getCar().getCarModel().getName())
      .append(System.lineSeparator());


    Map<String, String> parts = new LinkedHashMap<>();
    parts.put("Body", carOrder.getCar().getBody().name());
    parts.put("Color", carOrder.getCar().getColor().name());
    parts.put("Engine", carOrder.getCar().getEngine().name());
    parts.put("Gearbox", carOrder.getCar().getGearbox().name());
    parts.put("Airco", carOrder.getCar().getAirco().name());
    parts.put("Wheels", carOrder.getCar().getWheels().name());
    parts.put("Seats", carOrder.getCar().getSeats().name());
    parts.put("Spoiler", carOrder.getCar().getSpoiler().name());

    for (Map.Entry<String, String> partWithOption : parts.entrySet()) {
      result
        .append(spacer.repeat(2))
        .append(partWithOption.getKey())
        .append(": ")
        .append(partWithOption.getValue())
        .append(System.lineSeparator());
    }

    return result.toString();
  }


  /**
   * Give the name of the logged in garage holder
   *
   * @return the name of the logged in garage holder
   * @inspects | this.loggedInGarageHolder
   */
  public String giveLoggedInGarageHolderName() {
    return loggedInGarageHolder.getName();
  }
}
