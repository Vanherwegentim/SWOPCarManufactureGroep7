package Domain;

import java.util.List;

public class CarAssemblyProcess {
    private List<AssemblyTask> assemblyTasks;

    public CarAssemblyProcess(List<AssemblyTask> assemblyTasks) {
        this.assemblyTasks = assemblyTasks;
    }

    public List<AssemblyTask> getAssemblyTasks() {
        return assemblyTasks;
    }
}
