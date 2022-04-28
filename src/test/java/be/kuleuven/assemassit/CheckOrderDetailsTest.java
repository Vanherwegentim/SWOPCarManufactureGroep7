package be.kuleuven.assemassit;

import be.kuleuven.assemassit.Controller.CheckOrderDetailsController;
import be.kuleuven.assemassit.Controller.ControllerFactory;
import be.kuleuven.assemassit.Controller.OrderNewCarController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CheckOrderDetailsTest {


  int carOrderID1, carOrderID2, carOrderID3;
  private OrderNewCarController orderNewCarController;
  private CheckOrderDetailsController checkOrderDetailsController;
  private ControllerFactory factory;

  @BeforeEach
  public void beforeEach() {
    factory = new ControllerFactory();

    // Precondition: The garage holder is successfully logged into the system.
    factory.createLoginController().logInGarageHolder(0);

    orderNewCarController = factory.createOrderNewCarController();
    checkOrderDetailsController = factory.createCheckOrderDetailsController();


  }

  @Test
  public void checkOrderDetails_MainSuccessScenario_GarageHolderHasNoOrders() {
    // 1. The system presents an overview of the orders placed by the user
    List<String> pendingOrders = checkOrderDetailsController.givePendingCarOrders();
    List<String> completedOrders = checkOrderDetailsController.giveCompletedCarOrders();

    assertTrue(pendingOrders.isEmpty());
    assertTrue(completedOrders.isEmpty());

    // The garage holder has no pending or completed orders and the use case ends.
  }

  @Test
  public void checkOrderDetails_MainSuccessScenario_GarageHolderHasPendingOrders() {
    carOrderID1 = placeCarOrder(0);
    carOrderID2 = placeCarOrder(1);
    carOrderID3 = placeCarOrder(2);

    // 1. The system presents an overview of the orders placed by the user
    List<String> pendingOrders = checkOrderDetailsController.givePendingCarOrders();
    List<String> completedOrders = checkOrderDetailsController.giveCompletedCarOrders();

    String expected = String.format(
      "Order ID: %d    [Estimated date: 29/08/1999]    [Car model: Model A]" + System.lineSeparator() +
        "Order ID: %d    [Estimated date: 29/08/1999]    [Car model: Model B]" + System.lineSeparator() +
        "Order ID: %d    [Estimated date: 29/08/1999]    [Car model: Model C]" + System.lineSeparator(),
      carOrderID1,
      carOrderID2,
      carOrderID3);

    assertEquals(expected, pendingOrders.stream().reduce("", (s, s2) -> s + s2));
    assertTrue(completedOrders.isEmpty());

    // 2. The user indicates the order he wants to check the details for.
    // 3. The system shows the details of the order.

    assertEquals(formatExpectedOrderDetails(carOrderID1, "29/08/1999", 0), checkOrderDetailsController.giveOrderDetails(carOrderID1).get());
    assertEquals(formatExpectedOrderDetails(carOrderID2, "29/08/1999", 1), checkOrderDetailsController.giveOrderDetails(carOrderID2).get());
    assertEquals(formatExpectedOrderDetails(carOrderID3, "29/08/1999", 2), checkOrderDetailsController.giveOrderDetails(carOrderID3).get());

    // 4. The user indicates he is finished viewing the details.
  }


  @Test
  public void checkOrderDetails_UserInsertsIllegalValues_GarageHolderHasPendingOrders() {
    carOrderID1 = placeCarOrder(0);
    carOrderID2 = placeCarOrder(1);
    carOrderID3 = placeCarOrder(2);

    // 1. The system presents an overview of the orders placed by the user
    List<String> pendingOrders = checkOrderDetailsController.givePendingCarOrders();
    List<String> completedOrders = checkOrderDetailsController.giveCompletedCarOrders();

    String expected = String.format(
      "Order ID: %d    [Estimated date: 29/08/1999]    [Car model: Model A]" + System.lineSeparator() +
        "Order ID: %d    [Estimated date: 29/08/1999]    [Car model: Model B]" + System.lineSeparator() +
        "Order ID: %d    [Estimated date: 29/08/1999]    [Car model: Model C]" + System.lineSeparator(),
      carOrderID1,
      carOrderID2,
      carOrderID3);

    assertEquals(expected, pendingOrders.stream().reduce("", (s, s2) -> s + s2));
    assertTrue(completedOrders.isEmpty());

    // 2. The user indicates the order he wants to check the details for but inserts illegal values.

    assertThrows(IllegalArgumentException.class, () -> checkOrderDetailsController.giveOrderDetails(-1));
    assertTrue(checkOrderDetailsController.giveOrderDetails(524).isEmpty());

    // 3. The system shows the details of the order.

    assertEquals(formatExpectedOrderDetails(carOrderID1, "29/08/1999", 0), checkOrderDetailsController.giveOrderDetails(carOrderID1).get());
    assertEquals(formatExpectedOrderDetails(carOrderID2, "29/08/1999", 1), checkOrderDetailsController.giveOrderDetails(carOrderID2).get());
    assertEquals(formatExpectedOrderDetails(carOrderID3, "29/08/1999", 2), checkOrderDetailsController.giveOrderDetails(carOrderID3).get());

    // 4. The user indicates he is finished viewing the details.
  }

  private int placeCarOrder(int modelID) {
    Map<String, List<String>> possibleOptionsOfCarModel = orderNewCarController.givePossibleOptionsOfCarModel(modelID);
    return orderNewCarController.placeCarOrder(
      modelID,
      possibleOptionsOfCarModel.get("Body").get(0),
      possibleOptionsOfCarModel.get("Color").get(0),
      possibleOptionsOfCarModel.get("Engine").get(0),
      possibleOptionsOfCarModel.get("GearBox").get(0),
      possibleOptionsOfCarModel.get("Seats").get(0),
      possibleOptionsOfCarModel.get("Airco").get(0),
      possibleOptionsOfCarModel.get("Wheels").get(0),
      possibleOptionsOfCarModel.get("Spoiler").get(0));
  }

  private String formatExpectedOrderDetails(int orderID, String estimatedTime, int modelID) {
    Map<String, List<String>> possibleOptionsOfCarModel = orderNewCarController.givePossibleOptionsOfCarModel(modelID);
    return String.format(
      "Order ID: %d    [Estimated date: %s]" + System.lineSeparator() +
        "    Car model: Model %s" + System.lineSeparator() +
        "        Body: %s" + System.lineSeparator() +
        "        Color: %s" + System.lineSeparator() +
        "        Engine: %s" + System.lineSeparator() +
        "        Gearbox: %s" + System.lineSeparator() +
        "        Airco: %s" + System.lineSeparator() +
        "        Wheels: %s" + System.lineSeparator() +
        "        Seats: %s" + System.lineSeparator() +
        "        Spoiler: %s" + System.lineSeparator(),
      orderID, estimatedTime,
      (char) (modelID + 65),
      possibleOptionsOfCarModel.get("Body").get(0),
      possibleOptionsOfCarModel.get("Color").get(0),
      possibleOptionsOfCarModel.get("Engine").get(0),
      possibleOptionsOfCarModel.get("GearBox").get(0),
      possibleOptionsOfCarModel.get("Airco").get(0),
      possibleOptionsOfCarModel.get("Wheels").get(0),
      possibleOptionsOfCarModel.get("Seats").get(0),
      possibleOptionsOfCarModel.get("Spoiler").get(0)
    );

    // Example of possible output:

    // Order ID: 0    [Estimated time: 25/04/2022 at 20:23]
    //     Car model: Model A
    //         Body: SEDAN
    //         Color: RED
    //         Engine: STANDARD
    //         Gearbox: SIX_SPEED_MANUAL
    //         Airco: MANUAL
    //         Wheels: WINTER
    //         Seats: LEATHER_WHITE
    //         Spoiler: NO_SPOILER
  }

  private String calculateExpectedDate(int orderCount) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' H:mm");

    LocalDateTime localDateTimeNow = LocalDateTime.now();
    LocalDateTime expectedDate = LocalDateTime.now();

    if (localDateTimeNow.getHour() < 6) {
      expectedDate = expectedDate.withHour(9).withMinute(0);
    }
    if (localDateTimeNow.getHour() >= 6 && localDateTimeNow.getHour() <= 19) {
      expectedDate = expectedDate.plusHours(3);
    }
    if (localDateTimeNow.getHour() > 19 || (localDateTimeNow.getHour() == 19 && localDateTimeNow.getMinute() > 0)) {
      expectedDate = expectedDate.plusDays(1).withHour(9).withMinute(0);
    }
    if (expectedDate.getHour() + orderCount > 22 || (expectedDate.getHour() + orderCount == 22 && localDateTimeNow.getMinute() > 0)) {
      expectedDate = expectedDate.plusDays(1).withHour(9).withMinute(0);
    }

    return expectedDate.plusHours(orderCount).format(formatter);
  }
}
