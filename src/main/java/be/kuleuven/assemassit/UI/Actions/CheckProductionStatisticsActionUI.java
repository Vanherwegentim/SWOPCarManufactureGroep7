package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.CheckProductionStatisticsController;
import be.kuleuven.assemassit.Controller.ControllerFactory;
import be.kuleuven.assemassit.UI.IOCall;
import be.kuleuven.assemassit.UI.UI;

public class CheckProductionStatisticsActionUI implements UI {
  private final ControllerFactory controllerFactory;
  private CheckProductionStatisticsController checkProductionStatisticsController;

  public CheckProductionStatisticsActionUI(ControllerFactory controllerFactory) {
    this.controllerFactory = controllerFactory;
  }

  @Override
  public void run() {
    this.checkProductionStatisticsController = controllerFactory.createCheckProductionStatisticsController();

    while (true) {

      IOCall.out("CAR STATISTICS:");
      IOCall.out();
      IOCall.out(checkProductionStatisticsController.averageCarsInADayToString());
      IOCall.out();
      IOCall.out(checkProductionStatisticsController.medianCarsInADayToString());
      IOCall.out();
      IOCall.out(checkProductionStatisticsController.exactCarsIn2DaystoString());
      IOCall.out();
      IOCall.out("DELAY STATISTICS:");
      IOCall.out();
      IOCall.out(checkProductionStatisticsController.averageDelayPerOrderToString());
      IOCall.out();
      IOCall.out(checkProductionStatisticsController.medianDelayPerOrderToString());
      IOCall.out();
      IOCall.out(checkProductionStatisticsController.last2DelaysToString());
      break; // if we reach this point, the use case is done, java call stack will now return to the previous UI
    }
  }
}
