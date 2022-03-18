package be.kuleuven.assemassit;

import be.kuleuven.assemassit.Controller.ControllerFactory;
import be.kuleuven.assemassit.Controller.OrderController;
import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;
import be.kuleuven.assemassit.Domain.CarModel;
import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.GarageHolder;
import be.kuleuven.assemassit.Domain.Repositories.CarModelRepository;
import be.kuleuven.assemassit.Domain.Repositories.GarageHolderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderNewCarTest {
  private OrderController orderController;
  private GarageHolderRepository mockedGarageHolderRepository;
  private CarModelRepository mockedCarModelRepository;

  @BeforeEach
  public void beforeEach() {
    ControllerFactory factory = new ControllerFactory();
    CarModel carModel = new CarModel(0, "Tolkswagen Rolo", Arrays.asList(Wheel.COMFORT), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values()));

    mockedGarageHolderRepository = mock(GarageHolderRepository.class);
    mockedCarModelRepository = mock(CarModelRepository.class);

    when(mockedGarageHolderRepository.getGarageHolders()).thenReturn(Arrays.asList(new GarageHolder(0, "WolksVagen Garage Lokeren BVBA NV")));
    when(mockedCarModelRepository.getCarModels()).thenReturn(Arrays.asList(carModel));

    orderController = factory.createOrderController(new CarManufactoringCompany(mockedCarModelRepository, LocalTime.of(6, 0), LocalTime.of(22, 0), new AssemblyLine()), mockedGarageHolderRepository);
  }

  @Test
  public void orderNewCarTest_MainSuccessScenario_IdealFlow() {

    //Precondition: The garage holder is successfully logged into the system
    orderController.logInGarageHolder(0);
    assertEquals("WolksVagen Garage Lokeren BVBA NV", orderController.giveLoggedInGarageHolderName());

    //Step 1: The garage holder has no pending or completed CarOrders yet
    assertArrayEquals(new String[]{}, orderController.givePendingCarOrders().toArray());
    assertArrayEquals(new String[]{}, orderController.giveCompletedCarOrders().toArray());

    //Step 3: The system shows a list of available car models.
    Map<Integer, String> listOfCarModels = orderController.giveListOfCarModels();
    assertEquals(1, listOfCarModels.size());
    assertEquals("Tolkswagen Rolo", listOfCarModels.get(0));

    //Step 5: The system displays an overview of all possible options, after which the user can assemble the desired car order
    String expectedPossibleOptionsOfCarModel = """
      GearBox
      MANUAL
      AUTOMATIC
      Airco
      MANUAL
      AUTOMATIC
      Wheels
      COMFORT
      SPORT
      Color
      RED
      BLUE
      BLACK
      WHITE
      Body
      SEAD
      BREAK
      Engine
      STANDARD
      PERFORMANCE
      Seats
      LEATHER_BLACK
      LEATHER_WHITE
      VINYL_GREY
      """;

    String actualPossibleOptionsOfCarModel = "";
    Map<String, List<String>> possibleOptionsOfCarModel = orderController.givePossibleOptionsOfCarModel(0);

    for (String key : possibleOptionsOfCarModel.keySet()) {
      actualPossibleOptionsOfCarModel += key + "\n";
      actualPossibleOptionsOfCarModel += possibleOptionsOfCarModel.get(key).stream().reduce("", (s, s2) -> s + s2 + "\n");
    }

    assertEquals(expectedPossibleOptionsOfCarModel, actualPossibleOptionsOfCarModel);

    //Step 6: the user completes the ordering form

    LocalDateTime estimatedTime = orderController.placeCarOrder(
      0,
      possibleOptionsOfCarModel.get("Body").get(0),
      possibleOptionsOfCarModel.get("Color").get(0),
      possibleOptionsOfCarModel.get("Engine").get(0),
      possibleOptionsOfCarModel.get("GearBox").get(0),
      possibleOptionsOfCarModel.get("Seats").get(0),
      possibleOptionsOfCarModel.get("Airco").get(0),
      possibleOptionsOfCarModel.get("Wheels").get(0));

    //Step 7: The system stores the new order and updates the production schedule.
    orderController.givePendingCarOrders().get(0);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' H:mm");

    String expected = String.format("""
      Order ID: 0    [Estimation time: %s]
          Car model: Tolkswagen Rolo
              Body: SEAD
              Color: RED
              Engine: STANDARD
              Gearbox: MANUAL
              Airco: MANUAL
              Wheels: COMFORT
              Seats: LEATHER_BLACK
            """, estimatedTime.format(formatter));

    assertEquals(expected, orderController.givePendingCarOrders().stream().reduce("", String::concat));

    //step 8: The system presents an estimated completion date for the new order.
    LocalDateTime localDateTimeNow = LocalDateTime.now();
    LocalDateTime actualDate = LocalDateTime.now();

    if (localDateTimeNow.getHour() < 6) {
      actualDate = actualDate.withHour(9);
    }
    if (localDateTimeNow.getHour() >= 6 && localDateTimeNow.getHour() <= 19) {
      actualDate = actualDate.plusHours(3);
    }
    if (localDateTimeNow.getHour() > 19) {
      actualDate = actualDate.plusDays(1).withHour(14).withMinute(0);
    }

    assertEquals(actualDate.format(formatter), estimatedTime.format(formatter));
  }

  @Test
  public void orderNewCarTest_MainSuccessScenario_UserInsertsIllegalValues() {

    //Precondition: The garage holder is successfully logged into the system
    //The garage holder inserts 2 wrong values and uses a valid id on the third try
    assertThrows(IllegalArgumentException.class, () -> orderController.logInGarageHolder(-1));
    assertThrows(IllegalArgumentException.class, () -> orderController.logInGarageHolder(2));
    orderController.logInGarageHolder(0);
    assertEquals("WolksVagen Garage Lokeren BVBA NV", orderController.giveLoggedInGarageHolderName());

    //Step 1: The garage holder has no pending or completed CarOrders yet
    assertArrayEquals(new String[]{}, orderController.givePendingCarOrders().toArray());
    assertArrayEquals(new String[]{}, orderController.giveCompletedCarOrders().toArray());

    //Step 3: The system shows a list of available car models.
    Map<Integer, String> listOfCarModels = orderController.giveListOfCarModels();
    assertEquals(1, listOfCarModels.size());
    assertEquals("Tolkswagen Rolo", listOfCarModels.get(0));

    //Step 5: The system displays an overview of all possible options, after which the user can assemble the desired car order
    String expectedPossibleOptionsOfCarModel = """
      GearBox
      MANUAL
      AUTOMATIC
      Airco
      MANUAL
      AUTOMATIC
      Wheels
      COMFORT
      Color
      RED
      BLUE
      BLACK
      WHITE
      Body
      SEAD
      BREAK
      Engine
      STANDARD
      PERFORMANCE
      Seats
      LEATHER_BLACK
      LEATHER_WHITE
      VINYL_GREY
      """;

    String actualPossibleOptionsOfCarModel = "";
    Map<String, List<String>> possibleOptionsOfCarModel = orderController.givePossibleOptionsOfCarModel(0);

    for (String key : possibleOptionsOfCarModel.keySet()) {
      actualPossibleOptionsOfCarModel += key + "\n";
      actualPossibleOptionsOfCarModel += possibleOptionsOfCarModel.get(key).stream().reduce("", (s, s2) -> s + s2 + "\n");
    }

    assertEquals(expectedPossibleOptionsOfCarModel, actualPossibleOptionsOfCarModel);

    //Step 6: the user tries to complete the ordering form

    //The user picks a non-existent carModelId
    assertThrows(IllegalArgumentException.class, () ->
      orderController.placeCarOrder(
        2,
        possibleOptionsOfCarModel.get("Body").get(0),
        possibleOptionsOfCarModel.get("Color").get(0),
        possibleOptionsOfCarModel.get("Engine").get(0),
        possibleOptionsOfCarModel.get("GearBox").get(0),
        possibleOptionsOfCarModel.get("Seats").get(0),
        possibleOptionsOfCarModel.get("Airco").get(0),
        possibleOptionsOfCarModel.get("Wheels").get(0))
    );

    //The user picks a negative carModelId
    assertThrows(IllegalArgumentException.class, () ->
      orderController.placeCarOrder(
        -1,
        possibleOptionsOfCarModel.get("Body").get(0),
        possibleOptionsOfCarModel.get("Color").get(0),
        possibleOptionsOfCarModel.get("Engine").get(0),
        possibleOptionsOfCarModel.get("GearBox").get(0),
        possibleOptionsOfCarModel.get("Seats").get(0),
        possibleOptionsOfCarModel.get("Airco").get(0),
        possibleOptionsOfCarModel.get("Wheels").get(0))
    );

    //The user picks a value for wheels which is not included in the carModel
    assertThrows(IllegalArgumentException.class, () ->
      orderController.placeCarOrder(
        0,
        possibleOptionsOfCarModel.get("Body").get(0),
        possibleOptionsOfCarModel.get("Color").get(0),
        possibleOptionsOfCarModel.get("Engine").get(0),
        possibleOptionsOfCarModel.get("GearBox").get(0),
        possibleOptionsOfCarModel.get("Seats").get(0),
        possibleOptionsOfCarModel.get("Airco").get(0),
        "SPORT")
    );

    //The user picks a value for wheels which does not exist in the system
    assertThrows(IllegalArgumentException.class, () ->
      orderController.placeCarOrder(
        0,
        possibleOptionsOfCarModel.get("Body").get(0),
        possibleOptionsOfCarModel.get("Color").get(0),
        possibleOptionsOfCarModel.get("Engine").get(0),
        possibleOptionsOfCarModel.get("GearBox").get(0),
        possibleOptionsOfCarModel.get("Seats").get(0),
        possibleOptionsOfCarModel.get("Airco").get(0),
        "MICHELIN")
    );

    //The user finally chooses valid options to place a carOrder, and proceeds in the use case
    LocalDateTime estimatedTime = orderController.placeCarOrder(
      0,
      possibleOptionsOfCarModel.get("Body").get(0),
      possibleOptionsOfCarModel.get("Color").get(0),
      possibleOptionsOfCarModel.get("Engine").get(0),
      possibleOptionsOfCarModel.get("GearBox").get(0),
      possibleOptionsOfCarModel.get("Seats").get(0),
      possibleOptionsOfCarModel.get("Airco").get(0),
      possibleOptionsOfCarModel.get("Wheels").get(0));


    //Step 7: The system stores the new order and updates the production schedule.
    orderController.givePendingCarOrders().get(0);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' H:mm");

    String expected = String.format("""
      Order ID: 0    [Estimation time: %s]
          Car model: Tolkswagen Rolo
              Body: SEAD
              Color: RED
              Engine: STANDARD
              Gearbox: MANUAL
              Airco: MANUAL
              Wheels: COMFORT
              Seats: LEATHER_BLACK
            """, estimatedTime.format(formatter));

    assertEquals(expected, orderController.givePendingCarOrders().stream().reduce("", String::concat));

    //step 8: The system presents an estimated completion date for the new order.
    LocalDateTime localDateTimeNow = LocalDateTime.now();
    LocalDateTime actualDate = LocalDateTime.now();

    if (localDateTimeNow.getHour() < 6) {
      actualDate = actualDate.withHour(9);
    }
    if (localDateTimeNow.getHour() >= 6 && localDateTimeNow.getHour() <= 19) {
      actualDate = actualDate.plusHours(3);
    }
    if (localDateTimeNow.getHour() > 19) {
      actualDate = actualDate.plusDays(1).withHour(14).withMinute(0);
    }

    assertEquals(actualDate.format(formatter), estimatedTime.format(formatter));
  }

  @Test
  public void orderNewCarTest_userNotLoggedIn() {

    //Step 1: The system throws an illegalStateException, indicating that it can not show orders if there is no logged-in user.
    //The user cannot proceed the use case at this point.

    assertThrows(IllegalStateException.class, () -> orderController.givePendingCarOrders());
    assertThrows(IllegalStateException.class, () -> orderController.giveCompletedCarOrders());
  }
}
