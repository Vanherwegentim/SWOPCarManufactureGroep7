package be.kuleuven.assemassit.Domain.Repositories;

import be.kuleuven.assemassit.Domain.CarModel;
import be.kuleuven.assemassit.Domain.Enums.*;

import java.util.List;

public class CarModelRepository {
  private List<CarModel> carModels;

  public List<CarModel> getCarModels() {
    if (carModels == null) {
      CarModel carModelA = new CarModel(
        0,
        "Model A",
        List.of(Wheel.WINTER, Wheel.COMFORT, Wheel.SPORT),
        List.of(Gearbox.SIX_SPEED_MANUAL, Gearbox.FIVE_SPEED_MANUAL, Gearbox.FIVE_SPEED_AUTOMATIC),
        List.of(Seat.LEATHER_WHITE, Seat.LEATHER_BLACK, Seat.VINYL_GREY),
        List.of(Body.SEDAN, Body.BREAK),
        List.of(Color.RED, Color.BLUE, Color.BLACK, Color.WHITE),
        List.of(Engine.STANDARD, Engine.PERFORMANCE),
        List.of(Airco.MANUAL, Airco.AUTOMATIC, Airco.NO_AIRCO),
        List.of(Spoiler.NO_SPOILER));
      CarModel carModelB = new CarModel(
        1,
        "Model B",
        List.of(Wheel.WINTER, Wheel.COMFORT, Wheel.SPORT),
        List.of(Gearbox.SIX_SPEED_MANUAL, Gearbox.FIVE_SPEED_AUTOMATIC),
        List.of(Seat.LEATHER_WHITE, Seat.LEATHER_BLACK, Seat.VINYL_GREY),
        List.of(Body.SEDAN, Body.BREAK, Body.SPORT),
        List.of(Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW),
        List.of(Engine.STANDARD, Engine.PERFORMANCE, Engine.ULTRA),
        List.of(Airco.MANUAL, Airco.AUTOMATIC, Airco.NO_AIRCO),
        List.of(Spoiler.LOW, Spoiler.NO_SPOILER));
      CarModel carModelC = new CarModel(2,
        "Model C",
        List.of(Wheel.WINTER, Wheel.SPORT),
        List.of(Gearbox.SIX_SPEED_MANUAL),
        List.of(Seat.LEATHER_WHITE, Seat.LEATHER_BLACK),
        List.of(Body.SPORT),
        List.of(Color.BLACK, Color.WHITE),
        List.of(Engine.PERFORMANCE, Engine.ULTRA),
        List.of(Airco.MANUAL, Airco.AUTOMATIC, Airco.NO_AIRCO),
        List.of(Spoiler.LOW, Spoiler.HIGH));
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
