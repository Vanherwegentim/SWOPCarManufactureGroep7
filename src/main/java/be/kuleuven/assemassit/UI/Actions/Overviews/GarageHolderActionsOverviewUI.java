package be.kuleuven.assemassit.UI.Actions.Overviews;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.OrderController;
import be.kuleuven.assemassit.UI.Actions.OrderNewCarActionUI;
import be.kuleuven.assemassit.UI.MainUI;

import java.util.Scanner;

public class GarageHolderActionsOverviewUI {
  public static void run(OrderController orderController, AssemblyLineController assemblyLineController) {
    Scanner scanner = new Scanner(System.in);
    int action;

    do {
      String loggedInGarageHolderName = orderController.giveLoggedInGarageHolderName();

      System.out.println("Welcome " + loggedInGarageHolderName);
      System.out.println("Please choose an action:");
      System.out.println(" 1: Order new car");
      System.out.println("-1: Logout and go back");

      action = scanner.nextInt();

      switch (action) {
        case 1 -> OrderNewCarActionUI.run(orderController, assemblyLineController);
        case -1 -> {
          orderController.logOffGarageHolder();
          MainUI.run(orderController, assemblyLineController);
        }
      }
    } while (action != -1 && action != 1);
  }
}
