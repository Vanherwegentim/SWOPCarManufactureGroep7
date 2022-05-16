package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;

import java.time.LocalDate;

public class CheckProductionStatisticsController {
  private final AssemblyLine assemblyLine;

  protected CheckProductionStatisticsController(AssemblyLine assemblyLine) {
    this.assemblyLine = assemblyLine;
  }

  /**
   * Get all the statistics
   *
   * @return all the statistics
   */
  public String getStatistics() {
    String stats = "CAR STATISTICS:" + System.lineSeparator() +
      "The average amount cars made in a day are: " + assemblyLine.averageCarsInADay() + System.lineSeparator() +
      "The median amount of cars made in a day are: " + assemblyLine.medianCarsInADay() + System.lineSeparator() +
      "The exact amount of cars made in the last 2 days are: " + assemblyLine.exactCarsIn2Days() + System.lineSeparator() +
      System.lineSeparator() + "DELAY STATISTICS:" + System.lineSeparator() +
      "The average delay at the moment is: " + assemblyLine.averageDelayPerOrder() + System.lineSeparator() +
      "The median delay at the moment is: " + assemblyLine.medianDelayPerOrder() + System.lineSeparator();
    String string = "The last 2 delays were at:" + System.lineSeparator();
    for (LocalDate localDate : assemblyLine.last2Delays().keySet()) {
      string = string + localDate.toString() + System.lineSeparator();
    }
    string += "and were this long:" + System.lineSeparator();
    for (LocalDate localDate : assemblyLine.last2Delays().keySet()) {
      string = string + assemblyLine.last2Delays().get(localDate) + System.lineSeparator();
    }
    stats = stats + string;

    return stats;


  }
  

}
