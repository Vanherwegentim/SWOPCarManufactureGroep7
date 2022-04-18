package be.kuleuven.assemassit.UI.Actions.ManagerActions;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.UI.Actions.AdvanceAssemblyLineActionUI;
import be.kuleuven.assemassit.UI.AuthenticateUI;
import be.kuleuven.assemassit.UI.IOCall;
import be.kuleuven.assemassit.UI.UI;

public class ManagerActionsOverviewUI implements UI {

  private AssemblyLineController assemblyLineController;

  public ManagerActionsOverviewUI(AssemblyLineController assemblyLineController) {
    this.assemblyLineController = assemblyLineController;
  }

  // TODO: change to correct controllers
  @Override
  public void run() {
    int action;

    do {
      IOCall.waitForConfirmation();
      IOCall.out("Welcome Manager");
      IOCall.out("Please choose an action:");
      IOCall.out(" 1: Advance assembly line");
      IOCall.out("-1: Logout and go back");

      action = IOCall.in();

      switch (action) {
        case 1 -> AdvanceAssemblyLineActionUI.run(orderNewCarController, assemblyLineController);
        case -1 -> AuthenticateUI.run(orderNewCarController, assemblyLineController);
      }
    } while (action != -1 && action != 1);
  }
}
