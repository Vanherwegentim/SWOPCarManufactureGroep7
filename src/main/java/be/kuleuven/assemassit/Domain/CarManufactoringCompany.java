package be.kuleuven.assemassit.Domain;

import java.util.ArrayList;
import java.util.List;

public class CarManufactoringCompany {
  private List<CarModel> carModels;
  private List<AssemblyLine> assemblyLines;

  public CarManufactoringCompany() {
    carModels = new ArrayList<>();
    assemblyLines = List.of(new AssemblyLine());
  }
}
