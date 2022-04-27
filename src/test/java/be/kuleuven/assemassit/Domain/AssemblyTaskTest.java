package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.Body;
import be.kuleuven.assemassit.Domain.Helper.CustomTime;
import be.kuleuven.assemassit.Domain.TaskTypes.CarBodyAssemblyTask;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class AssemblyTaskTest {
  @Test
  public void constructorTest() {
    AssemblyTask assemblyTask = new CarBodyAssemblyTask(Body.BREAK);
    assertEquals("Assembly car body", assemblyTask.getName());
  }

  @Test
  public void completionTimeTest() {
    AssemblyTask assemblyTask = new CarBodyAssemblyTask(Body.BREAK);
    assemblyTask.setPending(true);
    assertThrows(IllegalStateException.class, assemblyTask::getCompletionTime);
    LocalDateTime localDateTime = (new CustomTime().customLocalDateTimeNow());
    assemblyTask.setCompletionTime(localDateTime);
    assemblyTask.setDuration(60);
    assemblyTask.setPending(false);

    assertEquals(localDateTime, assemblyTask.getCompletionTime());
    assertEquals(60, assemblyTask.getDuration());
  }

  @Test
  public void completeTest() {
    AssemblyTask assemblyTask = new CarBodyAssemblyTask(Body.BREAK);
    assertTrue(assemblyTask.getPending());
    assemblyTask.complete();
    assertFalse(assemblyTask.getPending());
  }

  @Test
  public void equalsTest() {
    AssemblyTask.resetRunningId();
    AssemblyTask assemblyTask = new CarBodyAssemblyTask(Body.BREAK);
    AssemblyTask.resetRunningId();
    AssemblyTask assemblyTask1 = new CarBodyAssemblyTask(Body.BREAK);
    assertEquals(assemblyTask, assemblyTask1);
  }


}
