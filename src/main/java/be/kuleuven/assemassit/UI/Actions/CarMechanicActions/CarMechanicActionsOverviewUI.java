package be.kuleuven.assemassit.UI.Actions.CarMechanicActions;

import be.kuleuven.assemassit.Controller.CheckAssemblyLineStatusController;
import be.kuleuven.assemassit.Controller.PerformAssemblyTasksController;
import be.kuleuven.assemassit.UI.Actions.CheckAssemblyLineStatusActionUI;
import be.kuleuven.assemassit.UI.Actions.PerformAssemblyTasksActionUI;
import be.kuleuven.assemassit.UI.IOCall;
import be.kuleuven.assemassit.UI.LoginUI;
import be.kuleuven.assemassit.UI.UI;

public class CarMechanicActionsOverviewUI implements UI {

  private PerformAssemblyTasksController performAssemblyTasksController;
  private CheckAssemblyLineStatusController checkAssemblyLineStatusController;
  private PerformAssemblyTasksActionUI performAssemblyTasksActionUI;
  private CheckAssemblyLineStatusActionUI checkAssemblyLineStatusActionUI;
  private LoginUI loginUI;


  public CarMechanicActionsOverviewUI(PerformAssemblyTasksController performAssemblyTasksController, CheckAssemblyLineStatusController checkAssemblyLineStatusController) {
    this.performAssemblyTasksController = performAssemblyTasksController;
    this.checkAssemblyLineStatusController = checkAssemblyLineStatusController;
    this.performAssemblyTasksActionUI = new PerformAssemblyTasksActionUI(this.performAssemblyTasksController, this.checkAssemblyLineStatusController);
    this.checkAssemblyLineStatusActionUI = new CheckAssemblyLineStatusActionUI(this.checkAssemblyLineStatusController);
    this.loginUI = new LoginUI();
  }

  // TODO: change to correct controllers
  @Override
  public void run() {
    int action;

    do {
      IOCall.waitForConfirmation();
      IOCall.out("Welcome Mechanic");
      IOCall.out("Please choose an action:");
      IOCall.out(" 1: Perform assembly task");
      IOCall.out(" 2: Check assembly line status");
      IOCall.out("-1: Logout and go back");

      action = IOCall.in();

      switch (action) {
        case 1 -> {
          this.performAssemblyTasksActionUI.run();
        }
        case -1 -> {
          this.loginUI.run();
        }
        case 2 -> {
          this.checkAssemblyLineStatusActionUI.run();
        }
      }
    } while (action != -1 && action != 1);
  }
}
