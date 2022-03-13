package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.AssemblyTask;

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

	public List<Integer> giveAllWorkPosts() {
	  return List
      .of(assemblyLine.getAccessoriesPost(), assemblyLine.getCarBodyPost(), assemblyLine.getDrivetrainPost())
      .stream().map(post -> post.getId())
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

	public List<String> completeAssemblyTask(int workPostId) {
		assemblyLine.completeAssemblyTask(workPostId);
		return givePendingAssemblyTasks(workPostId);
	}

	public Map<String, List<String>> giveAssemblyLineStatusAndOverview(int statusId) {
	  //TODO: !!!REFACTOR THIS SHIT!!!

    Map<String, List<String>> output = new HashMap<>();

    Map<String, AssemblyTask> assemblyLineStatus = assemblyLine.giveStatus();
    Map<String, List<AssemblyTask>> giveTasksOverview = assemblyLine.giveTasksOverview();

    for (String key : giveTasksOverview.keySet()) {
      List<AssemblyTask> values = giveTasksOverview.get(key);
      List<String> valuesString = values.stream().map(at -> at.getName()).collect(Collectors.toList());

      for (int i = 0; i < valuesString.size(); i++) {
        if (assemblyLineStatus.containsValue(values.get(i))) {
          String newValue = values.get(i) + " (active)";
          valuesString.set(i, newValue);
        }

        if (values.get(i).getPending()) {
          String newValue = values.get(i) + " (pending)";
          valuesString.set(i, newValue);
        }
      }

      output.put(key, valuesString);
    }

    return output;
  }

  public void moveAssemblyLine(int id, int minutes) {
	  //TODO: implement
  }
}

