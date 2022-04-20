package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.OrderNewCarController;
import be.kuleuven.assemassit.UI.Actions.CarMechanicActions.CarMechanicActionsOverviewUI;
import be.kuleuven.assemassit.UI.Actions.ManagerActions.ManagerActionsOverviewUI;
import be.kuleuven.assemassit.UI.UI;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class PerformAssemblyTasksActionUI implements UI {
  private AssemblyLineController assemblyLineController;
  private CarMechanicActionsOverviewUI carMechanicActionsOverviewUI;

  public PerformAssemblyTasksActionUI(AssemblyLineController assemblyLineController) {
    this.assemblyLineController = assemblyLineController;
    this.carMechanicActionsOverviewUI = new CarMechanicActionsOverviewUI(this.assemblyLineController);
  }

  public void run() {

    Map<Integer, String> allWorkPosts = assemblyLineController.giveAllWorkPosts();
    Optional<Integer> chosenWorkPostIdOptional = displayChooseWorkPost(allWorkPosts);

    if (chosenWorkPostIdOptional.isEmpty()) {
      this.carMechanicActionsOverviewUI.run();
      return;
    }
    int chosenWorkPostId = chosenWorkPostIdOptional.get();

    Map<Integer, String> allAssemblyTasks = assemblyLineController.givePendingAssemblyTasks(chosenWorkPostId);

    if (allAssemblyTasks.isEmpty()) {
      System.out.println("There are currently no pending tasks for this work post");
      run(); //TODO check if dit is de manier juiste redirect naar zichzelf
      return;
    }

    Optional<Integer> chosenAssemblyTaskIdOptional = displayChooseAssemblyTask(allAssemblyTasks);

    if (chosenAssemblyTaskIdOptional.isEmpty()) {
      run(); //TODO check if dit is de manier juiste redirect naar zichzelf
      return;
    }
    int chosenAssemblyTaskId = chosenAssemblyTaskIdOptional.get();

    assemblyLineController.setActiveTask(chosenWorkPostId, chosenAssemblyTaskId);

    List<String> actions = assemblyLineController.giveAssemblyTaskActions(chosenWorkPostId, chosenAssemblyTaskId);

    System.out.println();
    System.out.println("Execute the following actions:");
    displayActions(actions);

    System.out.println();
    System.out.println("Press ENTER when the task is finished");
    Scanner scanner = new Scanner(System.in);
    scanner.nextLine();
    assemblyLineController.completeAssemblyTask(chosenWorkPostId);

    this.carMechanicActionsOverviewUI.run();
  }

  private static Optional<Integer> displayChooseWorkPost(Map<Integer, String> workPosts) {
    Scanner scanner = new Scanner(System.in);
    int workPostId;

    do {
      System.out.println();
      System.out.println("Please choose a work post:");
      workPosts.forEach((id, name) -> System.out.println(String.format("%2d", id) + ": " + name));
      System.out.println("-1: Go back");

      workPostId = scanner.nextInt();

      if (workPostId == -1) return Optional.empty();

    } while (!workPosts.containsKey(workPostId));

    System.out.println("Chosen work post: " + workPosts.get(workPostId));
    return Optional.of(workPostId);
  }

  private static void displayActions(List<String> actions) {
    for (int i = 0; i < actions.size(); i++) {
      System.out.println(String.format("%2d", (i + 1)) + ": " + actions.get(i));
    }
  }

  private static Optional<Integer> displayChooseAssemblyTask(Map<Integer, String> assemblyTasks) {
    Scanner scanner = new Scanner(System.in);
    int assemblyTaskId;

    do {
      System.out.println();
      System.out.println("Please choose an assembly task:");
      assemblyTasks.forEach((id, name) -> System.out.println(String.format("%4d", id) + ": " + name));
      System.out.println("-1: Go back");

      assemblyTaskId = scanner.nextInt();

      if (assemblyTaskId == -1) return Optional.empty();

    } while (!assemblyTasks.containsKey(assemblyTaskId));

    System.out.println("Chosen assembly task: " + assemblyTasks.get(assemblyTaskId));
    return Optional.of(assemblyTaskId);
  }
}
