package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;

import java.time.LocalDate;

public class CheckProductionStatisticsController {
  private final AssemblyLine assemblyLine;

  protected CheckProductionStatisticsController(AssemblyLine assemblyLine) {
    this.assemblyLine = assemblyLine;
  }


  public String averageCarsInADayToString() {
    return "The average amount cars made in a day are: " + assemblyLine.averageCarsInADay();
  }

  public String medianCarsInADayToString() {
    return "The median amount of cars made in a day are: " + assemblyLine.medianCarsInADay();
  }

  public String exactCarsIn2DaystoString() {
    return "The exact amount of cars made in the last 2 days are: " + assemblyLine.exactCarsIn2Days();
  }

  public String averageDelayPerOrderToString() {
    return "The average delay at the moment is: " + assemblyLine.averageDelayPerOrder();
  }

  public String medianDelayPerOrderToString() {
    return "The median delay at the moment is: " + assemblyLine.medianDelayPerOrder();
  }

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

//  public String checkStatistics() {
//    //TODO
//    return "The average amount cars made in a day are: " + assemblyLine.averageCarsInADay() + System.lineSeparator() +
//      "The median amount of cars produced in a day are: " + assemblyLine.medianCarsInADay() + System.lineSeparator() +
//      "The exact amount of cars made in the last 2 days are: " + assemblyLine.exactCarsIn2Days() + System.lineSeparator() +
//      "The average delay for orders is";
//
//
//  }


}
