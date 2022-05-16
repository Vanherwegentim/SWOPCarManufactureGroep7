package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.CheckProductionStatisticsController;
import be.kuleuven.assemassit.Controller.ControllerFactory;
import be.kuleuven.assemassit.UI.IOCall;
import be.kuleuven.assemassit.UI.UI;

public class CheckProductionStatisticsActionUI implements UI {
  private final ControllerFactory controllerFactory;

  public CheckProductionStatisticsActionUI(ControllerFactory controllerFactory) {
    this.controllerFactory = controllerFactory;
  }

  @Override
  public void run() {
    CheckProductionStatisticsController checkProductionStatisticsController = controllerFactory.createCheckProductionStatisticsController();

    IOCall.out(checkProductionStatisticsController.getStatistics());

  }
}
