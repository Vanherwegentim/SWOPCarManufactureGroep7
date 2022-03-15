package be.kuleuven.assemassit.Domain.TaskTypes;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Enums.Airco;
import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.Body;

public class InstallAircoAssemblyTask extends AssemblyTask {
  private Airco airco;
  private AssemblyTaskType assemblyTaskType = AssemblyTaskType.INSTALL_AIRCO;

  public InstallAircoAssemblyTask(String name, Airco airco) {
    super(name);
    this.airco = airco;
  }

  @Override
  public AssemblyTaskType getAssemblyTaskType() {
    return this.assemblyTaskType;
  }

  //Pattern matching not working here for some reason
  @Override
  public boolean equals(Object o){
    if(o instanceof InstallAircoAssemblyTask){
      return this.getId() == ((InstallAircoAssemblyTask) o).getId();
    }
    return false;
  }
}
