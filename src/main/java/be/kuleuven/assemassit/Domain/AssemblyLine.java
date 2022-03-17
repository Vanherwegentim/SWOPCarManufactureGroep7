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
  private LocalTime startTime;
  private LocalTime endTime;

  public AssemblyLine() {
    this.carBodyPost = new WorkPost(0, Arrays.asList(AssemblyTaskType.ASSEMBLE_CAR_BODY, AssemblyTaskType.PAINT_CAR), WorkPostType.CAR_BODY_POST, 60);
    this.drivetrainPost = new WorkPost(1, Arrays.asList(AssemblyTaskType.INSERT_ENGINE, AssemblyTaskType.INSERT_GEARBOX), WorkPostType.DRIVETRAIN_POST, 60);
    this.accessoriesPost = new WorkPost(2, Arrays.asList(AssemblyTaskType.INSTALL_AIRCO, AssemblyTaskType.INSTALL_SEATS, AssemblyTaskType.MOUNT_WHEELS), WorkPostType.ACCESSORIES_POST, 60);
    this.finishedCars = new ArrayList<>();
    this.carAssemblyProcessesQueue = new ArrayDeque<>();
  }

  public void setStartTime(LocalTime startTime) {
    this.startTime = startTime;
  }

  public void setEndTime(LocalTime endTime) {
    this.endTime = endTime;
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

  // TODO naar controller
  public List<AssemblyTask> givePendingAssemblyTasksFromWorkPost(int workPostId) {
    WorkPost workPost = findWorkPost(workPostId);
    return workPost.givePendingAssemblyTasks();
  }

  // TODO naar controller
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

  public WorkPost findWorkPost(int workPostId) {
    List<WorkPost> workPosts = this.giveWorkPostsAsList();

    Optional<WorkPost> optionalWorkPost = workPosts.stream().filter(workPost -> workPost.getId() == workPostId).findFirst();

    if (optionalWorkPost.isEmpty()) {
      throw new IllegalArgumentException("Workpost not found");
    }
    return optionalWorkPost.get();
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
    //The queue can not be empty and there still must be enough time to produce the whole car
    if (!carAssemblyProcessesQueue.isEmpty() && LocalTime.now().plusMinutes(giveManufacturingDurationInMinutes()).isBefore(this.endTime)) {
      carBodyPost.addProcessToWorkPost(carAssemblyProcessesQueue.poll());
    }
  }

  public LocalDateTime giveEstimatedCompletionDateOfLatestProcess() {
    // calculate remaining cars for this day (1)
    int remainingCarsForToday =
      (int) ((double) ((endTime.getHour() * 60 + endTime.getMinute()) - // end time
        giveManufacturingDurationInMinutes() - // time needed to manufacture a car
        (LocalTime.now().getHour() * 60 + LocalTime.now().getMinute()) - // current time
        maxTimeNeededForWorkPostOnLine() + // time needed for the slowest work post
        60) / (double) 60);

    // calculate cars for a whole day (2)
    int amountOfCarsWholeDay =
      (int) ((double) ((endTime.getHour() * 60 + endTime.getMinute()) - // end time
        giveManufacturingDurationInMinutes() - // time needed to manufacture a car
        (startTime.getHour() * 60 + startTime.getMinute()) - // opening time
        maxTimeNeededForWorkPostOnLine() + // time needed for the slowest work post
        60) / (double) 60);

    // car can still be manufactured today
    if (carAssemblyProcessesQueue.size() <= remainingCarsForToday) {
      /*LocalDateTime dateTime = LocalDateTime.now();
      if (dateTime.getHour() < 6)
        dateTime = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), 6, 0);
      return dateTime.plusMinutes((long) giveManufacturingDurationInMinutes() * carAssemblyProcessesQueue.size());*/

      // total duration - max duration of work post + max duration * amount
      return LocalDateTime.now().plusMinutes(giveManufacturingDurationInMinutes() - maxTimeNeededForWorkPostOnLine()).plusMinutes(maxTimeNeededForWorkPostOnLine() * carAssemblyProcessesQueue.size());
    }

    // car can not be manufactured today
    // Math.ceil(list - (1) / (2)) = days needed
    int daysNeeded = (carAssemblyProcessesQueue.size() - remainingCarsForToday) / amountOfCarsWholeDay;


    // return date of tomorrow + days needed + minutes needed
    LocalDateTime today = LocalDateTime.now();
    int remainingMinutesForLastDay = (((carAssemblyProcessesQueue.size() - remainingCarsForToday) % amountOfCarsWholeDay) + 1) * maxTimeNeededForWorkPostOnLine();
    return LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), startTime.getHour(), startTime.getMinute()).plusDays(1).plusDays(daysNeeded).plusMinutes(giveManufacturingDurationInMinutes() - maxTimeNeededForWorkPostOnLine()).plusMinutes(remainingMinutesForLastDay);
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

  // TODO naar controller
  public AssemblyTask giveCarAssemblyTask(int assemblyTaskId) {
    List<CarAssemblyProcess> allCarAssemblyProcesses = this.allCarAssemblyProcesses();

    AssemblyTask assemblyTask = null;
    for (CarAssemblyProcess assemblyProcess : allCarAssemblyProcesses) {
      Optional<AssemblyTask> optionalAssemblyTask = assemblyProcess.giveOptionalAssemblyTask(assemblyTaskId);
      if (optionalAssemblyTask.isPresent()) assemblyTask = optionalAssemblyTask.get();
    }

    if (assemblyTask == null) {
      throw new IllegalArgumentException("AssemblyTask cannot be found!");
    }
    return assemblyTask;
  }

  public AssemblyTask giveCarAssemblyTask(int workPostId, int assemblyTaskId) {
    WorkPost workPost = findWorkPost(workPostId);
    return workPost.findAssemblyTask(assemblyTaskId);
  }

  private List<CarAssemblyProcess> allCarAssemblyProcesses() {
    List<CarAssemblyProcess> carAssemblyProcesses = new ArrayList<>();

    for (WorkPost workPost : this.giveWorkPostsAsList()) {
      CarAssemblyProcess workPostCarAssemblyProcess = workPost.getCarAssemblyProcess();
      if (workPostCarAssemblyProcess != null) {
        carAssemblyProcesses.add(workPostCarAssemblyProcess);
      }
    }

    for (CarAssemblyProcess queueCarAssemblyProcess : this.carAssemblyProcessesQueue) {
      if (queueCarAssemblyProcess != null) {
        carAssemblyProcesses.add(queueCarAssemblyProcess);
      }
    }

    for (CarAssemblyProcess finishedCarsCarAssemblyProcess : this.finishedCars) {
      if (finishedCarsCarAssemblyProcess != null) {
        carAssemblyProcesses.add(finishedCarsCarAssemblyProcess);
      }
    }

    return carAssemblyProcesses;
  }

  public void setActiveTask(WorkPost workPost, int assemblyTaskId) {
    workPost.setActiveAssemblyTask(assemblyTaskId);
  }
}
