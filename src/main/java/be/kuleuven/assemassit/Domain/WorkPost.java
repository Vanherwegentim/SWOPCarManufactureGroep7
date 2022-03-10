package be.kuleuven.assemassit.Domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface WorkPost {
  List<AssemblyTask> assemblyTasks = new ArrayList<AssemblyTask>();

	public int getId();

	public List<AssemblyTask> givePendingAssemblyTasks();

	public void completeAssemblyTask(int assemblyTaskId);

  private AssemblyTask findAssemblyTask(int id) {
    Optional<AssemblyTask> assemblyTask = assemblyTasks.stream()
      .filter(at -> at.getId() == id)
      .findFirst();

    if (!assemblyTask.isPresent())
      throw new IllegalArgumentException("Workpost not found");

    return assemblyTask.get();
  }
}
