package be.kuleuven.assemassit.Domain;

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

  // the method receives a carAssemblyProcces with the assemblytasks that are necessary for the carOrder
  // The method then checks if one of tasks that the CarAssemblyProcces has should be done by this workpost
  public void setActiveAssemblyTask(int assemblyTaskId) {
    for(AssemblyTask assemblyTask: carAssemblyProcess.getAssemblyTasks()){
      if(assemblyTask.getId() == assemblyTaskId){
        this.activeAssemblyTask = assemblyTask;
      }
    }
    if(activeAssemblyTask == null){
      throw new IllegalArgumentException("There is no Assembly Task with that id");
    }

  }

  public List<AssemblyTask> givePendingAssemblyTasks() {
    //TODO filter on the tasks that need to be completed by this workpost
    List<AssemblyTask> tasks = carAssemblyProcess.getAssemblyTasks();
    return tasks.stream()
      .filter(at -> at.getPending() == true)
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
