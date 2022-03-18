package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.OrderController;
import be.kuleuven.assemassit.UI.Actions.Overviews.ManagerActionsOverviewUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class AdvanceAssemblyLineActionUI {
  public static void run(OrderController orderController, AssemblyLineController assemblyLineController) {
    Scanner scanner = new Scanner(System.in);
    int action;

    do {
      System.out.println();
      System.out.println("Current assembly line status:");
      displayStatus(assemblyLineController.giveAssemblyLineStatusOverview());

      System.out.println();
      System.out.println("Future assembly line status:");
      displayStatus(assemblyLineController.giveFutureAssemblyLineStatusOverview());

      System.out.println();
      System.out.println("Please choose an action:");
      System.out.println(" 1: Move assembly line forward");
      System.out.println("-1: Go back");

      action = scanner.nextInt();

      switch (action) {
        case 1 -> {
          Scanner input = new Scanner(System.in);
          int minutes;

          do {
            System.out.println();
            System.out.println("What was the amount of minutes spent during the current phase:");
            minutes = input.nextInt();
          } while (!(minutes >= 0 && minutes < 180));

          List<String> blockingWorkPosts = assemblyLineController.moveAssemblyLine(minutes);

          if (!blockingWorkPosts.isEmpty()) {
            System.out.println("These work posts are stopping you from moving forward:");
            blockingWorkPosts.forEach(System.out::println);
          } else {
            System.out.println("Assembly line moved.");
          }

          System.out.println();
          System.out.println("Current assembly line status:");
          displayStatus(assemblyLineController.giveAssemblyLineStatusOverview());

          System.out.println("Press ENTER to continue...");
          Scanner inspector = new Scanner(System.in);
          inspector.nextLine();

          ManagerActionsOverviewUI.run(orderController, assemblyLineController);
        }
        case -1 -> ManagerActionsOverviewUI.run(orderController, assemblyLineController);
      }
    } while (action != -1 && action != 1);
  }

  private static void displayStatus(HashMap<String, List<String>> status) {
    List<String> statusKeys = new ArrayList<>(status.keySet());

    for (String workPostName : statusKeys) {
      System.out.println("  * " + workPostName);

      if (status.get(workPostName).isEmpty()) {
        System.out.println("      - " + "no pending or active tasks");
      }
      for (int j = 0; j < status.get(workPostName).size(); j++) {
        System.out.println("      - " + status.get(workPostName).get(j));
      }
    }
  }
}
