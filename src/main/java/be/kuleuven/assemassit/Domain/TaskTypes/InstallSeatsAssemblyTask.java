package be.kuleuven.assemassit.Domain.TaskTypes;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.Seat;

import java.util.ArrayList;
import java.util.List;

public class InstallSeatsAssemblyTask extends AssemblyTask {
  private Seat seat;
  private AssemblyTaskType assemblyTaskType = AssemblyTaskType.INSTALL_SEATS;

  public InstallSeatsAssemblyTask(Seat seat) {
    super("Install seats");
    this.seat = seat;
  }

  @Override
  public List<String> getActions() {
    List<String> actions = new ArrayList<>();
    actions.add("Installing the " + seat + " seats");
    return actions;
  }

  @Override
  public AssemblyTaskType getAssemblyTaskType() {
    return this.assemblyTaskType;
  }

  //Pattern matching not working here for some reason

  @Override
  public boolean equals(Object o) {
    if (o instanceof InstallSeatsAssemblyTask) {
      return this.seat == ((InstallSeatsAssemblyTask) o).seat;
    }
    return false;
  }
}
