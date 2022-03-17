package be.kuleuven.assemassit.Domain.TaskTypes;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.Body;

import java.util.ArrayList;
import java.util.List;

public class CarBodyAssemblyTask extends AssemblyTask {
  private Body body;
  private AssemblyTaskType assemblyTaskType = AssemblyTaskType.ASSEMBLE_CAR_BODY;

  public CarBodyAssemblyTask(Body body) {
    super("Assembly car body");
    this.body = body;
  }

  @Override
  public List<String> getActions() {
    List<String> actions = new ArrayList<>();
    actions.add("Installing the " + body + " body");
    return actions;
  }

  @Override
  public AssemblyTaskType getAssemblyTaskType() {
    return this.assemblyTaskType;
  }

  //Pattern matching not working here for some reason

  //TODO pas deze equals aan tot iets decent
  @Override
  public boolean equals(Object o) {
    if (o instanceof CarBodyAssemblyTask) {
      return this.body == ((CarBodyAssemblyTask) o).body;
    }
    return false;
  }
}
