package be.kuleuven.assemassit;

import be.kuleuven.assemassit.Controller.AdaptSchedulingAlgorithmController;
import be.kuleuven.assemassit.Controller.ControllerFactory;
import be.kuleuven.assemassit.Controller.OrderNewCarController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AdaptSchedulingAlgorithmTest {

  private ControllerFactory factory;
  private AdaptSchedulingAlgorithmController adaptSchedulingAlgorithmController;

  @BeforeEach
  public void beforeEach() {
    factory = new ControllerFactory();
    factory.createLoginController().logInGarageHolder(0);

    for (int i = 0; i < 3; i++) {
      createBatchWithColor(i);
    }
    factory.logoutCarMechanic();

    factory.loginManager();
    adaptSchedulingAlgorithmController = factory.createAdaptSchedulingAlgorithmController();

  }

  @Test
  public void adaptSchedulingAlgorithm_MainSuccessScenario_UserSelectsFIFO() {

    // 1. The user wants to select an alternative scheduling algorithm
    // 2. The system shows the available algorithms, as well as the currently selected algorithm.

    assertEquals("FIFOScheduling", adaptSchedulingAlgorithmController.giveCurrentSchedulingAlgorithmName());

    String expectedAlgorithmNames = "FIFOScheduling" + System.lineSeparator() + "SpecificationBatchScheduling" + System.lineSeparator();
    String actualAlgorithmNames = adaptSchedulingAlgorithmController.giveSchedulingAlgorithmNames().stream().reduce("", (s1, s2) -> s1 + s2 + System.lineSeparator());

    assertEquals(expectedAlgorithmNames, actualAlgorithmNames);

    // 3. The user selects the new scheduling algorithm to be used.

    adaptSchedulingAlgorithmController.changeAlgorithmToFIFO();

    // 4. The system applies the new scheduling algorithm4 and updates its state accordingly.

    assertEquals("FIFOScheduling", adaptSchedulingAlgorithmController.giveCurrentSchedulingAlgorithmName());
  }

  @Test
  public void adaptSchedulingAlgorithm_AlternateFlow_UserSelectsSpecificationBatchScheduling() {
    // 1. The user wants to select an alternative scheduling algorithm
    // 2. The system shows the available algorithms, as well as the currently selected algorithm.

    assertEquals("FIFOScheduling", adaptSchedulingAlgorithmController.giveCurrentSchedulingAlgorithmName());

    String expectedAlgorithmNames = "FIFOScheduling" + System.lineSeparator() + "SpecificationBatchScheduling" + System.lineSeparator();
    String actualAlgorithmNames = adaptSchedulingAlgorithmController.giveSchedulingAlgorithmNames().stream().reduce("", (s1, s2) -> s1 + s2 + System.lineSeparator());

    assertEquals(expectedAlgorithmNames, actualAlgorithmNames);

    // 3. (a) The user indicates he wants to use the Specification Batch algorithm.
    // 4. The system shows a list of the sets of car options for which more than 3 orders are pending in the production queue

    Map<Integer, List<String>> possibleBatches = adaptSchedulingAlgorithmController.givePossibleBatches();

    String actualPossibleBatches = "";

    for (int index : possibleBatches.keySet()) {
      actualPossibleBatches += index + System.lineSeparator();
      actualPossibleBatches += possibleBatches.get(index).stream().reduce("", (s1, s2) -> s1 += s2 + System.lineSeparator());
    }

    String expectedPossibleBatches = "0" + System.lineSeparator() +
      "SEDAN" + System.lineSeparator() +
      "RED" + System.lineSeparator() +
      "STANDARD" + System.lineSeparator() +
      "SIX_SPEED_MANUAL" + System.lineSeparator() +
      "LEATHER_WHITE" + System.lineSeparator() +
      "MANUAL" + System.lineSeparator() +
      "WINTER" + System.lineSeparator() +
      "NO_SPOILER" + System.lineSeparator() +
      "1" + System.lineSeparator() +
      "SEDAN" + System.lineSeparator() +
      "BLUE" + System.lineSeparator() +
      "STANDARD" + System.lineSeparator() +
      "SIX_SPEED_MANUAL" + System.lineSeparator() +
      "LEATHER_WHITE" + System.lineSeparator() +
      "MANUAL" + System.lineSeparator() +
      "WINTER" + System.lineSeparator() +
      "NO_SPOILER" + System.lineSeparator() +
      "2" + System.lineSeparator() +
      "SEDAN" + System.lineSeparator() +
      "BLACK" + System.lineSeparator() +
      "STANDARD" + System.lineSeparator() +
      "SIX_SPEED_MANUAL" + System.lineSeparator() +
      "LEATHER_WHITE" + System.lineSeparator() +
      "MANUAL" + System.lineSeparator() +
      "WINTER" + System.lineSeparator() +
      "NO_SPOILER" + System.lineSeparator();

    assertEquals(expectedPossibleBatches, actualPossibleBatches);

    // 5. The user selects one of these sets for batch processing

    adaptSchedulingAlgorithmController.changeAlgorithmToSpecificationBatch(0);


    // 6. The use case continues in step 4.
    // 4. The system applies the new scheduling algorithm and updates its state accordingly.

    assertEquals("SpecificationBatchScheduling", adaptSchedulingAlgorithmController.giveCurrentSchedulingAlgorithmName());

  }


  @Test
  public void adaptSchedulingAlgorithm_AlternateFlow_UserSelectsSpecificationBatchSchedulingAndThenFIFO() {

    // 1. The user wants to select an alternative scheduling algorithm
    // 2. The system shows the available algorithms, as well as the currently selected algorithm.

    assertEquals("FIFOScheduling", adaptSchedulingAlgorithmController.giveCurrentSchedulingAlgorithmName());

    String expectedAlgorithmNames = "FIFOScheduling" + System.lineSeparator() + "SpecificationBatchScheduling" + System.lineSeparator();
    String actualAlgorithmNames = adaptSchedulingAlgorithmController.giveSchedulingAlgorithmNames().stream().reduce("", (s1, s2) -> s1 + s2 + System.lineSeparator());

    assertEquals(expectedAlgorithmNames, actualAlgorithmNames);

    // 3. (a) The user indicates he wants to use the Specification Batch algorithm.
    // 4. The system shows a list of the sets of car options for which more than 3 orders are pending in the production queue

    Map<Integer, List<String>> possibleBatches = adaptSchedulingAlgorithmController.givePossibleBatches();

    String actualPossibleBatches = "";

    for (int index : possibleBatches.keySet()) {
      actualPossibleBatches += index + System.lineSeparator();
      actualPossibleBatches += possibleBatches.get(index).stream().reduce("", (s1, s2) -> s1 += s2 + System.lineSeparator());
    }

    String expectedPossibleBatches = "0" + System.lineSeparator() +
      "SEDAN" + System.lineSeparator() +
      "RED" + System.lineSeparator() +
      "STANDARD" + System.lineSeparator() +
      "SIX_SPEED_MANUAL" + System.lineSeparator() +
      "LEATHER_WHITE" + System.lineSeparator() +
      "MANUAL" + System.lineSeparator() +
      "WINTER" + System.lineSeparator() +
      "NO_SPOILER" + System.lineSeparator() +
      "1" + System.lineSeparator() +
      "SEDAN" + System.lineSeparator() +
      "BLUE" + System.lineSeparator() +
      "STANDARD" + System.lineSeparator() +
      "SIX_SPEED_MANUAL" + System.lineSeparator() +
      "LEATHER_WHITE" + System.lineSeparator() +
      "MANUAL" + System.lineSeparator() +
      "WINTER" + System.lineSeparator() +
      "NO_SPOILER" + System.lineSeparator() +
      "2" + System.lineSeparator() +
      "SEDAN" + System.lineSeparator() +
      "BLACK" + System.lineSeparator() +
      "STANDARD" + System.lineSeparator() +
      "SIX_SPEED_MANUAL" + System.lineSeparator() +
      "LEATHER_WHITE" + System.lineSeparator() +
      "MANUAL" + System.lineSeparator() +
      "WINTER" + System.lineSeparator() +
      "NO_SPOILER" + System.lineSeparator();

    assertEquals(expectedPossibleBatches, actualPossibleBatches);

    // 5. The user selects one of these sets for batch processing

    adaptSchedulingAlgorithmController.changeAlgorithmToSpecificationBatch(0);


    // 6. The use case continues in step 4.
    // 4. The system applies the new scheduling algorithm and updates its state accordingly.

    assertEquals("SpecificationBatchScheduling", adaptSchedulingAlgorithmController.giveCurrentSchedulingAlgorithmName());


    // 1. The user wants to select an alternative scheduling algorithm
    // 2. The system shows the available algorithms, as well as the currently selected algorithm.

    expectedAlgorithmNames = "FIFOScheduling" + System.lineSeparator() + "SpecificationBatchScheduling" + System.lineSeparator();
    actualAlgorithmNames = adaptSchedulingAlgorithmController.giveSchedulingAlgorithmNames().stream().reduce("", (s1, s2) -> s1 + s2 + System.lineSeparator());

    assertEquals(expectedAlgorithmNames, actualAlgorithmNames);

    // 3. The user selects the new scheduling algorithm to be used.

    adaptSchedulingAlgorithmController.changeAlgorithmToFIFO();

    // 4. The system applies the new scheduling algorithm4 and updates its state accordingly.

    assertEquals("FIFOScheduling", adaptSchedulingAlgorithmController.giveCurrentSchedulingAlgorithmName());
  }

  @Test
  public void adaptSchedulingAlgorithm_AlternateFlow_UserSelectsSpecificationBatchSchedulingAndThenFIFO_UserMakesMistakes() {

    // 1. The user wants to select an alternative scheduling algorithm
    // 2. The system shows the available algorithms, as well as the currently selected algorithm.

    assertEquals("FIFOScheduling", adaptSchedulingAlgorithmController.giveCurrentSchedulingAlgorithmName());

    String expectedAlgorithmNames = "FIFOScheduling" + System.lineSeparator() + "SpecificationBatchScheduling" + System.lineSeparator();
    String actualAlgorithmNames = adaptSchedulingAlgorithmController.giveSchedulingAlgorithmNames().stream().reduce("", (s1, s2) -> s1 + s2 + System.lineSeparator());

    assertEquals(expectedAlgorithmNames, actualAlgorithmNames);

    // 3. (a) The user indicates he wants to use the Specification Batch algorithm.
    // 4. The system shows a list of the sets of car options for which more than 3 orders are pending in the production queue

    Map<Integer, List<String>> possibleBatches = adaptSchedulingAlgorithmController.givePossibleBatches();

    String actualPossibleBatches = "";

    for (int index : possibleBatches.keySet()) {
      actualPossibleBatches += index + System.lineSeparator();
      actualPossibleBatches += possibleBatches.get(index).stream().reduce("", (s1, s2) -> s1 += s2 + System.lineSeparator());
    }

    String expectedPossibleBatches = "0" + System.lineSeparator() +
      "SEDAN" + System.lineSeparator() +
      "RED" + System.lineSeparator() +
      "STANDARD" + System.lineSeparator() +
      "SIX_SPEED_MANUAL" + System.lineSeparator() +
      "LEATHER_WHITE" + System.lineSeparator() +
      "MANUAL" + System.lineSeparator() +
      "WINTER" + System.lineSeparator() +
      "NO_SPOILER" + System.lineSeparator() +
      "1" + System.lineSeparator() +
      "SEDAN" + System.lineSeparator() +
      "BLUE" + System.lineSeparator() +
      "STANDARD" + System.lineSeparator() +
      "SIX_SPEED_MANUAL" + System.lineSeparator() +
      "LEATHER_WHITE" + System.lineSeparator() +
      "MANUAL" + System.lineSeparator() +
      "WINTER" + System.lineSeparator() +
      "NO_SPOILER" + System.lineSeparator() +
      "2" + System.lineSeparator() +
      "SEDAN" + System.lineSeparator() +
      "BLACK" + System.lineSeparator() +
      "STANDARD" + System.lineSeparator() +
      "SIX_SPEED_MANUAL" + System.lineSeparator() +
      "LEATHER_WHITE" + System.lineSeparator() +
      "MANUAL" + System.lineSeparator() +
      "WINTER" + System.lineSeparator() +
      "NO_SPOILER" + System.lineSeparator();

    assertEquals(expectedPossibleBatches, actualPossibleBatches);

    // 5. The user selects one of these sets for batch processing but first inserts an illegal ID

    assertThrows(IllegalArgumentException.class, () -> adaptSchedulingAlgorithmController.changeAlgorithmToSpecificationBatch(8885));


    adaptSchedulingAlgorithmController.changeAlgorithmToSpecificationBatch(0);

    // 6. The use case continues in step 4.
    // 4. The system applies the new scheduling algorithm and updates its state accordingly.

    assertEquals("SpecificationBatchScheduling", adaptSchedulingAlgorithmController.giveCurrentSchedulingAlgorithmName());


    // 1. The user wants to select an alternative scheduling algorithm
    // 2. The system shows the available algorithms, as well as the currently selected algorithm.

    expectedAlgorithmNames = "FIFOScheduling" + System.lineSeparator() + "SpecificationBatchScheduling" + System.lineSeparator();
    actualAlgorithmNames = adaptSchedulingAlgorithmController.giveSchedulingAlgorithmNames().stream().reduce("", (s1, s2) -> s1 + s2 + System.lineSeparator());

    assertEquals(expectedAlgorithmNames, actualAlgorithmNames);

    // 3. The user selects the new scheduling algorithm to be used.

    adaptSchedulingAlgorithmController.changeAlgorithmToFIFO();

    // 4. The system applies the new scheduling algorithm4 and updates its state accordingly.

    assertEquals("FIFOScheduling", adaptSchedulingAlgorithmController.giveCurrentSchedulingAlgorithmName());
  }


  private void createBatchWithColor(int colorId) {
    factory.createLoginController().logInGarageHolder(0);
    OrderNewCarController orderNewCarController = factory.createOrderNewCarController();

    for (int i = 0; i < 3; i++) {
      placeCarOrderWithColor(orderNewCarController, 0, i);
    }

    factory.logoutCarMechanic();
    factory.loginManager();
  }

  private int placeCarOrderWithColor(OrderNewCarController orderNewCarController, int modelID, int colorID) {
    Map<String, List<String>> possibleOptionsOfCarModel = orderNewCarController.givePossibleOptionsOfCarModel(modelID);
    return orderNewCarController.placeCarOrder(
      modelID,
      possibleOptionsOfCarModel.get("Body").get(0),
      possibleOptionsOfCarModel.get("Color").get(colorID),
      possibleOptionsOfCarModel.get("Engine").get(0),
      possibleOptionsOfCarModel.get("GearBox").get(0),
      possibleOptionsOfCarModel.get("Seats").get(0),
      possibleOptionsOfCarModel.get("Airco").get(0),
      possibleOptionsOfCarModel.get("Wheels").get(0),
      possibleOptionsOfCarModel.get("Spoiler").get(0));
  }

}
