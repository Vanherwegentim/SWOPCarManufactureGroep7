package be.kuleuven.assemassit.Domain.Repositories;

import be.kuleuven.assemassit.Domain.CarModel;
import be.kuleuven.assemassit.Domain.Enums.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarModelRepository {
  private List<CarModel> carModels;

  public List<CarModel> getCarModels() {
    if (carModels == null)
      readCarModelsFromFile();

    return List.copyOf(carModels);
  }

  //TODO: if we get more Car Models with other options, we need to refactor this method
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
          List.of(Wheel.values()),
          List.of(Gearbox.values()),
          List.of(Seat.values()),
          List.of(Body.values()),
          List.of(Color.values()),
          List.of(Engine.values()),
          List.of(Airco.values())
        ));
      }

      this.carModels = carModels;
    } catch (FileNotFoundException e) {
      System.out.println("The application experienced unexpected behaviour, please contact the system administrator");
    }
  }
}
