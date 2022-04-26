package be.kuleuven.assemassit.Repositories;

import be.kuleuven.assemassit.Domain.CarModel;
import be.kuleuven.assemassit.Domain.Enums.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CarModelRepository {
  private List<CarModel> carModels;

  public List<CarModel> getCarModels() {
    if (carModels == null) {
      carModels = new ArrayList<>();
      CarModel carModelA = new CarModel(
        0,
        "Model A",
        Arrays.asList(Wheel.WINTER, Wheel.COMFORT, Wheel.SPORT),
        Arrays.asList(Gearbox.SIX_SPEED_MANUAL, Gearbox.FIVE_SPEED_MANUAL, Gearbox.FIVE_SPEED_AUTOMATIC),
        Arrays.asList(Seat.LEATHER_WHITE, Seat.LEATHER_BLACK, Seat.VINYL_GREY),
        Arrays.asList(Body.SEDAN, Body.BREAK),
        Arrays.asList(Color.RED, Color.BLUE, Color.BLACK, Color.WHITE),
        Arrays.asList(Engine.STANDARD, Engine.PERFORMANCE),
        Arrays.asList(Airco.MANUAL, Airco.AUTOMATIC, Airco.NO_AIRCO),
        Arrays.asList(Spoiler.NO_SPOILER),
        50);
      CarModel carModelB = new CarModel(
        1,
        "Model B",
        Arrays.asList(Wheel.WINTER, Wheel.COMFORT, Wheel.SPORT),
        Arrays.asList(Gearbox.SIX_SPEED_MANUAL, Gearbox.FIVE_SPEED_AUTOMATIC),
        Arrays.asList(Seat.LEATHER_WHITE, Seat.LEATHER_BLACK, Seat.VINYL_GREY),
        Arrays.asList(Body.SEDAN, Body.BREAK, Body.SPORT),
        Arrays.asList(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW),
        Arrays.asList(Engine.STANDARD, Engine.PERFORMANCE, Engine.ULTRA),
        Arrays.asList(Airco.MANUAL, Airco.AUTOMATIC, Airco.NO_AIRCO),
        Arrays.asList(Spoiler.LOW, Spoiler.NO_SPOILER),
        70);
      CarModel carModelC = new CarModel(2,
        "Model C",
        Arrays.asList(Wheel.WINTER, Wheel.SPORT),
        Arrays.asList(Gearbox.SIX_SPEED_MANUAL),
        Arrays.asList(Seat.LEATHER_WHITE, Seat.LEATHER_BLACK),
        Arrays.asList(Body.SPORT),
        Arrays.asList(Color.BLACK, Color.WHITE),
        Arrays.asList(Engine.PERFORMANCE, Engine.ULTRA),
        Arrays.asList(Airco.MANUAL, Airco.AUTOMATIC, Airco.NO_AIRCO),
        Arrays.asList(Spoiler.LOW, Spoiler.HIGH),
        60);
      carModels.add(carModelA);
      carModels.add(carModelB);
      carModels.add(carModelC);
    }
    //readCarModelsFromFile();

    return List.copyOf(carModels);
  }

//  private void readCarModelsFromFile() {
//    List<CarModel> carModels = new ArrayList<>();
//    try (Scanner input = new Scanner(new FileReader("src/main/resources/car-models.txt"))) {
//
//      while (input.hasNext()) {
//        int id = input.nextInt();
//        String name = input.nextLine();
//        carModels.add(
//          new CarModel(
//            id,
//            name,
//            Arrays.asList(Wheel.values()),
//            Arrays.asList(Gearbox.values()),
//            Arrays.asList(Seat.values()),
//            Arrays.asList(Body.values()),
//            Arrays.asList(Color.values()),
//            Arrays.asList(Engine.values()),
//            Arrays.asList(Airco.values())
//          ));
//      }
//
//      this.carModels = carModels;
//    } catch (FileNotFoundException e) {
//      System.out.println("The application experienced unexpected behaviour, please contact the system administrator");
//    }
//  }
}
