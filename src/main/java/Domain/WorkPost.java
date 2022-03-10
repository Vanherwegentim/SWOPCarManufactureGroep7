package Domain;

import java.util.ArrayList;
import java.util.List;

public class WorkPost {
    private String carMechanic;
    private List<AssemblyTask> assemblyTasks;

    public WorkPost(String carMechanic, List<AssemblyTask> assemblyTasks) {
        this.carMechanic = carMechanic;
        this.assemblyTasks = assemblyTasks;
    }

    public String getCarMechanic() {
        return carMechanic;
    }

    public void setCarMechanic(String carMechanic) {
        this.carMechanic = carMechanic;
    }

    public List<AssemblyTask> getAssemblyTasks() {
        return assemblyTasks;
    }

    public AssemblyTask getAssemblyTask(int taskId) {
        return assemblyTasks.get(taskId);
    }

    public List<AssemblyTask> givePendingAssemblyTasks() {
        ArrayList pendingAssemblyTasks = new ArrayList();
        for (AssemblyTask assemblyTask : assemblyTasks)
            if (!assemblyTask.isFinished()) {
                pendingAssemblyTasks.add(assemblyTask);
            }
        return (List<AssemblyTask>) pendingAssemblyTasks;
    }
}
