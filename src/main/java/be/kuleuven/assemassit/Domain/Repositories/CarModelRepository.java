package be.kuleuven.assemassit.Domain.Repositories;

import be.kuleuven.assemassit.Domain.CarModel;
import be.kuleuven.assemassit.Domain.Enums.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CarModelRepository {
  private List<CarModel> carModels;

  public List<CarModel> getCarModels() {
    if (carModels == null)
      readCarModelsFromFile();

    return List.copyOf(carModels);
  }

  private void readCarModelsFromFile() {
    List<CarModel> carModels = new ArrayList<>();
    try (Scanner input = new Scanner(new FileReader("src/main/resources/car-models.txt"))) {

      while (input.hasNext()) {
        int id = input.nextInt();
        String name = input.nextLine();
        carModels.add(
          new CarModel(
            id,
            name,
            Arrays.asList(Wheel.values()),
            Arrays.asList(Gearbox.values()),
            Arrays.asList(Seat.values()),
            Arrays.asList(Body.values()),
            Arrays.asList(Color.values()),
            Arrays.asList(Engine.values()),
            Arrays.asList(Airco.values())
          ));
      }

      this.carModels = carModels;
    } catch (FileNotFoundException e) {
      System.out.println("The application experienced unexpected behaviour, please contact the system administrator");
    }
  }
}
