package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.Repositories.CarModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class CarManufactoringCompanyTest {
  private List<CarModel> carModels;
  private AssemblyLine assemblyLine;
  private CarModelRepository carModelRepository;
  private LocalTime openingTime;
  private LocalTime closingTime;
  private CarManufactoringCompany carManufactoringCompany;
  private CarAssemblyProcess carAssemblyProcess;


  @BeforeEach
  public void beforeEach() {
    CarModelRepository carModelRepository = new CarModelRepository();
    this.carModels = carModelRepository.getCarModels();
    this.assemblyLine = new AssemblyLine();
    this.openingTime = LocalTime.of(LocalTime.of(6, 0).getHour(), LocalTime.of(6, 0).getMinute());
    this.closingTime = LocalTime.of(LocalTime.of(22, 0).getHour(), LocalTime.of(22, 0).getMinute());
    carManufactoringCompany = new CarManufactoringCompany(openingTime, closingTime, assemblyLine);
    carAssemblyProcess = new CarAssemblyProcess(
      new CarOrder(
        new Car(
          new CarModel(0, "Tolkswagen Rolo", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values()), Arrays.asList(Spoiler.values())),
          Body.SEDAN,
          Color.BLACK,
          Engine.PERFORMANCE,
          Gearbox.FIVE_SPEED_MANUAL,
          Seat.LEATHER_BLACK,
          Airco.MANUAL,
          Wheel.SPORT,
          Spoiler.LOW)));

    carManufactoringCompany.addCarAssemblyProcess(carAssemblyProcess);
  }


  @Test
  public void giveEstimatedCompletionDateOfLatestProcessTest() {
    // TODO: DONE this test should be rewritten, also, do no use equals with date; instead compare hour, minutes (and seconds)
    // assertEquals(carManufactoringCompany.giveEstimatedCompletionDateOfLatestProcess(), LocalDateTime.now().plusHours(3));
    assertTrue((carManufactoringCompany.giveEstimatedCompletionDateOfLatestProcess().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - LocalDateTime.now().plusHours(3).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() < 1000));
    /*
    LocalDateTime localDateTimeNow = LocalDateTime.now();
    LocalDateTime expectedDate = LocalDateTime.now();
    LocalDateTime actual = carManufactoringCompany.giveEstimatedCompletionDateOfLatestProcess();

    if (localDateTimeNow.getHour() < 6) {
      expectedDate = expectedDate.withHour(8).withMinute(0);
    }
    if (localDateTimeNow.getHour() >= 6 && localDateTimeNow.getHour() <= 19) {
      expectedDate = expectedDate.plusHours(3);
    }
    if (localDateTimeNow.getHour() > 19) {
      expectedDate = expectedDate.plusDays(1).withHour(8).withMinute(0);
    }

    assertEquals(expectedDate.getMinute(), actual.getMinute());
    assertEquals(expectedDate.getHour(), actual.getHour());
    assertEquals(expectedDate.toLocalDate(), actual.toLocalDate());
    */
  }

  @Test
  public void constructorTest() {
    assertEquals(carManufactoringCompany.getOpeningTime(), this.openingTime);
    assertEquals(carManufactoringCompany.getClosingTime(), this.closingTime);
    for (CarAssemblyProcess carAssemblyProcess : carManufactoringCompany.getAssemblyLine().getCarAssemblyProcessesQueue()) {
      assert assemblyLine.getCarAssemblyProcessesQueue().contains(carAssemblyProcess);
    }
  }

  @Test
  public void giveCarModelWithIdTest() {
    assertThrows(IllegalArgumentException.class, () -> carManufactoringCompany.giveCarModelWithId(-1));
    assertEquals(carManufactoringCompany.giveCarModelWithId(0).getName(), carModels.get(0).getName());
  }

  @Test
  public void addCarAssemblyProcessTest() {

    assert carManufactoringCompany.getAssemblyLine().getCarAssemblyProcessesQueue().contains(carAssemblyProcess);
  }


}
