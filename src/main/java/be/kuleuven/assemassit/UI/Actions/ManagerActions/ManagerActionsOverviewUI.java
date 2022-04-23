package be.kuleuven.assemassit.UI.Actions.ManagerActions;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.UI.IOCall;
import be.kuleuven.assemassit.UI.LoginUI;
import be.kuleuven.assemassit.UI.UI;

public class ManagerActionsOverviewUI implements UI {

  private LoginUI loginUI;

  public ManagerActionsOverviewUI() {
    this.loginUI = new LoginUI();
  }

  // TODO: change to correct controllers
  @Override
  public void run() {
    int action;

    do {
      IOCall.waitForConfirmation();
      IOCall.out("Welcome Manager");
      IOCall.out("Please choose an action:");
      IOCall.out("-1: Logout and go back");

      action = IOCall.in();

      switch (action) {
        case -1 -> this.loginUI.run();
      }
    } while (action != -1 && action != 1);
  }
}
