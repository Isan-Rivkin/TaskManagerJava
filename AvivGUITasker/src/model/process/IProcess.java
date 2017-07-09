package model.process;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import model.task.ITask;

public interface IProcess extends Serializable,Comparable<IProcess>{
	//tasks related
	public void addTask(ITask task);
	public ITask getTask(String title);
	public void deleteTask(String title);
	public boolean isFinishedTask(String title);
	public void finishTask(String title);
	public boolean isAllTasksFinished();
	//process related;
	public void setName(String name);
	public void setDesc(String desc);
	public void addComment(String comment);
	public void start();
	public boolean isStarted();
	public void deleteProcess();
	public boolean isDeletedProcess();
	public void finishProcess();
	public boolean isFinishedProcess();
	public void freeze();
	public boolean isFreezed();
	public String getTimeSinceStart();
	public long getStartTimeMillis();
	public long getFinishTimeMillis();
	public String getProcessName();
	public ArrayList<String> getComments();
	public String getDescription();
	//date stuff
	public void addSubmitionDate(LocalDate submitionDate);
	//managment
	public String getProcessID();
	public HashMap<String,ITask> getTasks();
	//delete !!!!
	public String getFirstName();
	public void setReplaceComments(ArrayList<String> new_comments);
	//target days calcs
	public void setTargetDays(int days);
	public int getTargetDays();
	public long getDaysLeftToTarget();
	// table view data - SimpleStringProperty
	public String getTableTitle();
	public String getTableComments();
	public String getTableTimeSinceStart();
	public String getTableDaysLeft();
	public boolean isPastTarget();

	
  
}
