package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.OrderController;
import be.kuleuven.assemassit.UI.Actions.Overviews.ManagerActionsOverviewUI;

import java.util.*;

public class AdvanceAssemblyLineActionUI {
  public static void run(OrderController orderController, AssemblyLineController assemblyLineController) {
    Scanner scanner = new Scanner(System.in);
    int action;

    do {
      System.out.println("--- Current assembly line status ---");
      displayStatus(assemblyLineController.giveAssemblyLineStatusAndOverview());

      System.out.println("--- Future assembly line status ---");
      displayStatus(assemblyLineController.giveAssemblyLineStatusAndOverview());

      System.out.println("Please choose an action:");
      System.out.println("1: Move assembly line forward");
      System.out.println("0: Go back");

      action = scanner.nextInt();

      switch (action) {
        case 1 -> {
          Scanner input = new Scanner(System.in);
          int minutes;

          do {
            System.out.println("What was the amount of minutes spent during the current phase:");
            minutes = input.nextInt();
          } while (!(minutes > 0 && minutes < 180));

          assemblyLineController.moveAssemblyLine(minutes);

          System.out.println("Assembly line moved.");

          System.out.println("--- Current assembly line status ---");
          displayStatus(assemblyLineController.giveAssemblyLineStatusAndOverview());

          Scanner inspector = new Scanner(System.in);
          boolean quit;

          do {
            System.out.println("Press any key to continue...");
            quit = inspector.hasNext();
          } while (!quit);

          ManagerActionsOverviewUI.run(orderController, assemblyLineController);
        }
        case 0 -> ManagerActionsOverviewUI.run(orderController, assemblyLineController);
      }
    } while (action < 0 || action > 1);
  }

  private static void displayStatus(Map<String, List<String>> status) {
    List<String> statusKeys = new ArrayList<>(status.keySet());

    for (String workPostName : statusKeys) {
      System.out.println(workPostName);
      for (int j = 0; j < status.get(workPostName).size(); j++) {
        System.out.println(" ".repeat(4) + status.get(workPostName).get(j));
      }
    }
  }
}
