package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.OrderController;
import be.kuleuven.assemassit.Domain.GarageHolder;
import be.kuleuven.assemassit.UI.Actions.Overviews.GarageHolderActionsOverviewUI;

import java.util.List;
import java.util.Scanner;

public class OrderNewCarActionUI {
  public static void run(OrderController orderController, AssemblyLineController assemblyLineController) {
    Scanner scanner = new Scanner(System.in);
    int action;

    do {
      System.out.println("Orders placed by: " + orderController.giveLoggedInGarageHolderName());

      System.out.println("Pending:");
      List<String> pendingCarOrders = orderController.givePendingCarOrders();
      displayOrders(pendingCarOrders);

      System.out.println("History:");
      List<String> completedCarOrders = orderController.giveCompletedCarOrders(new GarageHolder(0, ""));
      displayOrders(completedCarOrders);

      System.out.println("Please choose an action:");
      System.out.println("1: Order a new car");
      System.out.println("0: Go back");

      action = scanner.nextInt();

      switch (action) {
        case 1 -> {
          GarageHolderActionsOverviewUI.run(orderController, assemblyLineController);
        }
        case 0 -> {
          GarageHolderActionsOverviewUI.run(orderController, assemblyLineController);
        }
      }
    } while (action < 0 || action > 1);
  }

  private static void displayOrders(List<String> orders) {
    for (String order : orders) {
      System.out.println(order);
    }
  }
}
