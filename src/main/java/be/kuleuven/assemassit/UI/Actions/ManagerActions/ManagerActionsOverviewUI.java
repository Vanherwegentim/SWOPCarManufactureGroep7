package be.kuleuven.assemassit.UI.Actions.ManagerActions;

import be.kuleuven.assemassit.Controller.CheckProductionStatisticsController;
import be.kuleuven.assemassit.UI.Actions.CheckProductionStatisticsActionUI;
import be.kuleuven.assemassit.UI.IOCall;
import be.kuleuven.assemassit.UI.LoginUI;
import be.kuleuven.assemassit.UI.UI;

public class ManagerActionsOverviewUI implements UI {

  private LoginUI loginUI;
  private CheckProductionStatisticsActionUI checkProductionStatisticsActionUI;

  public ManagerActionsOverviewUI(CheckProductionStatisticsController checkProductionStatisticsController) {
    this.loginUI = new LoginUI();
    this.checkProductionStatisticsActionUI = new CheckProductionStatisticsActionUI(checkProductionStatisticsController);
  }

  // TODO: change to correct controllers
  @Override
  public void run() {
    int action;

    do {
      IOCall.waitForConfirmation();
      IOCall.out("Welcome Manager");
      IOCall.out("Please choose an action:");
      IOCall.out(" 1: Car and delay statistics");
      IOCall.out("-1: Logout and go back");

      action = IOCall.in();

      switch (action) {
        case -1 -> this.loginUI.run();
        case 1 -> this.checkProductionStatisticsActionUI.run();
      }
    } while (action != -1 && action != 1);
  }
}
