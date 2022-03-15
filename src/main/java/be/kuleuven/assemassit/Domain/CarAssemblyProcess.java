package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.TaskTypes.*;

import java.util.Arrays;
import java.util.List;

public class CarAssemblyProcess {
  private List<AssemblyTask> assemblyTasks;
  private CarOrder carOrder;

  public CarAssemblyProcess(CarOrder carOrder) {
    if(carOrder == null){
      throw new NullPointerException("The car order can't be null");
    }
    this.carOrder = carOrder;
    this.assemblyTasks = Arrays.asList(
      new CarBodyAssemblyTask("", carOrder.getCar().getBody()),
      new InsertGearboxAssemblyTask("", carOrder.getCar().getGearbox()),
      new InsertEngineAssemblyTask("", carOrder.getCar().getEngine()),
      new InstallAircoAssemblyTask("", carOrder.getCar().getAirco()),
      new MountWheelsAssemblyTask("", carOrder.getCar().getWheels()),
      new PaintCarAssemblyTask("", carOrder.getCar().getColor()),
      new InstallSeatsAssemblyTask("", carOrder.getCar().getSeats())
    );
  }



  public List<AssemblyTask> getAssemblyTasks(){
    return assemblyTasks;
  }
}
