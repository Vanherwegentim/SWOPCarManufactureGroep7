package be.kuleuven.assemassit.UI;

import java.io.IOException;
import java.util.Scanner;

public class MainUI {
  public static void run() {
    try {

      Scanner input = new Scanner(System.in);

      System.out.println("------- ASSEMASSIST ------");
      System.out.println("Hit enter to continue...");
      System.in.read();

      System.out.println("Please choose an action");

      int action = -1;

      do {

        /* -- possible actions --*/
        System.out.println("1: Order a new car");
        System.out.println("2: Perform assembly line");
        System.out.println("3: Advance assembly line");
        System.out.println("0: Quit");

        action = input.nextInt();

        switch (action) {
          case 1:
            OrderNewCarUI.run();
            break;
          case 2:
            // start ui
            break;
          case 3:
            // start ui
            break;
        }

      } while (action < 0 || action > 3);

    } catch (IOException e) {
      System.out.println("Something went wrong with the UI, please restart the application");
    } catch (Exception e) {
      System.out.println("Something went wrong, please restart the application");
    }
  }
}
