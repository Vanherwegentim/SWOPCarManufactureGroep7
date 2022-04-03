package be.kuleuven.assemassit.UI.Actions.Overviews;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.OrderNewCarController;
import be.kuleuven.assemassit.UI.Actions.AdvanceAssemblyLineActionUI;
import be.kuleuven.assemassit.UI.AuthenticateUI;

import java.util.Scanner;

public class ManagerActionsOverviewUI {
  public static void run(OrderNewCarController orderNewCarController, AssemblyLineController assemblyLineController) {
    Scanner scanner = new Scanner(System.in);
    int action;

    do {
      System.out.println();
      System.out.println("Welcome Manager");
      System.out.println("Please choose an action:");
      System.out.println(" 1: Advance assembly line");
      System.out.println("-1: Logout and go back");

      action = scanner.nextInt();

      switch (action) {
        case 1 -> AdvanceAssemblyLineActionUI.run(orderNewCarController, assemblyLineController);
        case -1 -> AuthenticateUI.run(orderNewCarController, assemblyLineController);
      }
    } while (action != -1 && action != 1);
  }
}
