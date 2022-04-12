package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;

import java.util.List;
import java.util.stream.Collectors;

public class AdaptSchedulingAlgorithmController {

  private AssemblyLine assemblyLine;

  public AdaptSchedulingAlgorithmController(AssemblyLine assemblyLine) {
    this.assemblyLine = assemblyLine;
  }

  public List<String> giveSchedulingAlgorithmNames() {
    return assemblyLine.giveSchedulingAlgorithmNames();
  }

  public String giveCurrentSchedulingAlgorithmName() {
    return assemblyLine
      .getSchedulingAlgorithm()
      .getClass()
      .getSimpleName();
  }

  public List<List<String>> givePossibleBatches() {
    return assemblyLine.givePossibleBatchCars()
      .stream()
      .map(c -> c.giveListOfCarOptions()
        .stream()
        .map(co -> co.getClass().getSimpleName()).collect(Collectors.toList()))
      .collect(Collectors.toList());
  }
}
