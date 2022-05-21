package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.CarManufactoringCompany;
import be.kuleuven.assemassit.Domain.GarageHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ControllerFactoryTest {
  private ControllerFactoryMiddleWare controllerFactoryMiddleWare;
  private GarageHolder garageHolder;


  @BeforeEach
  public void beforeEach() {
    this.controllerFactoryMiddleWare = new ControllerFactoryMiddleWare();
    this.garageHolder = new GarageHolder(0, "Joe Lamb");

  }

  @Test
  public void controllerTest() {
    assertEquals(LocalTime.of(6, 0), controllerFactoryMiddleWare.getCarManufactoringCompany().getOpeningTime());
    assertEquals(LocalTime.of(22, 0), controllerFactoryMiddleWare.getCarManufactoringCompany().getClosingTime());

    assertEquals(controllerFactoryMiddleWare.getControllerFactoryMiddleWareState(), new ControllerFactoryMiddleWareLoginState());
  }

  @Test
  public void loginGarageHolderTest() {
    assertThrows(IllegalArgumentException.class, () -> controllerFactoryMiddleWare.loginGarageHolder(null));
    controllerFactoryMiddleWare.loginGarageHolder(garageHolder);
    assertEquals(controllerFactoryMiddleWare.getLoggedInGarageHolder(), garageHolder);
    assertEquals(controllerFactoryMiddleWare.getControllerFactoryMiddleWareState(), new ControllerFactoryMiddleWareGarageHolderState());


  }

  @Test
  public void logOutGarageHolderTest() {
    controllerFactoryMiddleWare.logoutGarageHolder();
    assertTrue(controllerFactoryMiddleWare.getLoggedInGarageHolder() == null);
    assertEquals(controllerFactoryMiddleWare.getControllerFactoryMiddleWareState(), (new ControllerFactoryMiddleWareLoginState()));
  }

  @Test
  public void loginManager() {
    controllerFactoryMiddleWare.loginManager();
    assertEquals(controllerFactoryMiddleWare.getControllerFactoryMiddleWareState(), (new ControllerFactoryMiddleWareManagerState()));
  }

  @Test
  public void logOutManagerTest() {
    controllerFactoryMiddleWare.logoutManager();
    assertEquals(controllerFactoryMiddleWare.getControllerFactoryMiddleWareState(), (new ControllerFactoryMiddleWareLoginState()));
  }

  @Test
  public void loginCarMechanic() {
    controllerFactoryMiddleWare.loginCarMechanic();
    assertEquals(controllerFactoryMiddleWare.getControllerFactoryMiddleWareState(), (new ControllerFactoryMiddleWareCarMechanicState()));
  }

  @Test
  public void logOutCarMechanicTest() {
    controllerFactoryMiddleWare.logoutCarMechanic();
    assertEquals(controllerFactoryMiddleWare.getControllerFactoryMiddleWareState(), (new ControllerFactoryMiddleWareLoginState()));
  }

  @Test
  public void createOrderNewCarControllerTest() {
    assertThrows(IllegalStateException.class, () -> controllerFactoryMiddleWare.createOrderNewCarController());

    controllerFactoryMiddleWare.loginGarageHolder(garageHolder);

    assertEquals("OrderNewCarController", controllerFactoryMiddleWare.createOrderNewCarController().getClass().getSimpleName());
    controllerFactoryMiddleWare.logoutGarageHolder();
  }

  @Test
  public void createOrderNewCarControllerTest2() {
    CarManufactoringCompany carManufactoringCompany = new CarManufactoringCompany(LocalTime.of(6, 0), LocalTime.of(22, 0), new AssemblyLine());

    assertThrows(IllegalStateException.class, () -> controllerFactoryMiddleWare.createOrderNewCarController(carManufactoringCompany, garageHolder));

    controllerFactoryMiddleWare.loginGarageHolder(garageHolder);

    assertEquals("OrderNewCarController", controllerFactoryMiddleWare.createOrderNewCarController(carManufactoringCompany, garageHolder).getClass().getSimpleName());
    controllerFactoryMiddleWare.logoutGarageHolder();
  }

  @Test
  public void createCheckOrderDetailsControllerTest() {
    assertThrows(IllegalStateException.class, () -> controllerFactoryMiddleWare.createCheckOrderDetailsController());

    controllerFactoryMiddleWare.loginGarageHolder(garageHolder);


    assertEquals("CheckOrderDetailsController", controllerFactoryMiddleWare.createCheckOrderDetailsController().getClass().getSimpleName());
    controllerFactoryMiddleWare.logoutGarageHolder();

  }

  @Test
  public void createCheckAssemblyLineStatusControllerTest() {
    assertThrows(IllegalStateException.class, () -> controllerFactoryMiddleWare.createCheckAssemblyLineStatusController());
    controllerFactoryMiddleWare.loginCarMechanic();
    assertEquals("CheckAssemblyLineStatusController", controllerFactoryMiddleWare.createCheckAssemblyLineStatusController().getClass().getSimpleName());
    assertTrue(controllerFactoryMiddleWare.createCheckAssemblyLineStatusController() instanceof CheckAssemblyLineStatusController);
    controllerFactoryMiddleWare.logoutCarMechanic();
  }

  @Test
  public void createCheckProductionStatisticsControllerTest() {
    assertThrows(IllegalStateException.class, () -> controllerFactoryMiddleWare.createCheckProductionStatisticsController());
    controllerFactoryMiddleWare.loginManager();
    assertEquals("CheckProductionStatisticsController", controllerFactoryMiddleWare.createCheckProductionStatisticsController().getClass().getSimpleName());
    assertTrue(controllerFactoryMiddleWare.createCheckProductionStatisticsController() instanceof CheckProductionStatisticsController);

    controllerFactoryMiddleWare.logoutManager();
  }

  @Test
  public void createPerformyAssemblyTasksController() {
    assertThrows(IllegalStateException.class, () -> controllerFactoryMiddleWare.createPerformAssemblyTasksController());
    controllerFactoryMiddleWare.loginCarMechanic();
    assertEquals("PerformAssemblyTasksController", controllerFactoryMiddleWare.createPerformAssemblyTasksController().getClass().getSimpleName());
    assertTrue(controllerFactoryMiddleWare.createPerformAssemblyTasksController() instanceof PerformAssemblyTasksController);

    controllerFactoryMiddleWare.logoutCarMechanic();
  }


  @Test
  void giveLoggedInGarageHolderNameTest() {
    controllerFactoryMiddleWare.loginGarageHolder(garageHolder);
    assertEquals(controllerFactoryMiddleWare.giveLoggedInGarageHolderName(), "Joe Lamb");

  }

  @Test
  void createLoginControllerTest() {
    controllerFactoryMiddleWare.loginManager();
    assertThrows(IllegalStateException.class, () -> controllerFactoryMiddleWare.createLoginController());
    controllerFactoryMiddleWare.logoutManager();
    assertEquals("LoginController", controllerFactoryMiddleWare.createLoginController().getClass().getSimpleName());
    assertTrue(controllerFactoryMiddleWare.createLoginController() instanceof LoginController);

  }

  @Test
  void createAdaptSchedulingAlgorithmControllerTest() {
    assertThrows(IllegalStateException.class, () -> controllerFactoryMiddleWare.createAdaptSchedulingAlgorithmController());
    controllerFactoryMiddleWare.loginManager();
    assertEquals("AdaptSchedulingAlgorithmController", controllerFactoryMiddleWare.createAdaptSchedulingAlgorithmController().getClass().getSimpleName());
    assertTrue(controllerFactoryMiddleWare.createAdaptSchedulingAlgorithmController() instanceof AdaptSchedulingAlgorithmController);

    controllerFactoryMiddleWare.logoutManager();
  }

  @Test
  void getAssemblyLineTest() {
    assertEquals("AssemblyLine", controllerFactoryMiddleWare.getAssemblyLine().getClass().getSimpleName());
  }
}
