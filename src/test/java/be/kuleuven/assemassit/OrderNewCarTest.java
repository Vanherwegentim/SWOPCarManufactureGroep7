package be.kuleuven.assemassit;

import be.kuleuven.assemassit.Controller.CheckOrderDetailsController;
import be.kuleuven.assemassit.Controller.ControllerFactory;
import be.kuleuven.assemassit.Controller.LoginController;
import be.kuleuven.assemassit.Controller.OrderNewCarController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class OrderNewCarTest {
  private OrderNewCarController orderNewCarController;
  private CheckOrderDetailsController checkOrderDetailsController;
  private ControllerFactory factory;

  @BeforeEach
  public void beforeEach() {
    factory = new ControllerFactory();

    LoginController loginController = factory.createLoginController();
    loginController.logInGarageHolder(0);
    orderNewCarController = factory.createOrderNewCarController();
    checkOrderDetailsController = factory.createCheckOrderDetailsController();
  }

  @Test
  public void orderNewCarTest_MainSuccessScenario_IdealFlow() {

    //Precondition: The garage holder is successfully logged into the system

    assertEquals("Joe Lamb", orderNewCarController.giveLoggedInGarageHolderName());

    //Step 1: The garage holder has no pending or completed CarOrders yet
    assertArrayEquals(new String[]{}, checkOrderDetailsController.givePendingCarOrders().toArray());
    assertArrayEquals(new String[]{}, checkOrderDetailsController.giveCompletedCarOrders().toArray());

    //Step 3: The system shows a list of available car models.
    Map<Integer, String> listOfCarModels = orderNewCarController.giveListOfCarModels();
    assertEquals(3, listOfCarModels.size());
    assertEquals("Model A", listOfCarModels.get(0));
    assertEquals("Model B", listOfCarModels.get(1));
    assertEquals("Model C", listOfCarModels.get(2));

    //Step 5: The system displays an overview of all possible options, after which the user can assemble the desired car order
    String expectedPossibleOptionsOfCarModel = """
      GearBox
      SIX_SPEED_MANUAL
      FIVE_SPEED_MANUAL
      FIVE_SPEED_AUTOMATIC
      Airco
      MANUAL
      AUTOMATIC
      NO_AIRCO
      Spoiler
      NO_SPOILER
      Wheels
      WINTER
      COMFORT
      SPORT
      Color
      RED
      BLUE
      BLACK
      WHITE
      Body
      SEDAN
      BREAK
      Engine
      STANDARD
      PERFORMANCE
      Seats
      LEATHER_WHITE
      LEATHER_BLACK
      VINYL_GREY
      """;

    String actualPossibleOptionsOfCarModel = "";
    Map<String, List<String>> possibleOptionsOfCarModel = orderNewCarController.givePossibleOptionsOfCarModel(0);

    for (String key : possibleOptionsOfCarModel.keySet()) {
      actualPossibleOptionsOfCarModel += key + "\n";
      actualPossibleOptionsOfCarModel += possibleOptionsOfCarModel.get(key).stream().reduce("", (s, s2) -> s + s2 + "\n");
    }

    assertEquals(expectedPossibleOptionsOfCarModel, actualPossibleOptionsOfCarModel);

    //Step 6: the user completes the ordering form

    LocalDateTime estimatedTime = orderNewCarController.placeCarOrder(
      0,
      possibleOptionsOfCarModel.get("Body").get(0),
      possibleOptionsOfCarModel.get("Color").get(0),
      possibleOptionsOfCarModel.get("Engine").get(0),
      possibleOptionsOfCarModel.get("GearBox").get(0),
      possibleOptionsOfCarModel.get("Seats").get(0),
      possibleOptionsOfCarModel.get("Airco").get(0),
      possibleOptionsOfCarModel.get("Wheels").get(0),
      possibleOptionsOfCarModel.get("Spoiler").get(0));

    //Step 7: The system stores the new order and updates the production schedule.
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' H:mm");

    String expected = String.format("Order ID: 0    [Estimation time: %s]    [Car model: Model A]\n", estimatedTime.format(formatter));
    String actual = checkOrderDetailsController.givePendingCarOrders().stream().reduce("", String::concat);

    assertEquals(expected, actual);

    //step 8: The system presents an estimated completion date for the new order.
    LocalDateTime localDateTimeNow = LocalDateTime.now();
    LocalDateTime expectedDate = LocalDateTime.now();

    if (localDateTimeNow.getHour() < 6) {
      expectedDate = expectedDate.withHour(8).withMinute(0);
    }
    if (localDateTimeNow.getHour() >= 6 && localDateTimeNow.getHour() <= 19) {
      expectedDate = expectedDate.plusHours(3);
    }
    if (localDateTimeNow.getHour() > 19) {
      expectedDate = expectedDate.plusDays(1).withHour(8).withMinute(0);
    }

    assertEquals(expectedDate.format(formatter), estimatedTime.format(formatter));
  }

  @Test
  public void orderNewCarTest_MainSuccessScenario_UserInsertsIllegalValues() {

    //Precondition: The garage holder is successfully logged into the system
    //The garage holder inserts 2 wrong values and uses a valid id on the third try
    assertEquals("Joe Lamb", orderNewCarController.giveLoggedInGarageHolderName());

    //Step 1: The garage holder has no pending or completed CarOrders yet
    assertArrayEquals(new String[]{}, checkOrderDetailsController.givePendingCarOrders().toArray());
    assertArrayEquals(new String[]{}, checkOrderDetailsController.giveCompletedCarOrders().toArray());

    //Step 3: The system shows a list of available car models.
    Map<Integer, String> listOfCarModels = orderNewCarController.giveListOfCarModels();
    assertEquals(3, listOfCarModels.size());
    assertEquals("Model A", listOfCarModels.get(0));
    assertEquals("Model B", listOfCarModels.get(1));
    assertEquals("Model C", listOfCarModels.get(2));

    //Step 5: The system displays an overview of all possible options, after which the user can assemble the desired car order
    String expectedPossibleOptionsOfCarModel = """
      GearBox
      SIX_SPEED_MANUAL
      FIVE_SPEED_MANUAL
      FIVE_SPEED_AUTOMATIC
      Airco
      MANUAL
      AUTOMATIC
      NO_AIRCO
      Spoiler
      NO_SPOILER
      Wheels
      WINTER
      COMFORT
      SPORT
      Color
      RED
      BLUE
      BLACK
      WHITE
      Body
      SEDAN
      BREAK
      Engine
      STANDARD
      PERFORMANCE
      Seats
      LEATHER_WHITE
      LEATHER_BLACK
      VINYL_GREY
      """;

    String actualPossibleOptionsOfCarModel = "";
    Map<String, List<String>> possibleOptionsOfCarModel = orderNewCarController.givePossibleOptionsOfCarModel(0);

    for (String key : possibleOptionsOfCarModel.keySet()) {
      actualPossibleOptionsOfCarModel += key + "\n";
      actualPossibleOptionsOfCarModel += possibleOptionsOfCarModel.get(key).stream().reduce("", (s, s2) -> s + s2 + "\n");
    }

    assertEquals(expectedPossibleOptionsOfCarModel, actualPossibleOptionsOfCarModel);

    //Step 6: the user tries to complete the ordering form

    //The user picks a non-existent carModelId
    assertThrows(IllegalArgumentException.class, () ->
      orderNewCarController.placeCarOrder(
        2,
        possibleOptionsOfCarModel.get("Body").get(0),
        possibleOptionsOfCarModel.get("Color").get(0),
        possibleOptionsOfCarModel.get("Engine").get(0),
        possibleOptionsOfCarModel.get("GearBox").get(0),
        possibleOptionsOfCarModel.get("Seats").get(0),
        possibleOptionsOfCarModel.get("Airco").get(0),
        possibleOptionsOfCarModel.get("Wheels").get(0),
        possibleOptionsOfCarModel.get("Spoiler").get(0))
    );

    //The user picks a negative carModelId
    assertThrows(IllegalArgumentException.class, () ->
      orderNewCarController.placeCarOrder(
        -1,
        possibleOptionsOfCarModel.get("Body").get(0),
        possibleOptionsOfCarModel.get("Color").get(0),
        possibleOptionsOfCarModel.get("Engine").get(0),
        possibleOptionsOfCarModel.get("GearBox").get(0),
        possibleOptionsOfCarModel.get("Seats").get(0),
        possibleOptionsOfCarModel.get("Airco").get(0),
        possibleOptionsOfCarModel.get("Wheels").get(0),
        possibleOptionsOfCarModel.get("Spoiler").get(0))
    );

    //The user picks a value for wheels which is not included in the carModel
    assertThrows(IllegalArgumentException.class, () ->
      orderNewCarController.placeCarOrder(
        0,
        possibleOptionsOfCarModel.get("Body").get(0),
        possibleOptionsOfCarModel.get("Color").get(0),
        possibleOptionsOfCarModel.get("Engine").get(0),
        possibleOptionsOfCarModel.get("GearBox").get(0),
        possibleOptionsOfCarModel.get("Seats").get(0),
        possibleOptionsOfCarModel.get("Airco").get(0),
        possibleOptionsOfCarModel.get("Wheels").get(0),
        "LOW")
    );

    //The user picks a value for wheels which does not exist in the system
    assertThrows(IllegalArgumentException.class, () ->
      orderNewCarController.placeCarOrder(
        0,
        possibleOptionsOfCarModel.get("Body").get(0),
        possibleOptionsOfCarModel.get("Color").get(0),
        possibleOptionsOfCarModel.get("Engine").get(0),
        possibleOptionsOfCarModel.get("GearBox").get(0),
        possibleOptionsOfCarModel.get("Seats").get(0),
        possibleOptionsOfCarModel.get("Airco").get(0),
        "MICHELIN",
        possibleOptionsOfCarModel.get("Spoiler").get(0))
    );

    //The user finally chooses valid options to place a carOrder, and proceeds in the use case
    LocalDateTime estimatedTime = orderNewCarController.placeCarOrder(
      0,
      possibleOptionsOfCarModel.get("Body").get(0),
      possibleOptionsOfCarModel.get("Color").get(0),
      possibleOptionsOfCarModel.get("Engine").get(0),
      possibleOptionsOfCarModel.get("GearBox").get(0),
      possibleOptionsOfCarModel.get("Seats").get(0),
      possibleOptionsOfCarModel.get("Airco").get(0),
      possibleOptionsOfCarModel.get("Wheels").get(0),
      possibleOptionsOfCarModel.get("Spoiler").get(0));


    //Step 7: The system stores the new order and updates the production schedule.
    //orderNewCarController.givePendingCarOrders().get(0);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' H:mm");

    String expected = String.format("    Car model: Tolkswagen Rolo\n" +
      "        Body: SEAD\n" +
      "        Color: RED\n" +
      "        Engine: STANDARD\n" +
      "        Gearbox: MANUAL\n" +
      "        Airco: MANUAL\n" +
      "        Wheels: COMFORT\n" +
      "        Seats: LEATHER_BLACK\n", estimatedTime.format(formatter));

    //String actual = orderNewCarController.givePendingCarOrders().stream().reduce("", String::concat);
    //actual = actual.substring(actual.indexOf('\n') + 1);

    //assertEquals(expected, actual);

    //step 8: The system presents an estimated completion date for the new order.
    LocalDateTime localDateTimeNow = LocalDateTime.now();
    LocalDateTime actualDate = LocalDateTime.now();

    if (localDateTimeNow.getHour() < 6) {
      actualDate = actualDate.withHour(8).withMinute(0);
    }
    if (localDateTimeNow.getHour() >= 6 && localDateTimeNow.getHour() <= 19) {
      actualDate = actualDate.plusHours(3);
    }
    if (localDateTimeNow.getHour() > 19) {
      actualDate = actualDate.plusDays(1).withHour(8).withMinute(0);
    }

    assertEquals(actualDate.format(formatter), estimatedTime.format(formatter));
  }

  @Test
  public void orderNewCarTest_userNotLoggedIn() {

    //Step 1: The system throws an illegalStateException, indicating that it can not show orders if there is no logged-in user.
    //The user cannot proceed the use case at this point.
    factory.createLoginController().logOffGarageHolder();

    assertThrows(IllegalStateException.class, () -> factory.createOrderNewCarController());
  }
}
