package model.process;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javafx.beans.property.SimpleStringProperty;
import model.task.ITask;
import utils.AUtil;

public class Process implements IProcess, Serializable 
{
	private HashMap<String,ITask> tasks;
	private long startTime,currentTime,finishedTime,freezedTime;
	private ArrayList<String> process_comments;
	private boolean finished,deleted,started,freezed;
	private String title,description,process_id;
	private SimpleStringProperty firstName;
	private LocalDate submitonDate;
	private int targetDays;
	//handle table view 
	private SimpleStringProperty tableTitle,tableComments,tableTimeSinceStart,tableDaysLeft;
	public Process() 
	{	tableDaysLeft=new SimpleStringProperty();
		tableTimeSinceStart=new SimpleStringProperty();
		firstName=new SimpleStringProperty();
		tableTitle=new SimpleStringProperty();
		tableComments=new SimpleStringProperty();
		process_comments=new ArrayList<>();
		finishedTime=0;
		freezedTime=0;
		startTime=0;
		finished=false;
		deleted=false;
		started=false;
		freezed=true;
		tasks=new HashMap<String,ITask>();
	}

		@Override
	public void addTask(ITask task) 
		{
		tasks.put(task.getTitle(), task);
	    }

	@Override
	public ITask getTask(String title)
	{
		return tasks.get(title);
	}

	@Override
	public void deleteTask(String title) 
	{
		tasks.remove(title);
		
	}

	@Override
	public boolean isFinishedTask(String title) 
	{
		return tasks.get(title).isFinished();
	}

	@Override
	public void finishTask(String title)
	{
		tasks.get(title).finish();
	}

	@Override
	public void start()
	{
		freezed=false;
		started=true;
		finished=false;
		if(startTime == 0)
		{
			startTime=System.currentTimeMillis();
		}
	}

	@Override
	public boolean isStarted()
	{
		return started;
	}
	@Override
	public void freeze()
	{
		freezedTime=System.currentTimeMillis();
		freezed=true;
	}
	@Override
	public boolean isFreezed() 
	{
		return freezed;
	}
	@Override
	public void deleteProcess() 
	{
		for(ITask t: tasks.values())
		{
			t.delete();
		}
		deleted=true;
			
		
	}

	@Override
	public boolean isDeletedProcess()
	{
		return deleted;
	}

	@Override
	public void finishProcess()
	{
		for(ITask t: tasks.values())
		{
			t.finish();
		}
		finished=true;
		finishedTime=System.currentTimeMillis();
	}

	@Override
	public boolean isFinishedProcess() 
	{
		return finished;
	}

	@Override
	public String getTimeSinceStart() 
	{
		if(this.isFinished())
			return ""+finishedTime;
		long curr=System.currentTimeMillis()-startTime;
		String s=""+curr;
		return s;
	}

	@Override
	public HashMap<String,ITask> getTasks() 
	{
		return tasks;
	}
	
	@Override
	public void setName(String name) 
	{
		title=name;
		generateProcessID();
	}
	@Override
	public void setDesc(String desc)
	{
		description=desc;
	}
	@Override
	public void addComment(String comment) 
	{
		process_comments.add(comment);
		
	}
	
	//default getters setters
	public long getStartTime() 
	{
		return startTime;
	}
	public void setStartTime(long startTime)
	{
		this.startTime = startTime;
	}
	public long getCurrentTime()
	{
		return currentTime;
	}
	public void setCurrentTime(long currentTime) 
	{
		this.currentTime = currentTime;
	}
	public ArrayList<String> getProcess_comments() 
	{
		return process_comments;
	}
	public void setProcess_comments(ArrayList<String> process_comments) 
	{
		this.process_comments = process_comments;
	}
	public boolean isFinished()
	{
		return finished;
	}
	public void setFinished(boolean finished)
	{
		this.finished = finished;
	}
	public boolean isDeleted() 
	{
		return deleted;
	}
	public void setDeleted(boolean deleted) 
	{
		this.deleted = deleted;
	}
	public void setTasks(HashMap<String, ITask> tasks) 
	{
		this.tasks = tasks;
	}
	public void setStarted(boolean started) 
	{
		this.started = started;
	}
	@Override
	public String getProcessName()
	{
		return title;
	}
	public long getFinishedTime() 
	{
		return finishedTime;
	}
	public void setFinishedTime(long finishedTime)
	{
		this.finishedTime = finishedTime;
	}
	public String getTitle() 
	{
		return title;
	}
	public void setTitle(String title) 
	{
		this.title = title;
		this.tableTitle=new SimpleStringProperty(title);
	}
	public String getDescription() 
	{
		return description;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}
	@Override
	public boolean isAllTasksFinished()
	{
		for(ITask t: tasks.values()){
			if(!t.isFinished())
				return false;
		}
		return true;

	}

	@Override
	public long getStartTimeMillis() 
	{
		return startTime;
	}
	@Override
	public ArrayList<String> getComments()
	{
		return this.process_comments;
	}
	@Override
	public long getFinishTimeMillis() 
	{
		return finishedTime;
	}
	@Override
	public String getFirstName() 
	{
		return firstName.get();
	}
	@Override
	public String getTableTitle() 
	{
		return title;
	}
	@Override
	public String getTableComments()
	{
		return process_comments.toString();
	}
	@Override
	public String getTableTimeSinceStart() 
	{
		if (isFinishedProcess()) 
		{
			tableTimeSinceStart=new SimpleStringProperty(AUtil.getTotalTimeDaysHoursMinSec(getStartTimeMillis(),getFinishTimeMillis()));
		}
		else if(isStarted() && !isFreezed())
		{
			tableTimeSinceStart=new SimpleStringProperty(AUtil.getTotalTimeDaysHoursMinSec(getStartTimeMillis(), System.currentTimeMillis()));
		}
		else if(isFreezed())
		{
			tableTimeSinceStart=new SimpleStringProperty(AUtil.getTotalTimeDaysHoursMinSec(getStartTimeMillis(), freezedTime));
		}
		else 
		{
			tableTimeSinceStart=new SimpleStringProperty(""+0);
		}
		return tableTimeSinceStart.get();
	}
	@Override
	public String getProcessID() 
	{
		return generateProcessID();
	}
	public String generateProcessID()
	{
		Scanner scanner= new Scanner(title);
		scanner.useDelimiter(" ");
		String id="";
		while(scanner.hasNext())
		{
			id+=scanner.next()+"%";
		}
		process_id=id;
		return id;
	}

	@Override
	public void addSubmitionDate(LocalDate submitionDate)
	{
		this.submitonDate=submitionDate;
	}

	@Override
	public void setReplaceComments(ArrayList<String> new_comments) 
	{
		this.process_comments=new_comments;
	}

	@Override
	public void setTargetDays(int days) 
	{
		targetDays=days;
		
	}

	@Override
	public int getTargetDays()
	{
		return targetDays;
	}

	@Override
	public long getDaysLeftToTarget() 
	{
		if(!isStarted())
		{
			return (long)targetDays;
		}
		long startDays = TimeUnit.MILLISECONDS.toDays(startTime);
		long nowDays = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis())-startDays;
		long left = (long)getTargetDays()-nowDays;
		if(left <=0)
			return (long)0;
		return left;
	}


	@Override
	public String getTableDaysLeft()
	{
		return ""+getDaysLeftToTarget();
	}

	@Override
	public boolean isPastTarget() 
	{
		if(targetDays == 0)
			return false;
		if(getDaysLeftToTarget() == 0)
		{
			return true;
		}
		return false;
	}

	@Override
	public int compareTo(IProcess o) {
		System.out.println("process: compareTo -> activated");
		return 0;
	}

}
