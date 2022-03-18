package be.kuleuven.assemassit;

import be.kuleuven.assemassit.Controller.ControllerFactory;
import be.kuleuven.assemassit.UI.MainUI;

import java.util.InputMismatchException;

public class App {

  public static void main(String[] args) {
    ControllerFactory controllerFactory = new ControllerFactory();
    try {
      MainUI.run(controllerFactory.createOrderController(), controllerFactory.createAssemblyLineController());
      // better error handling should be implemented in the future iteration
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
