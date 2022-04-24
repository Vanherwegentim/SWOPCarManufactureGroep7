package be.kuleuven.assemassit.Domain.Repositories;

import be.kuleuven.assemassit.Domain.GarageHolder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GarageHolderRepository {
  private List<GarageHolder> garageHolders;

  public List<GarageHolder> getGarageHolders() {
    if (garageHolders == null)
      readGarageHoldersFromFile();

    return List.copyOf(garageHolders);
  }

  private void readGarageHoldersFromFile() {
    List<GarageHolder> garageHolders = new ArrayList<>();
    try (Scanner input = new Scanner(new FileReader("src/main/resources/garage-holders.txt"))) {

      while (input.hasNext()) {
        int id = input.nextInt();
        String name = input.nextLine().substring(1);
        garageHolders.add(new GarageHolder(id, name));
      }

      this.garageHolders = garageHolders;
    } catch (FileNotFoundException e) {
      System.out.println("The application experienced unexpected behaviour, please contact the system administrator");
    }
  }
}
