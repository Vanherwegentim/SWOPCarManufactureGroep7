package be.kuleuven.assemassit;

import be.kuleuven.assemassit.Controller.CheckProductionStatisticsController;
import be.kuleuven.assemassit.Controller.ControllerFactory;
import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.GarageHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckProductionStatisticsTest {
  private CheckProductionStatisticsController controller;
  private AssemblyLine assemblyLine;

  @BeforeEach
  public void beforeEach() {
    assemblyLine = new AssemblyLine();
    ControllerFactory controllerFactory = new ControllerFactory();
    GarageHolder garageHolder = new GarageHolder(5, "yeet smith");
    controllerFactory.loginManager();
    controller = controllerFactory.createCheckProductionStatisticsController();
  }

  @Test
  public void checkProductionStatisticsIntegrationTest_MainScenario_Success() {
    //MAIN SUCCESS SCENARIO

    // Step 1

    // executed in UI tests

    // Step 2
    assertEquals("The average amount cars made in a day are: 0", controller.averageCarsInADayToString());
    assertEquals("The median amount of cars made in a day are: 0", controller.medianCarsInADayToString());
    assertEquals("The exact amount of cars made in the last 2 days are: 0", controller.exactCarsIn2DaystoString());
    assertEquals("The average delay at the moment is: 0", controller.averageDelayPerOrderToString());
    assertEquals("The median delay at the moment is: 0", controller.medianDelayPerOrderToString());
    assertEquals("The last 2 delays were at:\n" + "and were this long:\n", controller.last2DelaysToString());

    // Step 3
    //Done
  }
}
