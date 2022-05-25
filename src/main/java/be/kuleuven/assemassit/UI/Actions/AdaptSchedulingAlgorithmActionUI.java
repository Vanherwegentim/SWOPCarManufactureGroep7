package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.AdaptSchedulingAlgorithmController;
import be.kuleuven.assemassit.Controller.ControllerFactoryMiddleWare;
import be.kuleuven.assemassit.Exceptions.UIException;
import be.kuleuven.assemassit.UI.IOCall;
import be.kuleuven.assemassit.UI.UI;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

public class AdaptSchedulingAlgorithmActionUI implements UI {
  final AdaptSchedulingAlgorithmController algorithmController;

  public AdaptSchedulingAlgorithmActionUI(ControllerFactoryMiddleWare controllerFactoryMiddleWare) {
    algorithmController = controllerFactoryMiddleWare.createAdaptSchedulingAlgorithmController();
  }

  @Override
  public void run() {


    int algoNum = 0;
    do {
      IOCall.out("Active Algorithm:");
      IOCall.out(algorithmController.giveCurrentSchedulingAlgorithmName());
      IOCall.out();
      IOCall.out("Available Algorithms:");
      IOCall.out(" 1: " + algorithmController.giveSchedulingAlgorithmNames().get(0));
      IOCall.out(" 2: " + algorithmController.giveSchedulingAlgorithmNames().get(1));
      IOCall.out("-1: Quit");
      IOCall.out("Please type the number of the algorithm of your choosing");
      try {
        algoNum = IOCall.in();
        switch (algoNum) {
          case 1 -> {
            algorithmController.changeAlgorithmToFIFO();
            IOCall.out("The scheduling algorithm has succesfully changed to FIFO Scheduling");
          }
          case 2 -> {
            changeToSpecificationBatchScheduling();
          }
          case -1 -> {
            IOCall.out("Quitting...");
            IOCall.out();
            IOCall.out();
          }
        }
      } catch (InputMismatchException | UIException ex) {
        IOCall.out("ERROR, only integers are allowed here!");
        IOCall.next();
      }

    } while (algoNum != -1);
  }

  public void changeToSpecificationBatchScheduling() {
    Map<Integer, List<String>> batches = algorithmController.givePossibleBatches();
    if (batches.isEmpty()) {
      IOCall.out("There are currently no batches to choose from.");
    } else {
      IOCall.out("Please give the number of the batch of your choosing:");
      for (int i = 0; i < batches.size(); i++) {
        IOCall.out(i + ": " + batches.get(i));
      }
      int carBatch = IOCall.in();
      algorithmController.changeAlgorithmToSpecificationBatch(carBatch);
      IOCall.out("The scheduling algorithm has succesfully changed to Batch Scheduling");
    }


  }
}
