package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.WorkPostType;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
	  // return here the new schedule?
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

  public LocalDateTime giveEstimatedCompletionDateOfLatestProcess(LocalTime openingTime, LocalTime closingTime) {
    // calculate remaining cars for this day (1)
    int remainingCarsForToday =
      ((closingTime.getHour() * 60 + closingTime.getMinute()) - // end time
      giveManufacturingDurationInMinutes() - // time needed to manufacture a car
      (LocalTime.now().getHour() * 60 + LocalTime.now().getMinute()) - // current time
      maxTimeNeededForWorkPostOnLine() + // time needed for the slowest work post
      1) / 60;

    // calculate cars for a whole day (2)
    int amountOfCarsWholeDay =
      ((closingTime.getHour() * 60 + closingTime.getMinute()) - // end time
      giveManufacturingDurationInMinutes() - // time needed to manufacture a car
      (openingTime.getHour() * 60 + openingTime.getMinute()) - // opening time
      maxTimeNeededForWorkPostOnLine() + // time needed for the slowest work post
      1) / 60;

    // Math.ceil(list - (1) / (2)) = days needed
    int daysNeeded = (int)Math.ceil((carAssemblyProcesses.size() - remainingCarsForToday) / amountOfCarsWholeDay);

    // return date of tomorrow + days needed
    return LocalDateTime.now().plusDays(1).plusDays(daysNeeded);
  }

  private int maxTimeNeededForWorkPostOnLine() {
    return giveWorkPostsAsList()
      .stream()
      .mapToInt(WorkPost::getExpectedWorkPostDurationInMinutes)
      .max()
      .orElse(0);
  }

  private int giveManufacturingDurationInMinutes() {
    return giveWorkPostsAsList()
      .stream()
      .mapToInt(WorkPost::getExpectedWorkPostDurationInMinutes)
      .reduce(0, Integer::sum);
  }

  private List<WorkPost> giveWorkPostsAsList() {
    return Arrays.asList(carBodyPost, drivetrainPost, accessoriesPost);
  }

  /*private int calculateEstimatedCompletionTime(CarAssemblyProcess carAssemblyProcess) {

    List<Integer> expectedWorkPostDurations = new ArrayList<>();
    expectedWorkPostDurations.add(carBodyPost.remainingTimeInMinutes());
    expectedWorkPostDurations.add(drivetrainPost.remainingTimeInMinutes());
    expectedWorkPostDurations.add(accessoriesPost.remainingTimeInMinutes());
    int minutes = expectedWorkPostDurations.stream().mapToInt(i -> i).max().orElse(0);

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
  }*/
}
