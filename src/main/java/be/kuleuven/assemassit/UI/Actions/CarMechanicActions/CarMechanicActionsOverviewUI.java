package be.kuleuven.assemassit.UI.Actions.CarMechanicActions;

import be.kuleuven.assemassit.Controller.ControllerFactory;
import be.kuleuven.assemassit.UI.Actions.CheckAssemblyLineStatusActionUI;
import be.kuleuven.assemassit.UI.Actions.PerformAssemblyTasksActionUI;
import be.kuleuven.assemassit.UI.IOCall;
import be.kuleuven.assemassit.UI.UI;

public class CarMechanicActionsOverviewUI implements UI {

  private final ControllerFactory controllerFactory;

  public CarMechanicActionsOverviewUI(ControllerFactory controllerFactory) {
    this.controllerFactory = controllerFactory;
  }

  @Override
  public void run() {

    while (true) {
      int action;

      IOCall.out();
      IOCall.out("Welcome Mechanic");
      IOCall.out("Please choose an action:");
      IOCall.out(" 1: Perform assembly task");
      IOCall.out(" 2: Check assembly line status");
      IOCall.out("-1: Logout and go back");

      action = IOCall.in();

      switch (action) {
        case 1 -> new PerformAssemblyTasksActionUI(controllerFactory).run();
        case 2 -> new CheckAssemblyLineStatusActionUI(controllerFactory).run();
        case -1 -> {
          controllerFactory.logoutCarMechanic();
          return;
        }
      }
    }
  }
}
