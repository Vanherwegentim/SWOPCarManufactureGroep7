package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.CheckOrderDetailsController;
import be.kuleuven.assemassit.Controller.ControllerFactory;
import be.kuleuven.assemassit.UI.IOCall;

import java.util.List;
import java.util.Optional;

public class CheckOrderDetailsActionUI {
  private ControllerFactory controllerFactory;
  private CheckOrderDetailsController checkOrderDetailsController;

  public CheckOrderDetailsActionUI(ControllerFactory controllerFactory) {
    this.controllerFactory = controllerFactory;
  }

  public void run() {
    this.checkOrderDetailsController = controllerFactory.createCheckOrderDetailsController();

    int choice;

    do {
      IOCall.out();
      IOCall.out("Orders placed by: " + checkOrderDetailsController.giveLoggedInGarageHolderName());

      IOCall.out("Pending:");
      displayCarOrders(checkOrderDetailsController.givePendingCarOrders());

      IOCall.out("History:");
      displayCarOrders(checkOrderDetailsController.giveCompletedCarOrders());

      IOCall.out();
      IOCall.out("Please insert the ID of the order you want to view or insert -1 to go back:");

      choice = IOCall.in();
      if (choice >= 0) {
        Optional<String> carOrder = checkOrderDetailsController.giveOrderDetails(choice);
        if (carOrder.isEmpty()) {
          IOCall.out("Please enter a valid ID");
        } else {
          IOCall.out(carOrder.get());

          IOCall.out("Press ENTER to continue...");
          IOCall.waitForConfirmation();
        }
      }
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
