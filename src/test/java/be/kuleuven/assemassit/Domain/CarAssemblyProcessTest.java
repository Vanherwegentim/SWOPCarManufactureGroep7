package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.*;
import be.kuleuven.assemassit.Domain.TaskTypes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CarAssemblyProcessTest {
  private List<AssemblyTask> assemblyTasks;
  private CarOrder carOrder;

  @BeforeEach
  public void beforeEach() {
    carOrder = new CarOrder(
      new Car(
        new CarModel(0, "Tolkswagen Rolo", Arrays.asList(Wheel.values()), Arrays.asList(Gearbox.values()), Arrays.asList(Seat.values()), Arrays.asList(Body.values()), Arrays.asList(Color.values()), Arrays.asList(Engine.values()), Arrays.asList(Airco.values()), Arrays.asList(Spoiler.values())),
        Body.SEDAN,
        Color.BLACK,
        Engine.PERFORMANCE,
        Gearbox.FIVE_SPEED_MANUAL,
        Seat.LEATHER_BLACK,
        Airco.MANUAL,
        Wheel.SPORT,
        Spoiler.LOW));

    this.assemblyTasks = Arrays.asList(
      new CarBodyAssemblyTask(carOrder.getCar().getBody()),
      new InsertGearboxAssemblyTask(carOrder.getCar().getGearbox()),
      new InsertEngineAssemblyTask(carOrder.getCar().getEngine()),
      new InstallAircoAssemblyTask(carOrder.getCar().getAirco()),
      new MountWheelsAssemblyTask(carOrder.getCar().getWheels()),
      new PaintCarAssemblyTask(carOrder.getCar().getColor()),
      new InstallSeatsAssemblyTask(carOrder.getCar().getSeats()),
      new InstallSpoilerAssemblyTask(carOrder.getCar().getSpoiler())

    );
  }

  @Test
  public void CarAssemblyProcessTest_throws() {
    assertThrows(NullPointerException.class, () -> new CarAssemblyProcess(null));

  }

  @Test
  public void getAssemblyTasksTest() {
    CarAssemblyProcess carAssemblyProcess = new CarAssemblyProcess(carOrder);
    assert !carAssemblyProcess.getAssemblyTasks().isEmpty();
    for (int i = 0; i < assemblyTasks.size(); i++) {

      //all id's of assemblytask are 0 now so this is trivial
      assertEquals(carAssemblyProcess.getAssemblyTasks().get(i).getActions(), (assemblyTasks.get(i).getActions()));

    }
  }

  @Test
  void giveAssemblyTask() {
    CarAssemblyProcess carAssemblyProcess = new CarAssemblyProcess(carOrder);
    List<AssemblyTask> assemblyTaskList = carAssemblyProcess.getAssemblyTasks();

    assertEquals(assemblyTaskList.get(0), carAssemblyProcess.giveAssemblyTask(assemblyTaskList.get(0).getId()));

  }

  @Test
  void getId() {
    CarAssemblyProcess.resetRunningId();
    CarAssemblyProcess carAssemblyProcess = new CarAssemblyProcess(carOrder);
    assertEquals(carAssemblyProcess.getId(), 0);
  }

}
