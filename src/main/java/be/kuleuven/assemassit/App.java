package be.kuleuven.assemassit;

import be.kuleuven.assemassit.Controller.ControllerFactory;
import be.kuleuven.assemassit.UI.MainUI;

public class App {

  public static void main(String[] args) {
    ControllerFactory controllerFactory = new ControllerFactory();
    MainUI.run(controllerFactory.createOrderController(), controllerFactory.createAssemblyLineController());
  }
}
