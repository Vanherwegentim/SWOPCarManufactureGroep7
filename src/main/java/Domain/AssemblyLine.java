package Domain;

import java.util.ArrayList;
import java.util.List;

public class AssemblyLine {
    private List<WorkPost> workPosts;
    private boolean canMove;
    private int totalMinutes = 0;

    public AssemblyLine(List<WorkPost> workPosts) {
        this.workPosts = workPosts;
        this.canMove = false;
    }

    public List<WorkPost> getWorkPosts() {
        return List.copyOf(workPosts);
    }

    public WorkPost giveWorkPost(int postId) {
        return workPosts.get(postId);
    }

    public boolean canMove() {
        return canMove;
    }

    public void move(int minutes) {
        totalMinutes += minutes;
    }

    public String getStatus(){
        return "movable: " + canMove;
    }
}
