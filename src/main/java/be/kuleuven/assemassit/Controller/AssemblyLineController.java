package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.WorkPost;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AssemblyLineController {

  private AssemblyLine assemblyLine;

  public AssemblyLineController() {
    this.assemblyLine = new AssemblyLine();
  }

  public AssemblyLineController(AssemblyLine assemblyLine) {
    this.assemblyLine = assemblyLine;
  }

  public Map<Integer, String> giveAllWorkPosts() {
    return List
      .of(assemblyLine.getAccessoriesPost(), assemblyLine.getCarBodyPost(), assemblyLine.getDrivetrainPost())
      .stream()
      .collect(Collectors.toMap(WorkPost::getId, (wp -> wp.getWorkPostType().toString())));
  }


  public Map<Integer, String> givePendingAssemblyTasks(int postId) {
    if (postId < 0)
      throw new IllegalArgumentException("postId cannot be smaller than 0");
    // TODO: better way to show tasks
    List<AssemblyTask> pendingAssemblyTasks = assemblyLine.givePendingAssemblyTasksFromWorkPost(postId);


    return pendingAssemblyTasks
      .stream()
      .collect(Collectors.toMap(AssemblyTask::getId, AssemblyTask::getName));
  }

  public Map<Integer, String> completeAssemblyTask(int workPostId) {
    assemblyLine.completeAssemblyTask(workPostId);
    return givePendingAssemblyTasks(workPostId);
  }

  public List<String> giveAssemblyTaskActions(int workPostId, int assemblyTaskid) {
    return assemblyLine.giveCarAssemblyTask(workPostId, assemblyTaskid).getActions();
  }

  public Map<String, List<String>> giveAssemblyLineStatusAndOverview(int statusId) {
    //TODO: !!!REFACTOR THIS SHIT!!!

    Map<String, List<String>> output = new HashMap<>();

    Map<String, AssemblyTask> assemblyLineStatus = assemblyLine.giveStatus();
    Map<String, List<AssemblyTask>> giveTasksOverview = assemblyLine.giveTasksOverview();

    for (String key : giveTasksOverview.keySet()) {
      List<AssemblyTask> values = giveTasksOverview.get(key);
      List<String> valuesString = values.stream().map(AssemblyTask::getName).collect(Collectors.toList());

      for (int i = 0; i < valuesString.size(); i++) {
        if (assemblyLineStatus.get(key) == values.get(i)) { //assemblyLineStatus.containsValue(values.get(i))
          String newValue = valuesString.get(i) + " (active)";
          valuesString.set(i, newValue);
        }

        if (values.get(i).getPending()) {
          String newValue = valuesString.get(i) + " (pending)";
          valuesString.set(i, newValue);
        }
      }

      output.put(key, valuesString);
    }

    return output;
  }

  public String moveAssemblyLine(int minutes) {
	  return assemblyLine.move(minutes);
  }
}

