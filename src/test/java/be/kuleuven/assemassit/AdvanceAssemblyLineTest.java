package be.kuleuven.assemassit;

import be.kuleuven.assemassit.Domain.AssemblyTask;
import be.kuleuven.assemassit.Domain.WorkPost;
import org.junit.jupiter.api.Test;

public class AdvanceAssemblyLineTest {

    @Test
    public void AdvanceAssemblyLineTest() {
        int assemblyTaskCounter = 0;
        WorkPost carBodyPost = new WorkPost("Car Body Post");
        AssemblyTask assemblyCarBody = new AssemblyTask(assemblyTaskCounter++, "Assembly Car Body");
        AssemblyTask paintCar = new AssemblyTask(assemblyTaskCounter++, "Paint Car");

        WorkPost drivetrainPost = new WorkPost("Drivetrain Post");
        AssemblyTask insertEngine = new AssemblyTask(assemblyTaskCounter++, "Insert engine");
        AssemblyTask insertGearbox = new AssemblyTask(assemblyTaskCounter++, "Insert gearbox");

        WorkPost accessoriesPost = new WorkPost("Accessories Post");
        AssemblyTask instalSeats = new AssemblyTask(assemblyTaskCounter++, "Install seats");
        AssemblyTask installAirco = new AssemblyTask(assemblyTaskCounter++, "Install airco");
        AssemblyTask mountWheels = new AssemblyTask(assemblyTaskCounter++, "Mount wheels");


    }
}
