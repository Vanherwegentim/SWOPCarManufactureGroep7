package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;

public class CheckProductionStatisticsController {
  private AssemblyLine assemblyLine;

  public CheckProductionStatisticsController(AssemblyLine assemblyLine) {
    this.assemblyLine = assemblyLine;
  }


  public String checkStatistics() {
    //TODO
    return "The average amount cars made in a day are: " + assemblyLine.averageCarsInADay() + "\n" +
      "The median amount of cars produced in a day are: " + assemblyLine.medianCarsInADay() + "\n" +
      "The exact amount of cars made in the last 2 days are: " + assemblyLine.exactCarsIn2Days() + "\n";

  }
}
