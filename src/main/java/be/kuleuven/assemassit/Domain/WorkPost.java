package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.WorkPostType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @invar | getAssemblyTaskTypes() != null
 * @invar | getCarAssemblyProcess().getAssemblyTasks().contains(getActiveAssemblyTask())
 * @invar | getWorkPostType() != null
 * @invar | getExpectedWorkPostDurationInMinutes() >= 0
 */
public class WorkPost {
  private final int id;
  /**
   * @invar | assemblyTaskTypes != null
   * @invar | carAssemblyProcess.getAssemblyTasks().contains(getActiveAssemblyTask())
   * @invar | workPostType != null
   * @invar | expectedWorkPostDurationInMinutes >= 0
   * @representationObject
   * @representationObjects
   */
  private final List<AssemblyTaskType> assemblyTaskTypes;
  private final WorkPostType workPostType;
  private final int expectedWorkPostDurationInMinutes;
  /**
   * @representationObject
   */
  private AssemblyTask activeAssemblyTask;
  /**
   * @representationObject
   */
  private CarAssemblyProcess carAssemblyProcess;

  /**
   * @param id
   * @param assemblyTaskTypes                 the list of task types that can be handled by the work post
   * @param workPostType                      the type of the work post
   * @param expectedWorkPostDurationInMinutes the total duration of the work post to complete all possible tasks from process in minutes
   * @post | this.id = id
   * @post | this.assemblyTaskTypes != null
   * @post | this.assemblyTaskTypes.size().equals(assemblyTaskTypes.size())
   * @post | this.workPostType = workPostType
   * @post | this.expectedWorkPostDurationInMinutes = expectedWorkPostDurationInMinutes
   * @mutates | this
   */
  public WorkPost(int id, List<AssemblyTaskType> assemblyTaskTypes, WorkPostType workPostType, int expectedWorkPostDurationInMinutes) {
    this.id = id;
    this.assemblyTaskTypes = new ArrayList<>(assemblyTaskTypes);
    this.workPostType = workPostType;
    this.expectedWorkPostDurationInMinutes = expectedWorkPostDurationInMinutes;
  }

  public int getId() {
    return this.id;
  }

  public WorkPostType getWorkPostType() {
    return this.workPostType;
  }

  /**
   * @param carAssemblyProcess
   * @mutates | this
   * @inspects | carAssemblyProcess
   * @post | getCarAssemblyProcess().contains(carAssemblyProcess)
   */
  public void addProcessToWorkPost(CarAssemblyProcess carAssemblyProcess) {
    this.carAssemblyProcess = carAssemblyProcess;
  }

  /**
   * @mutates | this
   * @post | getCarAssemblyProcess() == null
   */
  public void removeCarAssemblyProcess() {
    this.carAssemblyProcess = null;
  }

  /**
   * @return the list of assembly task types
   * @creates | result
   */
  public List<AssemblyTaskType> getAssemblyTaskTypes() {
    return new ArrayList<>(assemblyTaskTypes);
  }

  public CarAssemblyProcess getCarAssemblyProcess() {
    return carAssemblyProcess;
  }

  public int getExpectedWorkPostDurationInMinutes() {
    if (getCarAssemblyProcess() != null)
      return getCarAssemblyProcess().getCarOrder().getCar().getCarModel().getWorkPostDuration();
    else
      return 60;
  }

  /**
   * Removes the process from the work post and returns it
   *
   * @return the car assembly processed that is removed
   * @post | getCarAssemblyProcess() == null
   * @post | result == old(getCarAssemblyProcess())
   * @mutates | this
   */
  public CarAssemblyProcess removeProcessFromWorkPost() {
    CarAssemblyProcess temporaryProcess = this.carAssemblyProcess;
    this.carAssemblyProcess = null;
    return temporaryProcess;
  }

  public AssemblyTask getActiveAssemblyTask() {
    return this.activeAssemblyTask;
  }

  /**
   * @param assemblyTaskId
   * @throws IllegalArgumentException assembly task ID lower than 0 | assemblyTaskId < 0
   * @mutates | this
   */
  public void setActiveAssemblyTask(int assemblyTaskId) {
    if (assemblyTaskId < 0)
      throw new IllegalArgumentException("Assembly task ID can not be lower than 0");
    this.activeAssemblyTask = findAssemblyTask(assemblyTaskId);
    if (activeAssemblyTask == null) { // this is actually already checked and thrown in findAssemblyTask
      throw new IllegalArgumentException("There is no Assembly Task with that id.");
    }
  }

  /**
   * @post | getActiveAssemblyTask() == null
   * @mutates | this
   */
  public void removeActiveAssemblyTask() {
    this.activeAssemblyTask = null;
  }

  /**
   * Gives the list of assembly tasks from the work post that can be performed by the work post
   *
   * @return list of assembly tasks that can be performed by the work post
   * @creates | result
   * @post there should be no tasks in the result that can not be performed by the work post
   * | result.stream().filter(task -> !getAssemblyTaskTypes().contains(task.getAssemblyTaskType())).size() == 0
   */
  public List<AssemblyTask> getWorkPostAssemblyTasks() {
    if (carAssemblyProcess == null)
      return new ArrayList<>();

    return carAssemblyProcess.getAssemblyTasks().stream().filter(task -> assemblyTaskTypes.contains(task.getAssemblyTaskType())).toList();
  }

  /**
   * @return the list of pending assembly tasks that can be performed by the work post
   * @creates | result
   * @inspects | this
   * @post there should be no tasks in the result that can not be performed by the work post
   * | result.stream().filter(task -> !getAssemblyTaskTypes().contains(task.getAssemblyTaskType())).toList().size() == 0
   * @post all tasks in the result should be pending
   * | result.stream().filter(task -> !task.getPending()).toList().size() == 0
   */
  public List<AssemblyTask> givePendingAssemblyTasks() {

    if (carAssemblyProcess == null)
      return new ArrayList<>();

    List<AssemblyTask> tasks = carAssemblyProcess.getAssemblyTasks();
    List<AssemblyTask> filteredTasks = tasks.stream().filter(task -> assemblyTaskTypes.contains(task.getAssemblyTaskType())).toList();
    return filteredTasks.stream().filter(AssemblyTask::getPending).toList();
  }

  public List<AssemblyTask> giveFinishedAssemblyTasks() {

    if (carAssemblyProcess == null)
      return new ArrayList<>();

    List<AssemblyTask> tasks = carAssemblyProcess.getAssemblyTasks();
    List<AssemblyTask> filteredTasks = tasks.stream().filter(task -> assemblyTaskTypes.contains(task.getAssemblyTaskType())).toList();
    return filteredTasks.stream().filter(at -> !at.getPending()).toList();
  }

  /**
   * @throws IllegalStateException assembly task is null | getActiveAssemblyTask() == null
   * @mutates | this
   * @post | getActiveAssemblyTask() == null
   */
  public void completeAssemblyTask(int duration, LocalDateTime completionTime) {
    if (activeAssemblyTask == null)
      throw new IllegalStateException("There is no active assembly task in this work post");
    activeAssemblyTask.complete();
    activeAssemblyTask.setDuration(duration);
    activeAssemblyTask = null;
  }

  public int remainingTimeInMinutes() {
    List<AssemblyTask> assemblyTasksFromWorkPost = carAssemblyProcess
      .getAssemblyTasks()
      .stream()
      .filter(p -> assemblyTaskTypes.contains(p.getAssemblyTaskType()))
      .toList();

    if (assemblyTasksFromWorkPost.size() == 0) return 0;

    return (int) Math.floor(expectedWorkPostDurationInMinutes / assemblyTasksFromWorkPost.size() * assemblyTasksFromWorkPost.stream().filter(wp -> wp.getPending()).count());
  }

  /**
   * @param id
   * @return the assembly task
   * @throws IllegalArgumentException ID is lower than 0 | id < 0
   * @throws IllegalArgumentException assembly task not found
   *                                  | Optional<AssemblyTask> assemblyTask == carAssemblyProcess.getAssemblyTasks().stream()
   *                                  .filter(at -> at.getId() == id)
   *                                  .findFirst()
   *                                  .get()
   * @inspects | this
   */
  public AssemblyTask findAssemblyTask(int id) {
    if (id < 0)
      throw new IllegalArgumentException("ID can not be lower than 0");
    Optional<AssemblyTask> assemblyTask = carAssemblyProcess.getAssemblyTasks().stream()
      .filter(at -> at.getId() == id)
      .findFirst();

    if (assemblyTask.isEmpty())
      throw new IllegalArgumentException("AssemblyTask not found");

    return assemblyTask.get();
  }
}
