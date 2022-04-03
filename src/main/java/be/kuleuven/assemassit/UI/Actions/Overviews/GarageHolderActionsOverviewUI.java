package be.kuleuven.assemassit.UI.Actions.Overviews;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.OrderNewCarController;
import be.kuleuven.assemassit.UI.Actions.OrderNewCarActionUI;
import be.kuleuven.assemassit.UI.AuthenticateUI;

import java.util.Scanner;

public class GarageHolderActionsOverviewUI {
  public static void run(OrderNewCarController orderNewCarController, AssemblyLineController assemblyLineController) {
    Scanner scanner = new Scanner(System.in);
    int action;

    do {
      String loggedInGarageHolderName = orderNewCarController.giveLoggedInGarageHolderName();
      System.out.println();
      System.out.println("Welcome " + loggedInGarageHolderName);
      System.out.println("Please choose an action:");
      System.out.println(" 1: Order new car");
      System.out.println("-1: Logout and go back");

      action = scanner.nextInt();
      switch (action) {
        case 1 -> OrderNewCarActionUI.run(orderNewCarController, assemblyLineController);
        case -1 -> {
          orderNewCarController.logOffGarageHolder();
          AuthenticateUI.run(orderNewCarController, assemblyLineController);
        }
      }
    } while (action != -1 && action != 1);
  }
}
