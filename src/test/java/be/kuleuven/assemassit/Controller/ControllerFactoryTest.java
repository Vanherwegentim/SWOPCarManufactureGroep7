package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Controller.ControllerFactory.ControllerFactory;
import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ControllerFactoryTest {
  private AssemblyLine assemblyLine;
  private CarManufactoringCompany carManufactoringCompany;


  @BeforeEach
  public void beforeEach() {
    this.assemblyLine = new AssemblyLine();
    this.carManufactoringCompany = new CarManufactoringCompany(LocalTime.of(6, 0), LocalTime.of(22, 0), assemblyLine);
  }

  @Test
  public void controllerTest() {
    ControllerFactory controllerFactory = new ControllerFactory();
    OrderNewCarController orderNewCarController = controllerFactory.createOrderNewCarController();
    AssemblyLineController assemblyLineController = controllerFactory.createAssemblyLineController();
    assert orderNewCarController instanceof OrderNewCarController;
    assertNotNull(orderNewCarController);

    assert assemblyLineController instanceof AssemblyLineController;
    assertNotNull(assemblyLineController);
  }
}
