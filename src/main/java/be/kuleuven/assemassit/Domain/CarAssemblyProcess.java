package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.TaskTypes.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CarAssemblyProcess {
  private static int idRunner = 0;

  private List<AssemblyTask> assemblyTasks;
  private CarOrder carOrder;
  private int id;

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
      new InstallSeatsAssemblyTask(carOrder.getCar().getSeats())
    );
  }

  public List<AssemblyTask> getAssemblyTasks() {
    return assemblyTasks;
  }

  public int getId() {
    return this.id;
  }

  public AssemblyTask giveAssemblyTask(int id) {
    Optional<AssemblyTask> carAssemblyProcess = giveOptionalAssemblyTask(id);

    if (carAssemblyProcess.isEmpty())
      throw new IllegalArgumentException("Assembly task not found");

    return carAssemblyProcess.get();
  }

  public Optional<AssemblyTask> giveOptionalAssemblyTask(int id) {
    return assemblyTasks.stream()
      .filter(p -> p.getId() == id)
      .findFirst();
  }
}
