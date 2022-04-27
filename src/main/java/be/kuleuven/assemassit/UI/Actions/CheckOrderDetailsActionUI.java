package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.CheckOrderDetailsController;
import be.kuleuven.assemassit.Controller.ControllerFactory;
import be.kuleuven.assemassit.UI.IOCall;

import java.util.List;
import java.util.Optional;

public class CheckOrderDetailsActionUI {
  private final ControllerFactory controllerFactory;

  public CheckOrderDetailsActionUI(ControllerFactory controllerFactory) {
    this.controllerFactory = controllerFactory;
  }

  public void run() {
    CheckOrderDetailsController checkOrderDetailsController = controllerFactory.createCheckOrderDetailsController();

    int choice;

    do {
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
      IOCall.out("Please insert the ID of the order you want to view or insert -1 to go back:");

      choice = IOCall.in();
      if (choice >= 0) {
        Optional<String> carOrder = checkOrderDetailsController.giveOrderDetails(choice);
        if (carOrder.isEmpty()) {
          IOCall.out("Please enter a valid ID");
        } else {

          // 3. The system shows the details of the order
          IOCall.out(carOrder.get());

          // 4. The user indicates he is finished viewing the details.
          IOCall.out("Press ENTER to continue...");
          IOCall.waitForConfirmation();
        }
      }
      // Alternate flow: The user indicates he wants to view another order.
    } while (choice >= 0);
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
