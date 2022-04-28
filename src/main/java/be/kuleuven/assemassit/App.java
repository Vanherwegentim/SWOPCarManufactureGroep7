package be.kuleuven.assemassit;

import be.kuleuven.assemassit.Domain.Helper.CustomTime;
import be.kuleuven.assemassit.Exceptions.UIException;
import be.kuleuven.assemassit.UI.IOCall;
import be.kuleuven.assemassit.UI.LoginUI;

import java.util.InputMismatchException;
import java.util.Objects;

public class App {

  public static void main(String[] args) {
    String simulateTimeString = "simulate_time";

    for (String arg : args) {
      if (Objects.equals(arg, simulateTimeString)) {
        CustomTime.getInstance().setSimulateTime(true);
      }
    }
    LoginUI loginUI = new LoginUI();
    try {
      loginUI.run();
    } catch (IllegalArgumentException e) {
      IOCall.out(e.getMessage());
    } catch (IllegalStateException e) {
      IOCall.out(e.getMessage());
    } catch (InputMismatchException | UIException ex) {
      IOCall.out("Be aware, only integers are allowed to choose options in the UI");
    } catch (Exception e) {
      IOCall.out("The application experienced unexpected behaviour, please contact the system administrator");
      IOCall.out(e.getMessage());
    }
  }
}
