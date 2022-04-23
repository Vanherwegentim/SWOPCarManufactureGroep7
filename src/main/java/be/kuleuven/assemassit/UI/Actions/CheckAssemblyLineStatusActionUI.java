package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.CheckAssemblyLineStatusController;
import be.kuleuven.assemassit.UI.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CheckAssemblyLineStatusActionUI implements UI {

  public CheckAssemblyLineStatusController checkAssemblyLineStatusController;

  public CheckAssemblyLineStatusActionUI(CheckAssemblyLineStatusController checkAssemblyLineStatusController) {
    this.checkAssemblyLineStatusController = checkAssemblyLineStatusController;
  }

  private static void displayStatus(HashMap<String, List<String>> status) {
    List<String> statusKeys = new ArrayList<>(status.keySet());

    for (String workPostName : statusKeys) {
      System.out.println("  * " + workPostName);

      if (status.get(workPostName).isEmpty()) {
        System.out.println("      - " + "no pending or active tasks");
      }
      for (int j = 0; j < status.get(workPostName).size(); j++) {
        System.out.println("      - " + status.get(workPostName).get(j));
      }
    }
  }

  @Override
  public void run() {
    while (true) {

      System.out.println();
      System.out.println("Current assembly line status:");
      displayStatus(checkAssemblyLineStatusController.giveAssemblyLineStatusOverview());

      System.out.println();
      System.out.println("Future assembly line status:");
      displayStatus(checkAssemblyLineStatusController.giveFutureAssemblyLineStatusOverview());

      String tasks = "";
      for (int i : checkAssemblyLineStatusController.giveAllWorkPosts().keySet()) {
        tasks += "The " + checkAssemblyLineStatusController.giveAllWorkPosts().get(i) + "workpost has these pending tasks:\n";
        for (String s : checkAssemblyLineStatusController.givePendingAssemblyTasks(i).values()) {
          tasks += s + "\n";
        }
        tasks += "and has these finished tasks: \n";
        for (String s : checkAssemblyLineStatusController.giveFinishedAssemblyTasks(i).values()) {
          tasks += s + "\n";
        }
      }
      System.out.println(tasks);
      break; // if we reach this point, the use case is done, java call stack will now return to the previous UI

    }


  }
}
