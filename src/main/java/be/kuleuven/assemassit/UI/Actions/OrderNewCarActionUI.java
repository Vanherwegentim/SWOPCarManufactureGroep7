package be.kuleuven.assemassit.UI.Actions;

import be.kuleuven.assemassit.Controller.CheckOrderDetailsController;
import be.kuleuven.assemassit.Controller.ControllerFactoryMiddleWare;
import be.kuleuven.assemassit.Controller.OrderNewCarController;
import be.kuleuven.assemassit.Exceptions.UIException;
import be.kuleuven.assemassit.UI.IOCall;
import be.kuleuven.assemassit.UI.UI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class OrderNewCarActionUI implements UI {
  private final ControllerFactoryMiddleWare controllerFactoryMiddleWare;

  public OrderNewCarActionUI(ControllerFactoryMiddleWare controllerFactoryMiddleWare) {
    this.controllerFactoryMiddleWare = controllerFactoryMiddleWare;
  }

  private static void displayCarOrders(List<String> carOrders) {
    String output = "";
    for (int i = 0; i < carOrders.size(); i++) {
      output += carOrders.get(i);
    }
    if (output == "") {
      output = "   --no orders found--";
    }
    IOCall.out(output);
  }

  private static Optional<Integer> displayChooseCarModel(Map<Integer, String> carModels) {
    int carModelId = -2;

    do {
      try {
        IOCall.out();
        IOCall.out("Please choose the model:");
        carModels.forEach((id, name) -> IOCall.out(String.format("%2d", id) + ": " + name));
        IOCall.out("-1: Go back");

        carModelId = IOCall.in();

        if (carModelId == -1) return Optional.empty();
      } catch (InputMismatchException | UIException ex) {
        IOCall.out("ERROR, only integers are allowed here!");
        String dump = IOCall.next();
      }
    } while (!carModels.containsKey(carModelId));

    IOCall.out("Chosen model: " + carModels.get(carModelId));
    return Optional.of(carModelId);
  }

  private static Optional<Map<String, String>> displayOrderingForm(Map<String, List<String>> parts) {
    Map<String, String> selectedParts = new HashMap<>();

    for (Map.Entry<String, List<String>> part : parts.entrySet()) {
      int optionIndex = -2;
      List<String> options = part.getValue();

      IOCall.out();
      IOCall.out("Choose the option for: " + part.getKey());

      do {
        try {
          for (int i = 0; i < options.size(); i++) {
            IOCall.out(String.format("%2d", i) + ": " + options.get(i));
          }
          IOCall.out("-1: Cancel placing the order");

          optionIndex = IOCall.in();

          if (optionIndex == -1) {
            return Optional.empty();
          }
        } catch (InputMismatchException | UIException ex) {
          IOCall.out("ERROR, only integers are allowed here!");
          IOCall.next();
        }

      } while (optionIndex < 0 || optionIndex > options.size() - 1);

      selectedParts.put(part.getKey(), part.getValue().get(optionIndex));
      IOCall.out("Chosen: " + options.get(optionIndex));
    }
    return Optional.of(selectedParts);
  }

  public void run() {
    OrderNewCarController orderNewCarController = controllerFactoryMiddleWare.createOrderNewCarController();
    CheckOrderDetailsController checkOrderDetailsController = controllerFactoryMiddleWare.createCheckOrderDetailsController();

    int choice = 0;

    do {
      try {
        // 1. The system presents an overview of the orders placed by the user

        IOCall.out();
        IOCall.out("Orders placed by: " + orderNewCarController.giveLoggedInGarageHolderName());

        IOCall.out("Pending:");
        displayCarOrders(checkOrderDetailsController.givePendingCarOrders());

        IOCall.out("History:");
        displayCarOrders(checkOrderDetailsController.giveCompletedCarOrders());


        // 2. The user indicates he wants to place a new car order
        IOCall.out();
        IOCall.out("Please choose an action:");
        IOCall.out(" 1: Place a new order");
        IOCall.out(" 2: Create batch of 4");
        IOCall.out("-1: Go back");

        choice = IOCall.in();

        switch (choice) {
          case 1 -> {

            // 3. The system shows a list of available car models
            // 4. The user indicates the car model he wishes to order.
            Optional<Integer> chosenCarModelIdOptional = displayChooseCarModel(orderNewCarController.giveListOfCarModels());

            if (chosenCarModelIdOptional.isEmpty())
              continue;

            int chosenCarModelId = chosenCarModelIdOptional.get();

            // 5. The system displays the ordering form.
            // 6. The user completes the ordering form.
            Map<String, List<String>> parts = orderNewCarController.givePossibleOptionsOfCarModel(chosenCarModelId);

            int choice2 = -1;
            do {
              try {
                Optional<Map<String, String>> selectedPartsOptional = displayOrderingForm(parts);

                if (selectedPartsOptional.isEmpty())
                  continue;


                Map<String, String> selectedParts = selectedPartsOptional.get();

                // 7. The system stores the new order and updates the production schedule.
                // 8. The system presents an estimated completion date for the new order


                LocalDateTime estimatedCompletionDate = orderNewCarController.placeCarOrderAndReturnEstimatedCompletionTime(chosenCarModelId, selectedParts.get("Body"), selectedParts.get("Color"), selectedParts.get("Engine"), selectedParts.get("GearBox"), selectedParts.get("Seats"), selectedParts.get("Airco"), selectedParts.get("Wheels"), selectedParts.get("Spoiler"));

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                IOCall.out();
                IOCall.out("The estimated completion date for the order is: " + estimatedCompletionDate.format(formatter));

                IOCall.out();
                IOCall.out("Press ENTER to continue...");
                IOCall.waitForConfirmation();
                choice2 = -1;

              } catch (IllegalArgumentException e) {

                do {
                  IOCall.out();
                  IOCall.out();
                  IOCall.out("Invalid configuration detected");
                  IOCall.out();
                  IOCall.out(" 1: Retry creating an order");
                  IOCall.out("-1: Go back");

                  choice2 = IOCall.in();
                } while (choice2 != 1 && choice2 != -1);
              } catch (InputMismatchException | UIException ex) {
                IOCall.out("ERROR, only integers are allowed here!");
                IOCall.next();
              }
            } while (choice2 != -1);
          }
          case 2 -> {
            orderNewCarController.placeCarOrder(0, "BREAK", "RED", "STANDARD", "FIVE_SPEED_MANUAL", "LEATHER_BLACK", "MANUAL", "COMFORT", "NO_SPOILER");
            orderNewCarController.placeCarOrder(0, "BREAK", "RED", "STANDARD", "FIVE_SPEED_MANUAL", "LEATHER_BLACK", "MANUAL", "COMFORT", "NO_SPOILER");
            orderNewCarController.placeCarOrder(0, "BREAK", "RED", "STANDARD", "FIVE_SPEED_MANUAL", "LEATHER_BLACK", "MANUAL", "COMFORT", "NO_SPOILER");
            orderNewCarController.placeCarOrder(0, "BREAK", "RED", "STANDARD", "FIVE_SPEED_MANUAL", "LEATHER_BLACK", "MANUAL", "COMFORT", "NO_SPOILER");

            IOCall.out("Ordered");
          }
          // Alternate flow: The user indicates he wants to leave the overview.
        }
      } catch (InputMismatchException | UIException ex) {
        IOCall.out("ERROR, only integers are allowed here!");
        IOCall.next();
      }
    } while (choice != -1 && (choice != 1));
  }
}
