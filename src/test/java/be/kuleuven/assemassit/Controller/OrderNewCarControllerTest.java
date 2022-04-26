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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderNewCarControllerTest {

  private OrderNewCarController orderNewCarController;
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
    when(mockedGarageHolder.findCarOrder(anyInt())).thenReturn(Optional.of(mockedCarOrder));

    when(mockedGarageHolderRepository.getGarageHolders()).thenReturn(Arrays.asList(mockedGarageHolder));

    when(mockedCarOrder.getEstimatedCompletionTime()).thenReturn(LocalDateTime.of(1998, 12, 15, 12, 0));
    when(mockedCarOrder.getCompletionTime()).thenReturn(LocalDateTime.of(1998, 12, 15, 15, 0));
    when(mockedCarOrder.getCar()).thenReturn(mockedCar);
    when(mockedCarOrder.getId()).thenReturn(0);

    when(mockedCar.getCarModel()).thenReturn(mockedCarModel);
    when(mockedCar.getBody()).thenReturn(Body.BREAK);
    when(mockedCar.getColor()).thenReturn(Color.BLACK);
    when(mockedCar.getEngine()).thenReturn(Engine.PERFORMANCE);
    when(mockedCar.getGearbox()).thenReturn(Gearbox.FIVE_SPEED_MANUAL);
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
    when(mockedCarModel.getSpoilerOptions()).thenReturn(Arrays.stream(Spoiler.values()).toList());
    when(mockedCarModel.isValidConfiguration(Body.BREAK, Color.BLACK, Engine.PERFORMANCE, Gearbox.FIVE_SPEED_MANUAL, Seat.LEATHER_BLACK, Airco.AUTOMATIC, Wheel.COMFORT, Spoiler.NO_SPOILER)).thenReturn(true);

    orderNewCarController = new OrderNewCarController(mockedCarManufacturingCompany, mockedGarageHolder);
  }

  @Test
  public void giveLoggedInGarageHolderNameTest_succeeds() {
    assertEquals("WolksVagen Garage Lokeren BVBA NV", orderNewCarController.giveLoggedInGarageHolderName());
  }

  @Test
  public void createOrderController_throws() {
    assertThrows(IllegalArgumentException.class, () -> new OrderNewCarController(null, mockedGarageHolder));
    assertThrows(IllegalStateException.class, () -> new OrderNewCarController(mockedCarManufacturingCompany, null));
  }


  @Test
  public void getCompletionDateTest_succeeds() {
    assertEquals(LocalDateTime.of(1998, 12, 15, 12, 0), orderNewCarController.getCompletionDate(0));
  }

  @Test
  public void getCompletionDateTest_throws() {
    assertThrows(IllegalArgumentException.class, () -> orderNewCarController.getCompletionDate(-1));
  }

  @Test
  public void chooseOrderTest_succeeds() {
    assertEquals(0, orderNewCarController.chooseOrder(0).get().getCar().getId());
  }

  @Test
  public void chooseOrderTest_throws() {
    assertThrows(IllegalArgumentException.class, () -> orderNewCarController.chooseOrder(-1));
  }


  @Test
  public void giveListOfCarModelsTest() {
    Map<Integer, String> listOfCarModels = orderNewCarController.giveListOfCarModels();
    assertEquals(1, listOfCarModels.size());
    assertEquals("Tolkswagen Molf", listOfCarModels.get(0));
  }

  @Test
  public void givePossibleOptionsOfCarModelTest() {
    String expected = "GearBox" + System.lineSeparator() +
      "SIX_SPEED_MANUAL" + System.lineSeparator() +
      "FIVE_SPEED_MANUAL" + System.lineSeparator() +
      "FIVE_SPEED_AUTOMATIC" + System.lineSeparator() +
      "Airco" + System.lineSeparator() +
      "MANUAL" + System.lineSeparator() +
      "AUTOMATIC" + System.lineSeparator() +
      "NO_AIRCO" + System.lineSeparator() +
      "Spoiler" + System.lineSeparator() +
      "LOW" + System.lineSeparator() +
      "HIGH" + System.lineSeparator() +
      "NO_SPOILER" + System.lineSeparator() +
      "Wheels" + System.lineSeparator() +
      "COMFORT" + System.lineSeparator() +
      "SPORT" + System.lineSeparator() +
      "WINTER" + System.lineSeparator() +
      "Color" + System.lineSeparator() +
      "RED" + System.lineSeparator() +
      "BLUE" + System.lineSeparator() +
      "BLACK" + System.lineSeparator() +
      "WHITE" + System.lineSeparator() +
      "GREEN" + System.lineSeparator() +
      "YELLOW" + System.lineSeparator() +
      "Body" + System.lineSeparator() +
      "SEDAN" + System.lineSeparator() +
      "BREAK" + System.lineSeparator() +
      "SPORT" + System.lineSeparator() +
      "Engine" + System.lineSeparator() +
      "STANDARD" + System.lineSeparator() +
      "PERFORMANCE" + System.lineSeparator() +
      "ULTRA" + System.lineSeparator() +
      "Seats" + System.lineSeparator() +
      "LEATHER_BLACK" + System.lineSeparator() +
      "LEATHER_WHITE" + System.lineSeparator() +
      "VINYL_GREY" + System.lineSeparator();

    String actual = "";
    Map<String, List<String>> possibleOptionsOfCarModel = orderNewCarController.givePossibleOptionsOfCarModel(0);

    for (String key : possibleOptionsOfCarModel.keySet()) {
      actual += key + System.lineSeparator();
      actual += possibleOptionsOfCarModel.get(key).stream().reduce("", (s, s2) -> s + s2 + System.lineSeparator());
    }

    assertEquals(expected, actual);
  }

  @Test
  public void placeCarOrderTest_succeeds() {
    LocalDateTime time = orderNewCarController.placeCarOrderAndReturnEstimatedCompletionTime(0, "BREAK", "BLACK", "PERFORMANCE", "FIVE_SPEED_MANUAL", "LEATHER_BLACK", "AUTOMATIC", "COMFORT", "NO_SPOILER");
    assertEquals(LocalDateTime.of(1998, 12, 15, 12, 0), time);
  }

  @Test
  public void placeCarOrderTest_throws() {
    assertThrows(IllegalArgumentException.class, () -> orderNewCarController.placeCarOrder(0, "", "", "", "", "", "", "", ""));
  }

}
