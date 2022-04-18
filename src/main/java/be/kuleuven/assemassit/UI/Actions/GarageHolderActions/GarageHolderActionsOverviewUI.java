package be.kuleuven.assemassit.UI.Actions.GarageHolderActions;

import be.kuleuven.assemassit.Controller.OrderNewCarController;
import be.kuleuven.assemassit.UI.Actions.OrderNewCarActionUI;
import be.kuleuven.assemassit.UI.AuthenticateUI;
import be.kuleuven.assemassit.UI.IOCall;
import be.kuleuven.assemassit.UI.UI;

public class GarageHolderActionsOverviewUI implements UI {

  private OrderNewCarController orderNewCarController;

  public GarageHolderActionsOverviewUI(OrderNewCarController orderNewCarController) {
    this.orderNewCarController = orderNewCarController;
  }

  // TODO: change to correct controllers`
  @Override
  public void run() {
    int action;

    do {
      String loggedInGarageHolderName = orderNewCarController.giveLoggedInGarageHolderName();
      IOCall.waitForConfirmation();
      IOCall.out("Welcome " + loggedInGarageHolderName);
      IOCall.out("Please choose an action:");
      IOCall.out(" 1: Order new car");
      IOCall.out("-1: Logout and go back");

      action = IOCall.in();
      switch (action) {
        case 1 -> OrderNewCarActionUI.run(orderNewCarController, assemblyLineController);
        case -1 -> {
          orderNewCarController.logOffGarageHolder();
          AuthenticateUI.run(orderNewCarController, assemblyLineController);
        }
      }
    } while (action != -1 && action != 1);
  }
}
