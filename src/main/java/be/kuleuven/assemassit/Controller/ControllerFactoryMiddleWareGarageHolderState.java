package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.CarManufactoringCompany;
import be.kuleuven.assemassit.Domain.GarageHolder;

public class ControllerFactoryMiddleWareGarageHolderState extends ControllerFactoryMiddleWareState {

  /**
   * Get a new istance of the check order details controller
   *
   * @param loggedInGarageHolder the instance of the garage holder used for creation
   * @return the new instance of the check order details controller
   */
  public CheckOrderDetailsController createCheckOrderDetailsController(GarageHolder loggedInGarageHolder) {
    return factory.createCheckOrderDetailsController(loggedInGarageHolder);
  }

  /**
   * Get a new instance of the order new car controller
   *
   * @param carManufactoringCompany the instance of the car manufactoring company used for creation
   * @param loggedInGarageHolder    the instance of the garage holder that is currently logged in
   * @return the new instance of the order new car controller
   */
  public OrderNewCarController createOrderNewCarController(CarManufactoringCompany carManufactoringCompany, GarageHolder loggedInGarageHolder) {
    return factory.createOrderNewCarController(carManufactoringCompany, loggedInGarageHolder);
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof ControllerFactoryMiddleWareGarageHolderState;
  }
}
