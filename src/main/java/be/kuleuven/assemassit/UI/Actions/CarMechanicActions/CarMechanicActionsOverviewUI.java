package be.kuleuven.assemassit.UI.Actions.CarMechanicActions;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.UI.Actions.GarageHolderActions.GarageHolderActionsOverviewUI;
import be.kuleuven.assemassit.UI.Actions.ManagerActions.ManagerActionsOverviewUI;
import be.kuleuven.assemassit.UI.Actions.PerformAssemblyTasksActionUI;
import be.kuleuven.assemassit.UI.IOCall;
import be.kuleuven.assemassit.UI.UI;

public class CarMechanicActionsOverviewUI implements UI {

  private AssemblyLineController assemblyLineController;

  private ManagerActionsOverviewUI managerActionsOverviewUI;
  private PerformAssemblyTasksActionUI performAssemblyTasksActionUI;


  public CarMechanicActionsOverviewUI(AssemblyLineController assemblyLineController) {
    this.assemblyLineController = assemblyLineController;
    this.managerActionsOverviewUI = new ManagerActionsOverviewUI(this.assemblyLineController);
    this.performAssemblyTasksActionUI = new PerformAssemblyTasksActionUI(this.assemblyLineController);
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
      IOCall.out("-1: Logout and go back");

      action = IOCall.in();

      switch (action) {
        case 1 -> {
          this.performAssemblyTasksActionUI.run();
        }
        case -1 -> {
          this.managerActionsOverviewUI.run();
        }
      }
    } while (action != -1 && action != 1);
  }
}
