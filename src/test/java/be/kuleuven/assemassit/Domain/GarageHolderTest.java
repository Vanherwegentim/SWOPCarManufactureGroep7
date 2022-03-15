package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GarageHolderTest {
  private Car car;
  private GarageHolder garageHolder;
  private CarOrder carOrder;

  @BeforeEach
  public void beforeEach(){
    garageHolder = new GarageHolder(0, "testGarageHolder");
    carOrder = new CarOrder(
      new Car(
        new CarModel(0, "testCar", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values())),
        Body.SEAD,
        Color.BLACK,
        Engine.PERFORMANCE,
        Gearbox.MANUAL,
        Seat.LEATHER_BLACK,
        Airco.MANUAL,
        Wheel.SPORT));
  }

  @Test
  public void addCarOrderTest(){
    garageHolder.addCarOrder(carOrder);
    assert garageHolder.getCarOrders().contains(carOrder);
  }

  @Test
  public void findCarOrderTest(){
    assertThrows(IllegalArgumentException.class, ()-> garageHolder.findCarOrder(0));
    garageHolder.addCarOrder(carOrder);
    assertEquals(garageHolder.findCarOrder(0), carOrder);
  }

  public void getCompletionTimeTest(){
    //TODO
  }

}
