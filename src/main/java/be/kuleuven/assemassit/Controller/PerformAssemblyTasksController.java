package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;
import be.kuleuven.assemassit.Domain.WorkPost;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PerformAssemblyTasksController {

  private final AssemblyLine assemblyLine;
  private final CarManufactoringCompany carManufactoringCompany;

  /**
   * @param assemblyLine            the assembly line instance that should be used for creating the controller
   * @param carManufactoringCompany the car manufactoring company instance that should be used for creating the controller
   * @throws IllegalArgumentException assembly line is null | assemblyLine == null
   * @throws IllegalArgumentException carManufactoringCompany is null | carManufactoringCompany == null
   */
  public PerformAssemblyTasksController(AssemblyLine assemblyLine, CarManufactoringCompany carManufactoringCompany) {
    if (assemblyLine == null) throw new IllegalArgumentException("AssemblyLine can not be null");
    if (carManufactoringCompany == null) throw new IllegalArgumentException("CarManufactoringCompany can not be null");
    this.assemblyLine = assemblyLine;
    this.carManufactoringCompany = carManufactoringCompany;
  }

  /**
   * Generate a map of all work posts, the key of the map is the work post id, the value is the work post name
   *
   * @return map of work posts
   */
  public Map<Integer, String> giveAllWorkPosts() {
    return assemblyLine.getWorkPosts().stream().collect(Collectors.toMap(WorkPost::getId, (wp -> wp.getWorkPostType().toString())));
  }

  /**
   * Give the pending assembly tasks in a map, the key is the id of a task, the value is the name of a task
   *
   * @param postId the work post id to get the pending assembly tasks from
   * @return a map of pending assembly tasks
   * @throws IllegalArgumentException postId is smaller than 0 | postId < 0
   */
  public Map<Integer, String> givePendingAssemblyTasks(int postId) {
    if (postId < 0) throw new IllegalArgumentException("postId cannot be smaller than 0");
    List<AssemblyTask> pendingAssemblyTasks = assemblyLine.givePendingAssemblyTasksFromWorkPost(postId);
    return pendingAssemblyTasks.stream().collect(Collectors.toMap(AssemblyTask::getId, AssemblyTask::getName));
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
    if (workPostId < 0) throw new IllegalArgumentException("WorkPostId cannot be smaller than 0");
    if (assemblyTaskId < 0) throw new IllegalArgumentException("AssemblyTaskId cannot be smaller than 0");

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
    if (workPostId < 0) throw new IllegalArgumentException("WorkPostId cannot be smaller than 0");
    if (assemblyTaskId < 0) throw new IllegalArgumentException("AssemblyTaskId cannot be smaller than 0");

    return assemblyLine.giveCarAssemblyTask(workPostId, assemblyTaskId).getActions();
  }

  /**
   * Complete the active assembly task in a specific work post, then checks if the assemblyline can move if so then move the assemblyline
   *
   * @param workPostId the id of the work post
   * @param duration   the time it took him to Finish the task
   * @throws IllegalArgumentException workPostId is lower than 0
   * @throws IllegalArgumentException duration is lower than 0 | duration > 180
   */
  public void completeAssemblyTaskAndMoveIfPossible(int workPostId, int duration) {
    if (workPostId < 0) throw new IllegalArgumentException("WorkPostId cannot be smaller than 0");
    if (!(duration >= 0 && duration < 180))
      throw new IllegalArgumentException("The duration of a task cannot be smaller than 0 or greater than 180");
    assemblyLine.completeAssemblyTask(workPostId, duration);

    if (assemblyLine.canMove()) {
      carManufactoringCompany.moveAssemblyLine();
    }

  }


}
