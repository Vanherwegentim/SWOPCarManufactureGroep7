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

      AuthenticateUI.run();

    } catch (IOException e) {
      System.out.println("Something went wrong with the UI, please restart the application");
    } catch (Exception e) {
      System.out.println("Something went wrong, please restart the application");
    }
  }
}
