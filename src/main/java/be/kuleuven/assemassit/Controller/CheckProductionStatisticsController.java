package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;

import java.time.LocalDate;

public class CheckProductionStatisticsController {
  private final AssemblyLine assemblyLine;

  protected CheckProductionStatisticsController(AssemblyLine assemblyLine) {
    this.assemblyLine = assemblyLine;
  }


  /**
   * Get the average produced cars in a single work day
   *
   * @return the average produced cars in single work day
   */
  public String averageCarsInADayToString() {
    return "The average amount cars made in a day are: " + assemblyLine.averageCarsInADay();
  }

  /**
   * Get the median produced cars in a single work day
   *
   * @return the median produced cars in a single work day
   */
  public String medianCarsInADayToString() {
    return "The median amount of cars made in a day are: " + assemblyLine.medianCarsInADay();
  }

  /**
   * The exact amount of produced cars in the last two days
   *
   * @return exact amount of produced cars in the last two days
   */
  public String exactCarsIn2DaystoString() {
    return "The exact amount of cars made in the last 2 days are: " + assemblyLine.exactCarsIn2Days();
  }

  /**
   * The average delay of an order
   *
   * @return average delay of an order
   */
  public String averageDelayPerOrderToString() {
    return "The average delay at the moment is: " + assemblyLine.averageDelayPerOrder();
  }

  /**
   * The median delay of an order
   *
   * @return median delay per order
   */
  public String medianDelayPerOrderToString() {
    return "The median delay at the moment is: " + assemblyLine.medianDelayPerOrder();
  }

  /**
   * The last two delayed orders
   *
   * @return last two delayed orders
   */
  public String last2DelaysToString() {
    String string = "The last 2 delays were at:" + System.lineSeparator();
    for (LocalDate localDate : assemblyLine.last2Delays().keySet()) {
      string = string + localDate.toString() + System.lineSeparator();
    }
    string += "and were this long:" + System.lineSeparator();
    for (LocalDate localDate : assemblyLine.last2Delays().keySet()) {
      string = string + assemblyLine.last2Delays().get(localDate) + System.lineSeparator();
    }
    return string;
  }


}
