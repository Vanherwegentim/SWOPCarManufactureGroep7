package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.CheckAssemblyLineStatusController;
import be.kuleuven.assemassit.Controller.ControllerFactoryMiddleWare;
import be.kuleuven.assemassit.UI.IOCall;
import be.kuleuven.assemassit.UI.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CheckAssemblyLineStatusActionUI implements UI {

  private ControllerFactoryMiddleWare controllerFactoryMiddleWare;
  private CheckAssemblyLineStatusController checkAssemblyLineStatusController;

  public CheckAssemblyLineStatusActionUI(ControllerFactoryMiddleWare controllerFactoryMiddleWare) {
    this.controllerFactoryMiddleWare = controllerFactoryMiddleWare;
  }

  private static void displayStatus(HashMap<String, List<String>> status) {

    List<String> statusKeys = new ArrayList<>(status.keySet());

    for (String workPostName : statusKeys) {
      IOCall.out("  * " + workPostName);

      if (status.get(workPostName).isEmpty()) {
        IOCall.out("      - " + "no pending or active tasks");
      }
      for (int j = 0; j < status.get(workPostName).size(); j++) {
        IOCall.out("      - " + status.get(workPostName).get(j));
      }
    }
  }

  @Override
  public void run() {

    this.checkAssemblyLineStatusController = controllerFactoryMiddleWare.createCheckAssemblyLineStatusController();


    IOCall.out();
    IOCall.out("Current assembly line status:");
    displayStatus(checkAssemblyLineStatusController.giveAssemblyLineStatusOverview());

    IOCall.out();
    IOCall.out("Future assembly line status:");
    displayStatus(checkAssemblyLineStatusController.giveFutureAssemblyLineStatusOverview());
    IOCall.out();
    String tasks = "";
    for (int i : checkAssemblyLineStatusController.giveAllWorkPosts().keySet()) {
      tasks += "The " + checkAssemblyLineStatusController.giveAllWorkPosts().get(i);

      tasks += " has these finished tasks:" + System.lineSeparator();
      for (String s : checkAssemblyLineStatusController.giveFinishedAssemblyTasks(i).values()) {
        tasks += s + System.lineSeparator();
        IOCall.out();
      }
    }
    IOCall.out(tasks);


  }
}
