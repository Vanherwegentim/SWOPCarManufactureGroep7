package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.Helper.CustomTime;
import be.kuleuven.assemassit.Repositories.CarModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
    carModelRepository = new CarModelRepository();
    this.carModels = carModelRepository.getCarModels();
    this.assemblyLine = new AssemblyLine();
    this.openingTime = LocalTime.of(LocalTime.of(6, 0).getHour(), LocalTime.of(6, 0).getMinute());
    this.closingTime = LocalTime.of(LocalTime.of(22, 0).getHour(), LocalTime.of(22, 0).getMinute());
    carManufactoringCompany = new CarManufactoringCompany(openingTime, closingTime, assemblyLine);
    carAssemblyProcess = new CarAssemblyProcess(new CarOrder(new Car(new CarModel(0, "Tolkswagen Rolo", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values()), Arrays.asList(Spoiler.values())), Body.SEDAN, Color.BLACK, Engine.PERFORMANCE, Gearbox.FIVE_SPEED_MANUAL, Seat.LEATHER_BLACK, Airco.MANUAL, Wheel.SPORT, Spoiler.LOW)));

    carManufactoringCompany.addCarAssemblyProcess(carAssemblyProcess);
  }


  @Test
  public void giveEstimatedCompletionDateOfLatestProcessTest() {
    //todo: deze test werkt enkel overdag
    // TODO: DONE this test should be rewritten, also, do no use equals with date; instead compare hour, minutes (and seconds)
    // assertEquals(carManufactoringCompany.giveEstimatedCompletionDateOfLatestProcess(), (CustomTime.getInstance().customLocalDateTimeNow()).plusHours(3));
    //assertTrue((carManufactoringCompany.giveEstimatedCompletionDateOfLatestProcess().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - (CustomTime.getInstance().customLocalDateTimeNow()).plusHours(3).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() < 1000));
    //assertTrue((carManufactoringCompany.giveEstimatedCompletionDateOfLatestProcess().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - (CustomTime.getInstance().customLocalDateTimeNow()).plusHours(0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() < 1000));
    LocalDateTime localDateTimeNow = (CustomTime.getInstance().customLocalDateTimeNow());
    LocalDateTime expectedDate = (CustomTime.getInstance().customLocalDateTimeNow());
    LocalDateTime actual = carManufactoringCompany.giveEstimatedCompletionDateOfLatestProcess();

    if (localDateTimeNow.getHour() < 6) {
      expectedDate = expectedDate.withHour(9).withMinute(0);
    }
    if (localDateTimeNow.getHour() >= 6 && localDateTimeNow.getHour() <= 19) {
      expectedDate = expectedDate.plusHours(3);
    }
    if (localDateTimeNow.getHour() > 19) {
      expectedDate = expectedDate.plusDays(1).withHour(9).withMinute(0);
    }

    assertEquals(expectedDate.getMinute(), actual.getMinute());
    assertEquals(expectedDate.getHour(), actual.getHour());
    assertEquals(expectedDate.toLocalDate(), actual.toLocalDate());
  }

  @Test
  public void constructorTest() {
    assertEquals(assemblyLine.getOpeningTime(), this.openingTime);
    assertEquals(assemblyLine.getClosingTime(), this.closingTime);
    for (CarAssemblyProcess carAssemblyProcess : carManufactoringCompany.getAssemblyLine().getCarAssemblyProcessesQueue()) {
      assertTrue(assemblyLine.getCarAssemblyProcessesQueue().contains(carAssemblyProcess));
    }
  }

  @Test
  public void constructorTest2() {
    CarManufactoringCompany company = new CarManufactoringCompany(carModelRepository, openingTime, closingTime, assemblyLine);

    assertEquals(assemblyLine.getOpeningTime(), this.openingTime);
    assertEquals(assemblyLine.getClosingTime(), this.closingTime);
    for (CarAssemblyProcess carAssemblyProcess : company.getAssemblyLine().getCarAssemblyProcessesQueue()) {
      assertTrue(assemblyLine.getCarAssemblyProcessesQueue().contains(carAssemblyProcess));
    }
  }

  @Test
  public void giveCarModelWithIdTest() {
    assertThrows(IllegalArgumentException.class, () -> carManufactoringCompany.giveCarModelWithId(-1));
    assertEquals(carManufactoringCompany.giveCarModelWithId(0).getName(), carModels.get(0).getName());
  }

  @Test
  public void addCarAssemblyProcessTest() {

    assertTrue(carManufactoringCompany.getAssemblyLine().getCarAssemblyProcessesQueue().contains(carAssemblyProcess));
  }


  @Test
  void getCarModels() {
    List<CarModel> models = carManufactoringCompany.getCarModels();
    assertEquals(models.size(), 3);
  }

  @Test
  void assemblyLineMove() {
    LocalTime time = (CustomTime.getInstance().customLocalTimeNow());
    AssemblyLine line = new AssemblyLine();
    CarManufactoringCompany company = new CarManufactoringCompany(LocalTime.of(00, 1), LocalTime.of(23, 59), line);
    assertTrue(company.isAssemblyLineAvailable());
    CarAssemblyProcess process = new CarAssemblyProcess(new CarOrder(new Car(new CarModel(0, "Tolkswagen Rolo", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values()), Arrays.asList(Spoiler.values())), Body.SEDAN, Color.BLACK, Engine.PERFORMANCE, Gearbox.FIVE_SPEED_MANUAL, Seat.LEATHER_BLACK, Airco.MANUAL, Wheel.SPORT, Spoiler.LOW)));
    company.addCarAssemblyProcess(process);
    assertTrue(company.isAssemblyLineAvailable());
    company.triggerAutomaticFirstMove();
    assertFalse(company.isAssemblyLineAvailable());
  }

//  @Test
//  void update() {
//    LocalTime time = (CustomTime.getInstance().customLocalTimeNow());
//    AssemblyLine line = new AssemblyLine();
//    OvertimeRepository overTimeRepository = new OvertimeRepository();
//    CarManufactoringCompany company = new CarManufactoringCompany(carModelRepository, overTimeRepository, time.minusHours(1), time.plusHours(15), line);
//    company.update(line, 88);
//
//    assertEquals(overTimeRepository.getOverTime(), 88);
//    int companyOT = company.getOvertime();
//    int repOT = company.getOverTimeRepository().getOverTime();
//
//    assertEquals(companyOT, repOT);
//  }
}
