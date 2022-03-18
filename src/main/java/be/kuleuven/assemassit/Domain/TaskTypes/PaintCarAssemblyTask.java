package be.kuleuven.assemassit.Domain.TaskTypes;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * @mutable
 */
public class PaintCarAssemblyTask extends AssemblyTask {
  /**
   * @invar | color != null
   */
  private Color color;
  private AssemblyTaskType assemblyTaskType = AssemblyTaskType.PAINT_CAR;

  /**
   * @param color
   * @throws IllegalArgumentException color is null | color == null
   * @mutates | this
   * @post | this.color = color
   */
  public PaintCarAssemblyTask(Color color) {
    super("Paint car");
    if (color == null)
      throw new IllegalArgumentException("Color can not be null");
    this.color = color;
  }

  @Override
  public List<String> getActions() {
    List<String> actions = new ArrayList<>();
    actions.add("Painting the car " + color);
    return actions;
  }

  @Override
  public AssemblyTaskType getAssemblyTaskType() {
    return this.assemblyTaskType;
  }

  //Pattern matching not working here for some reason
  @Override
  public boolean equals(Object o) {
    if (o instanceof PaintCarAssemblyTask) {
      return this.color == ((PaintCarAssemblyTask) o).color;
    }
    return false;
  }
}
