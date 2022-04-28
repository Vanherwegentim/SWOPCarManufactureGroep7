package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.GarageHolder;
import be.kuleuven.assemassit.Repositories.GarageHolderRepository;

import java.util.Map;
import java.util.stream.Collectors;

public class LoginController {
  final GarageHolderRepository garageHolderRepository;
  GarageHolder loggedInGarageHolder;
  /**
   * @PeerObject
   */
  final ControllerFactory controllerFactory;

  public LoginController(GarageHolderRepository garageHolderRepository, ControllerFactory controllerFactory) {
    this.garageHolderRepository = garageHolderRepository;
    this.controllerFactory = controllerFactory;
  }

  /**
   * log in a garage holder
   *
   * @param garageHolderId
   * @throws IllegalArgumentException garageHolderId is below 0 | garageHolderId < 0
   */
  public void logInGarageHolder(int garageHolderId) {
    if (garageHolderId < 0)
      throw new IllegalArgumentException("GarageHolderId cannot be smaller than 0");

    try {
      this.loggedInGarageHolder = garageHolderRepository.getGarageHolders().get(garageHolderId);
      controllerFactory.loginGarageHolder(loggedInGarageHolder);
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("There is no garage holder with the given id");
    }
  }

  /**
   * Log off the garage holder
   */
  public void logOffGarageHolder() {
    this.loggedInGarageHolder = null;
    controllerFactory.logoutCarMechanic();
  }

  /**
   * @return a map with garage holders, the key is the id and de value is the name of the garage holder
   */
  public Map<Integer, String> giveGarageHolders() {
    return this.garageHolderRepository.getGarageHolders()
      .stream()
      .collect(Collectors.toMap(GarageHolder::getId, GarageHolder::getName));
  }

  public String giveLoggedInGarageHolderName() {
    if (this.loggedInGarageHolder == null)
      throw new IllegalStateException();
    return this.loggedInGarageHolder.getName();
  }
}
