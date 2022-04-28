package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.Helper.CustomTime;
import be.kuleuven.assemassit.Domain.TaskTypes.*;
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
    LocalDateTime localDateTime = (CustomTime.getInstance().customLocalDateTimeNow());
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
  public void equalsTestCarBodyAssemblyTask() {
    AssemblyTask.resetRunningId();
    AssemblyTask assemblyTask = new CarBodyAssemblyTask(Body.BREAK);
    AssemblyTask.resetRunningId();
    AssemblyTask assemblyTask1 = new CarBodyAssemblyTask(Body.BREAK);
    assertEquals(assemblyTask, assemblyTask1);
  }

  @Test
  public void equalsTestInsertEngineAssemblyTask() {
    AssemblyTask.resetRunningId();
    AssemblyTask assemblyTask = new InsertEngineAssemblyTask(Engine.PERFORMANCE);
    AssemblyTask.resetRunningId();
    AssemblyTask assemblyTask1 = new InsertEngineAssemblyTask(Engine.PERFORMANCE);
    assertEquals(assemblyTask, assemblyTask1);
  }

  @Test
  public void equalsTestInsertGearboxAssemblyTask() {
    AssemblyTask.resetRunningId();
    AssemblyTask assemblyTask = new InsertGearboxAssemblyTask(Gearbox.FIVE_SPEED_MANUAL);
    AssemblyTask.resetRunningId();
    AssemblyTask assemblyTask1 = new InsertGearboxAssemblyTask(Gearbox.FIVE_SPEED_MANUAL);
    assertEquals(assemblyTask, assemblyTask1);
  }

  @Test
  public void equalsTestInstallAircoAssemblyTask() {
    AssemblyTask.resetRunningId();
    AssemblyTask assemblyTask = new InstallAircoAssemblyTask(Airco.NO_AIRCO);
    AssemblyTask.resetRunningId();
    AssemblyTask assemblyTask1 = new InstallAircoAssemblyTask(Airco.NO_AIRCO);
    assertEquals(assemblyTask, assemblyTask1);
  }

  @Test
  public void equalsTestInstallSeatsAssemblyTask() {
    AssemblyTask.resetRunningId();
    AssemblyTask assemblyTask = new InstallSeatsAssemblyTask(Seat.LEATHER_BLACK);
    AssemblyTask.resetRunningId();
    AssemblyTask assemblyTask1= new InstallSeatsAssemblyTask(Seat.LEATHER_BLACK);
    assertEquals(assemblyTask, assemblyTask1);
  }

  @Test
  public void equalsTestInstallSpoilerAssemblyTask() {
    AssemblyTask.resetRunningId();
    AssemblyTask assemblyTask = new InstallSpoilerAssemblyTask(Spoiler.HIGH);
    AssemblyTask.resetRunningId();
    AssemblyTask assemblyTask1 = new InstallSpoilerAssemblyTask(Spoiler.HIGH);
    assertEquals(assemblyTask, assemblyTask1);
  }

  @Test
  public void equalsTestMountWheelsAssemblyTask() {
    AssemblyTask.resetRunningId();
    AssemblyTask assemblyTask = new MountWheelsAssemblyTask(Wheel.SPORT);
    AssemblyTask.resetRunningId();
    AssemblyTask assemblyTask1 = new MountWheelsAssemblyTask(Wheel.SPORT);
    assertEquals(assemblyTask, assemblyTask1);
  }

  @Test
  public void equalsTestPaintCarAssemblyTask() {
    AssemblyTask.resetRunningId();
    AssemblyTask assemblyTask = new PaintCarAssemblyTask(Color.BLACK);
    AssemblyTask.resetRunningId();
    AssemblyTask assemblyTask1 = new PaintCarAssemblyTask(Color.BLACK);
    assertEquals(assemblyTask, assemblyTask1);
  }


}
