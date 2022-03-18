package be.kuleuven.assemassit.Domain.TaskTypes;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.Seat;

import java.util.ArrayList;
import java.util.List;

/**
 * @mutable
 */
public class InstallSeatsAssemblyTask extends AssemblyTask {
  /**
   * @invar | seat != null
   */
  private final Seat seat;
  private final AssemblyTaskType assemblyTaskType = AssemblyTaskType.INSTALL_SEATS;

  /**
   * @param seat
   * @throws IllegalArgumentException seat is null | seat == null
   * @mutates | this
   * @post | this.seat = seat
   */
  public InstallSeatsAssemblyTask(Seat seat) {
    super("Install seats");
    if (seat == null)
      throw new IllegalArgumentException("Seat can not be null");
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
