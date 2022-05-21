package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Repositories.GarageHolderRepository;

public class ControllerFactoryMiddleWareLoginState extends ControllerFactoryMiddleWareState {
  
  public LoginController createLoginController(GarageHolderRepository garageHolderRepository, ControllerFactoryMiddleWare controllerFactoryMiddleWare) {
    return new LoginController(garageHolderRepository, controllerFactoryMiddleWare);
  }

  public LoginController createLoginController() {
    //return new LoginController(garageHolderRepository, controllerFactory);
    return null;
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof ControllerFactoryMiddleWareLoginState;
  }
}
