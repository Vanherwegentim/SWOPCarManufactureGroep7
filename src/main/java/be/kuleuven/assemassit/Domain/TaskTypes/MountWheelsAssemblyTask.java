package be.kuleuven.assemassit.Domain.TaskTypes;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.Body;
import be.kuleuven.assemassit.Domain.Enums.Wheel;

public class MountWheelsAssemblyTask extends AssemblyTask {
  private Wheel wheel;
  private AssemblyTaskType assemblyTaskType = AssemblyTaskType.MOUNT_WHEELS;

  public MountWheelsAssemblyTask(String name, Wheel wheel) {
    super(name);
    this.wheel = wheel;
  }

  @Override
  public AssemblyTaskType getAssemblyTaskType() {
    return this.assemblyTaskType;
  }

  //Pattern matching not working here for some reason
  @Override
  public boolean equals(Object o){
    if(o instanceof MountWheelsAssemblyTask){
      return this.wheel == ((MountWheelsAssemblyTask) o).wheel;
    }
    return false;
  }
}
