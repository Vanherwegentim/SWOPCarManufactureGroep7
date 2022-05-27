package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.CarOption;

import java.util.ArrayList;
import java.util.List;

/**
 * @immutable
 * @invar | getRestrictedCarOptions() != null && getRestrictedCarOptions().size() > 0
 */
public class CarOptionRestriction {
  /**
   * @invar | restrictedCarOptions != null
   * @invar | restrictedCarOptions.size() > 0
   */
  private final List<CarOption> restrictedCarOptions;

  /**
   * @param restrictedCarOptions a list of car options
   * @pre | restrictedCarOptions != null && restrictedCarOptions.size() > 0
   * @post | getRestrictedCarOptions().stream().allMatch(c -> restrictedCarOptions.contains(c)) &&
   * | restrictedCarOptions.stream().allMatch(c -> getRestrictedCarOptions().contains(c))
   */
  public CarOptionRestriction(List<CarOption> restrictedCarOptions) {
    this.restrictedCarOptions = new ArrayList<>(restrictedCarOptions);
  }

  public List<CarOption> getRestrictedCarOptions() {
    return new ArrayList<>(this.restrictedCarOptions);
  }
}
