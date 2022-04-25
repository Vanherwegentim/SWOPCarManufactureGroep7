package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.WorkPost;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AssemblyLineController {

  private final AssemblyLine assemblyLine;

  /**
   * @param assemblyLine
   * @throws IllegalArgumentException assembly line is null | assemblyLine == null
   */
  protected AssemblyLineController(AssemblyLine assemblyLine) {
    if (assemblyLine == null)
      throw new IllegalArgumentException("AssemblyLine can not be null");
    this.assemblyLine = assemblyLine;
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
   * This method makes sure that it is visible which tasks are pending, done and active
   *
   * @param workPostsWithActiveTasks
   * @param workPostPairs
   * @return a map with ids as key and a list of assemblyTasks as value
   */
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

  /**
   * Move the assembly line forward if possible
   * Also hand in the minutes spend during that phase
   *
   * @param minutes
   * @return a list of blocking work posts (if there are any)
   * @throws IllegalArgumentException minutes is smaller than 0 | minutes < 0
   */
  public List<String> moveAssemblyLine(int minutes) {
    if (minutes < 0)
      throw new IllegalArgumentException("Minutes cannot be smaller than 0");

    if (!assemblyLine.canMove()) {
      List<String> blockingWorkPosts = new ArrayList<>();
      List<WorkPost> workPosts = assemblyLine.getWorkPosts();
      for (WorkPost workPost : workPosts) {
        if (!workPost.givePendingAssemblyTasks().isEmpty()) {
          blockingWorkPosts.add(workPost.getWorkPostType().toString());
        }
      }
      return blockingWorkPosts;
    } else {
      assemblyLine.move();
      return new ArrayList<>();
    }
  }

  /**
   * Complete the active assembly task in a specific work post
   *
   * @param workPostId the id of the work post
   * @throws IllegalArgumentException workPostId is lower than 0
   * @throws IllegalArgumentException duration is lower than 0 | duration > 180
   */
  public void completeAssemblyTask(int workPostId) {
    if (workPostId < 0)
      throw new IllegalArgumentException("WorkPostId cannot be smaller than 0");
    assemblyLine.completeAssemblyTask(workPostId, 15);
  }

  /**
   * Sets a task active in the work post
   *
   * @param workPostId     the id of the work post
   * @param assemblyTaskId the id of the task in the work post
   * @throws IllegalArgumentException workPostId is lower than 0 | workPostId < 0
   * @throws IllegalArgumentException assemblyTaskId is lower than 0 | assemblyTaskId < 0
   */
  public void setActiveTask(int workPostId, int assemblyTaskId) {
    if (workPostId < 0)
      throw new IllegalArgumentException("WorkPostId cannot be smaller than 0");
    if (assemblyTaskId < 0)
      throw new IllegalArgumentException("AssemblyTaskId cannot be smaller than 0");

    WorkPost workPost = assemblyLine.findWorkPost(workPostId);
    assemblyLine.setActiveTask(workPost, assemblyTaskId);
  }

  /**
   * Give the list of actions of a corresponding work post
   *
   * @param workPostId     the id of the work post
   * @param assemblyTaskId the task id where the actions should be coming from
   * @return a list of assembly task actions
   * @throws IllegalArgumentException workPostId is smaller than 0 | workPostId < 0
   * @throws IllegalArgumentException assemblyTaskId is smaller than 0 | assemblyTaskId < 0
   */
  public List<String> giveAssemblyTaskActions(int workPostId, int assemblyTaskId) {
    if (workPostId < 0)
      throw new IllegalArgumentException("WorkPostId cannot be smaller than 0");
    if (assemblyTaskId < 0)
      throw new IllegalArgumentException("AssemblyTaskId cannot be smaller than 0");

    return assemblyLine.giveCarAssemblyTask(workPostId, assemblyTaskId).getActions();
  }
}

