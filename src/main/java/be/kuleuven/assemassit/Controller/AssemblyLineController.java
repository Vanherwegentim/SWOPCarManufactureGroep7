package be.kuleuven.assemassit.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.AssemblyTask;

public class AssemblyLineController {

	private AssemblyLine assemblyLine;

	public AssemblyLineController(AssemblyLine assemblyLine) {
		this.assemblyLine = assemblyLine;
	}

	public List<Integer> giveAllWorkPosts() {
		return assemblyLine.getWorkPosts()
				.stream()
				.map(workPost -> workPost.getId())
				.collect(Collectors.toList());
	}

	public List<String> givePendingAssemblyTasks(int postId) {
		// TODO: implement proper error handling
		// TODO: better way to show tasks

		List<String> output = new ArrayList<String>();
		List<AssemblyTask> pendingAssemblyTasks = assemblyLine.givePendingAssemblyTasksFromWorkPost(postId);

		for (AssemblyTask assemblyTask : pendingAssemblyTasks) {
			output.add(Integer.toString(assemblyTask.getId()));

			for (String action : assemblyTask.getActions())
				output.add(action);
		}

		return output;
	}

	public List<String> completeAssemblyTask(int workPostId, int taskId) {
		assemblyLine.completeAssemblyTask(workPostId, taskId);
		return givePendingAssemblyTasks(workPostId);
	}
}

