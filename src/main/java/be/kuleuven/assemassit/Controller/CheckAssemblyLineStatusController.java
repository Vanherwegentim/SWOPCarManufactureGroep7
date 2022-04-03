package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.AssemblyTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class CheckAssemblyLineStatusController {
  private AssemblyLine assemblyLine;

  public CheckAssemblyLineStatusController(AssemblyLine assemblyLine) {
    if (assemblyLine == null)
      throw new IllegalArgumentException("AssemblyLine can not be null");
    this.assemblyLine = assemblyLine;
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
}
