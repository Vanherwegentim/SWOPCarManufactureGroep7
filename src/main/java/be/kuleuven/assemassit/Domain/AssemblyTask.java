package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;

import java.util.List;

public abstract class AssemblyTask {
  /**
   * @invar | name != null && !name.equals("")
   */
  private static int runningId = 0;
  private boolean pending;
  private int id;
  private int completionTime;
  private String name;

  /**
   * @param name the name of the assembly task
   * @throws IllegalArgumentException name can not be null | name == null
   * @throws IllegalArgumentException name can not be empty | name.equals("")
   * @post | this.name.equals(name)
   * @inspects | name
   * @mutates | this
   */
  public AssemblyTask(String name) {
    if (name == null)
      throw new IllegalArgumentException("Name can not be null");
    if (name.equals(""))
      throw new IllegalArgumentException("Name can not be empty");

    this.pending = true;
    this.id = AssemblyTask.runningId++;
    this.name = name;
  }

  public boolean getPending() {
    return this.pending;
  }

  public void setPending(boolean pending) {
    this.pending = pending;
  }

  public abstract List<String> getActions();

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public void setCompletionTime(int minutes) {
    this.completionTime = minutes;
  }

  public abstract AssemblyTaskType getAssemblyTaskType();

  /**
   * The completion time can not be achieved if the task is still pending
   *
   * @return the completion time in minutes
   * @throws IllegalStateException task is still pending | getPending()
   */
  public int completionTime() {
    if (pending)
      throw new IllegalStateException();

    return this.completionTime;
  }

  /**
   * Complete the task, this sets the pending attribute to false
   *
   * @throws IllegalStateException the task is not pending | !getPending()
   * @post | getPending() == false
   */
  public void complete() {
    if (this.pending)
      throw new IllegalStateException("The task is already completed");

    this.pending = false;
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof AssemblyTask task) task.id = this.id;
    return false;
  }
}
