package be.kuleuven.assemassit.Domain.TaskTypes;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.Spoiler;

import java.util.ArrayList;
import java.util.List;

public class InstallSpoilerAssemblyTask extends AssemblyTask {

  private final Spoiler spoiler;
  private final AssemblyTaskType assemblyTaskType = AssemblyTaskType.INSTALL_SPOILER;

  /**
   * @param spoiler the name of the assembly task
   * @throws IllegalArgumentException spoiler can not be null | spoiler == null
   * @post | this.getSpoiler().equals(spoiler)
   * @mutates | this
   */
  public InstallSpoilerAssemblyTask(Spoiler spoiler) {
    super("Install spoiler");
    if (spoiler == null)
      throw new IllegalArgumentException("Spoiler can not be null");
    this.spoiler = spoiler;
  }

  public Spoiler getSpoiler() {
    return spoiler;
  }

  @Override
  public List<String> getActions() {
    List<String> actions = new ArrayList<>();
    actions.add("Installing the " + getSpoiler() + " spoiler");
    return actions;
  }

  @Override
  public AssemblyTaskType getAssemblyTaskType() {
    return this.assemblyTaskType;
  }
}
