package be.kuleuven.assemassit.UI.Actions.Overviews;

import be.kuleuven.assemassit.UI.Actions.OrderNewCarActionUI;
import be.kuleuven.assemassit.UI.MainUI;

import java.util.Scanner;

public class CarMechanicActionsOverviewUI {
  public static void run() {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Welcome ___");
    System.out.println("Please choose an action:");
    System.out.println("1: Perform assembly task");
    System.out.println("0: Logout and go back");

    int action = scanner.nextInt();

    switch (action) {
      case 1:
        OrderNewCarActionUI.run();
        break;
      case 0:
        MainUI.run();
    }
  }
}
