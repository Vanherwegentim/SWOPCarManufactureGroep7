package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.Body;
import be.kuleuven.assemassit.Domain.TaskTypes.CarBodyAssemblyTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AssemblyTaskTest {
  private List<String> actions;

  @BeforeEach
  public void beforeEach() {
  }

  @Test
  public void constructorTest() {
    AssemblyTask assemblyTask = new CarBodyAssemblyTask(Body.BREAK);
    assertTrue(assemblyTask.getName().equals("Assembly car body"));
  }

  @Test
  public void completionTimeTest() {
    AssemblyTask assemblyTask = new CarBodyAssemblyTask(Body.BREAK);
    assemblyTask.setPending(true);
    assertThrows(IllegalStateException.class, () -> assemblyTask.completionTime());
    assemblyTask.setCompletionTime(60);
    assemblyTask.setPending(false);

    assert assemblyTask.completionTime() == 60;
  }

  @Test
  public void completeTest() {
    AssemblyTask assemblyTask = new CarBodyAssemblyTask(Body.BREAK);
    assertFalse(!assemblyTask.getPending());
  }

  @Test
  public void equalsTest() {
    AssemblyTask assemblyTask = new CarBodyAssemblyTask(Body.BREAK);
    AssemblyTask assemblyTask1 = new CarBodyAssemblyTask(Body.BREAK);
    assemblyTask.equals(assemblyTask1);

  }


}
