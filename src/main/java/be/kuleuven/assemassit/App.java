package be.kuleuven.assemassit;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.OrderController;
import be.kuleuven.assemassit.UI.MainUI;

public class App {

  public static void main(String[] args) {
    MainUI.run(new OrderController(), new AssemblyLineController());
  }
}
