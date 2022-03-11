package be.kuleuven.assemassit.UI;

import be.kuleuven.assemassit.UI.Actions.CarMechanicActionsOverviewUI;
import be.kuleuven.assemassit.UI.Actions.GarageHolderActionsOverviewUI;
import be.kuleuven.assemassit.UI.Actions.ManagerActionsOverviewUI;

import java.util.Scanner;

public class AuthenticateUI {
  public static void run() {
    Scanner input = new Scanner(System.in);

    System.out.println("Please select the correct option");
    System.out.println("1: I am a garage holder");
    System.out.println("2: I am a car mechanic");
    System.out.println("3: I am a manager");
    System.out.println("0: Go back");

    int choice = input.nextInt();

    switch(choice) {
      case 1:
        GarageHolderActionsOverviewUI.run();
        break;
      case 2:
        CarMechanicActionsOverviewUI.run();
        break;
      case 3:
        ManagerActionsOverviewUI.run();
        break;
      case 0:
        MainUI.run();
    }
  }
}
