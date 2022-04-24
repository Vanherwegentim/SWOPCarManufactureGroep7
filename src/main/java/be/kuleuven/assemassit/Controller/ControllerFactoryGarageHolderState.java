package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.CarManufactoringCompany;
import be.kuleuven.assemassit.Domain.GarageHolder;

public class ControllerFactoryGarageHolderState extends ControllerFactory {

  protected ControllerFactoryGarageHolderState() {
  }

  public CheckOrderDetailsController createCheckOrderDetailsController(GarageHolder loggedInGarageHolder) {
    return new CheckOrderDetailsController(loggedInGarageHolder);
  }

  public OrderNewCarController createOrderNewCarController(CarManufactoringCompany carManufactoringCompany, GarageHolder loggedInGarageHolder) {
    return new OrderNewCarController(carManufactoringCompany, loggedInGarageHolder);
  }
}
