package be.kuleuven.assemassit.UI.Actions.GarageHolderActions;

import be.kuleuven.assemassit.Controller.ControllerFactoryMiddleWare;
import be.kuleuven.assemassit.Exceptions.UIException;
import be.kuleuven.assemassit.UI.Actions.CheckOrderDetailsActionUI;
import be.kuleuven.assemassit.UI.Actions.OrderNewCarActionUI;
import be.kuleuven.assemassit.UI.IOCall;
import be.kuleuven.assemassit.UI.UI;

import java.util.InputMismatchException;

public class GarageHolderActionsOverviewUI implements UI {

  private final ControllerFactoryMiddleWare controllerFactoryMiddleWare;

  public GarageHolderActionsOverviewUI(ControllerFactoryMiddleWare controllerFactoryMiddleWare) {
    this.controllerFactoryMiddleWare = controllerFactoryMiddleWare;
  }

  @Override
  public void run() {

    while (true) {
      try {
        int action;

        String loggedInGarageHolderName = controllerFactoryMiddleWare.giveLoggedInGarageHolderName();
        IOCall.out();
        IOCall.out("Welcome " + loggedInGarageHolderName);
        IOCall.out("Please choose an action:");
        IOCall.out(" 1: Order new car");
        IOCall.out(" 2: Check order details");
        IOCall.out("-1: Logout and go back");

        action = IOCall.in();
        switch (action) {
          case 1 -> new OrderNewCarActionUI(controllerFactoryMiddleWare).run();
          case 2 -> new CheckOrderDetailsActionUI(controllerFactoryMiddleWare).run();
          case -1 -> {
            controllerFactoryMiddleWare.logoutGarageHolder();
            return;
          }

        }
      } catch (InputMismatchException | UIException ex) {
        IOCall.out("ERROR, only integers are allowed here!");
        IOCall.next();
      }
    }

  }
}
