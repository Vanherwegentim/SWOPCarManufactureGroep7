package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.OrderController;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class PerformAssemblyTasksActionUI {
  public static void run(OrderController orderController, AssemblyLineController assemblyLineController) {

    List<String> allWorkPosts = assemblyLineController.giveAllWorkPosts().stream().map(Objects::toString).toList();
    int chosenWorkPostId = displayChooseWorkPost(allWorkPosts);

    // TODO choose the right method
    List<String> allAssemblyTasks = assemblyLineController.giveAllWorkPosts().stream().map(Objects::toString).toList();
    int displayChooseAssemblyTask = displayChooseAssemblyTask(allAssemblyTasks);

  }

  public static Integer displayChooseWorkPost(List<String> workPosts) {
    Scanner scanner = new Scanner(System.in);
    int workPostId;

    do {
      System.out.println("Please choose a workPost:");
      workPosts.forEach(System.out::println);
      workPostId = scanner.nextInt();
    } while (workPostId < 0 || workPostId > workPosts.size() - 1);

    System.out.println("Chosen workpost: " + workPosts.get(workPostId));
    return workPostId;
  }

  public static Integer displayChooseAssemblyTask(List<String> assemblyTasks) {
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
