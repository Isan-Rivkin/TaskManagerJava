package model.task;

import java.io.Serializable;
import java.util.ArrayList;


public class Task  implements ITask, Serializable {
	private long beginTime,finishTime;
	private String title,description;
	private ArrayList<String> comments;
	private volatile boolean start,deleted,finished;
	private int priority;

	public Task() {
		priority=0;
		beginTime=0;
		title="";
		description="";
		comments=new ArrayList<String>();
		deleted=false;
		start=false;
		finished=false;
	}

	public long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(long finishTime) {
		this.finishTime = finishTime;
	}

	@Override
	public void start() {
		start=true;
		beginTime=System.currentTimeMillis();
	}

	@Override
	public void delete() {
		deleted=true;

	}

	@Override
	public boolean isDeleted() {
		return deleted;
	}

	@Override
	public void finish() {
		finished=true;
		finishTime=System.currentTimeMillis()-beginTime;

	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	@Override
	public void setTitle(String title) {
		this.title=title;

	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setDescription(String desc) {
		this.description=desc;

	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void addComment(String comment) {
		comments.add(comment);
	}

	@Override
	public ArrayList<String> getComments() {
		return comments;
	}

	@Override
	public void editComment(String oldComment, String newComment) {
		if(comments.contains(oldComment)){
			deleteComment(oldComment);
			comments.add(newComment);
		}
	}

	@Override
	public long getTimeStart() {
		return beginTime;
	}
	@Override
	public void setPriority(int i) {
		if(i < 4 && i > -1)
			priority=i;
	}
	@Override
	public int getPriority() {
		return priority;
	}
	@Override
	public void deleteComment(String comment) {
		comments.remove(comment);
	}
	
	
	// default setters and getters
	public long getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}
	public boolean isStart() {
		return start;
	}
	public void setStart(boolean start) {
		this.start = start;
	}
	public void setComments(ArrayList<String> comments) {
		this.comments = comments;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}

}
