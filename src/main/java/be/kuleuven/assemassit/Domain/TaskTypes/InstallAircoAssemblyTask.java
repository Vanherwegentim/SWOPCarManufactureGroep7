package be.kuleuven.assemassit.Domain.TaskTypes;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Enums.Airco;
import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;

import java.util.ArrayList;
import java.util.List;

/**
 * @mutable
 */
public class InstallAircoAssemblyTask extends AssemblyTask {
  /**
   * @invar | airco != null
   */
  private final Airco airco;
  private final AssemblyTaskType assemblyTaskType = AssemblyTaskType.INSTALL_AIRCO;

  /**
   * @param airco
   * @throws IllegalArgumentException airco is null | airco == null
   * @mutates | this
   * @post | this.airco = airco
   */
  public InstallAircoAssemblyTask(Airco airco) {
    super("Install airco");
    if (airco == null)
      throw new IllegalArgumentException("Airco can not be null");
    this.airco = airco;
  }

  @Override
  public List<String> getActions() {
    List<String> actions = new ArrayList<>();
    actions.add("Installing the " + airco + " airco");
    return actions;
  }

  @Override
  public AssemblyTaskType getAssemblyTaskType() {
    return this.assemblyTaskType;
  }

  //Pattern matching not working here for some reason
  @Override
  public boolean equals(Object o) {
    if (o instanceof InstallAircoAssemblyTask) {
      return this.airco == ((InstallAircoAssemblyTask) o).airco;
    }
    return false;
  }
}
