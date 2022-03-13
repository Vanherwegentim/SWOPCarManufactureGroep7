package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.OrderController;
import be.kuleuven.assemassit.UI.Actions.Overviews.GarageHolderActionsOverviewUI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderNewCarActionUI {
  public static void run(OrderController orderController, AssemblyLineController assemblyLineController) {
    Scanner scanner = new Scanner(System.in);
    int action;

    do {
      System.out.println("Orders placed by: " + orderController.giveLoggedInGarageHolderName());

      System.out.println("Pending:");
      orderController.givePendingCarOrders().forEach(System.out::println);

      System.out.println("History:");
      orderController.giveCompletedCarOrders().forEach(System.out::println);

      System.out.println("Please choose an action:");
      System.out.println("1: Place a new order");
      System.out.println("0: Go back");

      action = scanner.nextInt();

      switch (action) {
        case 1 -> {
          int chosenCarModelId = displayChooseCarModel(orderController.giveListOfCarModels());

          Map<String, String> selectedParts = displayOrderingForm(orderController.givePossibleOptionsOfCarModel(chosenCarModelId));

          orderController.placeCarOrder(chosenCarModelId, selectedParts.get("Body"), selectedParts.get("Color"), selectedParts.get("Engine"), selectedParts.get("GearBox"), selectedParts.get("Seats"), selectedParts.get("Airco"), selectedParts.get("Wheels"));
          //int orderId = orderController.placeCarOrder(chosenCarModelId, selectedParts.get("Body"), selectedParts.get("Color"), selectedParts.get("Engine"), selectedParts.get("GearBox"), selectedParts.get("Seats"), selectedParts.get("Airco"), selectedParts.get("Wheels"));
          // TODO: Hoe krijg ik hier de orderId ???

          // TODO: updates the production schedule

          LocalDateTime estimatedCompletionDate = LocalDateTime.now().plusDays(3);
          // TODO: de echte estimatedCompletionDate activeren
          //estimatedCompletionDate = orderController.getCompletionDate(0);

          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
          System.out.println("The estimated completion date for the order is: " + estimatedCompletionDate.format(formatter));

          GarageHolderActionsOverviewUI.run(orderController, assemblyLineController);
        }
        case 0 -> OrderNewCarActionUI.run(orderController, assemblyLineController);
      }
    } while (action < 0 || action > 1);
  }

  public static Integer displayChooseCarModel(List<String> carModels) {
    Scanner scanner = new Scanner(System.in);
    int carModelId;

    do {
      System.out.println("Please choose the model:");
      carModels.forEach(System.out::println);
      carModelId = scanner.nextInt();
    } while (carModelId < 0 || carModelId > carModels.size() - 1);

    //TODO: is deze regex the way forward?
    System.out.println("Chosen model: " + carModels.get(carModelId).split(": ")[1]);
    return carModelId;
  }


  public static Map<String, String> displayOrderingForm(Map<String, List<String>> parts) {
    Map<String, String> selectedParts = new HashMap<>();

    for (Map.Entry<String, List<String>> entry : parts.entrySet()) {
      Scanner scanner = new Scanner(System.in);
      int optionIndex;
      List<String> options = entry.getValue();

      System.out.println("Choose the option for: " + entry.getKey());

      do {
        for (int i = 0; i < options.size(); i++) {
          System.out.println(i + ": " + options.get(i));
        }
        optionIndex = scanner.nextInt();
      } while (optionIndex < 0 || optionIndex > options.size() - 1);

      selectedParts.put(entry.getKey(), entry.getValue().get(optionIndex));
      System.out.println("Chosen: " + options.get(optionIndex));
    }
    return selectedParts;
  }
}
