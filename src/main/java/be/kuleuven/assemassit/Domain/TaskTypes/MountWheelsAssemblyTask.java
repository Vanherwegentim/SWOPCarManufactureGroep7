package be.kuleuven.assemassit.Domain.TaskTypes;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.Wheel;

import java.util.ArrayList;
import java.util.List;

public class MountWheelsAssemblyTask extends AssemblyTask {
  private Wheel wheel;
  private AssemblyTaskType assemblyTaskType = AssemblyTaskType.MOUNT_WHEELS;

  public MountWheelsAssemblyTask(Wheel wheel) {
    super("Mount wheels");
    this.wheel = wheel;
  }

  @Override
  public List<String> getActions() {
    List<String> actions = new ArrayList<>();
    actions.add("Installing the " + wheel + " wheels");
    return actions;
  }

  @Override
  public AssemblyTaskType getAssemblyTaskType() {
    return this.assemblyTaskType;
  }

  //Pattern matching not working here for some reason
  @Override
  public boolean equals(Object o) {
    if (o instanceof MountWheelsAssemblyTask) {
      return this.wheel == ((MountWheelsAssemblyTask) o).wheel;
    }
    return false;
  }
}
