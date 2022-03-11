package be.kuleuven.assemassit.Domain;

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
    this.assemblyLine = new AssemblyLine(new ArrayList<>());
    this.carBodyPost = new WorkPost(0, Arrays.asList(new AssemblyTask("Assembly car body"), new AssemblyTask("Paint car")));
    this.drivetrainPost = new WorkPost(1, Arrays.asList(new AssemblyTask("Insert engine"), new AssemblyTask("Insert gearbox")));
    this.accessoriesPost = new WorkPost(2, Arrays.asList(new AssemblyTask("Install seats"), new AssemblyTask("Install airco"), new AssemblyTask("Mount wheels")));

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
    Map<String, List<AssemblyTask>> expected = new HashMap<>();
    expected.put("Car Body Post", carBodyPost.getAssemblyTasks());
    expected.put("Drivetrain Post", drivetrainPost.getAssemblyTasks());
    expected.put("Accessories Post", accessoriesPost.getAssemblyTasks());

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
