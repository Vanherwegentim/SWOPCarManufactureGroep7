package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.WorkPostType;
import be.kuleuven.assemassit.Domain.Helper.Observer;
import be.kuleuven.assemassit.Domain.Helper.Subject;
import be.kuleuven.assemassit.Domain.Repositories.OverTimeRepository;
import be.kuleuven.assemassit.Domain.Scheduling.FIFOScheduling;
import be.kuleuven.assemassit.Domain.Scheduling.SchedulingAlgorithm;
import be.kuleuven.assemassit.Domain.Scheduling.SpecificationBatchScheduling;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @mutable
 * @invar | getCarBodyPost() != null
 * @invar | getDriveTrainPost() != null
 * @invar | getAccessoriesPost() != null
 * @invar | getCarAssemblyProcess() != null
 * @invar | getFinishedCars() != null
 * @invar | getSchedulingAlgorithm() != null
 * @invar | giveSchedulingAlgorithmNames() != null
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
  private LocalTime startTime;
  private LocalTime endTime;
  private int overTime;


  /**
   * @representationObject
   */
  private SchedulingAlgorithm schedulingAlgorithm;

  private OverTimeRepository overTimeRepository;

  private List<Observer> observers;

  /**
   * @post | carBodyPost != null
   * @post | driveTrainPost != null
   * @post | accessoriesPost != null
   * @post | carAssemblyProcessesQueue != null
   * @post | finishedCars != null
   * @post | overTimeRepository != null
   * @post | overTime >= 0
   * @mutates | this
   */
  public AssemblyLine(OverTimeRepository overTimeRepository) {
    this.carBodyPost = new WorkPost(0, Arrays.asList(AssemblyTaskType.ASSEMBLE_CAR_BODY, AssemblyTaskType.PAINT_CAR), WorkPostType.CAR_BODY_POST, 60);
    this.drivetrainPost = new WorkPost(1, Arrays.asList(AssemblyTaskType.INSERT_ENGINE, AssemblyTaskType.INSERT_GEARBOX), WorkPostType.DRIVETRAIN_POST, 60);
    this.accessoriesPost = new WorkPost(2, Arrays.asList(AssemblyTaskType.INSTALL_AIRCO, AssemblyTaskType.INSTALL_SEATS, AssemblyTaskType.MOUNT_WHEELS), WorkPostType.ACCESSORIES_POST, 60);
    this.finishedCars = new ArrayList<>();
    this.carAssemblyProcessesQueue = new ArrayDeque<>();
    this.schedulingAlgorithm = new FIFOScheduling();
    this.observers = new ArrayList<>();
    this.overTimeRepository = overTimeRepository;
    this.overTime = overTimeRepository.getOverTime();
  }

  /**
   * @post | carBodyPost != null
   * @post | driveTrainPost != null
   * @post | accessoriesPost != null
   * @post | carAssemblyProcessesQueue != null
   * @post | finishedCars != null
   * @post | overTimeRepository != null
   * @mutates | this
   */
  public AssemblyLine() {
    this(new OverTimeRepository());
  }

  /**
   * Sets the start time of the assembly line.
   *
   * @param startTime
   * @throws IllegalArgumentException startTime can not be null | startTime == null
   * @post | this.startTime == startTime
   */
  public void setStartTime(LocalTime startTime) {
    if (startTime == null) {
      throw new IllegalArgumentException("StartTime can not be null");
    }
    this.startTime = startTime;
  }

  /**
   * Sets the end time of the assembly line.
   * This is always set by the car manufacturing company and is not outside of the opening hours of the company.
   *
   * @param endTime
   * @throws IllegalArgumentException endTime can not be null | endTime == null
   * @post | this.endTime == endTime
   */
  public void setEndTime(LocalTime endTime) {
    if (endTime == null) {
      throw new IllegalArgumentException("EndTime can not be null");
    }
    this.endTime = endTime;
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
   * @post | this.schedulingAlgorithm == schedulingAlgorithm
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

  public WorkPost getCarBodyPost() {
    return this.carBodyPost;
  }

  public WorkPost getDrivetrainPost() {
    return this.drivetrainPost;
  }

  public WorkPost getAccessoriesPost() {
    return this.accessoriesPost;
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

  public int getOverTime() {
    return this.overTime;
  }

  private void setOverTime(int overTime) {
    this.overTime = overTime;
    notifyObservers(); // TODO: maybe the UI? this is an event that does not always occur
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
   * @return The list of pendfiing assembly tasks
   * @inspects | this
   * @creates | result
   */
  public List<AssemblyTask> giveFinishedAssemblyTasksFromWorkPost(int workPostId) {
    WorkPost workPost = findWorkPost(workPostId);
    return workPost.giveFinishedAssemblyTasks();

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
    workPost.completeAssemblyTask(duration, LocalDateTime.now());
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

  private boolean canWorkDayStart() {
    return (LocalTime.now().isAfter(this.startTime.plusMinutes(this.overTime)));
  }

  /**
   * Moves the assembly and gives the duration of the current phase.
   * The assembly process is moved from one work post to another on the assembly line.
   *
   * @param minutes the amount of minutes spent during the current phase
   * @throws IllegalStateException    when the assembly line can not be moved | !canMove()
   * @throws IllegalArgumentException minutes is below 0 | minutes < 0
   * @mutates | this
   */
  public void move(int minutes) {

    if (!canWorkDayStart())
      throw new IllegalArgumentException("The work day can not be started yet because of previous overtime");

    if (!canMove())
      throw new IllegalArgumentException("AssemblyLine cannot be moved forward!");

    int overtime = schedulingAlgorithm.moveAssemblyLine
      (
        minutes,
        this.overTime,
        endTime,
        carAssemblyProcessesQueue,
        finishedCars,
        getWorkPosts()
      );

    if (overtime > 0) {
      // overtime happened so we have to inform the assembly line
      setOverTime(overtime);
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
        giveManufacturingDurationInMinutes(),
        this.endTime,
        this.startTime,
        maxTimeNeededForWorkPostOnLine()
      );
  }

  /**
   * Returns the duration of the slowest work post on the process.
   * It is possible that all work post have equal durations in the system.
   *
   * @return the duration of the slowest work post on the assembly line in minutes
   * @post | result >= 0
   * @inspects | this
   */
  private int maxTimeNeededForWorkPostOnLine() {
    return getWorkPosts()
      .stream()
      .mapToInt(WorkPost::getExpectedWorkPostDurationInMinutes)
      .max()
      .orElse(0);
  }

  /**
   * Returns the total manufacturing duration of a car assembly process in minutes.
   *
   * @return total manufacturing duration of a car assembly process in minutes
   * @post | result >= 0
   * @inspects | this
   */
  private int giveManufacturingDurationInMinutes() {
    return getWorkPosts()
      .stream()
      .mapToInt(WorkPost::getExpectedWorkPostDurationInMinutes)
      .reduce(0, Integer::sum);
  }

  /**
   * Returns the corresponding assembly task of the corresponding work post
   *
   * @param workPostId     the id of the work post
   * @param assemblyTaskId the id of the assembly task
   * @return the corresponding assembly task from the work post
   * @throws IllegalArgumentException | workPostId < 0 || assemblyTaskId < 0
   * @post | result != null
   * @inspects | this
   */
  public AssemblyTask giveCarAssemblyTask(int workPostId, int assemblyTaskId) {
    if (workPostId < 0 || assemblyTaskId < 0)
      throw new IllegalArgumentException("The IDs must be greater than or equal to zero");

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
    carAssemblyProcessList.add(drivetrainPost.getCarAssemblyProcess());
    carAssemblyProcessList.add(accessoriesPost.getCarAssemblyProcess());
    carAssemblyProcessList.add(carBodyPost.getCarAssemblyProcess());
    if (!finishedCars.isEmpty()) {
      carAssemblyProcessList.addAll(finishedCars);
    }
    return carAssemblyProcessList;
  }

  public Map<LocalDate, Integer> createCarsPerDayMap() {
    //Create a map that counts how many cars were made every day(LocalDate)
    //TODO refactor to work with streams;
    List<LocalDateTime> dateTimeList = new ArrayList<>();
    for (CarAssemblyProcess carAssemblyProcess : finishedCars) {
      dateTimeList.add(carAssemblyProcess.getCarOrder().getCompletionTime());
    }
    Map<LocalDate, Integer> carsPerDayMap = new HashMap<>();
    for (LocalDateTime localDateTime : dateTimeList) {
      if (carsPerDayMap.containsKey(localDateTime.toLocalDate())) {
        int i = carsPerDayMap.get(localDateTime);
        i++;
        carsPerDayMap.replace(localDateTime.toLocalDate(), i);
      } else {
        carsPerDayMap.put(localDateTime.toLocalDate(), 1);
      }
    }
    return carsPerDayMap;
  }

  public int averageCarsInADay() {
    //Calculate the average
    Map<LocalDate, Integer> carsPerDayMap = createCarsPerDayMap();
    int total = 0;
    for (Map.Entry<LocalDate, Integer> entry : carsPerDayMap.entrySet()) {
      total = total + entry.getValue();
    }
    return total / carsPerDayMap.size();
  }

  public int medianCarsInADay() {
    Map<LocalDate, Integer> carsPerDayMap = createCarsPerDayMap();
    ArrayList<Integer> intList = new ArrayList<>();
    intList.addAll(carsPerDayMap.values());
    Collections.sort(intList);
    if (intList.size() == 0) {
      return 0;
    }
    if (intList.size() == 1) {
      return intList.get(0);
    } else {
      if (intList.size() % 2 == 0) {
        int middle = intList.size() / 2;
        return (intList.get(middle) + intList.get(middle + 1)) / 2;
      } else {
        double middle = (double) intList.size() / 2.0;
        int middleInt = (int) Math.ceil(middle);
        return intList.get(middleInt);
      }
    }
  }

  public int exactCarsIn2Days() {
    Map<LocalDate, Integer> carsPerDayMap = createCarsPerDayMap();
    int total = 0;

    for (Map.Entry<LocalDate, Integer> entry : carsPerDayMap.entrySet()) {
      if (entry.getKey().equals(LocalDate.now()) ||
        entry.getKey().equals(LocalDate.now().minusDays(1))) {
        total = total + entry.getValue();
      }
    }
    return total;
  }

  //Can a car be ready before it's estimated completion time? if so, add an if test
  public int averageDelayPerOrder() {
    int total = 0;
    for (CarAssemblyProcess carAssemblyProcess : finishedCars) {
      Duration duration = Duration.between(carAssemblyProcess.getCarOrder().getCompletionTime(), carAssemblyProcess.getCarOrder().getEstimatedCompletionTime());
      long diff = duration.toHours();
      //This conversion could error
      total = total + Math.toIntExact(diff);

    }
    return Math.round(total / finishedCars.size());
  }

  public int medianDelayPerOrder() {
    int total = 0;
    ArrayList<Integer> dates = new ArrayList<>();
    for (CarAssemblyProcess carAssemblyProcess : finishedCars) {
      Duration duration = Duration.between(carAssemblyProcess.getCarOrder().getCompletionTime(), carAssemblyProcess.getCarOrder().getEstimatedCompletionTime());
      long diff = duration.toHours();
      int conv = Math.toIntExact(diff);
      dates.add(conv);
    }
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
        int middleInt = (int) Math.ceil(middle);
        return dates.get(middleInt);
      }
    }
  }

  public Map<LocalDate, Integer> last2Delays() {
    Map<LocalDate, Integer> delays = new HashMap<>();
    if (finishedCars.size() == 0) {
      return delays;
    }
    if (finishedCars.size() == 1) {
      CarAssemblyProcess car1 = finishedCars.get(0);
      Duration duration1 = Duration.between(car1.getCarOrder().getCompletionTime(), car1.getCarOrder().getEstimatedCompletionTime());
      long diff1 = duration1.toHours();
      int conv1 = Math.toIntExact(diff1);
      delays.put(car1.getCarOrder().getCompletionTime().toLocalDate(), conv1);
      return delays;
    } else {
      CarAssemblyProcess car1 = finishedCars.get(0);
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
      return delays;
    }
  }

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

      frequencyMap.put(c, count++);
    }

    return cars.stream().filter(c -> frequencyMap.get(c) >= 3).distinct().collect(Collectors.toList());
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
  public void notifyObservers() {
    for (Observer observer : observers) {
      observer.update();
    }
  }
}

