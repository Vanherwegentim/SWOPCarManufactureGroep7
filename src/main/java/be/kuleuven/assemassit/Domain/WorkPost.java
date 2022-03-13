package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WorkPost {
  private int id;
  private List<AssemblyTaskType> assemblyTaskTypes;
  private AssemblyTask activeAssemblyTask;
  private CarAssemblyProcess carAssemblyProcess;

  public WorkPost(int id, List<AssemblyTaskType> assemblyTaskTypes) {
    this.id = id;
    this.assemblyTaskTypes = assemblyTaskTypes;
  }

  public int getId() {
    return this.id;
  }

  public void addProcessToWorkPost(CarAssemblyProcess carAssemblyProcess){
      this.carAssemblyProcess = carAssemblyProcess;
  }

  public CarAssemblyProcess getCarAssemblyProcess() {
    return carAssemblyProcess;
  }

  public CarAssemblyProcess removeProcessFromWorkPost(){
    CarAssemblyProcess temporaryProcess = this.carAssemblyProcess;
    this.carAssemblyProcess = null;
    return temporaryProcess;
  }

  public AssemblyTask getActiveAssemblyTask() {
    return this.activeAssemblyTask;
  }

  public void setActiveAssemblyTask(int assemblyTaskId) {
    this.activeAssemblyTask = findAssemblyTask(assemblyTaskId);
    if(activeAssemblyTask == null){
      throw new IllegalArgumentException("There is no Assembly Task with that id.");
    }

  }

  public List<AssemblyTask> getAllAssemblyTasks(){
    return carAssemblyProcess.getAssemblyTasks().stream().filter(e1 -> assemblyTaskTypes.contains(e1.getAssemblyTaskType())).collect(Collectors.toList());
  }

  public List<AssemblyTask> givePendingAssemblyTasks() {
    List<AssemblyTask> tasks = carAssemblyProcess.getAssemblyTasks();
    tasks = (List<AssemblyTask>) tasks.stream().filter(e1 -> assemblyTaskTypes.contains(e1.getAssemblyTaskType()));
    return tasks.stream()
      .filter(at -> at.getPending())
      .collect(Collectors.toList());
  }

  public void completeAssemblyTask() {
    activeAssemblyTask.complete();
    activeAssemblyTask = null;
  }

  private AssemblyTask findAssemblyTask(int id) {
    Optional<AssemblyTask> assemblyTask = carAssemblyProcess.getAssemblyTasks().stream()
      .filter(at -> at.getId() == id)
      .findFirst();

    if (!assemblyTask.isPresent())
      throw new IllegalArgumentException("Workpost not found");

    return assemblyTask.get();
  }
}
