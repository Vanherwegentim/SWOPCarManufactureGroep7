package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.OrderController;

import java.util.*;

public class PerformAssemblyTasksActionUI {
  public static void run(OrderController orderController, AssemblyLineController assemblyLineController) {

    Map<Integer, String> allWorkPosts = assemblyLineController.giveAllWorkPosts();
    int chosenWorkPostId = displayChooseWorkPost(allWorkPosts);


    Map<Integer, String> allAssemblyTasks = assemblyLineController.givePendingAssemblyTasks(chosenWorkPostId);

    int assemblyTaskId = displayChooseAssemblyTask(allAssemblyTasks);


    List<String> actions = assemblyLineController.giveAssemblyTaskActions(chosenWorkPostId, assemblyTaskId);
    actions.forEach(System.out::println);

    System.out.println("Press ENTER to continue...");
    Scanner scanner = new Scanner(System.in);
    scanner.nextLine();

  }

  private static Integer displayChooseWorkPost(Map<Integer, String> workPosts) {
    Scanner scanner = new Scanner(System.in);
    int workPostId;

    do {
      System.out.println("Please choose a workPost:");
      workPosts.forEach((id, name) -> System.out.println(String.format("%2d", id) + ": " + name));
      workPostId = scanner.nextInt();
    } while (!workPosts.containsKey(workPostId));

    System.out.println("Chosen workpost: " + workPosts.get(workPostId));
    return workPostId;
  }

  private static Integer displayChooseAssemblyTask(Map<Integer, String> assemblyTasks) {
    Scanner scanner = new Scanner(System.in);
    int assemblyTaskId;

    do {
      System.out.println("Please choose an assembly task:");
      assemblyTasks.forEach((id, name) -> System.out.println(String.format("%2d", id) + ": " + name));
      assemblyTaskId = scanner.nextInt();
    } while (!assemblyTasks.containsKey(assemblyTaskId));

    System.out.println("Chosen assembly task: " + assemblyTasks.get(assemblyTaskId));
    return assemblyTaskId;
  }
}
