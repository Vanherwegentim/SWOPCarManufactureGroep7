package be.kuleuven.assemassit.Domain;

import java.util.*;

public class AssemblyLine {

  private WorkPost carBodyPost;
  private WorkPost drivetrainPost;
  private WorkPost accessoriesPost;
	private List<CarAssemblyProcess> carAssemblyProcesses;


	public AssemblyLine() {
	  //TODO: is this the way to go?
	  this.carBodyPost = new WorkPost(0, Arrays.asList(new AssemblyTask("Assembly car body"), new AssemblyTask("Paint car")));
	  this.drivetrainPost = new WorkPost(1, Arrays.asList(new AssemblyTask("Insert engine"), new AssemblyTask("Insert gearbox")));
	  this.accessoriesPost = new WorkPost(2, Arrays.asList(new AssemblyTask("Install seats"), new AssemblyTask("Install airco"), new AssemblyTask("Mount wheels")));
		this.carAssemblyProcesses = new ArrayList<>();
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

	public void completeAssemblyTask(int workPostId, int assemblyTaskId) {
		WorkPost workPost = findWorkPost(workPostId);
		workPost.completeAssemblyTask(assemblyTaskId);
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

    workPostPairs.put("Car Body Post", carBodyPost.getAssemblyTasks());
    workPostPairs.put("Drivetrain Post", drivetrainPost.getAssemblyTasks());
    workPostPairs.put("Accessories Post", accessoriesPost.getAssemblyTasks());

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
}
