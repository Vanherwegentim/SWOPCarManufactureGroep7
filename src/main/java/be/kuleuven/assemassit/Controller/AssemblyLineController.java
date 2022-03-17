package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.WorkPost;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AssemblyLineController {

  private AssemblyLine assemblyLine;

  public AssemblyLineController() {
    this.assemblyLine = new AssemblyLine();
  }

  public AssemblyLineController(AssemblyLine assemblyLine) {
    this.assemblyLine = assemblyLine;
  }

  public Map<Integer, String> giveAllWorkPosts() {
    return Stream.of(assemblyLine.getAccessoriesPost(), assemblyLine.getCarBodyPost(), assemblyLine.getDrivetrainPost())
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

  public List<String> giveAssemblyTaskActions(int assemblyTaskId) {
    return assemblyLine.giveCarAssemblyTask(assemblyTaskId).getActions();
  }

  public HashMap<String, List<String>> giveAssemblyLineStatusOverview() {
    //TODO: !!!REFACTOR THIS SHIT!!!

    HashMap<String, AssemblyTask> assemblyLineStatus = assemblyLine.giveStatus();
    HashMap<String, List<AssemblyTask>> workPostPairs = assemblyLine.giveTasksOverview();

    return evaluateAssemblyLineStatusOverview(assemblyLineStatus, workPostPairs);
  }

  public HashMap<String, List<String>> giveFutureAssemblyLineStatusOverview() {
    //TODO: !!!REFACTOR THIS SHIT!!!

    HashMap<String, AssemblyTask> assemblyLineStatus = assemblyLine.giveStatus();
    HashMap<String, List<AssemblyTask>> workPostPairs = assemblyLine.giveFutureTasksOverview();

    return evaluateAssemblyLineStatusOverview(assemblyLineStatus, workPostPairs);
  }

  private HashMap<String, List<String>> evaluateAssemblyLineStatusOverview(
    HashMap<String, AssemblyTask> assemblyLineStatus, HashMap<String, List<AssemblyTask>> workPostPairs) {
    //TODO: !!!REFACTOR THIS SHIT!!!

    HashMap<String, List<String>> output = new LinkedHashMap<>();

    for (String key : workPostPairs.keySet()) {
      List<AssemblyTask> values = workPostPairs.get(key);
      List<String> valuesString = values.stream().map(AssemblyTask::getName).collect(Collectors.toList());

      for (int i = 0; i < valuesString.size(); i++) {
        if (assemblyLineStatus.get(key) == values.get(i)) {
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

  public List<String> moveAssemblyLine(int minutes) {
    if (!assemblyLine.canMove()) {
      List<String> blockingWorkPosts = new ArrayList<>();
      List<WorkPost> workPosts = assemblyLine.giveWorkPostsAsList();
      for (WorkPost workPost : workPosts) {
        if (!workPost.givePendingAssemblyTasks().isEmpty()) {
          blockingWorkPosts.add(workPost.getWorkPostType().toString());
        }
      }
      return blockingWorkPosts;
    } else {
      assemblyLine.move(minutes);
      return new ArrayList<>();
    }
  }

  public void setActiveTask(int workPostId, int assemblyTaskId) {
    WorkPost workPost = assemblyLine.findWorkPost(workPostId);
    assemblyLine.setActiveTask(workPost, assemblyTaskId);
  }
}

