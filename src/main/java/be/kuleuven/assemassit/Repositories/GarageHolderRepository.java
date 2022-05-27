package be.kuleuven.assemassit.Repositories;

import be.kuleuven.assemassit.Domain.GarageHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GarageHolderRepository {
  private List<GarageHolder> garageHolders;

  /**
   * Get a list of the current garage holder
   *
   * @return the list of garage holder
   * @creates | result
   */
  public List<GarageHolder> getGarageHolders() {
    if (garageHolders == null)
      readGarageHoldersFromFile();

    return List.copyOf(garageHolders);
  }

  private void readGarageHoldersFromFile() {
    List<GarageHolder> garageHolders = new ArrayList<>();
    try (Scanner input = new Scanner(getClass().getResourceAsStream("/garage-holders.txt"))) {

      while (input.hasNext()) {
        int id = input.nextInt();
        String name = input.nextLine().substring(1);
        garageHolders.add(new GarageHolder(id, name));
      }

      this.garageHolders = garageHolders;
    } catch (Exception e) {
      System.out.println(e);
      System.out.println("The application experienced unexpected behaviour, please contact the system administrator");
    }
  }
}
