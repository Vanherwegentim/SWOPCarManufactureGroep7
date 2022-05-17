package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.ProductionStatistics;

import java.time.LocalDate;

public class CheckProductionStatisticsController {
  private ProductionStatistics productionStatistics;

  protected CheckProductionStatisticsController(ProductionStatistics productionStatistics) {
    this.productionStatistics = productionStatistics;
  }

  /**
   * Get all the statistics
   *
   * @return all the statistics
   */
  public String getStatistics() {
    String stats = "CAR STATISTICS:" + System.lineSeparator() +
      "The average amount cars made in a day are: " + productionStatistics.averageCarsInADay() + System.lineSeparator() +
      "The median amount of cars made in a day are: " + productionStatistics.medianCarsInADay() + System.lineSeparator() +
      "The exact amount of cars made in the last 2 days are: " + productionStatistics.exactCarsIn2Days() + System.lineSeparator() +
      System.lineSeparator() + "DELAY STATISTICS:" + System.lineSeparator() +
      "The average delay at the moment is: " + productionStatistics.averageDelayPerOrder() + System.lineSeparator() +
      "The median delay at the moment is: " + productionStatistics.medianDelayPerOrder() + System.lineSeparator();
    String string = "The last 2 delays were at:" + System.lineSeparator();
    for (LocalDate localDate : productionStatistics.last2Delays().keySet()) {
      string = string + localDate.toString() + System.lineSeparator();
    }
    string += "and were this long:" + System.lineSeparator();
    for (LocalDate localDate : productionStatistics.last2Delays().keySet()) {
      string = string + productionStatistics.last2Delays().get(localDate) + System.lineSeparator();
    }
    stats = stats + string;

    return stats;


  }


}
