package be.kuleuven.assemassit.Domain.TaskTypes;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.Engine;

import java.util.ArrayList;
import java.util.List;

/**
 * @mutable
 */
public class InsertEngineAssemblyTask extends AssemblyTask {
  /**
   * @invar | engine != null
   */
  private final Engine engine;
  private final AssemblyTaskType assemblyTaskType = AssemblyTaskType.INSERT_ENGINE;

  /**
   * @param engine
   * @throws IllegalArgumentException engine can not be null | engine == null
   * @mutates | this
   * @post | this.engine = engine
   */
  public InsertEngineAssemblyTask(Engine engine) {
    super("Insert engine");
    if (engine == null)
      throw new IllegalArgumentException("Engine can not be null");
    this.engine = engine;
  }

  @Override
  public List<String> getActions() {
    List<String> actions = new ArrayList<>();
    actions.add("Inserting a  the " + engine + " engine");
    return actions;
  }

  @Override
  public AssemblyTaskType getAssemblyTaskType() {
    return this.assemblyTaskType;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof InsertEngineAssemblyTask) {
      InsertEngineAssemblyTask assemblyTask = (InsertEngineAssemblyTask) o;
      return assemblyTask.getId() == this.getId();
    }
    return false;
  }
}
