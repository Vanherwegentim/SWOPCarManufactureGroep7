package be.kuleuven.assemassit.Domain;

import be.kuleuven.assemassit.Domain.Enums.AssemblyTaskType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class AssemblyTask {
	private boolean pending;
	private List<String> actions;
	private int id;
	private int completionTime;
	private String name;

	private static int runningId = 0;

	public AssemblyTask(int id) {
		this.id = id;
	}

	public AssemblyTask(String name) {
	  this.id = AssemblyTask.runningId++;
	  this.name = name;
  }

	public boolean getPending() {
		return this.pending;
	}

	public void setPending(boolean pending) {
		this.pending = pending;
	}

	public List<String> getActions() {
		return new ArrayList<>(actions);
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
	  return this.name;
  }

  public void setCompletionTime(int minutes){
	  this.completionTime = minutes;
  }
  public abstract AssemblyTaskType getAssemblyTaskType();

	public int completionTime() {

		if (!pending)
			throw new IllegalStateException();

		return this.completionTime;
	}

	public void complete() {
		this.pending = false;
	}

  public boolean equals(Object object){
	  if(object instanceof AssemblyTask task) task.actions.equals(this.actions);
	  return false;
  }
}
