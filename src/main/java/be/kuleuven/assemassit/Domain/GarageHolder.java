package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;

import java.util.ArrayList;
import java.util.List;

public class GarageHolder {
  private List<CarOrder> carOrders = new ArrayList<>();
  public List<CarOrder> getCarOrders(){
    return List.copyOf(carOrders);
  }

  public void addCarOrder(CarModel carModel,String body, String color, String engine, String gearbox, String seats, String airco, String wheels){
    Body bodyEnum = Body.valueOf(body);
    Color colorEnum = Color.valueOf(color);
    Engine engineEnum = Engine.valueOf(engine);
    Gearbox gearboxEnum = Gearbox.valueOf(gearbox);
    Seat seatEnum = Seat.valueOf(seats);
    Airco aircoEnum = Airco.valueOf(airco);
    Wheel wheelsEnum = Wheel.valueOf(wheels);

    Car car = new Car(carModel, bodyEnum, colorEnum, engineEnum,gearboxEnum,seatEnum,aircoEnum,wheelsEnum );
    CarOrder carOrder = new CarOrder(car);
    carOrders.add(carOrder);
  }
}
