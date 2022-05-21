package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.ControllerFactoryMiddleWare;
import be.kuleuven.assemassit.Controller.PerformAssemblyTasksController;
import be.kuleuven.assemassit.UI.IOCall;
import be.kuleuven.assemassit.UI.UI;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PerformAssemblyTasksActionUI implements UI {
  private final PerformAssemblyTasksController performAssemblyTasksController;

  public PerformAssemblyTasksActionUI(ControllerFactoryMiddleWare controllerFactoryMiddleWare) {
    this.performAssemblyTasksController = controllerFactoryMiddleWare.createPerformAssemblyTasksController();
  }

  private static Optional<Integer> displayChooseWorkPost(Map<Integer, String> workPosts) {
    int workPostId;

    do {
      IOCall.out();
      IOCall.out("Please choose a work post:");
      workPosts.forEach((id, name) -> IOCall.out(String.format("%2d", id) + ": " + name));
      IOCall.out("-1: Go back");

      workPostId = IOCall.in();

      if (workPostId == -1) return Optional.empty();

    } while (!workPosts.containsKey(workPostId));

    IOCall.out("Chosen work post: " + workPosts.get(workPostId));
    return Optional.of(workPostId);
  }

  private static void displayActions(List<String> actions) {
    for (int i = 0; i < actions.size(); i++) {
      IOCall.out(String.format("%2d", (i + 1)) + ": " + actions.get(i));
    }
  }

  private static Optional<Integer> displayChooseAssemblyTask(Map<Integer, String> assemblyTasks) {
    int assemblyTaskId;

    do {
      IOCall.out();
      IOCall.out("Please choose an assembly task:");
      assemblyTasks.forEach((id, name) -> IOCall.out(String.format("%4d", id) + ": " + name));
      IOCall.out("-1: Go back");

      assemblyTaskId = IOCall.in();

      if (assemblyTaskId == -1) return Optional.empty();

    } while (!assemblyTasks.containsKey(assemblyTaskId));

    IOCall.out("Chosen assembly task: " + assemblyTasks.get(assemblyTaskId));
    return Optional.of(assemblyTaskId);
  }

  private static int displayInputMinutes() {
    int minutes;

    do {
      IOCall.out();
      IOCall.out("What was the amount of minutes spent on this task?");
      minutes = IOCall.in();
    } while (!(minutes >= 0 && minutes < 180));
    return minutes;
  }

  public void run() {
    while (true) {

      //Get data for 1.
      Map<Integer, String> allWorkPosts = performAssemblyTasksController.giveAllWorkPosts();
      //1. The system asks the user what work post he is currently residing at.
      //2. The user selects the corresponding work post.
      Optional<Integer> chosenWorkPostIdOptional = displayChooseWorkPost(allWorkPosts);

      if (chosenWorkPostIdOptional.isEmpty()) {
        IOCall.out("No work post was chosen.");
        break;
      }

      int chosenWorkPostId = chosenWorkPostIdOptional.get();

      // 8. The use case continues in step 4.
      while (true) {
        // Get data for 3.
        Map<Integer, String> allAssemblyTasks = performAssemblyTasksController.givePendingAssemblyTasks(chosenWorkPostId);

        if (allAssemblyTasks.isEmpty()) {
          IOCall.out("There are currently no pending tasks for this work post");
          break;
        }

        //3. The system presents an overview of the pending assembly tasks for the car at the current work post.
        //4. The user selects one of the assembly tasks.
        Optional<Integer> chosenAssemblyTaskIdOptional = displayChooseAssemblyTask(allAssemblyTasks);

        if (chosenAssemblyTaskIdOptional.isEmpty()) {
          IOCall.out("No task was chosen.");
          break;
        }

        int chosenAssemblyTaskId = chosenAssemblyTaskIdOptional.get();

        performAssemblyTasksController.setActiveTask(chosenWorkPostId, chosenAssemblyTaskId);

        //5. The system shows the assembly task information, including the sequence of actions to perform.
        List<String> actions = performAssemblyTasksController.giveAssemblyTaskActions(chosenWorkPostId, chosenAssemblyTaskId);

        IOCall.out();
        IOCall.out("Execute the following actions:");
        displayActions(actions);

        //6. The user performs the assembly tasks and indicates when the assembly task is fished together with the time it took him to finish the job.
        //7. If all the assembly tasks at the assembly line are finished, the assembly line is shifted automatically and the production schedule is updated The system presents an updated overview of pending assembly tasks for the car at the current work post.
        int duration = displayInputMinutes();
        performAssemblyTasksController.completeAssemblyTaskAndMoveIfPossible(chosenWorkPostId, duration);
      }
    }
  }
}
