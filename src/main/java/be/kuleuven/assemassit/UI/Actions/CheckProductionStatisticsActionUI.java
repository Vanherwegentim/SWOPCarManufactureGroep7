package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.CheckProductionStatisticsController;
import be.kuleuven.assemassit.UI.UI;

public class CheckProductionStatisticsActionUI implements UI {
  private CheckProductionStatisticsController checkProductionStatisticsController;

  public CheckProductionStatisticsActionUI(CheckProductionStatisticsController checkProductionStatisticsController) {
    this.checkProductionStatisticsController = checkProductionStatisticsController;
  }

  @Override
  public void run() {
    while (true) {

      System.out.println("CAR STATISTICS: \n");
      System.out.println(checkProductionStatisticsController.averageCarsInADayToString());
      System.out.println();
      System.out.println(checkProductionStatisticsController.medianCarsInADayToString());
      System.out.println();
      System.out.println(checkProductionStatisticsController.exactCarsIn2DaystoString());
      System.out.println();
      System.out.println("DELAY STATISTICS: \n");
      System.out.println(checkProductionStatisticsController.averageDelayPerOrderToString());
      System.out.println();
      System.out.println(checkProductionStatisticsController.medianDelayPerOrderToString());
      System.out.println();
      System.out.println(checkProductionStatisticsController.last2DelaysToString());
      break; // if we reach this point, the use case is done, java call stack will now return to the previous UI
    }
  }
}
