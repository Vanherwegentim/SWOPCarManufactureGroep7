package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Enums.WorkPostType;
import be.kuleuven.assemassit.Domain.TaskTypes.CarBodyAssemblyTask;
import be.kuleuven.assemassit.Domain.TaskTypes.InsertEngineAssemblyTask;
import be.kuleuven.assemassit.Domain.TaskTypes.InsertGearboxAssemblyTask;
import be.kuleuven.assemassit.Domain.WorkPost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CheckProductionStatisticsControllerTest {
  private CheckProductionStatisticsController controller;
  private AssemblyLine mockedAssemblyLine;



  @BeforeEach
  public void beforeEach() {
    mockedAssemblyLine = mock(AssemblyLine.class);
    controller = new CheckProductionStatisticsController(mockedAssemblyLine);
    when(mockedAssemblyLine.averageCarsInADay().)
  }
  when(mockedAssemblyLine.getAccessoriesPost()).thenReturn(mockedAccessoriesPost);
  when(mockedAssemblyLine.getDrivetrainPost()).thenReturn(mockedDrivetrainPost);

  @Test
  public void averageCarsInADayToStringTest(){
    "The average amount cars made in a day are: "
  }
}
