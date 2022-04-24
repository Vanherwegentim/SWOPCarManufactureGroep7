package be.kuleuven.assemassit;

import be.kuleuven.assemassit.Controller.CheckProductionStatisticsController;
import be.kuleuven.assemassit.Domain.AssemblyLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckProductionStatisticsTest {
  private CheckProductionStatisticsController controller;
  private AssemblyLine assemblyLine;

  @BeforeEach
  public void beforeEach() {
    assemblyLine = new AssemblyLine();
    controller = new CheckProductionStatisticsController(assemblyLine);
  }

  @Test
  public void checkProductionStatisticsIntegrationTest_MainScenario_Success() {
    //MAIN SUCCESS SCENARIO

    // Step 1

    // executed in UI tests

    // Step 2
    assertEquals("The average amount cars made in a day are: 1", controller.averageCarsInADayToString());
    assertEquals("The median amount of cars made in a day are:  1", controller.medianCarsInADayToString());
    assertEquals("The exact amount of cars made in the last 2 days are: 1", controller.exactCarsIn2DaystoString());
    assertEquals("The average delay at the moment is: ", controller.averageDelayPerOrderToString());
    assertEquals("The median delay at the moment is: 1", controller.medianDelayPerOrderToString());
    assertEquals("The last 2 delays were at:\n" + LocalDate.now() + "\nand were this long:\n" + assemblyLine.last2Delays().get(LocalDate.now()), controller.last2DelaysToString());

    // Step 3
    //Done
  }
}
