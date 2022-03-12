package be.kuleuven.assemassit.Domain;

import java.util.ArrayList;
import java.util.List;

public class CarAssemblyProcess {
  private List<AssemblyTask> assembyTasks;
  private CarOrder carOrder;

  public CarAssemblyProcess(CarOrder carOrder, List<AssemblyTask> assemblyTasks) {
    this.carOrder = carOrder;
    this.assembyTasks = List.copyOf(assemblyTasks);
  }
}
