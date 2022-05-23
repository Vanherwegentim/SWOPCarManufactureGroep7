package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.CheckOrderDetailsController;
import be.kuleuven.assemassit.Controller.ControllerFactoryMiddleWare;
import be.kuleuven.assemassit.Exceptions.UIException;
import be.kuleuven.assemassit.UI.IOCall;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

public class CheckOrderDetailsActionUI {
  private final ControllerFactoryMiddleWare controllerFactoryMiddleWare;

  public CheckOrderDetailsActionUI(ControllerFactoryMiddleWare controllerFactoryMiddleWare) {
    this.controllerFactoryMiddleWare = controllerFactoryMiddleWare;
  }

  public void run() {
    CheckOrderDetailsController checkOrderDetailsController = controllerFactoryMiddleWare.createCheckOrderDetailsController();

    int choice = -2;

    do {
      try {
        // 1. The system presents an overview of the orders placed by the user
        IOCall.out();
        IOCall.out("Orders placed by: " + checkOrderDetailsController.giveLoggedInGarageHolderName());

        IOCall.out("Pending:");
        displayCarOrders(checkOrderDetailsController.givePendingCarOrders());

        IOCall.out("History:");
        displayCarOrders(checkOrderDetailsController.giveCompletedCarOrders());

        // 2. The user indicates the order he wants to check the details for.
        // Alternate flow: The user indicates he wants to leave the overview.
        IOCall.out();
        IOCall.out("Insert the ID of the order you want to view.");
        IOCall.out("Insert -1 to go back.");

        choice = IOCall.in();
        if (choice >= 0) {
          Optional<String> carOrder = checkOrderDetailsController.giveOrderDetails(choice);
          if (carOrder.isEmpty()) {
            IOCall.out("Please enter a valid ID");
          } else {

            // 3. The system shows the details of the order
            IOCall.out(carOrder.get());

            // 4. The user indicates he is finished viewing the details.
            int one = 0;
            do {
              try {
                IOCall.out("Press 1 to continue...");
                one = IOCall.in();
              } catch (InputMismatchException | UIException ex) {
                IOCall.out("ERROR, only integers are allowed here!");
                IOCall.next();
              }
            } while (one != 1);
          }
        }
        // Alternate flow: The user indicates he wants to view another order.
      } catch (InputMismatchException | UIException ex) {
        IOCall.out("ERROR, only integers are allowed here!");
        IOCall.next();
      }
    } while (choice != -1);
  }

  private void displayCarOrders(List<String> carOrders) {
    String output = "";
    for (int i = 0; i < carOrders.size(); i++) {
      output += carOrders.get(i);
    }
    if (output == "") {
      output = "   --no orders found--";
    }
    IOCall.out(output);
  }
}
