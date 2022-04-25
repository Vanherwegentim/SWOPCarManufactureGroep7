package be.kuleuven.assemassit.UI.Actions.GarageHolderActions;

import be.kuleuven.assemassit.Controller.ControllerFactory;
import be.kuleuven.assemassit.UI.Actions.CheckOrderDetailsActionUI;
import be.kuleuven.assemassit.UI.Actions.OrderNewCarActionUI;
import be.kuleuven.assemassit.UI.IOCall;
import be.kuleuven.assemassit.UI.UI;

public class GarageHolderActionsOverviewUI implements UI {

  private final ControllerFactory controllerFactory;

  public GarageHolderActionsOverviewUI(ControllerFactory controllerFactory) {
    this.controllerFactory = controllerFactory;
  }

  @Override
  public void run() {

    while (true) {
      int action;

      String loggedInGarageHolderName = controllerFactory.giveLoggedInGarageHolderName();
      IOCall.out();
      IOCall.out("Welcome " + loggedInGarageHolderName);
      IOCall.out("Please choose an action:");
      IOCall.out(" 1: Order new car");
      IOCall.out(" 2: Check order details");
      IOCall.out("-1: Logout and go back");

      action = IOCall.in();
      switch (action) {
        case 1 -> new OrderNewCarActionUI(controllerFactory).run();
        case -1 -> {
          controllerFactory.logoutGarageHolder();
          return;
        }
        case 2 -> new CheckOrderDetailsActionUI(controllerFactory).run();
      }
    }
  }
}
