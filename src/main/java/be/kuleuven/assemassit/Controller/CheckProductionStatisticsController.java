package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;

import java.time.LocalDate;

public class CheckProductionStatisticsController {
  private AssemblyLine assemblyLine;

  public CheckProductionStatisticsController(AssemblyLine assemblyLine) {
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
    String string = "The last 2 delays were at:\n";
    for (LocalDate localDate : assemblyLine.last2Delays().keySet()) {
      string = string + localDate.toString() + "\n";
    }
    string += "and were this long:\n";
    for (LocalDate localDate : assemblyLine.last2Delays().keySet()) {
      string = string + assemblyLine.last2Delays().get(localDate) + "\n";
    }
    return string;
  }

//  public String checkStatistics() {
//    //TODO
//    return "The average amount cars made in a day are: " + assemblyLine.averageCarsInADay() + "\n" +
//      "The median amount of cars produced in a day are: " + assemblyLine.medianCarsInADay() + "\n" +
//      "The exact amount of cars made in the last 2 days are: " + assemblyLine.exactCarsIn2Days() + "\n" +
//      "The average delay for orders is";
//
//
//  }


}