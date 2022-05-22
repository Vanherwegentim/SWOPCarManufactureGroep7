package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.WorkPostType;
import be.kuleuven.assemassit.Domain.Helper.CustomTime;
import be.kuleuven.assemassit.Domain.Helper.Observer;
import be.kuleuven.assemassit.Domain.Helper.Subject;
import be.kuleuven.assemassit.Domain.Scheduling.FIFOScheduling;
import be.kuleuven.assemassit.Domain.Scheduling.SchedulingAlgorithm;
import be.kuleuven.assemassit.Domain.Scheduling.SpecificationBatchScheduling;
import be.kuleuven.assemassit.Repositories.OvertimeRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @mutable
 * @invar | this.getCarBodyPost() != null
 * @invar | this.getDriveTrainPost() != null
 * @invar | this.getAccessoriesPost() != null
 * @invar | this.getCarAssemblyProcess() != null
 * @invar | this.getFinishedCars() != null
 * @invar | this.getSchedulingAlgorithm() != null
 * @invar | this.giveSchedulingAlgorithmNames() != null
 * @invar | this.getObservers() != null
 */
public class AssemblyLine implements Subject {

  /**
   * @invar | carBodyPost != null
   * @invar | driveTrainPost != null
   * @invar | accessoriesPost != null
   * @invar | carAssemblyProcessesQueue != null
   * @invar | finishedCars != null
   * @invar | (startTime == null || endTime == null) || startTime.isBefore(endTime)
   * @invar | schedulingAlgorithm != null
   * @invar | observers != null
   * @representationObject
   */
  private final WorkPost carBodyPost;
  /**
   * @representationObject
   */
  private final WorkPost drivetrainPost;
  /**
   * @representationObject
   */
  private final WorkPost accessoriesPost;
  /**
   * @representationObject
   * @representationObjects
   */
  private final Queue<CarAssemblyProcess> carAssemblyProcessesQueue;
  /**
   * @representationObject
   * @representationObjects
   */
  private final List<CarAssemblyProcess> finishedCars;
  private final List<Observer> observers;
  private AssemblyLineTime assemblyLineTime;
  /**
   * @representationObject
   */
  private SchedulingAlgorithm schedulingAlgorithm;

  /**
   * @post | getCarBodyPost() != null
   * @post | getDriveTrainPost() != null
   * @post | getAccessoriesPost() != null
   * @post | getCarAssemblyProcessesQueue() != null
   * @post | getFinishedCars() != null
   * @post | getOverTimeRepository() != null
   * @post | getOverTime() >= 0
   * @post | getObservers() != null
   * @mutates | this
   */
  public AssemblyLine() {
    this.carBodyPost = new WorkPost(0, Arrays.asList(AssemblyTaskType.ASSEMBLE_CAR_BODY, AssemblyTaskType.PAINT_CAR), WorkPostType.CAR_BODY_POST, 60);
    this.drivetrainPost = new WorkPost(1, Arrays.asList(AssemblyTaskType.INSERT_ENGINE, AssemblyTaskType.INSERT_GEARBOX), WorkPostType.DRIVETRAIN_POST, 60);
    this.accessoriesPost = new WorkPost(2, Arrays.asList(AssemblyTaskType.INSTALL_AIRCO, AssemblyTaskType.INSTALL_SEATS, AssemblyTaskType.MOUNT_WHEELS, AssemblyTaskType.INSTALL_SPOILER), WorkPostType.ACCESSORIES_POST, 60);
    this.finishedCars = new ArrayList<>();
    this.carAssemblyProcessesQueue = new ArrayDeque<>();
    this.schedulingAlgorithm = new FIFOScheduling();
    this.observers = new ArrayList<>();
    this.assemblyLineTime = new AssemblyLineTime();
  }

  public WorkPost getCarBodyPost() {
    return this.carBodyPost;
  }

  public WorkPost getDrivetrainPost() {
    return this.drivetrainPost;
  }

  public WorkPost getAccessoriesPost() {
    return this.accessoriesPost;
  }

  public LocalTime getOpeningTime() {
    return assemblyLineTime.getOpeningTime();
  }

  public void setOpeningTime(LocalTime openingTime) {
    this.assemblyLineTime.setOpeningTime(openingTime);
  }

  public LocalTime getClosingTime() {
    return assemblyLineTime.getClosingTime();
  }

  public void setClosingTime(LocalTime closingTime) {
    this.assemblyLineTime.setClosingTime(closingTime);
  }

  public OvertimeRepository getOvertimeRepository() {
    return this.assemblyLineTime.getOverTimeRepository();
  }

  public int getOverTime() {
    return this.assemblyLineTime.getOvertime();
  }

  /**
   * Returns the current scheduling algorithm
   *
   * @return the current scheduling algorithm
   * @post | result != null
   */
  public SchedulingAlgorithm getSchedulingAlgorithm() {
    return this.schedulingAlgorithm;
  }

  /**
   * Sets the scheduling algorithm of the assembly line.
   *
   * @param schedulingAlgorithm
   * @throws IllegalArgumentException schedulingAlgorithm can not be null | schedulingAlgorithm == null
   * @post | this.getSchedulingAlgorithm() == schedulingAlgorithm
   */
  public void setSchedulingAlgorithm(SchedulingAlgorithm schedulingAlgorithm) {
    if (schedulingAlgorithm == null)
      throw new IllegalArgumentException("SchedulingAlgorithm can not be null");
    this.schedulingAlgorithm = schedulingAlgorithm;
  }

  /**
   * Adds a car assembly process to the queue of pending car assembly processes
   *
   * @param carAssemblyProcess
   * @mutates | this
   */
  public void addCarAssemblyProcess(CarAssemblyProcess carAssemblyProcess) {
    if (carAssemblyProcess == null) {
      throw new IllegalArgumentException("CarAssemblyProcess not found");
    }
    carAssemblyProcessesQueue.add(carAssemblyProcess);
  }

  /**
   * Collects the work posts of the assembly line in a list.
   *
   * @return the list of work posts on this assembly line
   * @post | result != null
   * @inspects | this
   * @creates | result
   */
  public List<WorkPost> getWorkPosts() {
    return Arrays.asList(carBodyPost, drivetrainPost, accessoriesPost);
  }

  public List<CarAssemblyProcess> getFinishedCars() {
    return this.finishedCars;
  }

  /**
   * Return the list of assembly tasks from an associated work post on the assembly line that are pending
   *
   * @param workPostId
   * @return The list of pending assembly tasks
   * @inspects | this
   * @creates | result
   */
  public List<AssemblyTask> givePendingAssemblyTasksFromWorkPost(int workPostId) {
    WorkPost workPost = findWorkPost(workPostId);
    return workPost.givePendingAssemblyTasks();

  }

  /**
   * Return the list of assembly tasks from an associated work post on the assembly line that are finished
   *
   * @param workPostId
   * @return The list of pending assembly tasks
   * @inspects | this
   * @creates | result
   */
  public List<AssemblyTask> giveFinishedAssemblyTasksFromWorkPost(int workPostId) {
    WorkPost workPost = findWorkPost(workPostId);
    return workPost.giveFinishedAssemblyTasks();
  }

  public AssemblyLineTime getAssemblyLineTime() {
    return assemblyLineTime;
  }

  /**
   * Complete the active assembly task of a work post
   *
   * @param workPostId the work post id
   * @param duration   the time it took him to Finish the task
   * @throws IllegalArgumentException workPostId is below 0 | workPostId < 0
   * @throws IllegalArgumentException duration is lower than 0 | duration > 180
   * @mutates | this
   */
  public void completeAssemblyTask(int workPostId, int duration) {
    if (workPostId < 0)
      throw new IllegalArgumentException("WorkPostId can not be below 0");
    if (!(duration >= 0 && duration < 180))
      throw new IllegalArgumentException("The duration of a task cannot be smaller than 0 or greater than 180");
    WorkPost workPost = findWorkPost(workPostId);
    workPost.completeAssemblyTask(duration, (CustomTime.getInstance().customLocalDateTimeNow()));
  }

  /**
   * Gives an overview of every work post with its corresponding active assembly task.
   * The corresponding active assembly task can be null if no task is active in the work post.
   *
   * @return an overview of the work posts with the corresponding active assembly task
   * @inspects | this
   * @creates | result
   */
  public HashMap<String, AssemblyTask> giveActiveTasksOverview() {
    HashMap<String, AssemblyTask> workPostStatuses = new LinkedHashMap<>();

    workPostStatuses.put("Car Body Post", carBodyPost.getActiveAssemblyTask());
    workPostStatuses.put("Drivetrain Post", drivetrainPost.getActiveAssemblyTask());
    workPostStatuses.put("Accessories Post", accessoriesPost.getActiveAssemblyTask());

    return workPostStatuses;
  }

  /**
   * Gives an overview of every work post with its corresponding pending and finished tasks.
   *
   * @return an overview of the work posts with all pending and finished assembly tasks
   * @inspects | this
   * @creates | result
   */
  public HashMap<String, List<AssemblyTask>> giveTasksOverview() {
    HashMap<String, List<AssemblyTask>> workPostPairs = new LinkedHashMap<>();
    workPostPairs.put("Car Body Post", carBodyPost.getWorkPostAssemblyTasks());
    workPostPairs.put("Drivetrain Post", drivetrainPost.getWorkPostAssemblyTasks());
    workPostPairs.put("Accessories Post", accessoriesPost.getWorkPostAssemblyTasks());
    return workPostPairs;
  }

  /**
   * Gives an overview of every work post with its corresponding pending and finished tasks in a future state
   * as if the assembly line would be moved by a responsible.
   *
   * @return an overview of the work posts with all pending and finished assembly tasks in a future state
   * @inspects | this
   * @creates | result
   */
  public HashMap<String, List<AssemblyTask>> giveFutureTasksOverview() {

    List<AssemblyTask> futureCarBodyPostAssemblyProcessAssemblyTasks = new ArrayList<>();
    if (!this.carAssemblyProcessesQueue.isEmpty()) {
      futureCarBodyPostAssemblyProcessAssemblyTasks =
        filterTasksOfSpecificTypeList(
          new ArrayList<>(this.carAssemblyProcessesQueue.peek().getAssemblyTasks()),
          this.carBodyPost.getAssemblyTaskTypes()
        );
    }

    List<AssemblyTask> futureDrivetrainPostAssemblyProcessAssemblyTasks = new ArrayList<>();
    if (this.carBodyPost.getCarAssemblyProcess() != null) {
      futureDrivetrainPostAssemblyProcessAssemblyTasks = filterTasksOfSpecificTypeList(
        new ArrayList<>(this.carBodyPost.getCarAssemblyProcess().getAssemblyTasks()),
        this.drivetrainPost.getAssemblyTaskTypes()
      );
    }

    List<AssemblyTask> futureAccessoriesPostAssemblyProcessAssemblyTasks = new ArrayList<>();
    if (this.drivetrainPost.getCarAssemblyProcess() != null) {
      futureAccessoriesPostAssemblyProcessAssemblyTasks = filterTasksOfSpecificTypeList(
        new ArrayList<>(this.drivetrainPost.getCarAssemblyProcess().getAssemblyTasks()),
        this.accessoriesPost.getAssemblyTaskTypes()
      );
    }

    HashMap<String, List<AssemblyTask>> workPostPairs = new LinkedHashMap<>();

    workPostPairs.put("Car Body Post", futureCarBodyPostAssemblyProcessAssemblyTasks);
    workPostPairs.put("Drivetrain Post", futureDrivetrainPostAssemblyProcessAssemblyTasks);
    workPostPairs.put("Accessories Post", futureAccessoriesPostAssemblyProcessAssemblyTasks);

    return workPostPairs;
  }

  /**
   * The assembly tasks from the given assembly task types are filtered from the given list of assembly tasks.
   *
   * @param allAssemblyTasks  the list of assembly tasks where the filter should be applied on
   * @param assemblyTaskTypes the list of assembly task types that has to be filtered on
   * @return
   * @throws IllegalArgumentException the list of assembly tasks is null or empty | (allAssemblyTasks == null || allAssemblyTasks.isEmpty())
   * @throws IllegalArgumentException the list of assembly tasks types is null or empty | (assemblyTaskTypes == null || assemblyTaskTypes.isEmpty())
   * @inspects | this
   * @creates | result
   */
  private List<AssemblyTask> filterTasksOfSpecificTypeList(List<AssemblyTask> allAssemblyTasks, List<AssemblyTaskType> assemblyTaskTypes) {
    if (allAssemblyTasks == null || allAssemblyTasks.isEmpty())
      throw new IllegalArgumentException("The list of assembly tasks can not be null or empty");
    if (assemblyTaskTypes == null || assemblyTaskTypes.isEmpty())
      throw new IllegalArgumentException("The list of assembly tasks types can not be null or empty");

    return allAssemblyTasks.stream().filter(task -> assemblyTaskTypes.contains(task.getAssemblyTaskType())).toList();
  }

  /**
   * Find a work post by ID
   *
   * @param workPostId the ID of the work post
   * @return the corresponding work post
   * @throws IllegalArgumentException | workPosts.stream().filter(workPost -> workPost.getId() == workPostId).findFirst().isEmpty
   * @throws IllegalArgumentException | workPostId < 0
   * @inspects | this
   */
  public WorkPost findWorkPost(int workPostId) {

    if (workPostId < 0)
      throw new IllegalArgumentException("The ID can not be lower than 0");


    List<WorkPost> workPosts = this.getWorkPosts();

    Optional<WorkPost> optionalWorkPost = workPosts.stream().filter(workPost -> workPost.getId() == workPostId).findFirst();

    if (optionalWorkPost.isEmpty()) {
      throw new IllegalArgumentException("Workpost not found");
    }
    return optionalWorkPost.get();
  }

  /**
   * Check if it is possible that the assembly line can be moved. This only possible if every work post is finished
   * with all its current tasks.
   *
   * @return true if the assembly line can be moved
   * @inspects | this
   */
  public boolean canMove() {
    List<WorkPost> workPosts = this.getWorkPosts();
    for (WorkPost workPost : workPosts) {
      if (!workPost.givePendingAssemblyTasks().isEmpty()) {
        return false;
      }
    }
    return true;
  }

  /**
   * Moves the assembly and gives the duration of the current phase.
   * The assembly process is moved from one work post to another on the assembly line.
   *
   * @param endTime the end time of the company
   * @throws IllegalStateException    when the assembly line can not be moved | !canMove()
   * @throws IllegalArgumentException minutes is below 0 | minutes < 0
   * @mutates | this
   */
  public void move(LocalTime endTime, int overtime) {

    if (!canMove())
      throw new IllegalArgumentException("AssemblyLine cannot be moved forward!");

    int newOvertime = schedulingAlgorithm.moveAssemblyLine
      (
        overtime,
        endTime,
        carAssemblyProcessesQueue,
        finishedCars,
        getWorkPosts()
      );

    if (newOvertime > 0) {
      // overtime happened so we have to inform the car manufacturing company
//      notifyObservers(newOvertime);
      assemblyLineTime.update(newOvertime);
    }
  }

  /**
   * Calculates the estimated delivery time of the latest task in the process queue.
   * The algorithm first calculates if processes still can be performed on the current day.
   * After that it checks if followings days should be used.
   * Finally it ends with the estimated delivery time of the last assembly process in the queue.
   *
   * @return the estimated delivery time for the latest task in the process queue of this assembly line
   * @inspects | this
   * @creates | result
   */
  public LocalDateTime giveEstimatedCompletionDateOfLatestProcess() {
    return this.schedulingAlgorithm
      .giveEstimatedDeliveryTime(
        this.carAssemblyProcessesQueue,
        this.carAssemblyProcessesQueue.stream().toList().get(carAssemblyProcessesQueue.size() - 1).getCarOrder().getCar().getCarModel().getWorkPostDuration() * 3,
        this.assemblyLineTime.getClosingTime(),
        this.assemblyLineTime.getOpeningTime(),
        this.carAssemblyProcessesQueue.stream().toList().get(carAssemblyProcessesQueue.size() - 1).getCarOrder().getCar().getCarModel().getWorkPostDuration()
      );
  }

  /**
   * Returns the corresponding assembly task of the corresponding work post
   *
   * @param workPostId     the id of the work post
   * @param assemblyTaskId the id of the assembly task
   * @return the corresponding assembly task from the work post
   * @throws IllegalArgumentException workPostId is smaller than 0 | workPostId < 0
   * @throws IllegalArgumentException assemblyTaskId is smaller than 0 | assemblyTaskId < 0
   * @post | result != null
   * @inspects | this
   */
  public AssemblyTask giveCarAssemblyTask(int workPostId, int assemblyTaskId) {
    if (workPostId < 0)
      throw new IllegalArgumentException("WorkPostId cannot be smaller than 0");
    if (assemblyTaskId < 0)
      throw new IllegalArgumentException("AssemblyTaskId cannot be smaller than 0");

    WorkPost workPost = findWorkPost(workPostId);
    return workPost.findAssemblyTask(assemblyTaskId);
  }

  /**
   * Set an assembly task as active in a work post
   *
   * @param workPost       the work post
   * @param assemblyTaskId the id of the assembly task
   * @throws IllegalArgumentException workPost is null | workPost == null
   * @throws IllegalArgumentException assemblyTaskId is below 0 | assemblyTaskId < 0
   */
  public void setActiveTask(WorkPost workPost, int assemblyTaskId) {
    if (workPost == null)
      throw new IllegalArgumentException("workPost can not be null");
    if (assemblyTaskId < 0)
      throw new IllegalArgumentException("assemblyTaskId can not be below 0");
    workPost.setActiveAssemblyTask(assemblyTaskId);
  }

  /**
   * @return a list of cas assembly processes
   * @creates | result
   * @inspects | this
   */
  public List<CarAssemblyProcess> getCarAssemblyProcessesQueue() {
    List<CarAssemblyProcess> carAssemblyProcessList = new ArrayList<>(carAssemblyProcessesQueue);
    if (!(drivetrainPost.getCarAssemblyProcess() == null)) {
      carAssemblyProcessList.add(drivetrainPost.getCarAssemblyProcess());

    }
    if (!(carBodyPost.getCarAssemblyProcess() == null)) {
      carAssemblyProcessList.add(drivetrainPost.getCarAssemblyProcess());

    }
    if (!(accessoriesPost.getCarAssemblyProcess() == null)) {
      carAssemblyProcessList.add(drivetrainPost.getCarAssemblyProcess());

    }
    return carAssemblyProcessList;
  }

  /**
   * @return a queue of cas assembly processes
   * @creates | result
   * @inspects | this
   */
  public Queue<CarAssemblyProcess> getCarAssemblyProcessesQueueAsQueue() {
    return carAssemblyProcessesQueue;
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

  //Can a car be ready before it's estimated completion time? if so, add an if test

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

  /**
   * Add a process (car) to the list of finished processes (cars)
   *
   * @param carAssemblyProcess the process that should be added to the list of finished processes
   * @mutates | this
   */
  public void addCarToFinishedCars(CarAssemblyProcess carAssemblyProcess) {
    finishedCars.add(carAssemblyProcess);
  }

  /**
   * Returns a list of strings that represent the possible scheduling algorithms
   *
   * @return list of algorithm names
   * @post | result != null
   */
  public List<String> giveSchedulingAlgorithmNames() {
    return List.of
      (
        FIFOScheduling.class.getSimpleName(),
        SpecificationBatchScheduling.class.getSimpleName()
      );
  }

  /**
   * @return the list of possible batches for the batch scheduling algorithm
   * @inspects | this
   */
  public List<Car> givePossibleBatchCars() {
    List<Car> cars = this.carAssemblyProcessesQueue
      .stream()
      .map(p -> p.getCarOrder().getCar())
      .collect(Collectors.toList());

    Map<Car, Integer> frequencyMap = new HashMap<>();
    for (Car c : cars) {
      Integer count = frequencyMap.get(c);
      if (count == null) {
        count = 0;
      }

      frequencyMap.put(c, ++count);
    }
    return cars.stream().filter(c -> frequencyMap.get(c) >= 3).distinct().collect(Collectors.toList());
  }


  //Observer for first move
  public List<Observer> getObservers() {
    return observers;
  }

  @Override
  public void attach(Observer observer) {
    this.observers.add(observer);
  }

  @Override
  public void detach(Observer observer) {
    this.observers.remove(observer);
  }

  @Override
  public void notifyObservers(Object value) {
    for (Observer observer : observers) {
      observer.update(this, value);
    }
  }
}

