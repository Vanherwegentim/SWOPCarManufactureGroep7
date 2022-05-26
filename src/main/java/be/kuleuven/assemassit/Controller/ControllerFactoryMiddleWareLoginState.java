package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Repositories.GarageHolderRepository;

public class ControllerFactoryMiddleWareLoginState extends ControllerFactoryMiddleWareState {

  /**
   * Get an instance of the login controller
   *
   * @param garageHolderRepository      the instance of garage holder repository used for creation
   * @param controllerFactoryMiddleWare the instance of the controller factory middleware used for creation
   * @return the new instance of the login controller
   */
  public LoginController createLoginController(GarageHolderRepository garageHolderRepository, ControllerFactoryMiddleWare controllerFactoryMiddleWare) {
    return factory.createLoginController(garageHolderRepository, controllerFactoryMiddleWare);
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof ControllerFactoryMiddleWareLoginState;
  }
}
