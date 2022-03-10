package Controller;

import Domain.AssemblyLine;
import Domain.AssemblyTask;
import Domain.WorkPost;

import java.time.LocalDateTime;
import java.util.List;

public class AssemblyLineController {
    private AssemblyLine assemblyLine;

    public List<WorkPost> giveAllWorkPosts(){
        return assemblyLine.getWorkPosts();
    }

    public List<AssemblyTask> givePendingAssemblyTasks(int workPostId){
        WorkPost workPost = assemblyLine.getWorkPosts().get(workPostId);
        return workPost.givePendingAssemblyTasks();
    }

    //    HOE?
    public void giveAssemblyTaskInformation(int taskId){
    }

    //    HOE?
    public void completeAssemblyTask(int taskId, LocalDateTime time){
        return;
    }

    //    HOE?
    public void giveAssemblyLineStatus(int id){
        return;
    }

    public void moveAssemblyLine(int id, int minutes){
        return;
    }

}
