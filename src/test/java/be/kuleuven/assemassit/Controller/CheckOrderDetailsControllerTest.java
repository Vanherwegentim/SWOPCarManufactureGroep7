package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.Car;
import be.kuleuven.assemassit.Domain.CarModel;
import be.kuleuven.assemassit.Domain.CarOrder;
import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.GarageHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckOrderDetailsControllerTest {

  private CheckOrderDetailsController checkOrderDetailsController;
  private CarOrder mockedCarOrder;
  private CarOrder mockedCarOrder2;
  private GarageHolder mockedGarageHolder;

  @BeforeEach
  public void beforeEach() {
    mockedGarageHolder = mock(GarageHolder.class);
    mockedCarOrder = mock(CarOrder.class);
    mockedCarOrder2 = mock(CarOrder.class);
    Car mockedCar = mock(Car.class);
    CarModel mockedCarModel = mock(CarModel.class);

    when(mockedGarageHolder.getOrder(0)).thenReturn(Optional.of(mockedCarOrder));

    when(mockedCarOrder.getEstimatedCompletionTime()).thenReturn(LocalDateTime.of(1998, 12, 15, 12, 0));
    when(mockedCarOrder.getCompletionTime()).thenReturn(LocalDateTime.of(1998, 12, 15, 15, 0));
    when(mockedCarOrder.getId()).thenReturn(0);
    when(mockedCarOrder.getCar()).thenReturn(mockedCar);

    when(mockedCarOrder2.getEstimatedCompletionTime()).thenReturn(LocalDateTime.of(1998, 12, 16, 12, 0));
    when(mockedCarOrder2.getCompletionTime()).thenReturn(LocalDateTime.of(1998, 12, 16, 15, 0));
    when(mockedCarOrder2.getId()).thenReturn(1);
    when(mockedCarOrder2.getCar()).thenReturn(mockedCar);

    when(mockedCar.getCarModel()).thenReturn(mockedCarModel);
    when(mockedCar.getBody()).thenReturn(Body.BREAK);
    when(mockedCar.getColor()).thenReturn(Color.BLACK);
    when(mockedCar.getEngine()).thenReturn(Engine.PERFORMANCE);
    when(mockedCar.getGearbox()).thenReturn(Gearbox.FIVE_SPEED_MANUAL);
    when(mockedCar.getAirco()).thenReturn(Airco.AUTOMATIC);
    when(mockedCar.getWheels()).thenReturn(Wheel.COMFORT);
    when(mockedCar.getSeats()).thenReturn(Seat.LEATHER_BLACK);

    when(mockedCarModel.getName()).thenReturn("Tolkswagen Molf");

    checkOrderDetailsController = new CheckOrderDetailsController(mockedGarageHolder);
  }


  @Test
  public void givePendingCarOrdersTest_GarageOwnerHasNoPendingOrders_succeeds() {
    assertArrayEquals(new String[]{}, checkOrderDetailsController.givePendingCarOrders().toArray());
  }

  @Test
  public void givePendingCarOrdersTest_GarageOwnerHasPendingOrders_succeeds() {
    when(mockedCarOrder.isPending()).thenReturn(true);
    when(mockedCarOrder2.isPending()).thenReturn(true);
    when(mockedGarageHolder.getCarOrders()).thenReturn(Arrays.asList(mockedCarOrder, mockedCarOrder2));

    String expected = "Order ID: 0    [Estimation time: 15/12/1998 at 12:00]    [Car model: Tolkswagen Molf]" + System.lineSeparator() + "Order ID: 1    [Estimation time: 16/12/1998 at 12:00]    [Car model: Tolkswagen Molf]" + System.lineSeparator();

    assertEquals(expected, checkOrderDetailsController.givePendingCarOrders().stream().reduce("", String::concat));
  }

  @Test
  public void giveCompletedCarOrdersTest_GarageOwnerHasNoCompletedOrders_succeeds() {
    assertArrayEquals(new String[]{}, checkOrderDetailsController.giveCompletedCarOrders().toArray());
  }

  @Test
  public void giveCompletedCarOrdersTest_GarageOwnerHasCompletedOrders_succeeds() {
    when(mockedCarOrder.isPending()).thenReturn(false);
    when(mockedCarOrder2.isPending()).thenReturn(false);
    when(mockedGarageHolder.getCarOrders()).thenReturn(Arrays.asList(mockedCarOrder, mockedCarOrder2));

    String expected = "Order ID: 0    [Completed at: 15/12/1998 at 15:00]    [Car model: Tolkswagen Molf]" + System.lineSeparator() + "Order ID: 1    [Completed at: 16/12/1998 at 15:00]    [Car model: Tolkswagen Molf]" + System.lineSeparator();

    assertEquals(expected, checkOrderDetailsController.giveCompletedCarOrders().stream().reduce("", String::concat));
  }

  @Test
  public void giveOrderDetailsTest_OrderIsPending() {

    when(mockedCarOrder.isPending()).thenReturn(true);

    String expected = "Order ID: 0    [Estimation time: 15/12/1998 at 12:00]" + System.lineSeparator() + "    Car model: Tolkswagen Molf" + System.lineSeparator() + "        Body: BREAK" + System.lineSeparator() + "        Color: BLACK" + System.lineSeparator() + "        Engine: PERFORMANCE" + System.lineSeparator() + "        Gearbox: FIVE_SPEED_MANUAL" + System.lineSeparator() + "        Airco: AUTOMATIC" + System.lineSeparator() + "        Wheels: COMFORT" + System.lineSeparator() + "        Seats: LEATHER_BLACK" + System.lineSeparator();

    assertEquals(expected, checkOrderDetailsController.giveOrderDetails(0));
  }

  @Test
  public void giveOrderDetailsTest_OrderIsCompleted() {

    when(mockedCarOrder.isPending()).thenReturn(false);

    String expected = "Order ID: 0    [Completed at: 15/12/1998 at 15:00]" + System.lineSeparator() + "    Car model: Tolkswagen Molf" + System.lineSeparator() + "        Body: BREAK" + System.lineSeparator() + "        Color: BLACK" + System.lineSeparator() + "        Engine: PERFORMANCE" + System.lineSeparator() + "        Gearbox: FIVE_SPEED_MANUAL" + System.lineSeparator() + "        Airco: AUTOMATIC" + System.lineSeparator() + "        Wheels: COMFORT" + System.lineSeparator() + "        Seats: LEATHER_BLACK" + System.lineSeparator();

    assertEquals(expected, checkOrderDetailsController.giveOrderDetails(0));
  }

}
