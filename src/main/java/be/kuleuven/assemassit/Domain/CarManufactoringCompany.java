package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Repositories.CarModelRepository;

import java.util.List;
import java.util.Optional;

public class CarManufactoringCompany {
  private List<CarModel> carModels;
  private AssemblyLine assemblyLine;
  private CarModelRepository carModelRepository;

  public CarManufactoringCompany() {
    this.carModelRepository = new CarModelRepository();
    this.carModels = carModelRepository.getCarModels();
    this.assemblyLine = new AssemblyLine();
  }

  public List<CarModel> getCarModels() {
    return List.copyOf(carModels);
  }

  public CarModel giveCarModelWithId(int id) {
    Optional<CarModel> carModel = this.carModels
      .stream()
      .filter(cm -> cm.getId() == id)
      .findFirst();

    if (!carModel.isPresent())
      throw new IllegalArgumentException("CarModel not found");

    return carModel.get();
  }
}
