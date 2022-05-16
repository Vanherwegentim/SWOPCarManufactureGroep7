package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.Helper.CustomTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckProductionStatisticsControllerTest {
  private CheckProductionStatisticsController controller;
  private AssemblyLine mockedAssemblyLine;


  @BeforeEach
  public void beforeEach() {
    mockedAssemblyLine = mock(AssemblyLine.class);
    controller = new CheckProductionStatisticsController(mockedAssemblyLine);
    when(mockedAssemblyLine.averageCarsInADay()).thenReturn(1.0);
    when(mockedAssemblyLine.medianCarsInADay()).thenReturn(2.5);
    when(mockedAssemblyLine.exactCarsIn2Days()).thenReturn(1.0);
    when(mockedAssemblyLine.averageDelayPerOrder()).thenReturn(1.0);
    when(mockedAssemblyLine.medianDelayPerOrder()).thenReturn(1.0);
    when(mockedAssemblyLine.last2Delays()).thenReturn(Map.of((CustomTime.getInstance().customLocalDateNow()), 1));

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
    assertTrue(actual.equals(expected));
  }

}
