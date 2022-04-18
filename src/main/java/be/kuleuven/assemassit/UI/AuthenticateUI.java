package be.kuleuven.assemassit.UI;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.OrderNewCarController;
import be.kuleuven.assemassit.UI.Actions.CarMechanicActions.CarMechanicActionsOverviewUI;
import be.kuleuven.assemassit.UI.Actions.GarageHolderActions.GarageHolderActionsOverviewUI;
import be.kuleuven.assemassit.UI.Actions.ManagerActions.ManagerActionsOverviewUI;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class AuthenticateUI {
  public static void run(OrderNewCarController orderNewCarController, AssemblyLineController assemblyLineController) {
    Scanner input = new Scanner(System.in);
    int choice;

    do {
      System.out.println();
      System.out.println("Please authenticate yourself:");
      System.out.println(" 1: I am a garage holder");
      System.out.println(" 2: I am a manager");
      System.out.println(" 3: I am a car mechanic");
      System.out.println("-1: Go back");

      choice = input.nextInt();

      switch (choice) {
        case 1 -> {
          Optional<Integer> selectedGarageHolderIdOptional = displayGarageHolderForm(orderNewCarController.giveGarageHolders());
          if (selectedGarageHolderIdOptional.isEmpty()) {
            AuthenticateUI.run(orderNewCarController, assemblyLineController);
            return;
          }
          orderNewCarController.logInGarageHolder(selectedGarageHolderIdOptional.get());
          GarageHolderActionsOverviewUI.run(orderNewCarController, assemblyLineController);
        }
        case 2 -> ManagerActionsOverviewUI.run(orderNewCarController, assemblyLineController);
        case 3 -> CarMechanicActionsOverviewUI.run(orderNewCarController, assemblyLineController);
        case -1 -> MainUI.run(orderNewCarController, assemblyLineController);
      }
    } while (choice != -1 && (choice < 1 || choice > 3));
  }

  private static Optional<Integer> displayGarageHolderForm(Map<Integer, String> garageHolders) {
    Scanner scanner = new Scanner(System.in);
    int garageHolderId;

    do {
      System.out.println();
      System.out.println("Please select your name:");
      garageHolders.forEach((id, name) -> System.out.println(String.format("%2d", id) + ": " + name));
      System.out.println("-1: Go back");

      garageHolderId = scanner.nextInt();

      if (garageHolderId == -1) return Optional.empty();

    } while (!garageHolders.containsKey(garageHolderId));

    return Optional.of(garageHolderId);
  }
}
