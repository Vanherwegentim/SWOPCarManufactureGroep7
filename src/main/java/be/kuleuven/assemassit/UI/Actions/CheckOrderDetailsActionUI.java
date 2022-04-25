package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.CheckOrderDetailsController;
import be.kuleuven.assemassit.Controller.ControllerFactory;
import be.kuleuven.assemassit.UI.IOCall;

import java.util.List;

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
        String carOrder = checkOrderDetailsController.giveOrderDetails(choice);
        IOCall.out(carOrder);

        IOCall.out("Press enter to go back to the overview");
        IOCall.waitForConfirmation();
      }
    } while (choice >= 0);
  }

  private void displayCarOrders(List<String> carOrders) {
    for (int i = 0; i < carOrders.size(); i++) {
      IOCall.out(String.format("%2d", (i + 1)) + ": " + carOrders.get(i));
    }
  }
}
