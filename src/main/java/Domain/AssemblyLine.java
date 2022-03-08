package Domain;

import java.util.ArrayList;
import java.util.List;

public class AssemblyLine {
	
	private List<WorkPost> workPosts;
	
	public AssemblyLine() {
		this.workPosts = new ArrayList<WorkPost>();
	}
	
	public AssemblyLine(List<WorkPost> workPosts) {
		this.workPosts = new ArrayList<WorkPost>(workPosts);
	}
	
	public List<WorkPost> getWorkPosts() {
		return new ArrayList<WorkPost>(this.workPosts);
	}
}
