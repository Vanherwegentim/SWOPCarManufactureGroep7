package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.CarOrder;
import be.kuleuven.assemassit.Domain.GarageHolder;

import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CheckOrderDetailsController {

  private GarageHolder loggedInGarageHolder;

  /**
   * @throws IllegalStateException loggedInGarageHolder is null | loggedInGarageHolder == null
   */
  public CheckOrderDetailsController(GarageHolder loggedInGarageHolder) {
    if (loggedInGarageHolder == null)
      throw new IllegalStateException();
    this.loggedInGarageHolder = loggedInGarageHolder;
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
      .map(this::carOrderFormattedString)
      .collect(Collectors.toList());
  }

  public String giveOrderDetails(int orderId) {

    CarOrder order = loggedInGarageHolder.getOrder(orderId);
    return carOrderDetailedFormattedString(order);
  }

  private String carOrderFormattedString(CarOrder carOrder) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' H:mm");
    String spacer = " ".repeat(4);
    StringBuilder result = new StringBuilder();
    result
      .append("Order ID: ")
      .append(carOrder.getId())
      .append(spacer);

    if (carOrder.isPending())
      result
        .append("[Estimation time: ")
        .append(carOrder.getEstimatedCompletionTime().format(formatter))
        .append("]");
    else
      result
        .append("[Completed at: ")
        .append(carOrder.getCompletionTime().format(formatter))
        .append("]");
    result.append(spacer);
    result
      .append("[Car model: ")
      .append(carOrder.getCar().getCarModel().getName())
      .append("]");
    result.append("\n");


    return result.toString();
  }

  private String carOrderDetailedFormattedString(CarOrder carOrder) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' H:mm");
    String spacer = " ".repeat(4);
    StringBuilder result = new StringBuilder();
    result
      .append("Order ID: ")
      .append(carOrder.getId())
      .append(spacer);

    if (carOrder.isPending())
      result
        .append("[Estimation time: ")
        .append(carOrder.getEstimatedCompletionTime().format(formatter))
        .append("]");
    else
      result
        .append("[Completed at: ")
        .append(carOrder.getCompletionTime().format(formatter))
        .append("]");
    result.append("\n");

    result
      .append(spacer)
      .append("Car model: ")
      .append(carOrder.getCar().getCarModel().getName())
      .append("\n");


    Map<String, String> parts = new LinkedHashMap<>();
    parts.put("Body", carOrder.getCar().getBody().name());
    parts.put("Color", carOrder.getCar().getColor().name());
    parts.put("Engine", carOrder.getCar().getEngine().name());
    parts.put("Gearbox", carOrder.getCar().getGearbox().name());
    parts.put("Airco", carOrder.getCar().getAirco().name());
    parts.put("Wheels", carOrder.getCar().getWheels().name());
    parts.put("Seats", carOrder.getCar().getSeats().name());

    for (Map.Entry<String, String> partWithOption : parts.entrySet()) {
      result
        .append(spacer.repeat(2))
        .append(partWithOption.getKey())
        .append(": ")
        .append(partWithOption.getValue())
        .append("\n");
    }

    return result.toString();
  }


}
