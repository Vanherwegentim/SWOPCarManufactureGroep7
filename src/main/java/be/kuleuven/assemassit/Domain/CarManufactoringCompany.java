package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.Helper.CustomTime;
import be.kuleuven.assemassit.Repositories.CarModelRepository;
import be.kuleuven.assemassit.Repositories.OvertimeRepository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 * @mutable
 * @invar | getCarModels() != null
 * @invar | getAssemblyLine() != null
 * @invar | getOpeningTime() != null
 * @invar | getClosingTime() != null
 * @invar Opening time should be before the closing time
 * | (getOpeningTime() != null && getClosingTime() != null) || getOpeningTime().isBefore(getClosingTime())
 * @invar | getOvertime() >= 0
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
   * @invar | overtime >= 0
   * @representationObject
   * @representationObjects
   */
  private final List<CarModel> carModels;
  /**
   * @representationObject
   */
  private final AssemblyLine assemblyLine;

  /**
   * @param openingTime  the opening time of the factory
   * @param closingTime  the closing time of the factory
   * @param assemblyLine the assembly line that the factory will be using
   * @throws IllegalArgumentException some parameters are null | (openingTime == null || closingTime == null || assemblyLine == null)
   * @mutates | this
   * @post | openingTime.getHour() == this.openingTime.getHour()
   * @post | closingTime.getHour() == this.closingTime.getHour()
   * @post | this.getAssemblyLine.equals(assemblyLine)
   */
  public CarManufactoringCompany(LocalTime openingTime, LocalTime closingTime, AssemblyLine assemblyLine) {
    this(new CarModelRepository(), openingTime, closingTime, assemblyLine);
  }

  /**
   * @param carModelRepository the repository that can be mocked
   * @param openingTime        the opening time of the factory
   * @param closingTime        the closing time of the factory
   * @param assemblyLine       the assembly line that the factory will be using
   * @throws IllegalArgumentException some parameters are null | (openingTime == null || closingTime == null || assemblyLine == null)
   * @mutates | this
   * @post | openingTime.getHour() == this.openingTime.getHour()
   * @post | closingTime.getHour() == this.closingTime.getHour()
   * @post | this.assemblyLine.equals(assemblyLine)
   */
  public CarManufactoringCompany(CarModelRepository carModelRepository, LocalTime openingTime, LocalTime closingTime, AssemblyLine assemblyLine) {
    if (openingTime == null || closingTime == null || assemblyLine == null || carModelRepository == null)
      throw new IllegalArgumentException("The parameters can not be null");

    this.carModels = carModelRepository.getCarModels();
    this.assemblyLine = assemblyLine;
    this.assemblyLine.setOpeningTime(openingTime);
    this.assemblyLine.setClosingTime(closingTime);
  }

  public AssemblyLine getAssemblyLine() {
    return assemblyLine;
  }

  /**
   * @return list of car models
   * @creates | result
   */
  public List<CarModel> getCarModels() {
    return List.copyOf(carModels);
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

    if (carModel.isEmpty())
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

  /**
   * @return the estimated completion date of the last placed process in the queue
   * @inspects | this
   */
  public LocalDateTime giveEstimatedCompletionDateOfLatestProcess() {
    return assemblyLine.giveEstimatedCompletionDateOfLatestProcess();
  }

  /**
   * A new car order is made
   *
   * @param carModelId
   * @param body
   * @param color
   * @param engine
   * @param gearbox
   * @param seats
   * @param airco
   * @param wheels
   * @return the id of the newly created car order
   * @throws IllegalStateException    currentGarageHolder == null
   * @throws IllegalArgumentException if there is a non-valid option provided
   */
  public int designCarOrder(GarageHolder currentGarageHolder, int carModelId, String body, String color, String engine, String gearbox, String seats, String airco, String wheels, String spoiler) {
    if (currentGarageHolder == null)
      throw new IllegalStateException();

    CarModel carModel = giveCarModelWithId(carModelId);
    Car car;

    try {
      car = new Car
        (
          carModel,
          Body.valueOf(body),
          Color.valueOf(color),
          Engine.valueOf(engine),
          Gearbox.valueOf(gearbox),
          Seat.valueOf(seats),
          Airco.valueOf(airco),
          Wheel.valueOf(wheels),
          Spoiler.valueOf(spoiler)
        );
    } catch (IllegalArgumentException e) {
      if (e.getLocalizedMessage().startsWith("No enum constant")) {
        throw new IllegalArgumentException("One or more invalid car options were provided");
      }
      throw new IllegalArgumentException(e.getMessage());
    }

    CarOrder carOrder = new CarOrder(car);
    currentGarageHolder.addCarOrder(carOrder);

    CarAssemblyProcess carAssemblyProcess = new CarAssemblyProcess(carOrder);

    addCarAssemblyProcess(carAssemblyProcess);
    LocalDateTime estimatedCompletionTime = giveEstimatedCompletionDateOfLatestProcess();
    carOrder.setEstimatedCompletionTime(estimatedCompletionTime);

    if (isAssemblyLineAvailable()) {
      triggerAutomaticFirstMove(carAssemblyProcess.giveManufacturingDurationInMinutes());
    }

    return carOrder.getId();
  }

  /**
   * Move the assembly line forward if possible
   *
   * @mutates | this
   */
  public void moveAssemblyLine() {
    this.assemblyLine.move(this.assemblyLine.getClosingTime(), this.assemblyLine.getOverTime());
  }

  /**
   * Trigger the first move, this can be done on placing an order and if the order is the first order of the day
   *
   * @inspects | this
   * @mutates | this
   */

  public void triggerAutomaticFirstMove(int manufacturingDurationInMinutes) {
    if (!(CustomTime.getInstance().customLocalTimeNow().isBefore(this.assemblyLine.getOpeningTime()))
      && assemblyLine.canMove()
      && !(CustomTime.getInstance().customLocalTimeNow().isAfter(this.assemblyLine.getClosingTime().minusMinutes(this.assemblyLine.getOverTime()).minusMinutes(manufacturingDurationInMinutes))))

      this.moveAssemblyLine();
  }

  public boolean isAssemblyLineAvailable() {
    return this.assemblyLine.getWorkPosts().stream().allMatch(wp -> wp.getCarAssemblyProcess() == null);
  }
}
