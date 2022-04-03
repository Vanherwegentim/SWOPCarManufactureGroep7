package be.kuleuven.assemassit.UI;

import be.kuleuven.assemassit.Exceptions.UIException;

import java.util.Scanner;

public class IOCall {

  private static Scanner input;

  private IOCall() {
  }

  public static void out(String message) {
    System.out.println(message);
  }

  public static int in() {
    try {
      return getScanner().nextInt();
    } catch (Exception e) {
      throw new UIException(e.getMessage());
    }
  }

  public static void waitForConfirmation() {
    getScanner().nextLine();
  }

  private static Scanner getScanner() {
    if (input == null)
      input = new Scanner(System.in);

    return input;
  }
}
