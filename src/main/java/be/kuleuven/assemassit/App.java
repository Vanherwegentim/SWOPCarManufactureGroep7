package be.kuleuven.assemassit;

import be.kuleuven.assemassit.UI.LoginUI;

import java.util.InputMismatchException;

public class App {

  public static void main(String[] args) {
    LoginUI loginUI = new LoginUI();
    try {
      loginUI.run();
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage());
    } catch (InputMismatchException e) {
      System.out.println("Be aware, only integers are allowed to choose options in the UI");
    } catch (Exception e) {
      System.out.println("The application experienced unexpected behaviour, please contact the system administrator");
      System.out.println(e.getMessage());
    }
  }
}
