package be.kuleuven.assemassit.Controller;

public class ControllerFactoryLoginState extends ControllerFactoryState {

  private ControllerFactory controllerFactory;

  protected ControllerFactoryLoginState(ControllerFactory controllerFactory) {
    if (controllerFactory == null)
      throw new IllegalArgumentException();
    this.controllerFactory = controllerFactory;
  }

  // TODO: create the logincontroller here
}
