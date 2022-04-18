package be.kuleuven.assemassit.UI;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.OrderNewCarController;

import java.util.Scanner;

public class MainUI {
  public static void run(OrderNewCarController orderNewCarController, AssemblyLineController assemblyLineController) {
    Scanner input = new Scanner(System.in);
    int action;
    do {
      System.out.println("\n------- ASSEMASSIST ------");
      System.out.println(" 1: Authenticate");
      System.out.println("-1: Quit");

      action = input.nextInt();
      switch (action) {
        case 1:
          AuthenticateUI.run(orderNewCarController, assemblyLineController);
        case -1:
          break;
      }
    }
    while (action != 1 && action != -1);
  }
}
