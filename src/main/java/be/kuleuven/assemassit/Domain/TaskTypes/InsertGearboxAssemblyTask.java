package be.kuleuven.assemassit.Domain.TaskTypes;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.Gearbox;

import java.util.ArrayList;
import java.util.List;

/**
 * @mutable
 */
public class InsertGearboxAssemblyTask extends AssemblyTask {
  /**
   * @invaer | gearbox != null
   */
  private final Gearbox gearbox;
  private final AssemblyTaskType assemblyTaskType = AssemblyTaskType.INSERT_GEARBOX;

  /**
   * @param gearbox
   * @throws IllegalArgumentException gearbox can not be null | gearbox == null
   * @mutates | this
   * @post | this.getGearbox() == gearbox
   */
  public InsertGearboxAssemblyTask(Gearbox gearbox) {
    super("Insert gearbox");
    if (gearbox == null)
      throw new IllegalArgumentException("Gearbox can not be null");
    this.gearbox = gearbox;
  }

  public Gearbox getGearbox() {
    return gearbox;
  }

  @Override
  public List<String> getActions() {
    List<String> actions = new ArrayList<>();
    actions.add("Installing the " + getGearbox() + " gearbox");
    return actions;
  }

  @Override
  public AssemblyTaskType getAssemblyTaskType() {
    return this.assemblyTaskType;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof InsertGearboxAssemblyTask) {
      InsertGearboxAssemblyTask assemblyTask = (InsertGearboxAssemblyTask) o;
      return assemblyTask.getId() == this.getId();
    }
    return false;
  }
}
