package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.CheckProductionStatisticsController;
import be.kuleuven.assemassit.Controller.ControllerFactoryMiddleWare;
import be.kuleuven.assemassit.UI.IOCall;
import be.kuleuven.assemassit.UI.UI;

public class CheckProductionStatisticsActionUI implements UI {
  private ControllerFactoryMiddleWare controllerFactoryMiddleWare;

  public CheckProductionStatisticsActionUI(ControllerFactoryMiddleWare controllerFactoryMiddleWare) {
    this.controllerFactoryMiddleWare = controllerFactoryMiddleWare;
  }

  @Override
  public void run() {
    CheckProductionStatisticsController checkProductionStatisticsController = controllerFactoryMiddleWare.createCheckProductionStatisticsController();

    IOCall.out(checkProductionStatisticsController.getStatistics());

  }
}
