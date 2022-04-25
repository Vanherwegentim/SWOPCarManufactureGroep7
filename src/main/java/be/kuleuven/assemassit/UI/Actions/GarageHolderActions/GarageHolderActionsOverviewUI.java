package be.kuleuven.assemassit.UI.Actions.GarageHolderActions;

import be.kuleuven.assemassit.Controller.ControllerFactory;
import be.kuleuven.assemassit.UI.Actions.OrderNewCarActionUI;
import be.kuleuven.assemassit.UI.IOCall;
import be.kuleuven.assemassit.UI.UI;

public class GarageHolderActionsOverviewUI implements UI {

  private OrderNewCarActionUI orderNewCarActionUI;
  private ControllerFactory controllerFactory;


  public GarageHolderActionsOverviewUI(ControllerFactory controllerFactory) {
    this.controllerFactory = controllerFactory;
    this.orderNewCarActionUI = new OrderNewCarActionUI(controllerFactory);
  }

  // TODO: change to correct controllers
  @Override
  public void run() {
    int action;

    do {
      String loggedInGarageHolderName = controllerFactory.giveLoggedInGarageHolderName();
      IOCall.waitForConfirmation();
      IOCall.out("Welcome " + loggedInGarageHolderName);
      IOCall.out("Please choose an action:");
      IOCall.out(" 1: Order new car");
      IOCall.out("-1: Logout and go back");

      action = IOCall.in();
      switch (action) {
        case 1 -> this.orderNewCarActionUI.run();
      }
    } while (action != -1 && action != 1);

    this.controllerFactory.logoutGarageHolder();
  }
}
