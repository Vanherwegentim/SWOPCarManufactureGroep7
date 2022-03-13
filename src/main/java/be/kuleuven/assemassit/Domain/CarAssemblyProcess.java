package be.kuleuven.assemassit.Domain;

import java.util.List;

public class CarAssemblyProcess {
  private List<AssemblyTask> assemblyTasks;
  private CarOrder carOrder;

  public CarAssemblyProcess(CarOrder carOrder, List<AssemblyTask> assemblyTasks) {
    this.carOrder = carOrder;
    this.assemblyTasks = List.copyOf(assemblyTasks);
  }

  public List<AssemblyTask> getAssemblyTasks(){
    return assemblyTasks;
  }
}
