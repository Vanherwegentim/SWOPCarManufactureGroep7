package be.kuleuven.assemassit.Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class WorkPost {
	private int id;
	private List<AssemblyTask> assemblyTasks;

	public WorkPost() {
		this.assemblyTasks = new ArrayList<AssemblyTask>();
	}

	public int getId() {
		return this.id;
	}

	public List<AssemblyTask> givePendingAssemblyTasks() {
		return assemblyTasks.stream()
				.filter(at -> at.getPending() == true)
				.collect(Collectors.toList());
	}

	public void completeAssemblyTask(int assemblyTaskId) {
		findAssemblyTask(assemblyTaskId).complete();
	}

	private AssemblyTask findAssemblyTask(int id) {
		Optional<AssemblyTask> assemblyTask = assemblyTasks.stream()
				.filter(at -> at.getId() == id)
				.findFirst();

		if (!assemblyTask.isPresent())
			throw new IllegalArgumentException("Workpost not found");

		return assemblyTask.get();
	}
}
