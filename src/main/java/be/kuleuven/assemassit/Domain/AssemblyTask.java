package be.kuleuven.assemassit.Domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AssemblyTask {
	private boolean pending;
	private List<String> actions;
	private int id;
	private LocalDateTime completionTime;
	
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
	
	public LocalDateTime completionTime() {
		
		if (!pending)
			throw new IllegalStateException();
		
		return this.completionTime;
	}
	
	public void complete() {
		this.pending = false;
		this.completionTime = LocalDateTime.now();
	}
}
