package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.Enums.CarOption;
import be.kuleuven.assemassit.Domain.Scheduling.FIFOScheduling;
import be.kuleuven.assemassit.Domain.Scheduling.SpecificationBatchScheduling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdaptSchedulingAlgorithmController {

  private final AssemblyLine assemblyLine;
  private Map<Integer, List<CarOption>> mapOfCarOptions;

  protected AdaptSchedulingAlgorithmController(AssemblyLine assemblyLine) {
    this.assemblyLine = assemblyLine;
  }

  /**
   * Get a list of names of scheduling algorithms that can be chosen
   *
   * @return list of strings that represent algorithm names
   */
  public List<String> giveSchedulingAlgorithmNames() {
    return assemblyLine.giveSchedulingAlgorithmNames();
  }

  /**
   * Get the name of the current scheduling algorithm
   *
   * @return name of the current scheduling algorithm
   */
  public String giveCurrentSchedulingAlgorithmName() {
    return assemblyLine
      .getSchedulingAlgorithm()
      .getClass()
      .getSimpleName();
  }

  /**
   * Get a map of possible batches that can be used for the batch scheduling algorithm
   * this list is automatically generated
   *
   * @return map of possible batches
   */
  public Map<Integer, List<String>> givePossibleBatches() {
    this.mapOfCarOptions = new HashMap<>();
    Map<Integer, List<String>> mapOfCarOptionsStrings = new HashMap<>();
    for (int i = 0; i < assemblyLine.givePossibleBatchCars().size(); i++) {
      List<CarOption> carOptions = assemblyLine.givePossibleBatchCars().get(i).giveListOfCarOptions();
      mapOfCarOptions.put(i, carOptions);
      List<String> carOptionsString = new ArrayList<>();
      for (CarOption carOption : carOptions) {
        carOptionsString.add(carOption.toString());
      }
      mapOfCarOptions.put(i, carOptions);
      mapOfCarOptionsStrings.put(i, carOptionsString);
    }
    return mapOfCarOptionsStrings;
  }


  /**
   * Change the current scheduling algorithm to the FIFO algorithm
   */
  public void changeAlgorithmToFIFO() {
    assemblyLine.setSchedulingAlgorithm(new FIFOScheduling());
  }

  /**
   * Change the current scheduling algorithm to the Specification batch algorithm
   *
   * @param specificationId the id of the batch, this is necessary for the algorithm, this ID can be achieved by calling
   *                        the givePossibleBatches() method
   */
  public void changeAlgorithmToSpecificationBatch(int specificationId) {

    if (this.mapOfCarOptions == null || this.mapOfCarOptions.isEmpty()) {
      throw new IllegalStateException("The map of car options can not be null or empty.");
    }

    List<CarOption> batch = this.mapOfCarOptions.get(specificationId);

    if (batch == null) {
      throw new IllegalArgumentException("No batch was found with the given ID");
    }

    assemblyLine.setSchedulingAlgorithm(new SpecificationBatchScheduling(batch));
  }
}
