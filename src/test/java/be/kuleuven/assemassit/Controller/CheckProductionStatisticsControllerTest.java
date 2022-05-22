package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.Helper.CustomTime;
import be.kuleuven.assemassit.Domain.ProductionStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckProductionStatisticsControllerTest {
  private CheckProductionStatisticsController controller;
  private ProductionStatistics productionStatistics;


  @BeforeEach
  public void beforeEach() {
    productionStatistics = mock(ProductionStatistics.class);
    controller = new CheckProductionStatisticsController(productionStatistics);
    when(productionStatistics.averageCarsInADay()).thenReturn(1.0);
    when(productionStatistics.medianCarsInADay()).thenReturn(2.5);
    when(productionStatistics.exactCarsIn2Days()).thenReturn(1.0);
    when(productionStatistics.averageDelayPerOrder()).thenReturn(1.0);
    when(productionStatistics.medianDelayPerOrder()).thenReturn(1.0);
    when(productionStatistics.last2Delays()).thenReturn(Map.of((CustomTime.getInstance().customLocalDateNow()), 1));

  }

  @Test
  public void getStatisticsTest() {
    String expected = "CAR STATISTICS:" + System.lineSeparator() +
      "The average amount cars made in a day are: 1.0" + System.lineSeparator() +
      "The median amount of cars made in a day are: 2.5" + System.lineSeparator() +
      "The exact amount of cars made in the last 2 days are: 1.0" + System.lineSeparator() +
      System.lineSeparator() +
      "DELAY STATISTICS:" + System.lineSeparator() +
      "The average delay at the moment is: 1.0" + System.lineSeparator() +
      "The median delay at the moment is: 1.0" + System.lineSeparator() +
      "The last 2 delays were at:" + System.lineSeparator() +
      "1999-08-29" + System.lineSeparator() +
      "and were this long:" + System.lineSeparator() +
      "1" + System.lineSeparator();
    String actual = controller.getStatistics();
    assertEquals(actual, expected);
  }

}
