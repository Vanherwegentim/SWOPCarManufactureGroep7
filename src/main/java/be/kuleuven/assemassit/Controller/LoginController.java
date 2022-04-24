package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.GarageHolder;
import be.kuleuven.assemassit.Domain.Repositories.GarageHolderRepository;

import java.util.Map;
import java.util.stream.Collectors;

public class LoginController {
  GarageHolderRepository garageHolderRepository;
  GarageHolder loggedInGarageHolder;

  public LoginController(GarageHolderRepository garageHolderRepository) {
    this.garageHolderRepository = garageHolderRepository;
  }

  /**
   * log in a garage holder
   *
   * @param garageHolderId
   * @throws IllegalArgumentException garageHolderId is below 0 | garageHolderId < 0
   */
  public GarageHolder logInGarageHolder(int garageHolderId) {
    if (garageHolderId < 0)
      throw new IllegalArgumentException("GarageHolderId cannot be smaller than 0");

    try {
      this.loggedInGarageHolder = garageHolderRepository.getGarageHolders().get(garageHolderId);
      return loggedInGarageHolder;
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("There is no garage holder with the given id");
    }
  }

  /**
   * Log off the garage holder
   */
  public void logOffGarageHolder() {
    this.loggedInGarageHolder = null;
  }

  /**
   * @return a map with garage holders, the key is the id and de value is the name of the garage holder
   */
  public Map<Integer, String> giveGarageHolders() {
    return this.garageHolderRepository.getGarageHolders()
      .stream()
      .collect(Collectors.toMap(GarageHolder::getId, GarageHolder::getName));
  }
}
