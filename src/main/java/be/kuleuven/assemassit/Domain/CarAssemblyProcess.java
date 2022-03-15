package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.TaskTypes.*;

import java.util.List;
import java.util.Optional;

public class CarAssemblyProcess {
  private static int idRunner = 0;

  private List<AssemblyTask> assemblyTasks;
  private CarOrder carOrder;
  private int id;

  public CarAssemblyProcess(CarOrder carOrder) {
    this.id = CarAssemblyProcess.idRunner++;
    this.carOrder = carOrder;
    this.assemblyTasks = List.of(
      new CarBodyAssemblyTask("", carOrder.getCar().getBody()),
      new InsertGearboxAssemblyTask("", carOrder.getCar().getGearbox()),
      new InsertEngineAssemblyTask("", carOrder.getCar().getEngine()),
      new InstallAircoAssemblyTask("", carOrder.getCar().getAirco()),
      new MountWheelsAssemblyTask("", carOrder.getCar().getWheels()),
      new PaintCarAssemblyTask("", carOrder.getCar().getColor())
    );
  }

  public CarAssemblyProcess(CarOrder carOrder, List<AssemblyTask> assemblyTasks) {
    this.carOrder = carOrder;
    this.assemblyTasks = List.copyOf(assemblyTasks);
  }

  public List<AssemblyTask> getAssemblyTasks(){
    return assemblyTasks;
  }

  public int getId() {
    return this.id;
  }

  public AssemblyTask giveAssemblyTask(int id) {
    Optional<AssemblyTask> carAssemblyProcess = assemblyTasks.stream()
      .filter(p -> p.getId() == id)
      .findFirst();

    if (carAssemblyProcess.isPresent())
      throw new IllegalArgumentException("Assembly task not found");

    return carAssemblyProcess.get();
  }
}
