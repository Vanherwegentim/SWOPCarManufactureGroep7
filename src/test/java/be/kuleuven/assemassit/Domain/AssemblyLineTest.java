package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssemblyLineTest {

  private AssemblyLine assemblyLine;
  private WorkPost carBodyPost;
  private WorkPost drivetrainPost;
  private WorkPost accessoriesPost;




  @BeforeEach
  public void beforeEach(){
    this.assemblyLine = new AssemblyLine();
    this.carBodyPost = new WorkPost(0, Arrays.asList(AssemblyTaskType.ASSEMBLE_CAR_BODY, AssemblyTaskType.PAINT_CAR));
    this.drivetrainPost = new WorkPost(1, Arrays.asList(AssemblyTaskType.INSERT_ENGINE,AssemblyTaskType.INSERT_GEARBOX));
    this.accessoriesPost = new WorkPost(2, Arrays.asList(AssemblyTaskType.INSTALL_AIRCO, AssemblyTaskType.INSTALL_SEATS,AssemblyTaskType.MOUNT_WHEELS));

  }

  /*
  Voorlopig wordt er nog nooit een assemblytask op active gezet dus vandaar de null.
   */
  @Test
  public void giveStatusTest_WorkpostsEmpty(){
    Map<String, AssemblyTask> workPostStatusses = new HashMap<>();
    workPostStatusses.put("Car Body Post",null);
    workPostStatusses.put("Drivetrain Post", null);
    workPostStatusses.put("Accessories Post", null);

    assertEquals(workPostStatusses, assemblyLine.giveStatus());
  }

  @Test
  public void giveTasksOverviewTest(){
    //TODO: ouwe test, moet nog geüpdated worden met de nieuwe code
    Map<String, List<AssemblyTask>> expected = new HashMap<>();
    expected.put("Car Body Post", carBodyPost.getAllAssemblyTasks());
    expected.put("Drivetrain Post", drivetrainPost.getAllAssemblyTasks());
    expected.put("Accessories Post", accessoriesPost.getAllAssemblyTasks());

    Map<String, List<AssemblyTask>> actual = assemblyLine.giveTasksOverview();
    assertEquals(expected.size(), actual.size());

    for(String key : expected.keySet()){
      assertArrayEquals(
        expected.get(key).stream().map(AssemblyTask::getName).toArray(),
        actual.get(key).stream().map(AssemblyTask::getName).toArray()
      );
    }



  }
}
