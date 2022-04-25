package be.kuleuven.assemassit;

import be.kuleuven.assemassit.UI.IOCall;
import be.kuleuven.assemassit.UI.LoginUI;

import java.util.InputMismatchException;

public class App {

  public static void main(String[] args) {
    LoginUI loginUI = new LoginUI();
    try {
      loginUI.run();
    } catch (IllegalArgumentException e) {
      IOCall.out(e.getMessage());
    } catch (IllegalStateException e) {
      IOCall.out(e.getMessage());
    } catch (InputMismatchException e) {
      IOCall.out("Be aware, only integers are allowed to choose options in the UI");
    } catch (Exception e) {
      IOCall.out("The application experienced unexpected behaviour, please contact the system administrator");
      IOCall.out(e.getMessage());
    }
  }
}
