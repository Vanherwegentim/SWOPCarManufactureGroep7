package be.kuleuven.assemassit.UI;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.OrderController;

import java.io.IOException;
import java.util.Scanner;

public class MainUI {
  public static void run(OrderController orderController, AssemblyLineController assemblyLineController) {
    Scanner input = new Scanner(System.in);

    System.out.println("------- ASSEMASSIST ------");
    System.out.println("1: Authenticate");
    System.out.println("0: Quit");

    int action = input.nextInt();
    switch (action) {
      case 1:
        AuthenticateUI.run(orderController, assemblyLineController);
      case 0: break;
    }
  }
}
