package be.kuleuven.assemassit.Domain.TaskTypes;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.Body;
import be.kuleuven.assemassit.Domain.Enums.Seat;

public class InstallSeatsAssemblyTask extends AssemblyTask {
  private Seat seat;
  private AssemblyTaskType assemblyTaskType = AssemblyTaskType.INSTALL_SEATS;

  public InstallSeatsAssemblyTask(String name, Seat seat) {
    super(name);
    this.seat = seat;
  }

  @Override
  public AssemblyTaskType getAssemblyTaskType() {
    return this.assemblyTaskType;
  }
}
