package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.OrderController;
import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.TaskTypes.*;

import java.util.*;

public class PerformAssemblyTasksActionUI {
  public static void run(OrderController orderController, AssemblyLineController assemblyLineController) {

    Map<Integer, String> allWorkPosts = assemblyLineController.giveAllWorkPosts();
    int chosenWorkPostId = displayChooseWorkPost(allWorkPosts);


    Map<Integer, java.lang.String> allAssemblyTasks = assemblyLineController.givePendingAssemblyTasks(chosenWorkPostId);

//    int displayChooseAssemblyTask = displayChooseAssemblyTask(allAssemblyTasks);

    List<String> actions;
//    actions = assemblyLineController.giveAssemblyTaskInformation();
//    actions.forEach(System.out::println);

    System.out.println("Press ENTER to continue...");
    Scanner scanner = new Scanner(System.in);
    scanner.nextLine();

  }

  private static Integer displayChooseWorkPost(Map<Integer, String> workPosts) {
    Scanner scanner = new Scanner(System.in);
    int workPostId;

    do {
      System.out.println("Please choose a workPost:");
      workPosts.forEach((id, name) -> System.out.println(id + ": " + name));
      workPostId = scanner.nextInt();
    } while (!workPosts.containsKey(workPostId));

    System.out.println("Chosen workpost: " + workPosts.get(workPostId));
    return workPostId;
  }

  private static Integer displayChooseAssemblyTask(List<String> assemblyTasks) {
    Scanner scanner = new Scanner(System.in);
    int assemblyTaskId;

    do {
      System.out.println("Please choose an assembly task:");
      assemblyTasks.forEach(System.out::println);
      assemblyTaskId = scanner.nextInt();
    } while (assemblyTaskId < 0 || assemblyTaskId > assemblyTasks.size() - 1);

    System.out.println("Chosen assembly task: " + assemblyTasks.get(assemblyTaskId));
    return assemblyTaskId;
  }
}
