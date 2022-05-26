package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.WorkPost;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CheckAssemblyLineStatusController {
  /**
   * @invar | assemblyLine != null
   */
  private final AssemblyLine assemblyLine;

  /**
   * @param assemblyLine The current assembly line of the program
   * @throws IllegalArgumentException | assemblyLine == null
   * @post | getAssemblyLine().equals(assemblyLine)
   */
  protected CheckAssemblyLineStatusController(AssemblyLine assemblyLine) {
    if (assemblyLine == null)
      throw new IllegalArgumentException("AssemblyLine can not be null");
    this.assemblyLine = assemblyLine;
  }

  protected AssemblyLine getAssemblyLine() {
    return this.assemblyLine;
  }

  /**
   * Give the pending assembly tasks in a map, the key is the id of a task, the value is the name of a task
   *
   * @param postId the work post id to get the pending assembly tasks from
   * @return a map of pending assembly tasks
   * @throws IllegalArgumentException postId is smaller than 0 | postId < 0
   */
  public Map<Integer, String> givePendingAssemblyTasks(int postId) {
    if (postId < 0)
      throw new IllegalArgumentException("postId cannot be smaller than 0");

    List<AssemblyTask> pendingAssemblyTasks = assemblyLine.givePendingAssemblyTasksFromWorkPost(postId);

    return pendingAssemblyTasks
      .stream()
      .collect(Collectors.toMap(AssemblyTask::getId, AssemblyTask::getName));
  }

  /**
   * Get a list of tasks that are finished on a work post
   *
   * @param postId the id of the work post
   * @return a map with the assembly task ID and name
   */
  public Map<Integer, String> giveFinishedAssemblyTasks(int postId) {
    if (postId < 0)
      throw new IllegalArgumentException("postId cannot be smaller than 0");

    List<AssemblyTask> finishedAssemblyTasks = assemblyLine.giveFinishedAssemblyTasksFromWorkPost(postId);

    return finishedAssemblyTasks
      .stream()
      .collect(Collectors.toMap(AssemblyTask::getId, AssemblyTask::getName));
  }


  /**
   * Give the status of an assembly line, this is a map, the key of the map is the work post name
   * the value is a list of tasks from the work post (pending, completed and active)
   *
   * @return a map that represents the status of an assembly line
   */
  public HashMap<String, List<String>> giveAssemblyLineStatusOverview() {
    HashMap<String, AssemblyTask> workPostsWithActiveTasks = assemblyLine.giveActiveTasksOverview();
    HashMap<String, List<AssemblyTask>> workPostPairs = assemblyLine.giveTasksOverview();
    return evaluateAssemblyLineStatusOverview(workPostsWithActiveTasks, workPostPairs);
  }

  /**
   * Give the future status of an assembly line, this is a map, the key of the map is the work post name
   * the value is a list of tasks from the work post (pending, completed and active)
   *
   * @return a map that represents the future status of an assembly line
   */
  public HashMap<String, List<String>> giveFutureAssemblyLineStatusOverview() {
    HashMap<String, AssemblyTask> workPostsWithActiveTasks = assemblyLine.giveActiveTasksOverview();
    HashMap<String, List<AssemblyTask>> workPostPairs = assemblyLine.giveFutureTasksOverview();
    return evaluateAssemblyLineStatusOverview(workPostsWithActiveTasks, workPostPairs);
  }

  /**
   * Generate a map of all work posts, the key of the map is the work post id, the value is the work post name
   *
   * @return map of work posts
   */
  public Map<Integer, String> giveAllWorkPosts() {
    return Stream.of(assemblyLine.getAccessoriesPost(), assemblyLine.getCarBodyPost(), assemblyLine.getDrivetrainPost())
      .collect(Collectors.toMap(WorkPost::getId, (wp -> wp.getWorkPostType().toString())));
  }

  private HashMap<String, List<String>> evaluateAssemblyLineStatusOverview(
    HashMap<String, AssemblyTask> workPostsWithActiveTasks, HashMap<String, List<AssemblyTask>> workPostPairs) {
    HashMap<String, List<String>> output = new LinkedHashMap<>();

    for (String key : workPostPairs.keySet()) {
      List<AssemblyTask> assemblyTasks = workPostPairs.get(key);
      List<String> assemblyTasksNames = new ArrayList<>(assemblyTasks.stream().map(AssemblyTask::getName).toList());

      for (int i = 0; i < assemblyTasksNames.size(); i++) {
        if (workPostsWithActiveTasks.get(key) == assemblyTasks.get(i)) {
          String assemblyTaskName = assemblyTasksNames.get(i) + " (active)";
          assemblyTasksNames.set(i, assemblyTaskName);
        }

        if (assemblyTasks.get(i).getPending()) {
          String assemblyTaskName = assemblyTasksNames.get(i) + " (pending)";
          assemblyTasksNames.set(i, assemblyTaskName);
        }
      }

      output.put(key, assemblyTasksNames);
    }

    return output;
  }


}
