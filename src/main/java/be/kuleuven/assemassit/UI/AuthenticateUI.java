package be.kuleuven.assemassit.UI;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.OrderController;
import be.kuleuven.assemassit.UI.Actions.Overviews.CarMechanicActionsOverviewUI;
import be.kuleuven.assemassit.UI.Actions.Overviews.GarageHolderActionsOverviewUI;
import be.kuleuven.assemassit.UI.Actions.Overviews.ManagerActionsOverviewUI;

import java.util.Map;
import java.util.Scanner;

public class AuthenticateUI {
  public static void run(OrderController orderController, AssemblyLineController assemblyLineController) {
    Scanner input = new Scanner(System.in);
    int choice = -1;

    do {
      System.out.println("Please select the correct option");
      System.out.println("1: I am a garage holder");
      System.out.println("2: I am a car mechanic");
      System.out.println("3: I am a manager");
      System.out.println("0: Go back");

      choice = input.nextInt();

      switch(choice) {
        case 1:
          Map<Integer, String> garageHolders = orderController.giveGarageHolders();
          int garageHolderId = -1;

          do {
            System.out.println("Please select your name:");
            for (int key : garageHolders.keySet()) System.out.println(key + ":" + garageHolders.get(key));
            garageHolderId = input.nextInt();
          } while (!garageHolders.keySet().contains(garageHolderId));

          orderController.logInGarageHolder(garageHolderId);
          GarageHolderActionsOverviewUI.run(orderController, assemblyLineController);
          break;
        case 2:
          CarMechanicActionsOverviewUI.run(orderController, assemblyLineController);
          break;
        case 3:
          ManagerActionsOverviewUI.run(orderController, assemblyLineController);
          break;
        case 0:
          MainUI.run(orderController, assemblyLineController);
      }
    } while (choice < 0 || choice > 3);
  }
}
