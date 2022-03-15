package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.WorkPostType;

import java.util.*;

public class AssemblyLine {

  private WorkPost carBodyPost;
  private WorkPost drivetrainPost;
  private WorkPost accessoriesPost;
	private Queue<CarAssemblyProcess> carAssemblyProcesses;
	private List<CarAssemblyProcess> finishedCars;


	public AssemblyLine() {
	  this.carBodyPost = new WorkPost(0, Arrays.asList(AssemblyTaskType.ASSEMBLE_CAR_BODY, AssemblyTaskType.PAINT_CAR), WorkPostType.CAR_BODY_POST, 60);
	  this.drivetrainPost = new WorkPost(1, Arrays.asList(AssemblyTaskType.INSERT_ENGINE,AssemblyTaskType.INSERT_GEARBOX), WorkPostType.DRIVETRAIN_POST, 60);
	  this.accessoriesPost = new WorkPost(2, Arrays.asList(AssemblyTaskType.INSTALL_AIRCO, AssemblyTaskType.INSTALL_SEATS), WorkPostType.ACCESSORIES_POST, 60);

	  this.carAssemblyProcesses = new ArrayDeque<>();

	}

	public void addCarAssemblyProcess(CarAssemblyProcess carAssemblyProcess) {
	  carAssemblyProcesses.add(carAssemblyProcess);
  }

	public WorkPost getCarBodyPost() {
	  return this.carBodyPost;
  }

  public WorkPost getDrivetrainPost() {
	  return this.drivetrainPost;
  }

  public WorkPost getAccessoriesPost() {
	  return this.accessoriesPost;
  }

	public List<AssemblyTask> givePendingAssemblyTasksFromWorkPost(int workPostId) {
		WorkPost workPost = findWorkPost(workPostId);
		return workPost.givePendingAssemblyTasks();
	}

	public void completeAssemblyTask(int workPostId) {
		WorkPost workPost = findWorkPost(workPostId);
		workPost.completeAssemblyTask();
	}

	public Map<String, AssemblyTask> giveStatus() {
    Map<String, AssemblyTask> workPostStatusses = new HashMap<>();

    workPostStatusses.put("Car Body Post", carBodyPost.getActiveAssemblyTask());
    workPostStatusses.put("Drivetrain Post", drivetrainPost.getActiveAssemblyTask());
    workPostStatusses.put("Accessories Post", accessoriesPost.getActiveAssemblyTask());

    return workPostStatusses;
  }

	public Map<String, List<AssemblyTask>> giveTasksOverview() {
    Map<String, List<AssemblyTask>> workPostPairs = new HashMap<>();

    workPostPairs.put("Car Body Post", carBodyPost.getAllAssemblyTasks());
    workPostPairs.put("Drivetrain Post", drivetrainPost.getAllAssemblyTasks());
    workPostPairs.put("Accessories Post", accessoriesPost.getAllAssemblyTasks());


    return workPostPairs;
  }

  //TODO check if this is a correct/good implementation
  public Map<String, List<AssemblyTask>> giveFutureTasksOverview() throws CloneNotSupportedException{
    AssemblyLine dummyAssemblyLine = (AssemblyLine) this.clone();
    dummyAssemblyLine.moveWithoutRestrictions();
    Map<String, List<AssemblyTask>> workPostPairs = new HashMap<>();

    workPostPairs.put("Car Body Post", dummyAssemblyLine.carBodyPost.getAllAssemblyTasks());
    workPostPairs.put("Drivetrain Post", dummyAssemblyLine.drivetrainPost.getAllAssemblyTasks());
    workPostPairs.put("Accessories Post", dummyAssemblyLine.accessoriesPost.getAllAssemblyTasks());


    return workPostPairs;
  }

  private WorkPost findWorkPost(int id) {
	  //TODO: refactor?
	  if (carBodyPost.getId() == id) return carBodyPost;
	  if (drivetrainPost.getId() == id) return drivetrainPost;
	  if (accessoriesPost.getId() == id) return accessoriesPost;

	  throw new IllegalArgumentException("Workpost not found");
  }

  public boolean canMove(){
    //TODO refactor?
    if(!(carBodyPost.givePendingAssemblyTasks().isEmpty() || carBodyPost.getCarAssemblyProcess() == null)){
      return false;
    }
    if(!(accessoriesPost.givePendingAssemblyTasks().isEmpty() || accessoriesPost.getCarAssemblyProcess() == null)){
      return false;
    }
    if(!(drivetrainPost.givePendingAssemblyTasks().isEmpty() || drivetrainPost.getCarAssemblyProcess() == null)){
      return false;
    }
    return true;
  }
  public String move(int minutes){
	  //TODO refactor to observer pattern?
    if(canMove()){
        //Give every task that was done, the time given.
        for(AssemblyTask assemblyTask: carBodyPost.getAllAssemblyTasks()){
          assemblyTask.setCompletionTime(minutes);
        }

        for(AssemblyTask assemblyTask: drivetrainPost.getAllAssemblyTasks()){
          assemblyTask.setCompletionTime(minutes);
        }

        for(AssemblyTask assemblyTask: accessoriesPost.getAllAssemblyTasks()){
          assemblyTask.setCompletionTime(minutes);
        }

        //Remove the car from the third post
        finishedCars.add(accessoriesPost.getCarAssemblyProcess());
        //Give the third post the car of the second post
        accessoriesPost.addProcessToWorkPost(drivetrainPost.getCarAssemblyProcess());
        //Give the second post the car of the first post
        drivetrainPost.addProcessToWorkPost(carBodyPost.getCarAssemblyProcess());
        //Give the first post a car from the queue;
        carBodyPost.addProcessToWorkPost(carAssemblyProcesses.poll());
        return "";
    }
    else{
        String s = "These workposts are stopping you from moving forward:";
        if(!(carBodyPost.givePendingAssemblyTasks().isEmpty() || carBodyPost.getCarAssemblyProcess() == null)){
          s = s + "Carbody WorkPost, ";
        }
        if(!(drivetrainPost.givePendingAssemblyTasks().isEmpty() || drivetrainPost.getCarAssemblyProcess() == null)){
          s = s + "Drivetrain Workpost, ";
        }
        if(!(accessoriesPost.givePendingAssemblyTasks().isEmpty() || accessoriesPost.getCarAssemblyProcess() == null)){
          s = s + "Accessories Workpost, ";
        }
        return s;
    }
  }

  private void moveWithoutRestrictions(){
      //Remove the car from the third post
      finishedCars.add(accessoriesPost.getCarAssemblyProcess());
      //Give the third post the car of the second post
      accessoriesPost.addProcessToWorkPost(drivetrainPost.getCarAssemblyProcess());
      //Give the second post the car of the first post
      drivetrainPost.addProcessToWorkPost(carBodyPost.getCarAssemblyProcess());
      //Give the first post a car from the queue;
      carBodyPost.addProcessToWorkPost(carAssemblyProcesses.poll());
  }

  private int calculateEstimatedCompletionTime(CarAssemblyProcess carAssemblyProcess) {
    int minutes = carBodyPost.getExpectedWorkPostDurationInMinutes();
    minutes += drivetrainPost.getExpectedWorkPostDurationInMinutes();
    minutes += accessoriesPost.getExpectedWorkPostDurationInMinutes();

    minutes += this.carAssemblyProcesses.stream().map(p ->{
      int result = 0;

      // take the max of getExpectedWorkPostDurationInMinutes()
      if (carBodyPost.canPerformTasksForProcess(p))
        result += carBodyPost.getExpectedWorkPostDurationInMinutes();
      if (drivetrainPost.canPerformTasksForProcess(p))
        result += drivetrainPost.getExpectedWorkPostDurationInMinutes();
      if (accessoriesPost.canPerformTasksForProcess(p))
        result += accessoriesPost.getExpectedWorkPostDurationInMinutes();

      return result;
    }).reduce(0, (subtotal, elem) -> subtotal+elem);

    minutes += carBodyPost.remainingTimeInMinutes();

    return minutes;
  }
}
