package be.kuleuven.assemassit;

import be.kuleuven.assemassit.Controller.CheckProductionStatisticsController;
import be.kuleuven.assemassit.Controller.ControllerFactoryMiddleWare;
import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.GarageHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckProductionStatisticsTest {
  private CheckProductionStatisticsController controller;
  private AssemblyLine assemblyLine;

  @BeforeEach
  public void beforeEach() {
    assemblyLine = new AssemblyLine();
    ControllerFactoryMiddleWare controllerFactoryMiddleWare = new ControllerFactoryMiddleWare();
    GarageHolder garageHolder = new GarageHolder(5, "yeet smith");
    controllerFactoryMiddleWare.loginManager();
    controller = controllerFactoryMiddleWare.createCheckProductionStatisticsController();
  }

  @Test
  public void checkProductionStatisticsIntegrationTest_MainScenario_Success() {
    //MAIN SUCCESS SCENARIO

    // Step 1

    // executed in UI tests

    // Step 2

    String expected = "CAR STATISTICS:" + System.lineSeparator() +
      "The average amount cars made in a day are: 0.0" + System.lineSeparator() +
      "The median amount of cars made in a day are: 0.0" + System.lineSeparator() +
      "The exact amount of cars made in the last 2 days are: 0.0" + System.lineSeparator() +
      System.lineSeparator() +
      "DELAY STATISTICS:\n" +
      "The average delay at the moment is: 0.0" + System.lineSeparator() +
      "The median delay at the moment is: 0.0" + System.lineSeparator() +
      "The last 2 delays were at:" + System.lineSeparator() +
      "and were this long:" + System.lineSeparator();

    // Step 3
    //Done
  }
}
