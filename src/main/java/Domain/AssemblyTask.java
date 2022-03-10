package Domain;

import java.time.LocalDateTime;

public class AssemblyTask {
    private LocalDateTime finishedTime;
    private boolean isFinished;
    private String epitome;

    public AssemblyTask(String epitome) {
        this.epitome = epitome;
        this.isFinished = false;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public String getEpitome() {
        return epitome;
    }

    public void complete(LocalDateTime finishedTime){
        this.finishedTime = finishedTime;
    }
}
