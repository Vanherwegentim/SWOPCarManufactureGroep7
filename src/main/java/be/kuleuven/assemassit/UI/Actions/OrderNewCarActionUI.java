package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.AssemblyLineController;
import be.kuleuven.assemassit.Controller.OrderController;
import be.kuleuven.assemassit.UI.Actions.Overviews.GarageHolderActionsOverviewUI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class OrderNewCarActionUI {
  public static void run(OrderController orderController, AssemblyLineController assemblyLineController) {
    Scanner scanner = new Scanner(System.in);
    int choice;

    do {
      System.out.println();
      System.out.println("Orders placed by: " + orderController.giveLoggedInGarageHolderName());

      System.out.println("Pending:");
      displayCarOrders(orderController.givePendingCarOrders());

      System.out.println("History:");
      displayCarOrders(orderController.giveCompletedCarOrders());

      System.out.println();
      System.out.println("Please choose an action:");
      System.out.println(" 1: Place a new order");
      System.out.println("-1: Go back");

      choice = scanner.nextInt();

      switch (choice) {
        case 1 -> {
          int chosenCarModelId = displayChooseCarModel(orderController.giveListOfCarModels());

          Map<String, List<String>> parts = orderController.givePossibleOptionsOfCarModel(chosenCarModelId);

          // TODO: replace with private method
          //Map<String, String> selectedParts = displayOrderingForm(orderController.givePossibleOptionsOfCarModel(chosenCarModelId));
          Map<String, String> selectedParts = new HashMap<>();
          for (Map.Entry<String, List<String>> part : parts.entrySet()) {
            Scanner scanner2 = new Scanner(System.in);
            int optionIndex;
            List<String> options = part.getValue();
            do {
              System.out.println();
              System.out.println("Choose the option for: " + part.getKey());
              for (int i = 0; i < options.size(); i++) {
                System.out.println(String.format("%2d", i) + ": " + options.get(i));
              }
              System.out.println("-1: Cancel placing the order");
              optionIndex = scanner2.nextInt();
              if (optionIndex == -1) {
                OrderNewCarActionUI.run(orderController, assemblyLineController);
                break;
              }
            } while (optionIndex < 0 || optionIndex > options.size() - 1);
            selectedParts.put(part.getKey(), part.getValue().get(optionIndex));
            System.out.println("Chosen: " + options.get(optionIndex));
          }

          LocalDateTime estimatedCompletionDate = orderController.placeCarOrder(chosenCarModelId, selectedParts.get("Body"), selectedParts.get("Color"), selectedParts.get("Engine"), selectedParts.get("GearBox"), selectedParts.get("Seats"), selectedParts.get("Airco"), selectedParts.get("Wheels"));

          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' H:mm");
          System.out.println();
          System.out.println("The estimated completion date for the order is: " + estimatedCompletionDate.format(formatter));

          System.out.println();
          System.out.println("Press ENTER to continue...");
          Scanner scanner3 = new Scanner(System.in);
          scanner3.nextLine();

          OrderNewCarActionUI.run(orderController, assemblyLineController);
        }
        case -1 -> GarageHolderActionsOverviewUI.run(orderController, assemblyLineController);
      }
    } while (choice != -1 && (choice != 1));
  }

  private static void displayCarOrders(List<String> carOrders) {
    for (int i = 0; i < carOrders.size(); i++) {
      System.out.println(String.format("%2d", (i + 1)) + ": " + carOrders.get(i));
    }
  }

  private static Integer displayChooseCarModel(Map<Integer, String> carModels) {
    Scanner scanner = new Scanner(System.in);
    int carModelId;

    do {
      System.out.println();
      System.out.println("Please choose the model:");
      carModels.forEach((id, name) -> System.out.println(String.format("%2d", id) + ": " + name));
      carModelId = scanner.nextInt();
    } while (!carModels.containsKey(carModelId));

    System.out.println("Chosen model: " + carModels.get(carModelId));
    return carModelId;
  }


  private static Map<String, String> displayOrderingForm(Map<String, List<String>> parts) {
    Map<String, String> selectedParts = new HashMap<>();

    for (Map.Entry<String, List<String>> part : parts.entrySet()) {
      Scanner scanner = new Scanner(System.in);
      int optionIndex;
      List<String> options = part.getValue();

      System.out.println("Choose the option for: " + part.getKey());

      do {
        for (int i = 0; i < options.size(); i++) {
          System.out.println(String.format("%2d", i) + ": " + options.get(i));
        }
        System.out.println("-1: Cancel placing the order");

        optionIndex = scanner.nextInt();

        if (optionIndex == -1) {
          // TODO: stop the code and return to menu
        }
      } while (optionIndex < 0 || optionIndex > options.size() - 1);

      selectedParts.put(part.getKey(), part.getValue().get(optionIndex));
      System.out.println("Chosen: " + options.get(optionIndex));
    }
    return selectedParts;
  }
}
