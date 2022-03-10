package Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AssemblyLine {

	private List<WorkPost> workPosts;

	public AssemblyLine() {
		this.workPosts = new ArrayList<WorkPost>();
	}

	public AssemblyLine(List<WorkPost> workPosts) {
		this.workPosts = new ArrayList<WorkPost>(workPosts);
	}

	public List<WorkPost> getWorkPosts() {
		return new ArrayList<WorkPost>(this.workPosts);
	}

	public List<AssemblyTask> givePendingAssemblyTasksFromWorkPost(int workPostId) {
		WorkPost workPost = findWorkPost(workPostId);
		return workPost.givePendingAssemblyTasks();
	}

	public void completeAssemblyTask(int workPostId, int assemblyTaskId) {
		WorkPost workPost = findWorkPost(workPostId);
		workPost.completeAssemblyTask(assemblyTaskId);
	}

	private WorkPost findWorkPost(int id) {
		Optional<WorkPost> workPost = workPosts.stream()
				.filter(wp -> wp.getId() == id)
				.findFirst();

		if (!workPost.isPresent())
			throw new IllegalArgumentException("Workpost not found");

		return workPost.get();
	}

	//TODO
	public boolean canMove() {
		return false;
	}

	//TODO
	public void move(int minutes) {
	}

	//TODO
	public String getStatus(){
		return canMove() ? "movable" : "not movable";
	}

}
