package model.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import model.task.ITask;
import view.View.process_type;
import model.process.sorting.ComparatorGenerator.Sort_Type;
public interface IProcessManager 
{

	
	public void updateComment(String pID, String taskName,String oldComment, String upDatedComment);
//general 
	public void loadData(String currentProcessPath,String inQueueProcessPath,String finishedProcessPath);
	public void saveData(String currentProcessPath, String inQueueProcessPath,String finishedProcessPath);
	public void exit();
	//process related
	public String getProcessCurrentTime(String pID);
	public void deleteProcess(String pID);
	public void finishProcess(String pID);
	public boolean isProcessFinished(String pID);
	public HashMap<String, IProcess> getCurrentProcess();
	public HashMap<String, IProcess> getFinishedtProcess();
	public HashMap<String, IProcess> getQueuedProcess();
	public void addProcess(IProcess process);
	public String getProcessStartTime(String pID);
	public int getNumOfProcess();
	public int getNumOfCurrentProcess();
	public int getNumOfQueuedProcess();
	public int getNumOfFinishedProcess();
	public ArrayList<String> getAllNamesOfProecss();
	//task related
	public boolean isTaskFinished(String pID, String taskName);
	public void finishTask(String pID, String taskName);
	public void deleteTask(String pID, String taskName);
	public void addTask(String pID,ITask task);
	public String getTaskCurrentTime(String pID, String taskName);
	public String getTaskStartTime(String pID, String taskName);
	public IProcess getProcessByID(String processID);
	public void startProcess(String processID);
	public void freezeProcess(String processID);
	public void editProcess(String lastID, IProcess copy);
	public HashMap<String, IProcess> findListByPID(String processID);
	// sorting related
	public void sort(process_type processType,Sort_Type sortType);
	public List<IProcess> getSortedList();
	public void setSortedList(List<IProcess> list);
	//search related
	public Collection<IProcess> searchInProcess(String query);
	public Collection<IProcess> getSearchedList();
	
}
