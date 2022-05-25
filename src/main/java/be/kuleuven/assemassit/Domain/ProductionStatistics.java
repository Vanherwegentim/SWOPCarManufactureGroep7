package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Helper.CustomTime;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ProductionStatistics {

  private List<CarAssemblyProcess> finishedCars;

  public ProductionStatistics(List<CarAssemblyProcess> finishedCars) {
    this.finishedCars = finishedCars;
  }

  /**
   * Creates a map of cars per day, this can be used for further calculations
   *
   * @return a map of cars for each work day
   * @creates | result
   */
  public Map<LocalDate, Double> createCarsPerDayMap() {
    //Create a map that counts how many cars were made every day(LocalDate)
    List<LocalDateTime> dateTimeList = finishedCars.stream().map(carAssemblyProcess -> carAssemblyProcess.getCarOrder().getCompletionTime()).collect(Collectors.toList());

    Map<LocalDate, Double> carsPerDayMap = new HashMap<>();
    for (LocalDateTime localDateTime : dateTimeList) {
      if (carsPerDayMap.containsKey(localDateTime.toLocalDate())) {
        double i = carsPerDayMap.get(localDateTime.toLocalDate());
        i = i + 1.0;
        carsPerDayMap.replace(localDateTime.toLocalDate(), i);
      } else {
        carsPerDayMap.put(localDateTime.toLocalDate(), 1.0);
      }
    }
    return carsPerDayMap;
  }

  /**
   * Calculates the average cars in a single work day
   *
   * @return the average of cars in a single work day
   * @inspects | this
   */
  public double averageCarsInADay() {
    Map<LocalDate, Double> carsPerDayMap = createCarsPerDayMap();
    if (carsPerDayMap.size() == 0) {
      return 0;
    } else {
      double total = carsPerDayMap.values().stream().mapToDouble(v -> v).sum();
      return total / carsPerDayMap.size();
    }
  }

  /**
   * Calculates the median of cars in a single work day
   *
   * @return the median of cars in a single work day
   * @inspects | this
   */
  public double medianCarsInADay() {
    Map<LocalDate, Double> carsPerDayMap = createCarsPerDayMap();
    ArrayList<Double> numList = new ArrayList<>();
    numList.addAll(carsPerDayMap.values());
    Collections.sort(numList);
    if (numList.size() == 0) {
      return 0;
    }
    if (numList.size() == 1) {
      return numList.get(0);
    }
    if (numList.size() == 2) {
      return (numList.get(0) + numList.get(1)) / 2;
    } else {
      if (numList.size() % 2 == 0) {
        int middle = numList.size() / 2;
        return (numList.get(middle) + numList.get(middle + 1)) / 2;
      } else {
        return numList.size() / 2.0;
      }
    }
  }

  /**
   * Returns the amount of cars produced in the previous two days
   *
   * @return the amount of cars produces in the previous two days
   * @inspects | this
   */
  public double exactCarsIn2Days() {
    Map<LocalDate, Double> carsPerDayMap = createCarsPerDayMap();
    double total = 0;
    if (carsPerDayMap.get((CustomTime.getInstance().customLocalDateNow()).minusDays(1)) != null) {
      total += carsPerDayMap.get((CustomTime.getInstance().customLocalDateNow()).minusDays(1));
    }
    if (carsPerDayMap.get((CustomTime.getInstance().customLocalDateNow()).minusDays(2)) != null) {
      total += carsPerDayMap.get((CustomTime.getInstance().customLocalDateNow()).minusDays(2));
    }
    return total;
  }


  /**
   * Returns the average of delays from all the car orders
   *
   * @return the average of delays from all the car orders
   * @inspects | this
   */
  public double averageDelayPerOrder() {
    double total;
    if (finishedCars.size() == 0) {
      return 0;
    } else {
      total = finishedCars.stream().map(carAssemblyProcess -> Duration.between(carAssemblyProcess.getCarOrder().getCompletionTime(), carAssemblyProcess.getCarOrder().getEstimatedCompletionTime())).mapToLong(Duration::toHours).asDoubleStream().sum();

      return total / finishedCars.size();
    }
  }

  /**
   * Returns the mean of delays from all the car orders
   *
   * @return the mean of delays from all the car orders
   */
  public double medianDelayPerOrder() {
    ArrayList<Double> dates = finishedCars.stream().map(carAssemblyProcess -> Duration.between(carAssemblyProcess.getCarOrder().getCompletionTime(), carAssemblyProcess.getCarOrder().getEstimatedCompletionTime())).mapToLong(Duration::toHours).asDoubleStream().boxed().collect(Collectors.toCollection(ArrayList::new));
    if (dates.size() == 0) {
      return 0;
    }
    if (dates.size() == 1) {
      return dates.get(0);
    } else {
      if (dates.size() % 2 == 0) {
        int middle = dates.size() / 2;
        return (dates.get(middle) + dates.get(middle + 1)) / 2;
      } else {
        double middle = (double) dates.size() / 2.0;
        int middleInt = (int) Math.floor(middle);
        return dates.get(middleInt);
      }
    }
  }

  /**
   * Returns the last two delays of the car company
   *
   * @return the last two delays of the car company
   * @inspects | this
   */
  public Map<LocalDate, Integer> last2Delays() {
    Map<LocalDate, Integer> delays = new HashMap<>();
    if (finishedCars.size() == 0) {
      return delays;
    }
    CarAssemblyProcess car1 = finishedCars.get(0);
    if (finishedCars.size() == 1) {
      Duration duration1 = Duration.between(car1.getCarOrder().getCompletionTime(), car1.getCarOrder().getEstimatedCompletionTime());
      long diff1 = duration1.toHours();
      int conv1 = Math.toIntExact(diff1);
      delays.put(car1.getCarOrder().getCompletionTime().toLocalDate(), conv1);
    } else {
      CarAssemblyProcess car2 = finishedCars.get(1);
      for (CarAssemblyProcess carAssemblyProcess : finishedCars) {
        if (carAssemblyProcess.getCarOrder().getCompletionTime().isAfter(car1.getCarOrder().getCompletionTime())) {
          car1 = carAssemblyProcess;
        } else if (carAssemblyProcess.getCarOrder().getCompletionTime().isAfter(car2.getCarOrder().getCompletionTime())) {
          car2 = carAssemblyProcess;
        }
      }
      Duration duration1 = Duration.between(car1.getCarOrder().getCompletionTime(), car1.getCarOrder().getEstimatedCompletionTime());
      long diff1 = duration1.toHours();
      int conv1 = Math.toIntExact(diff1);
      delays.put(car1.getCarOrder().getCompletionTime().toLocalDate(), conv1);
      Duration duration2 = Duration.between(car2.getCarOrder().getCompletionTime(), car2.getCarOrder().getEstimatedCompletionTime());
      long diff2 = duration2.toHours();
      int conv2 = Math.toIntExact(diff2);
      delays.put(car2.getCarOrder().getCompletionTime().toLocalDate(), conv2);
    }
    return delays;
  }
}
