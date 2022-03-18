package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.*;
import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.Repositories.GarageHolderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

  private OrderController orderController;
  private CarManufactoringCompany mockedCarManufacturingCompany;
  private GarageHolderRepository mockedGarageHolderRepository;
  private GarageHolder mockedGarageHolder;
  private CarOrder mockedCarOrder;
  private Car mockedCar;
  private CarModel mockedCarModel;

  @BeforeEach
  public void beforeEach() {
    mockedCarManufacturingCompany = mock(CarManufactoringCompany.class);
    mockedGarageHolderRepository = mock(GarageHolderRepository.class);
    mockedGarageHolder = mock(GarageHolder.class);
    mockedCarOrder = mock(CarOrder.class);
    mockedCar = mock(Car.class);
    mockedCarModel = mock(CarModel.class);

    when(mockedCarManufacturingCompany.getCarModels()).thenReturn(Arrays.asList(mockedCarModel));
    when(mockedCarManufacturingCompany.giveCarModelWithId(0)).thenReturn(mockedCarModel);
    when(mockedCarManufacturingCompany.giveEstimatedCompletionDateOfLatestProcess()).thenReturn(LocalDateTime.of(1998, 12, 15, 12, 0));

    when(mockedGarageHolder.getId()).thenReturn(0);
    when(mockedGarageHolder.getName()).thenReturn("WolksVagen Garage Lokeren BVBA NV");
    when(mockedGarageHolder.getCompletionTimeFromOrder(0)).thenReturn(LocalDateTime.of(1998, 12, 15, 12, 0));
    when(mockedGarageHolder.getOrder(0)).thenReturn(mockedCarOrder);

    when(mockedGarageHolderRepository.getGarageHolders()).thenReturn(Arrays.asList(mockedGarageHolder));

    when(mockedCarOrder.getEstimatedCompletionTime()).thenReturn(LocalDateTime.of(1998, 12, 15, 12, 0));
    when(mockedCarOrder.getCompletionTime()).thenReturn(LocalDateTime.of(1998, 12, 15, 15, 0));
    when(mockedCarOrder.getCar()).thenReturn(mockedCar);

    when(mockedCar.getCarModel()).thenReturn(mockedCarModel);
    when(mockedCar.getBody()).thenReturn(Body.BREAK);
    when(mockedCar.getColor()).thenReturn(Color.BLACK);
    when(mockedCar.getEngine()).thenReturn(Engine.PERFORMANCE);
    when(mockedCar.getGearbox()).thenReturn(Gearbox.MANUAL);
    when(mockedCar.getAirco()).thenReturn(Airco.AUTOMATIC);
    when(mockedCar.getWheels()).thenReturn(Wheel.COMFORT);
    when(mockedCar.getSeats()).thenReturn(Seat.LEATHER_BLACK);

    when(mockedCarModel.getName()).thenReturn("Tolkswagen Molf");
    when(mockedCarModel.getBodyOptions()).thenReturn(Arrays.stream(Body.values()).toList());
    when(mockedCarModel.getColorOptions()).thenReturn(Arrays.stream(Color.values()).toList());
    when(mockedCarModel.getEngineOptions()).thenReturn(Arrays.stream(Engine.values()).toList());
    when(mockedCarModel.getGearboxOptions()).thenReturn(Arrays.stream(Gearbox.values()).toList());
    when(mockedCarModel.getAircoOptions()).thenReturn(Arrays.stream(Airco.values()).toList());
    when(mockedCarModel.getWheelOptions()).thenReturn(Arrays.stream(Wheel.values()).toList());
    when(mockedCarModel.getSeatOptions()).thenReturn(Arrays.stream(Seat.values()).toList());
    when(mockedCarModel.isValidConfiguration(Body.BREAK, Color.BLACK, Engine.PERFORMANCE, Gearbox.MANUAL, Seat.LEATHER_BLACK, Airco.AUTOMATIC, Wheel.COMFORT)).thenReturn(true);

    orderController = new OrderController(mockedCarManufacturingCompany, mockedGarageHolderRepository);
  }

  @Test
  public void logInGarageHolderTest_succeeds() {
    assertAll(() -> orderController.logInGarageHolder(0));
  }

  @Test
  public void logInGarageHolderTest_throws() {
    assertThrows(IllegalArgumentException.class, () -> orderController.logInGarageHolder(-2));
    assertThrows(IllegalArgumentException.class, () -> orderController.logInGarageHolder(200));
  }

  @Test
  public void logOffTest() {
    orderController.logInGarageHolder(0);
    assertEquals("WolksVagen Garage Lokeren BVBA NV", orderController.giveLoggedInGarageHolderName());
    assertAll(orderController::logOffGarageHolder);
    assertThrows(IllegalStateException.class, orderController::giveLoggedInGarageHolderName);
  }

  @Test
  public void giveLoggedInGarageHolderNameTest_succeeds() {
    orderController.logInGarageHolder(0);
    assertEquals("WolksVagen Garage Lokeren BVBA NV", orderController.giveLoggedInGarageHolderName());
  }

  @Test
  public void giveLoggedInGarageHolderNameTest_throws() {
    assertThrows(IllegalStateException.class, orderController::giveLoggedInGarageHolderName);
  }

  @Test
  public void giveGarageHoldersTest() {
    Map<Integer, String> garageHolders = orderController.giveGarageHolders();
    assertEquals(1, garageHolders.size());
    assertEquals("WolksVagen Garage Lokeren BVBA NV", garageHolders.get(0));
  }

  @Test
  public void getCompletionDateTest_succeeds() {
    orderController.logInGarageHolder(0);
    assertEquals(LocalDateTime.of(1998, 12, 15, 12, 0), orderController.getCompletionDate(0));
  }

  @Test
  public void getCompletionDateTest_throws() {
    assertThrows(IllegalArgumentException.class, () -> orderController.getCompletionDate(-1));
    assertThrows(IllegalStateException.class, () -> orderController.getCompletionDate(0));
  }

  @Test
  public void chooseOrderTest_succeeds() {
    orderController.logInGarageHolder(0);
    assertEquals(0, orderController.chooseOrder(0).getCar().getId());
  }

  @Test
  public void chooseOrderTest_throws() {
    assertThrows(IllegalArgumentException.class, () -> orderController.chooseOrder(-1));
    assertThrows(IllegalStateException.class, () -> orderController.chooseOrder(0));
  }

  @Test
  public void givePendingCarOrdersTest_throws() {
    assertThrows(IllegalStateException.class, orderController::givePendingCarOrders);
  }

  @Test
  public void givePendingCarOrdersTest_GarageOwnerHasNoPendingOrders_succeeds() {
    orderController.logInGarageHolder(0);
    assertArrayEquals(new String[]{}, orderController.givePendingCarOrders().toArray());
  }

  @Test
  public void givePendingCarOrdersTest_GarageOwnerHasPendingOrders_succeeds() {
    when(mockedCarOrder.isPending()).thenReturn(true);
    when(mockedGarageHolder.getCarOrders()).thenReturn(Arrays.asList(mockedCarOrder));
    orderController.logInGarageHolder(0);

    String expected = """
      Order ID: 0    [Estimation time: 15/12/1998 at 12:00]
          Car model: Tolkswagen Molf
              Body: BREAK
              Color: BLACK
              Engine: PERFORMANCE
              Gearbox: MANUAL
              Airco: AUTOMATIC
              Wheels: COMFORT
              Seats: LEATHER_BLACK
      """;

    assertEquals(expected, orderController.givePendingCarOrders().stream().reduce("", String::concat));
  }

  @Test
  public void giveCompletedCarOrdersTest_GarageOwnerHasNoCompletedOrders_succeeds() {
    orderController.logInGarageHolder(0);
    assertArrayEquals(new String[]{}, orderController.giveCompletedCarOrders().toArray());
  }

  @Test
  public void giveCompletedCarOrdersTest_GarageOwnerHasCompletedOrders_succeeds() {
    when(mockedCarOrder.isPending()).thenReturn(false);
    when(mockedGarageHolder.getCarOrders()).thenReturn(Arrays.asList(mockedCarOrder));
    orderController.logInGarageHolder(0);

    String expected = """
      Order ID: 0    [Completed at: 15/12/1998 at 15:00]
          Car model: Tolkswagen Molf
              Body: BREAK
              Color: BLACK
              Engine: PERFORMANCE
              Gearbox: MANUAL
              Airco: AUTOMATIC
              Wheels: COMFORT
              Seats: LEATHER_BLACK
      """;

    assertEquals(expected, orderController.giveCompletedCarOrders().stream().reduce("", String::concat));
  }

  @Test
  public void giveCompletedCarOrdersTest_throws() {
    assertThrows(IllegalStateException.class, () -> orderController.giveCompletedCarOrders());
  }

  @Test
  public void giveListOfCarModelsTest() {
    Map<Integer, String> listOfCarModels = orderController.giveListOfCarModels();
    assertEquals(1, listOfCarModels.size());
    assertEquals("Tolkswagen Molf", listOfCarModels.get(0));
  }

  @Test
  public void givePossibleOptionsOfCarModelTest() {
    String expected = """
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

    String actual = "";
    Map<String, List<String>> possibleOptionsOfCarModel = orderController.givePossibleOptionsOfCarModel(0);

    for (String key : possibleOptionsOfCarModel.keySet()) {
      actual += key + "\n";
      actual += possibleOptionsOfCarModel.get(key).stream().reduce("", (s, s2) -> s + s2 + "\n");
    }

    assertEquals(expected, actual);
  }

  @Test
  public void placeCarOrderTest_succeeds() {
    orderController.logInGarageHolder(0);
    LocalDateTime estimatedCompletionTime = orderController.placeCarOrder(0, "BREAK", "BLACK", "PERFORMANCE", "MANUAL", "LEATHER_BLACK", "AUTOMATIC", "COMFORT");
    assertEquals(LocalDateTime.of(1998, 12, 15, 12, 0), estimatedCompletionTime);
  }

  @Test
  public void placeCarOrderTest_throws() {
    assertThrows(IllegalStateException.class, () -> orderController.placeCarOrder(0, "", "", "", "", "", "", ""));
    orderController.logInGarageHolder(0);
    assertThrows(IllegalArgumentException.class, () -> orderController.placeCarOrder(0, "", "", "", "", "", "", ""));

  }

}
