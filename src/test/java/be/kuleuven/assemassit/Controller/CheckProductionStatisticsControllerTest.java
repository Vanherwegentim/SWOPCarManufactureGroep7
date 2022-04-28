package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.Helper.CustomTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    when(mockedAssemblyLine.last2Delays()).thenReturn(Map.of((new CustomTime().customLocalDateNow()), 1));

  }


  @Test
  public void averageCarsInADayToStringTest() {
    String expected = "The average amount cars made in a day are: 1.0";
    String actual = controller.averageCarsInADayToString();
    assertEquals(expected, actual);
  }

  @Test
  public void medianCarsInADayToStringTest() {
    String expected = "The median amount of cars made in a day are: 2.5";
    String actual = controller.medianCarsInADayToString();
    assertEquals(expected, actual);
  }

  @Test
  public void exactCarsIn2DaystoStringTest() {
    String expected = "The exact amount of cars made in the last 2 days are: 1.0";
    String actual = controller.exactCarsIn2DaystoString();
    assertEquals(expected, actual);
  }

  @Test
  public void averageDelayPerOrderToStringTest() {
    String expected = "The average delay at the moment is: 1.0";
    String actual = controller.averageDelayPerOrderToString();
    assertEquals(expected, actual);
  }

  @Test
  public void medianDelayPerOrderToStringTest() {
    String expected = "The median delay at the moment is: 1.0";
    String actual = controller.medianDelayPerOrderToString();
    assertEquals(expected, actual);
  }

  @Test
  public void last2DelaysToStringTest() {
    String expected = "The last 2 delays were at:" + System.lineSeparator() + (new CustomTime().customLocalDateNow()) + System.lineSeparator() + "and were this long:" + System.lineSeparator() + mockedAssemblyLine.last2Delays().get((new CustomTime().customLocalDateNow())) + System.lineSeparator();
    String actual = controller.last2DelaysToString();
    assertEquals(expected, actual);

  }
}
