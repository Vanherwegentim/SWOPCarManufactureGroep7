package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.TaskTypes.*;

import java.util.List;

public class CarAssemblyProcess {
  private List<AssemblyTask> assemblyTasks;
  private CarOrder carOrder;

  public CarAssemblyProcess(CarOrder carOrder) {
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
}
