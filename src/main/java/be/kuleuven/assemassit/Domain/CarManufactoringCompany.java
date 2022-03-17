package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Repositories.CarModelRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * @mutable
 * @invar | getCarModels() != null
 * @invar | assemblyLine != null
 * @invar | getOpeningTime() != null
 * @invar | getClosingTime() != null
 * @invar Opening time should be before the closing time
 * | (getOpeningTime() != null && getClosingTime() != null) || getOpeningTime().isBefore(getClosingTime())
 */
public class CarManufactoringCompany {
  /**
   * @invar | carModels != null
   * @invar | assemblyLine != null
   * @invar | carModelRepository != null
   * @invar | openingTime != null
   * @invar | closingTime != null
   * @invar Opening time should be before the closing time
   * | (openingTime != null && closingTime != null) || openingTime.isBefore(closingTime)
   * @representationObject
   * @representationObjects
   */
  private List<CarModel> carModels;
  /**
   * @representationObject
   */
  private AssemblyLine assemblyLine;
  private CarModelRepository carModelRepository;
  private LocalTime openingTime;
  private LocalTime closingTime;

  /**
   * @param openingTime  the opening time of the factory
   * @param closingTime  the closing time of the factory
   * @param assemblyLine the assembly line that the factory will be using
   * @throws IllegalArgumentException some parameters are null | (openingTime == null || closingTime == null || assemblyLine == null)
   * @mutates | this
   * @post | openingTime.getHour() == this.openingTime.getHour()
   * @post | closingTime.getHour() == this.closingTime.getHour()
   * @post | this.assemblyLine.equals(assemblyLine)
   */
  public CarManufactoringCompany(LocalTime openingTime, LocalTime closingTime, AssemblyLine assemblyLine) {
    if (openingTime == null || closingTime == null || assemblyLine == null)
      throw new IllegalArgumentException("The parameters can not be null");

    this.carModelRepository = new CarModelRepository();
    this.carModels = carModelRepository.getCarModels();
    this.assemblyLine = assemblyLine;
    this.assemblyLine.setStartTime(openingTime);
    this.assemblyLine.setEndTime(closingTime);
    this.openingTime = LocalTime.of(openingTime.getHour(), openingTime.getMinute());
    this.closingTime = LocalTime.of(closingTime.getHour(), closingTime.getMinute());
  }

  public AssemblyLine getAssemblyLine() {
    return assemblyLine;
  }

  public List<CarModel> getCarModels() {
    return List.copyOf(carModels);
  }

  public LocalTime getOpeningTime() {
    return LocalTime.of(openingTime.getHour(), openingTime.getMinute());
  }

  public LocalTime getClosingTime() {
    return LocalTime.of(closingTime.getHour(), closingTime.getMinute());
  }

  /**
   * Return a specific car model from the car manufacturing company
   *
   * @param id the id of the car model
   * @return the car model
   * @throws IllegalArgumentException car model not found with given id | getCarModels().stream().filter(cm -> cm.getId() == id).findFirst().isEmpty()
   * @inspects | this
   */
  public CarModel giveCarModelWithId(int id) {
    Optional<CarModel> carModel = this.carModels
      .stream()
      .filter(cm -> cm.getId() == id)
      .findFirst();

    if (!carModel.isPresent())
      throw new IllegalArgumentException("CarModel not found");

    return carModel.get();
  }

  /**
   * Adds the car assembly process to the list of car assembly processes in the assembly line of the factory.
   *
   * @param carAssemblyProcess the instance of the to be added car assembly process
   * @inspects | carAssemblyProcess
   * @mutates | this
   */
  public void addCarAssemblyProcess(CarAssemblyProcess carAssemblyProcess) {
    if (carAssemblyProcess == null) {
      throw new IllegalArgumentException("CarAssemblyProcess not found");
    }
    assemblyLine.addCarAssemblyProcess(carAssemblyProcess);
  }

  // TODO: is this still used?
  public LocalDateTime giveEstimatedCompletionDateOfLatestProcess() {
    return assemblyLine.giveEstimatedCompletionDateOfLatestProcess();
  }
}
