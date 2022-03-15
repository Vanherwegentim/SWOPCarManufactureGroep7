package be.kuleuven.assemassit.Domain.TaskTypes;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.Body;
import be.kuleuven.assemassit.Domain.Enums.Engine;

public class InsertEngineAssemblyTask extends AssemblyTask {
  private Engine engine;
  private AssemblyTaskType assemblyTaskType = AssemblyTaskType.INSERT_ENGINE;

  public InsertEngineAssemblyTask(String name, Engine engine) {
    super(name);
    this.engine = engine;
  }

  @Override
  public AssemblyTaskType getAssemblyTaskType() {
    return this.assemblyTaskType;
  }

  //Pattern matching not working here for some reason
  @Override
  public boolean equals(Object o){
    if(o instanceof InsertEngineAssemblyTask){
      return this.getId() == ((InsertEngineAssemblyTask) o).getId();
    }
    return false;
  }
}
