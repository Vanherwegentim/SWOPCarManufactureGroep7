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
   * @param wheel the wheel car option
   * @throws IllegalArgumentException wheel is null | wheel == null
   * @mutates | this
   * @post | this.getWheel() == wheel
   */
  public MountWheelsAssemblyTask(Wheel wheel) {
    super("Mount wheels");
    if (wheel == null)
      throw new IllegalArgumentException("Wheel can not be null");
    this.wheel = wheel;
  }

  public Wheel getWheel() {
    return wheel;
  }

  @Override
  public List<String> getActions() {
    List<String> actions = new ArrayList<>();
    actions.add("Installing the " + getWheel() + " wheels");
    return actions;
  }

  @Override
  public AssemblyTaskType getAssemblyTaskType() {
    return this.assemblyTaskType;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof MountWheelsAssemblyTask) {
      MountWheelsAssemblyTask assemblyTask = (MountWheelsAssemblyTask) o;
      return assemblyTask.getId() == this.getId();
    }
    return false;
  }
}
