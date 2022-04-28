package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.Helper.CustomTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CarOrderTest {

  private Car car;

  @BeforeEach
  public void beforeEach() {
    CarModel carModel = new CarModel(0, "testmodel", Arrays.asList(Wheel.COMFORT, Wheel.SPORT), Arrays.asList(Gearbox.FIVE_SPEED_AUTOMATIC, Gearbox.FIVE_SPEED_MANUAL), Arrays.asList(Seat.LEATHER_BLACK), Arrays.asList(Body.BREAK), Arrays.asList(Color.BLACK, Color.BLUE, Color.RED), Arrays.asList(Engine.PERFORMANCE, Engine.STANDARD), Arrays.asList(Airco.MANUAL, Airco.AUTOMATIC), Arrays.asList(Spoiler.NO_SPOILER));

    car = new Car(carModel, Body.BREAK, Color.BLACK, Engine.PERFORMANCE, Gearbox.FIVE_SPEED_MANUAL, Seat.LEATHER_BLACK, Airco.AUTOMATIC, Wheel.COMFORT, Spoiler.NO_SPOILER);
  }

  @Test
  public void carOrderTest_succeeds() {
    CarOrder.resetIdRunner();
    CarOrder carOrder = new CarOrder(car);
    assertTrue((new CustomTime().customLocalDateTimeNow()).toEpochSecond(ZoneOffset.UTC) - carOrder.getOrderTime().toEpochSecond(ZoneOffset.UTC) < 1000);
    assertEquals(0, carOrder.getId());
    assertTrue(carOrder.isPending());
    carOrder.setPending(false);
    assertFalse(carOrder.isPending());
    LocalDateTime localDateTime = LocalDateTime.of(1999, 8, 29, 13, 0);
    carOrder.setCompletionTime(localDateTime);
    assertEquals(localDateTime, carOrder.getCompletionTime());
    assertEquals(car, carOrder.getCar());
    carOrder.setEstimatedCompletionTime(localDateTime);
    assertEquals(localDateTime, carOrder.getEstimatedCompletionTime());
  }

  @Test
  public void carOrderTest_throws() {
    assertThrows(IllegalArgumentException.class, () -> new CarOrder(null));
  }

  @Test
  void getOrderTime() {
  }
}
