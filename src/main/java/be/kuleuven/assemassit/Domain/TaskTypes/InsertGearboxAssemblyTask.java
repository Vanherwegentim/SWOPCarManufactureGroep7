package be.kuleuven.assemassit.Domain.TaskTypes;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.Gearbox;

import java.util.ArrayList;
import java.util.List;

public class InsertGearboxAssemblyTask extends AssemblyTask {
  private Gearbox gearbox;
  private AssemblyTaskType assemblyTaskType = AssemblyTaskType.INSERT_GEARBOX;

  public InsertGearboxAssemblyTask(Gearbox gearbox) {
    super("Insert gearbox");
    this.gearbox = gearbox;
  }

  @Override
  public List<String> getActions() {
    List<String> actions = new ArrayList<>();
    actions.add("Installing the " + gearbox + " gearbox");
    return actions;
  }

  @Override
  public AssemblyTaskType getAssemblyTaskType() {
    return this.assemblyTaskType;
  }

  //Pattern matching not working here for some reason
  @Override
  public boolean equals(Object o) {
    if (o instanceof InsertGearboxAssemblyTask) {
      return this.gearbox == ((InsertGearboxAssemblyTask) o).gearbox;
    }
    return false;
  }
}
