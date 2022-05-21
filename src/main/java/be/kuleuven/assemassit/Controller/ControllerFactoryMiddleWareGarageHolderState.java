package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.CarManufactoringCompany;
import be.kuleuven.assemassit.Domain.GarageHolder;

public class ControllerFactoryMiddleWareGarageHolderState extends ControllerFactoryMiddleWareState {

  public CheckOrderDetailsController createCheckOrderDetailsController(GarageHolder loggedInGarageHolder) {
    return new CheckOrderDetailsController(loggedInGarageHolder);
  }

  public OrderNewCarController createOrderNewCarController(CarManufactoringCompany carManufactoringCompany, GarageHolder loggedInGarageHolder) {
    return new OrderNewCarController(carManufactoringCompany, loggedInGarageHolder);
  }

  public CheckOrderDetailsController createCheckOrderDetailsController() {
    //return new CheckOrderDetailsController(loggedInGarageHolder);
    return null;
  }

  public OrderNewCarController createOrderNewCarController() {
    //return new OrderNewCarController(carManufactoringCompany, loggedInGarageHolder);
    return null;
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof ControllerFactoryMiddleWareGarageHolderState;
  }
}
