package be.kuleuven.assemassit.Controller.ControllerFactory;

import be.kuleuven.assemassit.Controller.CheckOrderDetailsController;
import be.kuleuven.assemassit.Controller.OrderNewCarController;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;
import be.kuleuven.assemassit.Domain.Repositories.GarageHolderRepository;

public class ControllerFactoryGarageHolderState extends ControllerFactory {

  protected ControllerFactoryGarageHolderState() {
  }

  public CheckOrderDetailsController createCheckOrderDetailsController() {
    return new CheckOrderDetailsController();
  }

  public OrderNewCarController createOrderNewCarController(CarManufactoringCompany carManufactoringCompany, GarageHolderRepository garageHolderRepository) {
    return new OrderNewCarController(carManufactoringCompany, garageHolderRepository);
  }
}
