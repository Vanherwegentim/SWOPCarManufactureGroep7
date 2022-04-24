package be.kuleuven.assemassit.UI;

import be.kuleuven.assemassit.Controller.*;
import be.kuleuven.assemassit.UI.Actions.CarMechanicActions.CarMechanicActionsOverviewUI;
import be.kuleuven.assemassit.UI.Actions.GarageHolderActions.GarageHolderActionsOverviewUI;
import be.kuleuven.assemassit.UI.Actions.ManagerActions.ManagerActionsOverviewUI;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class LoginUI implements UI {
  private ControllerFactory controllerFactory;
  private OrderNewCarController orderNewCarController;
  private LoginController loginController;
  private PerformAssemblyTasksController performAssemblyTasksController;
  private CheckAssemblyLineStatusController checkAssemblyLineStatusController;
  private CheckProductionStatisticsController checkProductionStatisticsController;


  private ManagerActionsOverviewUI managerActionsOverviewUI;
  private CarMechanicActionsOverviewUI carMechanicActionsOverviewUI;
  private GarageHolderActionsOverviewUI garageHolderActionsOverviewUI;

  public LoginUI() {
    this.controllerFactory = new ControllerFactory();
    this.loginController = controllerFactory.createLoginController();
    this.orderNewCarController = controllerFactory.createOrderNewCarController();
    this.performAssemblyTasksController = controllerFactory.createPerformAssemblyTasksController();
    this.checkAssemblyLineStatusController = controllerFactory.createCheckAssemblyLineStatusController();
    this.checkProductionStatisticsController = controllerFactory.createCheckProductionStatisticsController();

    this.managerActionsOverviewUI = new ManagerActionsOverviewUI(this.checkProductionStatisticsController);
    this.carMechanicActionsOverviewUI = new CarMechanicActionsOverviewUI(performAssemblyTasksController, checkAssemblyLineStatusController);
    this.garageHolderActionsOverviewUI = new GarageHolderActionsOverviewUI(this.orderNewCarController);
  }

  private Optional<Integer> displayGarageHolderForm(Map<Integer, String> garageHolders) {
    Scanner scanner = new Scanner(System.in);
    int garageHolderId;

    do {
      System.out.println();
      System.out.println("Please select your name:");
      garageHolders.forEach((id, name) -> System.out.println(String.format("%2d", id) + ": " + name));
      System.out.println("-1: Go back");

      garageHolderId = scanner.nextInt();

      if (garageHolderId == -1) return Optional.empty();

    } while (!garageHolders.containsKey(garageHolderId));

    return Optional.of(garageHolderId);
  }

  @Override
  public void run() {
    loginController.logOffGarageHolder();
    int choice;

    do {
      IOCall.waitForConfirmation();
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
            //TODO redirect terug naar deze loginUI methode
            return;
          }
          loginController.logInGarageHolder(selectedGarageHolderIdOptional.get());
          this.garageHolderActionsOverviewUI.run();
        }
        case 2 -> this.managerActionsOverviewUI.run();
        case 3 -> this.carMechanicActionsOverviewUI.run();
        case -1 -> {
          return;
        }
      }
    } while (choice != -1 && (choice < 1 || choice > 3));
  }
}
