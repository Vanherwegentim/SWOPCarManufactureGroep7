package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.OrderController;
import be.kuleuven.assemassit.UI.Actions.Overviews.CarMechanicActionsOverviewUI;
import be.kuleuven.assemassit.UI.AuthenticateUI;

import java.util.*;

public class PerformAssemblyTasksActionUI {
  public static void run(OrderController orderController, AssemblyLineController assemblyLineController) {

    Map<Integer, String> allWorkPosts = assemblyLineController.giveAllWorkPosts();
    int chosenWorkPostId = displayChooseWorkPost(allWorkPosts);

    Map<Integer, String> allAssemblyTasks = assemblyLineController.givePendingAssemblyTasks(chosenWorkPostId);

    if (allAssemblyTasks.isEmpty()) {
      System.out.println("There are currently no pending tasks for this work post");
      PerformAssemblyTasksActionUI.run(orderController, assemblyLineController);
      return;
    }

    int chosenAssemblyTaskId = displayChooseAssemblyTask(allAssemblyTasks);
    assemblyLineController.setActiveTask(chosenWorkPostId, chosenAssemblyTaskId);

    //todo refactor met workpostid
    List<String> actions = assemblyLineController.giveAssemblyTaskActions(chosenAssemblyTaskId);
    actions.forEach(System.out::println);

    //todo task to active
    System.out.println("Press ENTER when the task if finished");
    Scanner scanner = new Scanner(System.in);
    scanner.nextLine();
    assemblyLineController.completeAssemblyTask(chosenWorkPostId);

    CarMechanicActionsOverviewUI.run(orderController, assemblyLineController);
  }

  private static int displayChooseWorkPost(Map<Integer, String> workPosts) {
    Scanner scanner = new Scanner(System.in);
    int workPostId;

    do {
      System.out.println();
      System.out.println("Please choose a work post:");
      workPosts.forEach((id, name) -> System.out.println(String.format("%2d", id) + ": " + name));
      workPostId = scanner.nextInt();
    } while (!workPosts.containsKey(workPostId));

    System.out.println("Chosen workpost: " + workPosts.get(workPostId));
    return workPostId;
  }

  private static int displayChooseAssemblyTask(Map<Integer, String> assemblyTasks) {
    Scanner scanner = new Scanner(System.in);
    int assemblyTaskId;

    do {
      System.out.println();
      System.out.println("Please choose an assembly task:");
      assemblyTasks.forEach((id, name) -> System.out.println(String.format("%2d", id) + ": " + name));
      assemblyTaskId = scanner.nextInt();
    } while (!assemblyTasks.containsKey(assemblyTaskId));

    System.out.println("Chosen assembly task: " + assemblyTasks.get(assemblyTaskId));
    return assemblyTaskId;
  }
}
