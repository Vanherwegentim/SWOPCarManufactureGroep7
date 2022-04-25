package be.kuleuven.assemassit.UI;

import be.kuleuven.assemassit.Controller.ControllerFactory;
import be.kuleuven.assemassit.Controller.LoginController;
import be.kuleuven.assemassit.UI.Actions.CarMechanicActions.CarMechanicActionsOverviewUI;
import be.kuleuven.assemassit.UI.Actions.GarageHolderActions.GarageHolderActionsOverviewUI;
import be.kuleuven.assemassit.UI.Actions.ManagerActions.ManagerActionsOverviewUI;

import java.util.Map;
import java.util.Optional;

public class LoginUI implements UI {
  private final ControllerFactory controllerFactory;
  private final LoginController loginController;


  private final ManagerActionsOverviewUI managerActionsOverviewUI;
  private final CarMechanicActionsOverviewUI carMechanicActionsOverviewUI;
  private final GarageHolderActionsOverviewUI garageHolderActionsOverviewUI;

  public LoginUI() {
    this.controllerFactory = new ControllerFactory();
    this.loginController = controllerFactory.createLoginController();

    this.managerActionsOverviewUI = new ManagerActionsOverviewUI(this.controllerFactory);
    this.carMechanicActionsOverviewUI = new CarMechanicActionsOverviewUI(this.controllerFactory);
    this.garageHolderActionsOverviewUI = new GarageHolderActionsOverviewUI(this.controllerFactory);
  }

  private Optional<Integer> displayGarageHolderForm(Map<Integer, String> garageHolders) {
    int garageHolderId;

    do {
      IOCall.out();
      IOCall.out("Please select your name:");
      garageHolders.forEach((id, name) -> IOCall.out(String.format("%2d", id) + ": " + name));
      IOCall.out("-1: Go back");

      garageHolderId = IOCall.in();

      if (garageHolderId == -1) return Optional.empty();

    } while (!garageHolders.containsKey(garageHolderId));

    return Optional.of(garageHolderId);
  }

  @Override
  public void run() {
    loginController.logOffGarageHolder();
    int choice;

    do {
      IOCall.out();
      IOCall.out("Please authenticate yourself:");
      IOCall.out(" 1: I am a garage holder");
      IOCall.out(" 2: I am a manager");
      IOCall.out(" 3: I am a car mechanic");
      IOCall.out("-1: Exit");

      choice = IOCall.in();

      switch (choice) {
        case 1 -> {
          Optional<Integer> selectedGarageHolderIdOptional = displayGarageHolderForm(loginController.giveGarageHolders());
          if (selectedGarageHolderIdOptional.isEmpty()) {
            continue;
          }
          loginController.logInGarageHolder(selectedGarageHolderIdOptional.get());
          this.garageHolderActionsOverviewUI.run();
        }
        case 2 -> {
          controllerFactory.loginManager();
          this.managerActionsOverviewUI.run();
        }
        case 3 -> {
          controllerFactory.loginCarMechanic();
          this.carMechanicActionsOverviewUI.run();
        }
      }
    } while (choice != -1);
  }
}
