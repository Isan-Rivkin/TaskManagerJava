package model.task;

import java.io.Serializable;
import java.util.ArrayList;

public interface ITask extends Serializable{
	public void start();
	public void delete();
	public boolean isDeleted();
	public void finish();
	public boolean isFinished();
	public void setTitle(String title);
	public String getTitle();
	public void setDescription(String desc);
	public String getDescription();
	public void addComment(String comment);
	public ArrayList<String> getComments();
	public void deleteComment(String comment);
	public long getTimeStart();
	public void setPriority(int i);
	public int getPriority();
	void editComment(String oldComment, String newComment);
}
