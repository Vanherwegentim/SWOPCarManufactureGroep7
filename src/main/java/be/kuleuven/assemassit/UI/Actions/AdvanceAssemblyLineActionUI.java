package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.OrderController;
import be.kuleuven.assemassit.UI.Actions.Overviews.ManagerActionsOverviewUI;

import java.util.*;

public class AdvanceAssemblyLineActionUI {
  public static void run(OrderController orderController, AssemblyLineController assemblyLineController) {
    Scanner scanner = new Scanner(System.in);

    Map<String, List<String>> statusses = assemblyLineController.giveAssemblyLineStatusAndOverview(0);

    List<String> statusesKeys = new ArrayList<>(statusses.keySet());
    String spacer = "    ";

    System.out.println("--- Current assembly line status ---");
    for (String workPostName : statusesKeys) {
      System.out.println(workPostName);
      for (int j = 0; j < statusses.get(workPostName).size(); j++) {
        System.out.println(spacer + statusses.get(workPostName).get(j));
      }
    }
    System.out.println("--- Future assembly line status ---");
    System.out.println("???");

    System.out.println("Please choose an action:");
    System.out.println("1: Move assembly line forward");
    System.out.println("0: Go back");

    int action = scanner.nextInt();

    switch (action) {
      case 1 -> {
        Scanner input = new Scanner(System.in);
        int minutes = -1;
        do {
          System.out.println("What was the amount of minutes spent during the current phase:");
          minutes = input.nextInt();
        } while (!(minutes > 0 && minutes < 180));
        assemblyLineController.moveAssemblyLine(0, minutes);
        System.out.println("Assembly line moved.");
        System.out.println("TODO: print new status");
      }
      case 0 -> ManagerActionsOverviewUI.run(orderController, assemblyLineController);
    }
  }
}
