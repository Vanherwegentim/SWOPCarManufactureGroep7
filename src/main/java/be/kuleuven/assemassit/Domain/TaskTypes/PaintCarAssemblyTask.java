package be.kuleuven.assemassit.Domain.TaskTypes;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.Body;
import be.kuleuven.assemassit.Domain.Enums.Color;

public class PaintCarAssemblyTask extends AssemblyTask {
  private Color color;
  private AssemblyTaskType assemblyTaskType = AssemblyTaskType.PAINT_CAR;

  public PaintCarAssemblyTask(String name, Color color) {
    super(name);
    this.color = color;
  }

  @Override
  public AssemblyTaskType getAssemblyTaskType() {
    return this.assemblyTaskType;
  }

  //Pattern matching not working here for some reason
  @Override
  public boolean equals(Object o){
    if(o instanceof PaintCarAssemblyTask){
      return this.color == ((PaintCarAssemblyTask) o).color;
    }
    return false;
  }
}
