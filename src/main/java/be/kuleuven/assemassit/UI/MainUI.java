package be.kuleuven.assemassit.UI;

import java.io.IOException;
import java.util.Scanner;

public class MainUI {
  public static void run() {
    Scanner input = new Scanner(System.in);

    System.out.println("------- ASSEMASSIST ------");
    System.out.println("1: Authenticate");
    System.out.println("0: Quit");

    int action = input.nextInt();
    switch (action) {
      case 1:
        AuthenticateUI.run();
      case 0: break;
    }
  }
}
