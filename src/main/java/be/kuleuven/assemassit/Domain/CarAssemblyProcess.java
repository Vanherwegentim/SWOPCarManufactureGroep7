package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Helper.CustomTime;
import be.kuleuven.assemassit.Domain.TaskTypes.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @mutable
 * @invar | getAssemblyTasks() != null
 * @invar | getCarOrder() != null
 */
public class CarAssemblyProcess {
  private static int idRunner = 0;

  /**
   * @invar | assemblyTasks != null
   * @invar | carOrder != null
   * @representationObject
   * @representationObjects
   */
  private final List<AssemblyTask> assemblyTasks;
  /**
   * @representationObject
   */
  private final CarOrder carOrder;
  private final int id;

  /**
   * @param carOrder the order that should be connected to the car assembly process
   * @throws NullPointerException | carOrder == null
   * @post | this.getId() > -1
   * @post | this.getCarOrder() == carOrder
   * @post | this.getAssemblyTasks() != null
   * @inspects | carOrder
   * @mutates | this
   */
  public CarAssemblyProcess(CarOrder carOrder) {
    if (carOrder == null) {
      throw new NullPointerException("The car order can't be null");
    }

    this.id = CarAssemblyProcess.idRunner++;

    this.carOrder = carOrder;
    this.assemblyTasks = Arrays.asList(
      new CarBodyAssemblyTask(carOrder.getCar().getBody()),
      new InsertGearboxAssemblyTask(carOrder.getCar().getGearbox()),
      new InsertEngineAssemblyTask(carOrder.getCar().getEngine()),
      new InstallAircoAssemblyTask(carOrder.getCar().getAirco()),
      new MountWheelsAssemblyTask(carOrder.getCar().getWheels()),
      new PaintCarAssemblyTask(carOrder.getCar().getColor()),
      new InstallSeatsAssemblyTask(carOrder.getCar().getSeats()),
      new InstallSpoilerAssemblyTask(carOrder.getCar().getSpoiler())
    );
  }

  /**
   * Reset the counter of automatic IDs, this is for testing purposes
   */
  public static void resetRunningId() {
    CarAssemblyProcess.idRunner = 0;
  }

  public List<AssemblyTask> getAssemblyTasks() {
    return assemblyTasks;
  }

  public CarOrder getCarOrder() {
    return this.carOrder;
  }

  public int getId() {
    return this.id;
  }

  /**
   * Searches in the list of assembly tasks to find the corresponding assembly task
   *
   * @param id of the assembly task
   * @return the assembly task
   * @throws IllegalArgumentException id is not found | giveOptionalAssemblyTask(id).isEmpty()
   */
  public AssemblyTask giveAssemblyTask(int id) {
    Optional<AssemblyTask> carAssemblyProcess = giveOptionalAssemblyTask(id);

    if (carAssemblyProcess.isEmpty())
      throw new IllegalArgumentException("Assembly task not found");

    return carAssemblyProcess.get();
  }

  /**
   * Searches in the list of assembly tasks to find the corresponding assembly task but returns an optional
   *
   * @param id the id of the assembly task
   * @return an optional of the assembly task (found or not found)
   * @inspects | this
   */
  public Optional<AssemblyTask> giveOptionalAssemblyTask(int id) {
    return assemblyTasks.stream()
      .filter(p -> p.getId() == id)
      .findFirst();
  }

  /**
   * Sets the completion time of a process.
   * This method is called when a process is finished on the assembly line.
   *
   * @mutates | this
   */
  public void complete() {
    carOrder.setCompletionTime((CustomTime.getInstance().customLocalDateTimeNow()));
    carOrder.setPending(false);
  }

  /**
   * @return the manufacturing duration of a process in minutes
   * @inspects | this
   */
  public int giveManufacturingDurationInMinutes() {
    return carOrder.getCar().getCarModel().getWorkPostDuration() * 3;
  }
}
