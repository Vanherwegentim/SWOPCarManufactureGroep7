package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.GarageHolder;
import be.kuleuven.assemassit.Domain.Scheduling.FIFOScheduling;
import be.kuleuven.assemassit.Domain.Scheduling.SpecificationBatchScheduling;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AdaptSchedulingAlgorithmControllerTest {
  AssemblyLine assemblyLine = new AssemblyLine();
  ControllerFactory controllerFactory = new ControllerFactory();
  AdaptSchedulingAlgorithmController algorithmController;
  OrderNewCarController orderNewCarController;

  @BeforeEach
  public void beforeEach() {
    GarageHolder garageHolder = new GarageHolder(0, "Joe Lamb");
    controllerFactory.loginGarageHolder(garageHolder);
    orderNewCarController = controllerFactory.createOrderNewCarController();
    //Add 1 extra placeCarOrder because one move is already executed when adding a CarOrder
    orderNewCarController.placeCarOrder(0, "BREAK", "RED", "STANDARD", "FIVE_SPEED_MANUAL", "LEATHER_BLACK", "MANUAL", "COMFORT", "NO_SPOILER");
    orderNewCarController.placeCarOrder(0, "BREAK", "RED", "STANDARD", "FIVE_SPEED_MANUAL", "LEATHER_BLACK", "MANUAL", "COMFORT", "NO_SPOILER");
    orderNewCarController.placeCarOrder(0, "BREAK", "RED", "STANDARD", "FIVE_SPEED_MANUAL", "LEATHER_BLACK", "MANUAL", "COMFORT", "NO_SPOILER");
    orderNewCarController.placeCarOrder(0, "BREAK", "RED", "STANDARD", "FIVE_SPEED_MANUAL", "LEATHER_BLACK", "MANUAL", "COMFORT", "NO_SPOILER");

    controllerFactory.logoutGarageHolder();
    controllerFactory.loginManager();
    algorithmController = controllerFactory.createAdaptSchedulingAlgorithmController();
  }

  @Test
  public void giveSchedulingAlgorithmNames() {
    assertEquals(List.of(FIFOScheduling.class.getSimpleName(),
      SpecificationBatchScheduling.class.getSimpleName()), algorithmController.giveSchedulingAlgorithmNames());
  }

  @Test
  public void giveCurrentSchedulingAlgorithmNameTest() {
    assertEquals("FIFOScheduling", algorithmController.giveCurrentSchedulingAlgorithmName());
  }

  @Test
  public void givePossibleBatchesTest() {
    assertEquals(Map.of(0, List.of("BREAK", "RED", "STANDARD", "FIVE_SPEED_MANUAL", "LEATHER_BLACK", "MANUAL", "COMFORT", "NO_SPOILER")), algorithmController.givePossibleBatches());
  }

  @Test
  public void changeAlgorithmToFIFOTest() {
    algorithmController.changeAlgorithmToFIFO();
    assertEquals(List.of(FIFOScheduling.class.getSimpleName(),
      SpecificationBatchScheduling.class.getSimpleName()), algorithmController.giveSchedulingAlgorithmNames());
  }

  @Test
  public void changeAlgorithmToSpecificationBatchTest() {
    assertThrows(IllegalStateException.class, () -> algorithmController.changeAlgorithmToSpecificationBatch(0));
    algorithmController.givePossibleBatches();
    algorithmController.changeAlgorithmToSpecificationBatch(0);
    assertEquals(List.of(FIFOScheduling.class.getSimpleName(),
      SpecificationBatchScheduling.class.getSimpleName()), algorithmController.giveSchedulingAlgorithmNames());
  }
}
