package be.kuleuven.assemassit.UI.Actions.Overviews;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.OrderController;
import be.kuleuven.assemassit.UI.Actions.OrderNewCarActionUI;
import be.kuleuven.assemassit.UI.MainUI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

public class GarageHolderActionsOverviewUI {
  public static void run(OrderController orderController, AssemblyLineController assemblyLineController) {
    Scanner scanner = new Scanner(System.in);
    int action;

    do {
      String loggedInGarageHolderName = orderController.giveLoggedInGarageHolderName();
      System.out.println();
      System.out.println("Welcome " + loggedInGarageHolderName);
      System.out.println("Please choose an action:");
      System.out.println(" 1: Order new car");
      System.out.println(" 2: FAST Order car -> logout -> go back");
      System.out.println("-1: Logout and go back");

      action = scanner.nextInt();
      switch (action) {
        case 1 -> OrderNewCarActionUI.run(orderController, assemblyLineController);
        case 2 -> {
          Map<String, String> selectedParts = Map.of(
            "GearBox", "AUTOMATIC",
            "Engine", "PERFORMANCE",
            "Airco", "AUTOMATIC",
            "Body", "BREAK",
            "Color", "BLUE",
            "Seats", "LEATHER_WHITE",
            "Wheels", "SPORT");
          LocalDateTime estimatedCompletionDate = orderController.placeCarOrder(orderController.giveListOfCarModels().keySet().stream().findFirst().get(), selectedParts.get("Body"), selectedParts.get("Color"), selectedParts.get("Engine"), selectedParts.get("GearBox"), selectedParts.get("Seats"), selectedParts.get("Airco"), selectedParts.get("Wheels"));
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' H:mm");
          System.out.println("The estimated completion date for the order is: " + estimatedCompletionDate.format(formatter));
          System.out.println("\n");

          orderController.logOffGarageHolder();
          MainUI.run(orderController, assemblyLineController);
        }
        case -1 -> {
          orderController.logOffGarageHolder();
          MainUI.run(orderController, assemblyLineController);
        }
      }
    } while (action != -1 && action != 1 && action != 2);
  }
}
