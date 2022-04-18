package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
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
    when(mockedAssemblyLine.averageCarsInADay()).thenReturn(1);
    when(mockedAssemblyLine.medianCarsInADay()).thenReturn(1);
    when(mockedAssemblyLine.exactCarsIn2Days()).thenReturn(1);
    when(mockedAssemblyLine.averageDelayPerOrder()).thenReturn(1);
    when(mockedAssemblyLine.medianDelayPerOrder()).thenReturn(1);
    when(mockedAssemblyLine.last2Delays()).thenReturn(Map.of(LocalDate.now(), 1));

  }


  @Test
  public void averageCarsInADayToStringTest() {
    String expected = "The average amount cars made in a day are: 1";
    String actual = controller.averageCarsInADayToString();
    assertEquals(expected, actual);
  }

  @Test
  public void medianCarsInADayToStringTest() {
    String expected = "The median amount cars made in a day are: 1";
    String actual = controller.medianCarsInADayToString();
    assertEquals(expected, actual);
  }

  @Test
  public void exactCarsIn2DaystoStringTest() {
    String expected = "The exact amount of cars made in the last 2 days are: 1";
    String actual = controller.exactCarsIn2DaystoString();
    assertEquals(expected, actual);
  }

  @Test
  public void averageDelayPerOrderToStringTest() {
    String expected = "The average delay at the moment is: 1";
    String actual = controller.averageDelayPerOrderToString();
    assertEquals(expected, actual);
  }

  @Test
  public void medianDelayPerOrderToStringTest() {
    String expected = "The median delay at the moment is: 1";
    String actual = controller.medianDelayPerOrderToString();
    assertEquals(expected, actual);
  }

  @Test
  public void last2DelaysToStringTest() {
    String expected = "The last 2 delays were at:\n" + LocalDate.now() + "\nand were this long:\n" + mockedAssemblyLine.last2Delays().get(LocalDate.now());
    String actual = controller.last2DelaysToString();
    assertEquals(expected, actual);
  }
}
