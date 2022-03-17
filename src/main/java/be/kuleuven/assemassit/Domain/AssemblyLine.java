package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import be.kuleuven.assemassit.Domain.Enums.WorkPostType;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class AssemblyLine {

  private final WorkPost carBodyPost;
  private final WorkPost drivetrainPost;
  private final WorkPost accessoriesPost;
  private final Queue<CarAssemblyProcess> carAssemblyProcessesQueue;
  private final List<CarAssemblyProcess> finishedCars;
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
    if (startTime == null) {
      throw new IllegalArgumentException("StartTime can not be null");
    }
    this.startTime = startTime;
  }

  public void setEndTime(LocalTime endTime) {
    if (endTime == null) {
      throw new IllegalArgumentException("EndTime can not be null");
    }
    this.endTime = endTime;
  }

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
      futureDrivetrainPostAssemblyProcessAssemblyTasks = futureTaskListConverter(
        new ArrayList<>(this.carBodyPost.getCarAssemblyProcess().getAssemblyTasks()),
        this.drivetrainPost.getAssemblyTaskTypes()
      );
    }

    List<AssemblyTask> futureAccessoriesPostAssemblyProcessAssemblyTasks = new ArrayList<>();
    if (this.drivetrainPost.getCarAssemblyProcess() != null) {
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

      CarAssemblyProcess carAssemblyProcess = accessoriesPost.getCarAssemblyProcess();
      carAssemblyProcess.determineCompletionTime();
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
    double remaningCarsForTodayDouble = ((double) ((endTime.getHour() * 60 + endTime.getMinute()) - // end time
      giveManufacturingDurationInMinutes() - // time needed to manufacture a car
      (LocalTime.now().getHour() * 60 + LocalTime.now().getMinute()) - // current time
      maxTimeNeededForWorkPostOnLine() + // time needed for the slowest work post
      60) / (double) 60);
    int remainingCarsForToday =
      (int) Math.ceil(((double) ((endTime.getHour() * 60 + endTime.getMinute()) - // end time
        giveManufacturingDurationInMinutes() - // time needed to manufacture a car
        (LocalTime.now().getHour() * 60 + LocalTime.now().getMinute()) - // current time
        maxTimeNeededForWorkPostOnLine() + // time needed for the slowest work post
        60) / (double) 60));

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
    int daysNeeded = Math.max(0,(carAssemblyProcessesQueue.size() - remainingCarsForToday) / amountOfCarsWholeDay - 1);


    // return date of tomorrow + days needed + minutes needed
    LocalDateTime today = LocalDateTime.now();
    int remainingMinutesForLastDay = (((carAssemblyProcessesQueue.size() - Math.abs(remainingCarsForToday)) % amountOfCarsWholeDay) + 1) * maxTimeNeededForWorkPostOnLine();
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

  public AssemblyTask giveCarAssemblyTask(int workPostId, int assemblyTaskId) {
    WorkPost workPost = findWorkPost(workPostId);
    return workPost.findAssemblyTask(assemblyTaskId);
  }

  public void setActiveTask(WorkPost workPost, int assemblyTaskId) {
    workPost.setActiveAssemblyTask(assemblyTaskId);
  }
}
