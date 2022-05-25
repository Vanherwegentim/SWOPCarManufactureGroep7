package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.CarManufactoringCompany;
import be.kuleuven.assemassit.Domain.GarageHolder;

public class ControllerFactoryMiddleWareGarageHolderState extends ControllerFactoryMiddleWareState {

  public CheckOrderDetailsController createCheckOrderDetailsController(GarageHolder loggedInGarageHolder) {
    return factory.createCheckOrderDetailsController(loggedInGarageHolder);
  }

  public OrderNewCarController createOrderNewCarController(CarManufactoringCompany carManufactoringCompany, GarageHolder loggedInGarageHolder) {
    return factory.createOrderNewCarController(carManufactoringCompany, loggedInGarageHolder);
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof ControllerFactoryMiddleWareGarageHolderState;
  }
}
