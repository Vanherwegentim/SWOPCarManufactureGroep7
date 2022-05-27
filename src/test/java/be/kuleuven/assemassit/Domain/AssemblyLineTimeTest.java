package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Repositories.OvertimeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AssemblyLineTimeTest {

  private AssemblyLineTime assemblyLineTime;
  private OvertimeRepository overtimeRepository;

  @BeforeEach
  public void beforeEach() {
    assemblyLineTime = new AssemblyLineTime();
    overtimeRepository = new OvertimeRepository();
  }

  @Test
  public void updateTest() {
    assertThrows(IllegalArgumentException.class, () -> assemblyLineTime.update(-5));

    assemblyLineTime.update(5);
    assertEquals(5, assemblyLineTime.getOvertime());
    overtimeRepository.clearFile();
  }

  @Test
  public void setOpeningTimeTest() {
    assertThrows(IllegalArgumentException.class, () -> assemblyLineTime.setOpeningTime(null));
    assemblyLineTime.setOpeningTime(LocalTime.of(5, 30));
    assertEquals(LocalTime.of(5, 30), assemblyLineTime.getOpeningTime());
  }

  @Test
  public void setClosingTimeTest() {
    assertThrows(IllegalArgumentException.class, () -> assemblyLineTime.setClosingTime(null));
    assemblyLineTime.setClosingTime(LocalTime.of(22, 30));
    assertEquals(LocalTime.of(22, 30), assemblyLineTime.getClosingTime());
  }

  @Test
  public void getOverTimeRepositoryTest() {
    AssemblyLineTime assemblyLineTime1 = new AssemblyLineTime(LocalTime.of(6, 0), LocalTime.of(22, 0), overtimeRepository);
    assertEquals(overtimeRepository, assemblyLineTime1.getOverTimeRepository());
  }


}
