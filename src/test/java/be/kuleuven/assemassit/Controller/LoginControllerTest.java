package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.GarageHolder;
import be.kuleuven.assemassit.Domain.Repositories.GarageHolderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginControllerTest {

  private LoginController loginController;
  private GarageHolderRepository mockedGarageHolderRepository;
  private ControllerFactory mockedControllerFactory;
  private GarageHolder mockedGarageHolder;

  @BeforeEach
  public void beforeEach() {
    mockedGarageHolder = mock(GarageHolder.class);
    when(mockedGarageHolder.getId()).thenReturn(0);
    when(mockedGarageHolder.getName()).thenReturn("WolksVagen Garage Lokeren BVBA NV");

    mockedGarageHolderRepository = mock(GarageHolderRepository.class);
    when(mockedGarageHolderRepository.getGarageHolders()).thenReturn(Arrays.asList(mockedGarageHolder));

    mockedControllerFactory = mock(ControllerFactory.class);

    loginController = new LoginController(mockedGarageHolderRepository, mockedControllerFactory);
  }

  @Test
  public void logInGarageHolderTest_succeeds() {
    assertAll(() -> loginController.logInGarageHolder(0));
  }

  @Test
  public void logInGarageHolderTest_throws() {
    assertThrows(IllegalArgumentException.class, () -> loginController.logInGarageHolder(-2));
    assertThrows(IllegalArgumentException.class, () -> loginController.logInGarageHolder(200));
  }

  @Test
  public void logOffTest() {
    loginController.logInGarageHolder(0);
    assertEquals("WolksVagen Garage Lokeren BVBA NV", loginController.giveLoggedInGarageHolderName());
    assertAll(loginController::logOffGarageHolder);
    assertThrows(IllegalStateException.class, loginController::giveLoggedInGarageHolderName);
  }

  @Test
  public void giveGarageHoldersTest() {
    Map<Integer, String> garageHolders = loginController.giveGarageHolders();
    assertEquals(1, garageHolders.size());
    assertEquals("WolksVagen Garage Lokeren BVBA NV", garageHolders.get(0));
  }
}
