package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.WorkPostType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WorkPost {
  private int id;
  private List<AssemblyTaskType> assemblyTaskTypes;
  private AssemblyTask activeAssemblyTask;
  private CarAssemblyProcess carAssemblyProcess;
  private WorkPostType workPostType;
  private int expectedWorkPostDurationInMinutes;

  public WorkPost(int id, List<AssemblyTaskType> assemblyTaskTypes, WorkPostType workPostType, int expectedWorkPostDurationInMinutes) {
    this.id = id;
    this.assemblyTaskTypes = assemblyTaskTypes;
    this.workPostType = workPostType;
    this.expectedWorkPostDurationInMinutes = expectedWorkPostDurationInMinutes;
  }

  public int getId() {
    return this.id;
  }

  public WorkPostType getWorkPostType() {
    return this.workPostType;
  }

  public void addProcessToWorkPost(CarAssemblyProcess carAssemblyProcess) {
    this.carAssemblyProcess = carAssemblyProcess;
  }

  public List<AssemblyTaskType> getAssemblyTaskTypes() {
    return assemblyTaskTypes;
  }

  public CarAssemblyProcess getCarAssemblyProcess() {
    return carAssemblyProcess;
  }

  public int getExpectedWorkPostDurationInMinutes() {
    return this.expectedWorkPostDurationInMinutes;
  }

  public CarAssemblyProcess removeProcessFromWorkPost() {
    CarAssemblyProcess temporaryProcess = this.carAssemblyProcess;
    this.carAssemblyProcess = null;
    return temporaryProcess;
  }

  public AssemblyTask getActiveAssemblyTask() {
    return this.activeAssemblyTask;
  }

  public void setActiveAssemblyTask(int assemblyTaskId) {
    this.activeAssemblyTask = findAssemblyTask(assemblyTaskId);
    if (activeAssemblyTask == null) {
      throw new IllegalArgumentException("There is no Assembly Task with that id.");
    }
  }

  public void removeActiveAssemblyTask() {
    this.activeAssemblyTask = null;
  }

  public List<AssemblyTask> getWorkPostAssemblyTasks() {
    if (carAssemblyProcess == null)
      return new ArrayList<>();

    return carAssemblyProcess.getAssemblyTasks().stream().filter(task -> assemblyTaskTypes.contains(task.getAssemblyTaskType())).toList();
  }

  public List<AssemblyTask> givePendingAssemblyTasks() {

    if (carAssemblyProcess == null)
      return new ArrayList<>();

    List<AssemblyTask> tasks = carAssemblyProcess.getAssemblyTasks();
    List<AssemblyTask> filteredTasks = tasks.stream().filter(task -> assemblyTaskTypes.contains(task.getAssemblyTaskType())).toList();
    return filteredTasks.stream().filter(AssemblyTask::getPending).toList();
  }

  public void completeAssemblyTask() {
    activeAssemblyTask.complete();
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

  public boolean canPerformTasksForProcess(CarAssemblyProcess carAssemblyProcess) {
    return true;
  }

  public AssemblyTask findAssemblyTask(int id) {
    Optional<AssemblyTask> assemblyTask = carAssemblyProcess.getAssemblyTasks().stream()
      .filter(at -> at.getId() == id)
      .findFirst();

    if (!assemblyTask.isPresent())
      throw new IllegalArgumentException("AssemblyTask not found");

    return assemblyTask.get();
  }
}
