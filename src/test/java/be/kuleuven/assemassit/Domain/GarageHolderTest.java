package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.Helper.CustomTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GarageHolderTest {
  private Car car;
  private GarageHolder garageHolder;
  private CarOrder carOrder;

  @BeforeEach
  public void beforeEach() {
    garageHolder = new GarageHolder(0, "testGarageHolder");
    carOrder = new CarOrder(
      new Car(
        new CarModel(0, "testCar", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values()), Arrays.asList(Spoiler.values())),
        Body.SEDAN,
        Color.BLACK,
        Engine.PERFORMANCE,
        Gearbox.FIVE_SPEED_MANUAL,
        Seat.LEATHER_BLACK,
        Airco.MANUAL,
        Wheel.SPORT,
        Spoiler.NO_SPOILER));
  }

  @Test
  public void addCarOrderTest() {
    garageHolder.addCarOrder(carOrder);
    assertTrue(garageHolder.getCarOrders().contains(carOrder));
  }

  @Test
  public void findCarOrderTest() {
    garageHolder.addCarOrder(carOrder);
    assertEquals(garageHolder.findCarOrder(carOrder.getId()).get(), carOrder);
  }

  @Test
  public void propertiesTest() {
    assertEquals(0, garageHolder.getId());
    assertEquals("testGarageHolder", garageHolder.getName());
  }


  @Test
  void getCompletionTimeFromOrder() {
    LocalDateTime localDateTime = (CustomTime.getInstance().customLocalDateTimeNow());
    garageHolder.addCarOrder(carOrder);
    carOrder.setCompletionTime(localDateTime);

    assertEquals(localDateTime, garageHolder.getCompletionTimeFromOrder(carOrder.getId()));

  }
}
