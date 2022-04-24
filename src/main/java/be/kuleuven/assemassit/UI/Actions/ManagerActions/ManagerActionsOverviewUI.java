package be.kuleuven.assemassit.UI.Actions.ManagerActions;

import be.kuleuven.assemassit.Controller.ControllerFactory;
import be.kuleuven.assemassit.UI.Actions.CheckProductionStatisticsActionUI;
import be.kuleuven.assemassit.UI.IOCall;
import be.kuleuven.assemassit.UI.UI;

public class ManagerActionsOverviewUI implements UI {
  private CheckProductionStatisticsActionUI checkProductionStatisticsActionUI;
  private ControllerFactory controllerFactory;

  public ManagerActionsOverviewUI(ControllerFactory controllerFactory) {
    this.controllerFactory = controllerFactory;
  }

  @Override
  public void run() {
    this.checkProductionStatisticsActionUI = new CheckProductionStatisticsActionUI(controllerFactory);

    int action;

    do {
      IOCall.waitForConfirmation();
      IOCall.out("Welcome Manager");
      IOCall.out("Please choose an action:");
      IOCall.out(" 1: Car and delay statistics");
      IOCall.out("-1: Logout and go back");

      action = IOCall.in();

      switch (action) {
        case 1 -> this.checkProductionStatisticsActionUI.run();
      }
    } while (action != -1 && action != 1);

    controllerFactory.logoutManager();
  }
}
