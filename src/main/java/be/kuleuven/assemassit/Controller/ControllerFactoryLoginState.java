package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.Repositories.GarageHolderRepository;

public class ControllerFactoryLoginState extends ControllerFactoryState {

  protected ControllerFactoryLoginState() {
  }

  public LoginController createLoginController(GarageHolderRepository garageHolderRepository, ControllerFactory controllerFactory) {
    return new LoginController(garageHolderRepository, controllerFactory);
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof ControllerFactoryLoginState;
  }
}
