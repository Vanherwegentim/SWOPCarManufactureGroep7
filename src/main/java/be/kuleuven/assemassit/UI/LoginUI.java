package be.kuleuven.assemassit.UI;

import be.kuleuven.assemassit.Controller.ControllerFactoryMiddleWare;
import be.kuleuven.assemassit.Controller.LoginController;
import be.kuleuven.assemassit.Exceptions.UIException;
import be.kuleuven.assemassit.UI.Actions.CarMechanicActions.CarMechanicActionsOverviewUI;
import be.kuleuven.assemassit.UI.Actions.GarageHolderActions.GarageHolderActionsOverviewUI;
import be.kuleuven.assemassit.UI.Actions.ManagerActions.ManagerActionsOverviewUI;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Optional;

public class LoginUI implements UI {
  private final ControllerFactoryMiddleWare controllerFactoryMiddleWare;
  private final LoginController loginController;


  private final ManagerActionsOverviewUI managerActionsOverviewUI;
  private final CarMechanicActionsOverviewUI carMechanicActionsOverviewUI;
  private final GarageHolderActionsOverviewUI garageHolderActionsOverviewUI;

  public LoginUI() {
    this.controllerFactoryMiddleWare = new ControllerFactoryMiddleWare();
    this.loginController = controllerFactoryMiddleWare.createLoginController();

    this.managerActionsOverviewUI = new ManagerActionsOverviewUI(this.controllerFactoryMiddleWare);
    this.carMechanicActionsOverviewUI = new CarMechanicActionsOverviewUI(this.controllerFactoryMiddleWare);
    this.garageHolderActionsOverviewUI = new GarageHolderActionsOverviewUI(this.controllerFactoryMiddleWare);
  }

  private Optional<Integer> displayGarageHolderForm(Map<Integer, String> garageHolders) {
    int garageHolderId = -1;

    do {
      try {
        IOCall.out();
        IOCall.out("Please select your name:");
        garageHolders.forEach((id, name) -> IOCall.out(String.format("%2d", id) + ": " + name));
        IOCall.out("-1: Go back");

        garageHolderId = IOCall.in();

        if (garageHolderId == -1) return Optional.empty();
      } catch (InputMismatchException | UIException ex) {
        IOCall.out("ERROR, only integers are allowed here!");
        IOCall.next();
      }
    } while (!garageHolders.containsKey(garageHolderId));

    return Optional.of(garageHolderId);
  }

  @Override
  public void run() {
    loginController.logOffGarageHolder();
    int choice = 0;

    do {
      try {
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
            controllerFactoryMiddleWare.loginManager();
            this.managerActionsOverviewUI.run();
          }
          case 3 -> {
            controllerFactoryMiddleWare.loginCarMechanic();
            this.carMechanicActionsOverviewUI.run();
          }
        }
      } catch (InputMismatchException | UIException ex) {
        IOCall.out("ERROR, only integers are allowed here!");
        IOCall.next();
      }
    } while (choice != -1);
  }
}
