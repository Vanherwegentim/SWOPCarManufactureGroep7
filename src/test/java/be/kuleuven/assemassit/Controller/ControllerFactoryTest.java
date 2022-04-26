package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.GarageHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerFactoryTest {
  private ControllerFactory controllerFactory;
  private GarageHolder garageHolder;


  @BeforeEach
  public void beforeEach() {
    this.controllerFactory = new ControllerFactory();
    this.garageHolder = new GarageHolder(0, "Joe Lamb");

  }

  @Test
  public void controllerTest() {
    assertEquals(LocalTime.of(6, 0), controllerFactory.getCarManufactoringCompany().getOpeningTime());
    assertEquals(LocalTime.of(22, 0), controllerFactory.getCarManufactoringCompany().getClosingTime());

    assertEquals(controllerFactory.getControllerFactoryState(), new ControllerFactoryLoginState());
  }

  @Test
  public void loginGarageHolderTest() {
    assertThrows(IllegalArgumentException.class, () -> controllerFactory.loginGarageHolder(null));
    controllerFactory.loginGarageHolder(garageHolder);
    assertEquals(controllerFactory.getLoggedInGarageHolder(), garageHolder);
    assertEquals(controllerFactory.getControllerFactoryState(), new ControllerFactoryGarageHolderState());
  }

  @Test
  public void logOutGarageHolderTest() {
    controllerFactory.logoutGarageHolder();
    assertTrue(controllerFactory.getLoggedInGarageHolder() == null);
    assertEquals(controllerFactory.getControllerFactoryState(), (new ControllerFactoryLoginState()));
  }

  @Test
  public void loginManager() {
    controllerFactory.loginManager();
    assertEquals(controllerFactory.getControllerFactoryState(), (new ControllerFactoryManagerState()));
  }

  @Test
  public void logOutManagerTest() {
    controllerFactory.logoutManager();
    assertEquals(controllerFactory.getControllerFactoryState(), (new ControllerFactoryLoginState()));
  }

  @Test
  public void loginCarMechanic() {
    controllerFactory.loginCarMechanic();
    assertEquals(controllerFactory.getControllerFactoryState(), (new ControllerFactoryCarMechanicState()));
  }

  @Test
  public void logOutCarMechanicTest() {
    controllerFactory.logoutCarMechanic();
    assertEquals(controllerFactory.getControllerFactoryState(), (new ControllerFactoryLoginState()));
  }

  @Test
  public void createOrderNewCarControllerTest() {
    assertThrows(IllegalStateException.class, () -> controllerFactory.createOrderNewCarController());

    controllerFactory.loginGarageHolder(garageHolder);

    assertEquals("OrderNewCarController", controllerFactory.createOrderNewCarController().getClass().getSimpleName());
    controllerFactory.logoutGarageHolder();
  }

  @Test
  public void createCheckOrderDetailsControllerTest() {
    assertThrows(IllegalStateException.class, () -> controllerFactory.createCheckOrderDetailsController());

    controllerFactory.loginGarageHolder(garageHolder);


    assertEquals("CheckOrderDetailsController", controllerFactory.createCheckOrderDetailsController().getClass().getSimpleName());
    controllerFactory.logoutGarageHolder();

  }

  @Test
  public void createCheckAssemblyLineStatusControllerTest() {
    assertThrows(IllegalStateException.class, () -> controllerFactory.createCheckAssemblyLineStatusController());
    controllerFactory.loginCarMechanic();
    assertEquals("CheckAssemblyLineStatusController", controllerFactory.createCheckAssemblyLineStatusController().getClass().getSimpleName());
    assertTrue(controllerFactory.createCheckAssemblyLineStatusController() instanceof CheckAssemblyLineStatusController);
    controllerFactory.logoutCarMechanic();
  }

  @Test
  public void createCheckProductionStatisticsControllerTest() {
    assertThrows(IllegalStateException.class, () -> controllerFactory.createCheckProductionStatisticsController());
    controllerFactory.loginManager();
    assertEquals("CheckProductionStatisticsController", controllerFactory.createCheckProductionStatisticsController().getClass().getSimpleName());
    assertTrue(controllerFactory.createCheckProductionStatisticsController() instanceof CheckProductionStatisticsController);

    controllerFactory.logoutManager();
  }

  @Test
  public void createPerformyAssemblyTasksController() {
    assertThrows(IllegalStateException.class, () -> controllerFactory.createPerformAssemblyTasksController());
    controllerFactory.loginCarMechanic();
    assertEquals("PerformAssemblyTasksController", controllerFactory.createPerformAssemblyTasksController().getClass().getSimpleName());
    assertTrue(controllerFactory.createPerformAssemblyTasksController() instanceof PerformAssemblyTasksController);

    controllerFactory.logoutCarMechanic();
  }


}
