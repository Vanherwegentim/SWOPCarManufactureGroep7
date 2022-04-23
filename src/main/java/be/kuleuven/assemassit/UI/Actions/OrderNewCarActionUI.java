package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.OrderNewCarController;
import be.kuleuven.assemassit.UI.Actions.GarageHolderActions.GarageHolderActionsOverviewUI;
import be.kuleuven.assemassit.UI.UI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class OrderNewCarActionUI implements UI {
  private OrderNewCarController orderNewCarController;
  private GarageHolderActionsOverviewUI garageHolderActionsOverviewUI;

  public OrderNewCarActionUI(OrderNewCarController orderNewCarController) {
    this.orderNewCarController = orderNewCarController;
    this.garageHolderActionsOverviewUI = new GarageHolderActionsOverviewUI(this.orderNewCarController);
  }

  private static void displayCarOrders(List<String> carOrders) {
    for (int i = 0; i < carOrders.size(); i++) {
      System.out.println(String.format("%2d", (i + 1)) + ": " + carOrders.get(i));
    }
  }

  private static Optional<Integer> displayChooseCarModel(Map<Integer, String> carModels) {
    Scanner scanner = new Scanner(System.in);
    int carModelId;

    do {
      System.out.println();
      System.out.println("Please choose the model:");
      carModels.forEach((id, name) -> System.out.println(String.format("%2d", id) + ": " + name));
      System.out.println("-1: Go back");

      carModelId = scanner.nextInt();

      if (carModelId == -1) return Optional.empty();

    } while (!carModels.containsKey(carModelId));

    System.out.println("Chosen model: " + carModels.get(carModelId));
    return Optional.of(carModelId);
  }

  private static Optional<Map<String, String>> displayOrderingForm(Map<String, List<String>> parts) {
    Map<String, String> selectedParts = new HashMap<>();

    for (Map.Entry<String, List<String>> part : parts.entrySet()) {
      Scanner scanner = new Scanner(System.in);
      int optionIndex;
      List<String> options = part.getValue();

      System.out.println();
      System.out.println("Choose the option for: " + part.getKey());

      do {
        for (int i = 0; i < options.size(); i++) {
          System.out.println(String.format("%2d", i) + ": " + options.get(i));
        }
        System.out.println("-1: Cancel placing the order");

        optionIndex = scanner.nextInt();

        if (optionIndex == -1) {
          return Optional.empty();
        }
      } while (optionIndex < 0 || optionIndex > options.size() - 1);

      selectedParts.put(part.getKey(), part.getValue().get(optionIndex));
      System.out.println("Chosen: " + options.get(optionIndex));
    }
    return Optional.of(selectedParts);
  }

  public void run() {
    Scanner scanner = new Scanner(System.in);
    int choice;

    do {
      System.out.println();
      System.out.println("Orders placed by: " + orderNewCarController.giveLoggedInGarageHolderName());

      System.out.println("Pending:");
      displayCarOrders(orderNewCarController.givePendingCarOrders());

      System.out.println("History:");
      displayCarOrders(orderNewCarController.giveCompletedCarOrders());

      System.out.println();
      System.out.println("Please choose an action:");
      System.out.println(" 1: Place a new order");
      System.out.println("-1: Go back");

      choice = scanner.nextInt();

      switch (choice) {
        case 1 -> {
          Optional<Integer> chosenCarModelIdOptional = displayChooseCarModel(orderNewCarController.giveListOfCarModels());

          if (chosenCarModelIdOptional.isEmpty()) {
            run(); //TODO check if dit is de manier juiste redirect naar zichzelf
            return;
          }

          int chosenCarModelId = chosenCarModelIdOptional.get();

          Map<String, List<String>> parts = orderNewCarController.givePossibleOptionsOfCarModel(chosenCarModelId);

          Optional<Map<String, String>> selectedPartsOptional = displayOrderingForm(parts);

          if (selectedPartsOptional.isEmpty()) {
            run(); //TODO check if dit is de manier juiste redirect naar zichzelf
            return;
          }
          Map<String, String> selectedParts = selectedPartsOptional.get();

          LocalDateTime estimatedCompletionDate = orderNewCarController.placeCarOrder(chosenCarModelId, selectedParts.get("Body"), selectedParts.get("Color"), selectedParts.get("Engine"), selectedParts.get("GearBox"), selectedParts.get("Seats"), selectedParts.get("Airco"), selectedParts.get("Wheels"));

          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' H:mm");
          System.out.println();
          System.out.println("The estimated completion date for the order is: " + estimatedCompletionDate.format(formatter));

          System.out.println();
          System.out.println("Press ENTER to continue...");
          Scanner scanner3 = new Scanner(System.in);
          scanner3.nextLine();

          run(); //TODO check if dit is de manier juiste redirect naar zichzelf
        }
        case -1 -> this.garageHolderActionsOverviewUI.run();
      }
    } while (choice != -1 && (choice != 1));
  }
}
