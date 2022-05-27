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
   * @param airco the airco car option
   * @throws IllegalArgumentException airco is null | airco == null
   * @mutates | this
   * @post | this.getAirco() == airco
   */
  public InstallAircoAssemblyTask(Airco airco) {
    super("Install airco");
    if (airco == null)
      throw new IllegalArgumentException("Airco can not be null");
    this.airco = airco;
  }

  public Airco getAirco() {
    return airco;
  }

  @Override
  public List<String> getActions() {
    List<String> actions = new ArrayList<>();
    actions.add("Installing the " + getAirco() + " airco");
    return actions;
  }

  @Override
  public AssemblyTaskType getAssemblyTaskType() {
    return this.assemblyTaskType;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof InstallAircoAssemblyTask) {
      InstallAircoAssemblyTask assemblyTask = (InstallAircoAssemblyTask) o;
      return assemblyTask.getId() == this.getId();
    }
    return false;
  }
}
