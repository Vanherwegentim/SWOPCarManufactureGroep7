package Domain;

import java.util.ArrayList;
import java.util.List;

public class AssemblyTask {
	private boolean pending;
	private List<String> actions;
	private int id;
	
	public AssemblyTask(int id) {
		this.id = id;
	}
	
	public boolean getPending() {
		return this.pending;
	}
	
	public void setPending(boolean pending) {
		this.pending = pending;
	}
	
	public List<String> getActions() {
		return new ArrayList<String>(actions);
	}
	
	public int getId() {
		return this.id;
	}
	
	
}
