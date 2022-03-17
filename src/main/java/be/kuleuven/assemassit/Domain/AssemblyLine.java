package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.WorkPostType;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class AssemblyLine {

  private WorkPost carBodyPost;
  private WorkPost drivetrainPost;
  private WorkPost accessoriesPost;
  private Queue<CarAssemblyProcess> carAssemblyProcessesQueue;
  private List<CarAssemblyProcess> finishedCars;

  public AssemblyLine() {
    this.carBodyPost = new WorkPost(0, Arrays.asList(AssemblyTaskType.ASSEMBLE_CAR_BODY, AssemblyTaskType.PAINT_CAR), WorkPostType.CAR_BODY_POST, 60);
    this.drivetrainPost = new WorkPost(1, Arrays.asList(AssemblyTaskType.INSERT_ENGINE, AssemblyTaskType.INSERT_GEARBOX), WorkPostType.DRIVETRAIN_POST, 60);
    this.accessoriesPost = new WorkPost(2, Arrays.asList(AssemblyTaskType.INSTALL_AIRCO, AssemblyTaskType.INSTALL_SEATS, AssemblyTaskType.MOUNT_WHEELS), WorkPostType.ACCESSORIES_POST, 60);
    this.finishedCars = new ArrayList<>();
    this.carAssemblyProcessesQueue = new ArrayDeque<>();
  }

  //TODO remove when clone works
  public AssemblyLine(WorkPost carBodyPost, WorkPost drivetrainPost, WorkPost accessoriesPost, Queue<CarAssemblyProcess> carAssemblyProcessesQueue, List<CarAssemblyProcess> finishedCars) {
    this.carBodyPost = carBodyPost;
    this.drivetrainPost = drivetrainPost;
    this.accessoriesPost = accessoriesPost;
    this.carAssemblyProcessesQueue = carAssemblyProcessesQueue;
    this.finishedCars = finishedCars;
  }

  public void addCarAssemblyProcess(CarAssemblyProcess carAssemblyProcess) {
    if (carAssemblyProcess == null) {
      throw new IllegalArgumentException("CarAssemblyProcess not found");
    }
    carAssemblyProcessesQueue.add(carAssemblyProcess);
    // return here the new schedule?
  }

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

  public WorkPost getCarBodyPost() {
    return this.carBodyPost;
  }

  public WorkPost getDrivetrainPost() {
    return this.drivetrainPost;
  }

  public WorkPost getAccessoriesPost() {
    return this.accessoriesPost;
  }

  public List<AssemblyTask> givePendingAssemblyTasksFromWorkPost(int workPostId) {
    WorkPost workPost = findWorkPost(workPostId);
    return workPost.givePendingAssemblyTasks();
  }

  public void completeAssemblyTask(int workPostId) {
    WorkPost workPost = findWorkPost(workPostId);
    workPost.completeAssemblyTask();
  }

  public HashMap<String, AssemblyTask> giveStatus() {
    HashMap<String, AssemblyTask> workPostStatuses = new LinkedHashMap<>();

    workPostStatuses.put("Car Body Post", carBodyPost.getActiveAssemblyTask());
    workPostStatuses.put("Drivetrain Post", drivetrainPost.getActiveAssemblyTask());
    workPostStatuses.put("Accessories Post", accessoriesPost.getActiveAssemblyTask());

    return workPostStatuses;
  }

  public HashMap<String, List<AssemblyTask>> giveTasksOverview() {
    HashMap<String, List<AssemblyTask>> workPostPairs = new LinkedHashMap<>();

    workPostPairs.put("Car Body Post", carBodyPost.getWorkPostAssemblyTasks());
    workPostPairs.put("Drivetrain Post", drivetrainPost.getWorkPostAssemblyTasks());
    workPostPairs.put("Accessories Post", accessoriesPost.getWorkPostAssemblyTasks());

    return workPostPairs;
  }

  //TODO check if this is a correct/good implementation
  public HashMap<String, List<AssemblyTask>> giveFutureTasksOverview() {

    List<AssemblyTask> futureCarBodyPostAssemblyProcessAssemblyTasks = new ArrayList<>();
    if (!this.carAssemblyProcessesQueue.isEmpty()) {
      futureCarBodyPostAssemblyProcessAssemblyTasks =
        futureTaskListConverter(
          new ArrayList<>(this.carAssemblyProcessesQueue.peek().getAssemblyTasks()),
          this.carBodyPost.getAssemblyTaskTypes()
        );
    }

    List<AssemblyTask> futureDrivetrainPostAssemblyProcessAssemblyTasks = new ArrayList<>();
    if (this.carBodyPost.getCarAssemblyProcess() != null) {
      //if (this.carBodyPost.givePendingAssemblyTasks().isEmpty()) {
      futureDrivetrainPostAssemblyProcessAssemblyTasks = futureTaskListConverter(
        new ArrayList<>(this.carBodyPost.getCarAssemblyProcess().getAssemblyTasks()),
        this.drivetrainPost.getAssemblyTaskTypes()
      );
    }


    List<AssemblyTask> futureAccessoriesPostAssemblyProcessAssemblyTasks = new ArrayList<>();
    if (this.drivetrainPost.getCarAssemblyProcess() != null) {
      //if (this.drivetrainPost.givePendingAssemblyTasks().isEmpty()) {
      futureAccessoriesPostAssemblyProcessAssemblyTasks = futureTaskListConverter(
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

  private List<AssemblyTask> futureTaskListConverter(List<AssemblyTask> allAssemblyTasks, List<AssemblyTaskType> assemblyTaskTypes) {
    return allAssemblyTasks.stream().filter(task -> assemblyTaskTypes.contains(task.getAssemblyTaskType())).toList();
  }

  private WorkPost findWorkPost(int id) {
    //TODO: refactor?
    if (carBodyPost.getId() == id) return carBodyPost;
    if (drivetrainPost.getId() == id) return drivetrainPost;
    if (accessoriesPost.getId() == id) return accessoriesPost;

    throw new IllegalArgumentException("Workpost not found");
  }

  public boolean canMove() {
    List<WorkPost> workPosts = this.giveWorkPostsAsList();
    for (WorkPost workPost : workPosts) {
      //TODO uitleg verwijderen canMove moet false returnen wanneer de workpost nog niet klaar is, dus wanneer er nog taken niet zijn afgewerkt
      if (!workPost.givePendingAssemblyTasks().isEmpty()) {
        return false;
      }
    }
    return true;
  }

  public void move(int minutes) {
    if (!canMove()) {
      throw new IllegalStateException("AssemblyLine cannot be moved forward!");
    }

    //Remove the car from the third post
    if (accessoriesPost.getCarAssemblyProcess() != null) {
      for (AssemblyTask assemblyTask : accessoriesPost.getWorkPostAssemblyTasks()) {
        assemblyTask.setCompletionTime(minutes);
      }
      finishedCars.add(accessoriesPost.getCarAssemblyProcess());
    }
    //Give the third post the car of the second post
    if (drivetrainPost.getCarAssemblyProcess() != null) {
      for (AssemblyTask assemblyTask : drivetrainPost.getWorkPostAssemblyTasks()) {
        assemblyTask.setCompletionTime(minutes);
      }
      accessoriesPost.addProcessToWorkPost(drivetrainPost.getCarAssemblyProcess());
    }
    //Give the second post the car of the first post
    if (carBodyPost.getCarAssemblyProcess() != null) {
      for (AssemblyTask assemblyTask : carBodyPost.getWorkPostAssemblyTasks()) {
        assemblyTask.setCompletionTime(minutes);
      }
      drivetrainPost.addProcessToWorkPost(carBodyPost.getCarAssemblyProcess());
    }
    //Give the first post a car from the queue;
    if (!carAssemblyProcessesQueue.isEmpty()) {
      carBodyPost.addProcessToWorkPost(carAssemblyProcessesQueue.poll());
    }
  }

  public LocalDateTime giveEstimatedCompletionDateOfLatestProcess(LocalTime openingTime, LocalTime closingTime) {
    // calculate remaining cars for this day (1)
    int remainingCarsForToday =
      (int) ((double) ((closingTime.getHour() * 60 + closingTime.getMinute()) - // end time
        giveManufacturingDurationInMinutes() - // time needed to manufacture a car
        (LocalTime.now().getHour() * 60 + LocalTime.now().getMinute()) - // current time
        maxTimeNeededForWorkPostOnLine() + // time needed for the slowest work post
        60) / (double) 60);

    // calculate cars for a whole day (2)
    int amountOfCarsWholeDay =
      (int) ((double) ((closingTime.getHour() * 60 + closingTime.getMinute()) - // end time
        giveManufacturingDurationInMinutes() - // time needed to manufacture a car
        (openingTime.getHour() * 60 + openingTime.getMinute()) - // opening time
        maxTimeNeededForWorkPostOnLine() + // time needed for the slowest work post
        60) / (double) 60);

    // car can still be manufactured today
    if (carAssemblyProcessesQueue.size() <= remainingCarsForToday) {
      LocalDateTime dateTime = LocalDateTime.now();
      if (dateTime.getHour() < 6)
        dateTime = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), 6, 0);
      return dateTime.plusMinutes((long) giveManufacturingDurationInMinutes() * carAssemblyProcessesQueue.size());
    }

    // car can not be manufactured today
    // Math.ceil(list - (1) / (2)) = days needed
    int daysNeeded = (carAssemblyProcessesQueue.size() - remainingCarsForToday) / amountOfCarsWholeDay;


    // return date of tomorrow + days needed + minutes needed
    LocalDateTime today = LocalDateTime.now();
    int remainingMinutesForLastDay = ((carAssemblyProcessesQueue.size() - remainingCarsForToday) % amountOfCarsWholeDay) * giveManufacturingDurationInMinutes();
    return LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), openingTime.getHour(), openingTime.getMinute()).plusDays(1).plusDays(daysNeeded).plusMinutes(remainingMinutesForLastDay);
  }

  private int maxTimeNeededForWorkPostOnLine() {
    return giveWorkPostsAsList()
      .stream()
      .mapToInt(WorkPost::getExpectedWorkPostDurationInMinutes)
      .max()
      .orElse(0);
  }

  private int giveManufacturingDurationInMinutes() {
    return giveWorkPostsAsList()
      .stream()
      .mapToInt(WorkPost::getExpectedWorkPostDurationInMinutes)
      .reduce(0, Integer::sum);
  }

  public List<WorkPost> giveWorkPostsAsList() {
    return Arrays.asList(carBodyPost, drivetrainPost, accessoriesPost);
  }

  public AssemblyTask giveCarAssemblyTask(int carAssemblyProcessId, int assemblyTaskId) {
    return findCarAssemblyProcess(carAssemblyProcessId).giveAssemblyTask(assemblyTaskId);
  }

  private CarAssemblyProcess findCarAssemblyProcess(int id) {
    List<WorkPost> workPosts = this.giveWorkPostsAsList();
    for (WorkPost workPost : workPosts) {
      CarAssemblyProcess workPostCarAssemblyProcess = workPost.getCarAssemblyProcess();
      if (workPostCarAssemblyProcess != null && workPostCarAssemblyProcess.getId() == id) {
        return workPostCarAssemblyProcess;
      }
    }

    Optional<CarAssemblyProcess> queueCarAssemblyProcess = carAssemblyProcessesQueue
      .stream()
      .filter(p -> p.getId() == id)
      .findFirst();
    if (queueCarAssemblyProcess.isPresent()) return queueCarAssemblyProcess.get();

    Optional<CarAssemblyProcess> finishedCarsCarAssemblyProcess = finishedCars
      .stream()
      .filter(p -> p.getId() == id)
      .findFirst();
    if (finishedCarsCarAssemblyProcess.isPresent()) return finishedCarsCarAssemblyProcess.get();

    throw new IllegalArgumentException("CarAssemblyProcess not found");
  }
}
