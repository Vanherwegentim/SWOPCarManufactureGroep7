package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.Repositories.CarModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CarManufactoringCompanyTest {
  private List<CarModel> carModels;
  private AssemblyLine assemblyLine;
  private CarModelRepository carModelRepository;
  private LocalTime openingTime;
  private LocalTime closingTime;
  private CarManufactoringCompany carManufactoringCompany;
  private CarAssemblyProcess carAssemblyProcess;


  @BeforeEach
  public void beforeEach(){
    this.carModelRepository = new CarModelRepository();
    this.carModels = carModelRepository.getCarModels();
    this.assemblyLine = new AssemblyLine();
    this.openingTime = LocalTime.of(LocalTime.of(6,0).getHour(), LocalTime.of(6,0).getMinute());
    this.closingTime = LocalTime.of(LocalTime.of(22,0).getHour(), LocalTime.of(22,0).getMinute());
    carManufactoringCompany = new CarManufactoringCompany(openingTime,closingTime,assemblyLine);
    carAssemblyProcess = new CarAssemblyProcess(
      new CarOrder(
        new Car(
          new CarModel(0, "Tolkswagen Rolo", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values())),
          Body.SEAD,
          Color.BLACK,
          Engine.PERFORMANCE,
          Gearbox.MANUAL,
          Seat.LEATHER_BLACK,
          Airco.MANUAL,
          Wheel.SPORT)));
    carManufactoringCompany.addCarAssemblyProcess(carAssemblyProcess);
  }


  @Test
  public void giveEstimatedCompletionDateOfLatestProcessTest(){
    assertEquals(carManufactoringCompany.giveEstimatedCompletionDateOfLatestProcess(), LocalDateTime.now().plusHours(3));
  }

  @Test
  public void constructorTest(){
    assertEquals(carManufactoringCompany.getOpeningTime(), this.openingTime);
    assertEquals(carManufactoringCompany.getClosingTime(), this.closingTime);
    for (CarAssemblyProcess carAssemblyProcess: carManufactoringCompany.getAssemblyLine().getCarAssemblyProcessesQueue()){
      assert assemblyLine.getCarAssemblyProcessesQueue().contains(carAssemblyProcess);
    }
  }

  @Test
  public void giveCarModelWithIdTest(){
    assertThrows(IllegalArgumentException.class, ()-> carManufactoringCompany.giveCarModelWithId(-1));
    assertEquals(carManufactoringCompany.giveCarModelWithId(0).getName(), carModels.get(0).getName());
  }

  @Test
  public void addCarAssemblyProcessTest(){

  assert carManufactoringCompany.getAssemblyLine().getCarAssemblyProcessesQueue().contains(carAssemblyProcess);
  }


}
