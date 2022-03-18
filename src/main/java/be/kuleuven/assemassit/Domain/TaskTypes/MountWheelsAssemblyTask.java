package be.kuleuven.assemassit.Domain.TaskTypes;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.Wheel;

import java.util.ArrayList;
import java.util.List;

/**
 * @mutable
 */
public class MountWheelsAssemblyTask extends AssemblyTask {
  /**
   * @invar | wheel != null
   */
  private final Wheel wheel;
  private final AssemblyTaskType assemblyTaskType = AssemblyTaskType.MOUNT_WHEELS;

  /**
   * @param wheel
   * @throws IllegalArgumentException wheel is null | wheel == null
   * @mutates | this
   * @post | this.wheel = wheel
   */
  public MountWheelsAssemblyTask(Wheel wheel) {
    super("Mount wheels");
    if (wheel == null)
      throw new IllegalArgumentException("Wheel can not be null");
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
