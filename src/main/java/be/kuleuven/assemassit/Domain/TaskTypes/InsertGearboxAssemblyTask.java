package be.kuleuven.assemassit.Domain.TaskTypes;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.Body;
import be.kuleuven.assemassit.Domain.Enums.Gearbox;

public class InsertGearboxAssemblyTask extends AssemblyTask {
  private Gearbox gearbox;
  private AssemblyTaskType assemblyTaskType = AssemblyTaskType.INSERT_GEARBOX;

  public InsertGearboxAssemblyTask(String name, Gearbox gearbox ) {
    super(name);
    this.gearbox = gearbox;
  }

  @Override
  public AssemblyTaskType getAssemblyTaskType() {
    return this.assemblyTaskType;
  }

  //Pattern matching not working here for some reason
  @Override
  public boolean equals(Object o){
    if(o instanceof InsertGearboxAssemblyTask){
      return this.gearbox == ((InsertGearboxAssemblyTask) o).gearbox;
    }
    return false;
  }
}
