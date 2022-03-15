package be.kuleuven.assemassit.Domain.TaskTypes;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.Body;

public class CarBodyAssemblyTask extends AssemblyTask {
  private Body body;
  private AssemblyTaskType assemblyTaskType = AssemblyTaskType.ASSEMBLE_CAR_BODY;

  public CarBodyAssemblyTask(String name, Body body) {
    super(name);
    this.body = body;
  }

  @Override
  public AssemblyTaskType getAssemblyTaskType() {
    return this.assemblyTaskType;
  }
}