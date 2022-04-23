package be.kuleuven.assemassit.Controller;

import be.kuleuven.assemassit.Domain.AssemblyLine;
import be.kuleuven.assemassit.Domain.Car;
import be.kuleuven.assemassit.Domain.CarAssemblyProcess;
import be.kuleuven.assemassit.Domain.Enums.CarOption;
import be.kuleuven.assemassit.Domain.Scheduling.FIFOScheduling;
import be.kuleuven.assemassit.Domain.Scheduling.SpecificationBatchScheduling;

import java.util.ArrayList;
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


  // it is necessary to split it in multiple methods because of different arguments
  public void changeAlgorithmToFIFO() {
    assemblyLine.setSchedulingAlgorithm(new FIFOScheduling());
  }

  public void changeAlgorithmToSpecificationBatch(List<String> specification) {
    assemblyLine.setSchedulingAlgorithm(new SpecificationBatchScheduling(transferToCarOptionList(specification)));
  }

  private List<CarOption> transferToCarOptionList(List<String> specification) {
    ArrayList<CarAssemblyProcess> batchForQueue = new ArrayList<>();
    for (CarAssemblyProcess carAssemblyProcess : assemblyLine.getCarAssemblyProcessesQueue()) {
      Car car = carAssemblyProcess.getCarOrder().getCar();
      ArrayList<String> carOptions = new ArrayList<>();

      for (CarOption carOption : car.giveListOfCarOptions()) {
        carOptions.add(carOption.toString());
      }
      if (carOptions.containsAll(specification)) {
        batchForQueue.add(carAssemblyProcess);
      }
    }
    return batchForQueue;
  }


}
