package edu.ycp.cs320.project.model;
import java.util.*;

public class Objective {
	
	private int objectiveID;
	private int roomID;
	private List<Task> tasks;
	private Boolean isComplete;
	private Boolean isStarted;
	
	public Objective() {
		this.objectiveID = 0;
		this.roomID = 0;
		this.isStarted = true;
		this.isComplete = false;
		this.tasks = new ArrayList<Task>();
	}
	
	public Objective(int id, int roomID,Boolean isStarted, Boolean isComplete, List<Task> tasks) {
		this.objectiveID = id;
		this.roomID = roomID;
		this.isStarted = isStarted;
		this.isComplete = isComplete;
		this.tasks = tasks;
	}
	
	public Boolean getIsStarted() {
		return isStarted;
	}
	
	public Boolean getIsComplete() {
		return isComplete;
	}
	
	public List<Task> getTasks() {
		return tasks;
	}
	
	public int getObjectiveID() {
		return objectiveID;
	}
	
	public int getRoomID() {
		return roomID;
	}
	
	public void setIsStarted(Boolean isStarted) {
		this.isStarted = isStarted;
	}
	
	public void setIsComplete(Boolean isComplete) {
		this.isComplete = isComplete;
	}
	
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	public void setObjectiveID(int id) {
		this.objectiveID = id;
	}
	
	public void setRoomID(int id) {
		this.roomID = id;
	}
	
	public Boolean verifyComplete() {
		Boolean result = true;
		if(tasks!=null) {
			for(Task task : tasks) {
				//TODO: call verifyComplete for each task here
				if(!task.getIsComplete()) {
					result = false;
				}
			}
		}
		
		//TODO: if the tasks are all complete, change the objective to complete in the db and start the next one
		//Some objectives will need to unlock other things in the room, so we should figure out how we're doing that
		return result;
	}
}
