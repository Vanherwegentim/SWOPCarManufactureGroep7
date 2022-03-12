package be.kuleuven.assemassit.Domain;

import java.util.*;

public class AssemblyLine {

  private WorkPost carBodyPost;
  private WorkPost drivetrainPost;
  private WorkPost accessoriesPost;
	private Queue<CarAssemblyProcess> carAssemblyProcesses;
	private List<CarAssemblyProcess> finishedCars;


	//TODO Hoe worden carAssemblyProcesses nu gezet? want das nodig voor de move() methode
	public AssemblyLine() {
	  //TODO: is this the way to go?
	  this.carBodyPost = new WorkPost(0, Arrays.asList(AssemblyTaskType.ASSEMBLE_CAR_BODY, AssemblyTaskType.PAINT_CAR));
	  this.drivetrainPost = new WorkPost(1, Arrays.asList(AssemblyTaskType.INSERT_ENGINE,AssemblyTaskType.INSERT_GEARBOX));
	  this.accessoriesPost = new WorkPost(2, Arrays.asList(AssemblyTaskType.INSTALL_AIRCO, AssemblyTaskType.INSTALL_SEATS,AssemblyTaskType.MOUNT_WHEELS));

	  this.carAssemblyProcesses = new ArrayDeque<>();
	}

	public void addCarAssemblyProcess(CarAssemblyProcess carAssemblyProcess){
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
  //ik heb hier de id weg gedaan omdat alleen de active task kan gecomplete worden
  // en we weten altijd welke dat is omdat die in workpost zelf staat
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

  //TODO klopt dit aangezien we hier nu gewoon alle mogelijke tasks geven,
  // niet de tasks van de carAssemblyProcesses die nu bezig zijn en gaan bezig zijn
	public Map<String, AssemblyTask> giveTasksOverview() {
    Map<String, AssemblyTask> workPostPairs = new HashMap<>();

    workPostPairs.put("Car Body Post", carBodyPost.getActiveAssemblyTask());
    workPostPairs.put("Drivetrain Post", drivetrainPost.getActiveAssemblyTask());
    workPostPairs.put("Accessories Post", accessoriesPost.getActiveAssemblyTask());


    return workPostPairs;
  }

  //TODO check if this is a correct/good implementation
  public Map<String, AssemblyTask> giveFutureTasksOverview() throws CloneNotSupportedException{
    AssemblyLine dummyAssemblyLine = (AssemblyLine) this.clone();
    dummyAssemblyLine.move(60);
    Map<String, AssemblyTask> workPostPairs = new HashMap<>();

    workPostPairs.put("Car Body Post", dummyAssemblyLine.carBodyPost.getActiveAssemblyTask());
    workPostPairs.put("Drivetrain Post", dummyAssemblyLine.drivetrainPost.getActiveAssemblyTask());
    workPostPairs.put("Accessories Post", dummyAssemblyLine.accessoriesPost.getActiveAssemblyTask());


    return workPostPairs;
  }

  private WorkPost findWorkPost(int id) {
	  //TODO: refactor?
	  if (carBodyPost.getId() == id) return carBodyPost;
	  if (drivetrainPost.getId() == id) return drivetrainPost;
	  if (accessoriesPost.getId() == id) return accessoriesPost;

	  throw new IllegalArgumentException("Workpost not found");
  }

	/*private WorkPost findWorkPost2(int id) {
		Optional<WorkPost> workPost = workPosts.stream()
				.filter(wp -> wp.getId() == id)
				.findFirst();

		if (!workPost.isPresent())
			throw new IllegalArgumentException("Workpost not found");

		return workPost.get();
	}*/

  public boolean canMove(){
    //TODO refactor?
    // is equals null de juiste manier om te checken of er niks in activeAssemblyTask steekt?
//    if(!carBodyPost.givePendingAssemblyTasks().isEmpty()&&carBodyPost.getActiveAssemblyTask().equals(null)){
//      return false;
//    }
//    if(!accessoriesPost.givePendingAssemblyTasks().isEmpty()&&carBodyPost.getActiveAssemblyTask().equals(null)){
//      return false;
//    }
//    if(!drivetrainPost.givePendingAssemblyTasks().isEmpty()&&carBodyPost.getActiveAssemblyTask().equals(null)){
//      return false;
//    }
//    return true;
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
  //TODO what do we do with the car in the last Workpost?
  public void move(int minutes){
    if(canMove()){

        //Remove the car from the third post
        finishedCars.add(accessoriesPost.getCarAssemblyProcess());
        //Give the third post the car of the second post
        accessoriesPost.addProcessToWorkPost(drivetrainPost.getCarAssemblyProcess());
        //Give the second post the car of the first post
        drivetrainPost.addProcessToWorkPost(carBodyPost.getCarAssemblyProcess());
        //Give the first post a car from the queue;
        carBodyPost.addProcessToWorkPost(carAssemblyProcesses.poll());

    }
    else{
      throw new IllegalStateException("Can not move the assembly line");
    }
  }
}
