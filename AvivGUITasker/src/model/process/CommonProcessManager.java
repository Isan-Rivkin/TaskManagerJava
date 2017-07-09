package model.process;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import model.creators.DB;
import model.creators.DBCreator;
import model.process.sorting.BasicSorter;
import model.process.sorting.ComparatorFactory;
import model.process.sorting.ComparatorGenerator;
import model.process.sorting.ComparatorGenerator.Sort_Type;
import model.process.sorting.ISorter;
import model.search.ProcessSearcher;
import model.search.Searcher;
import model.task.ITask;
import view.View.process_type;

public class CommonProcessManager extends Observable implements IProcessManager
{
	public static long currentTime;
	private HashMap<String, IProcess> process_current;
	private HashMap<String, IProcess> process_finished;
	private HashMap<String, IProcess> process_queue;
	private DBCreator db;
	private boolean keepUpdating;
	private Timer timer;
	private ComparatorGenerator comparator_factory;
	private List<IProcess> lastSortedList;
	private Collection<IProcess> lastSearchedList;
	private Searcher searcher;
	public CommonProcessManager() 
	{
		searcher = new ProcessSearcher();
		lastSortedList = new ArrayList<IProcess>();
		timer = new Timer();
		keepUpdating = true;
		//asyncTimeUpdate();
		process_queue = new HashMap<>();
		process_current = new HashMap<>();
		process_finished = new HashMap<>();
		db=new DB();
		comparator_factory = new ComparatorFactory();
	}

	@Override
	public void updateComment(String processName, String taskName, String oldComment, String upDatedComment) 
	{
		IProcess p = process_current.get(processName);
		if (p == null) {
			return;
		}
		p.getTask(taskName).editComment(oldComment, upDatedComment);
		List<String> params = new LinkedList<String>();
		params.add("display");
		setChanged();
		notifyObservers(params);
	}

	@Override
	public void loadData(String currentProcessPath, String inQueueProcessPath, String finishedProcessPath)
{
		process_current = db.loadProcessDataXML(currentProcessPath);
		process_queue = db.loadProcessDataXML(inQueueProcessPath);
		process_finished = db.loadProcessDataXML(finishedProcessPath);
		updateObserver("display");
	}

	@Override
	public void saveData(String currentProcessPath, String inQueueProcessPath, String finishedProcessPath)
	{
		db.saveProcessDataXML(currentProcessPath, process_current);
		db.saveProcessDataXML(inQueueProcessPath, process_queue);
		db.saveProcessDataXML(finishedProcessPath, process_finished);

	}

	@Override
	public void exit()
	{
		keepUpdating=false;
		timer.cancel();
		timer.purge();
		System.exit(0);
		
	}

	@Override
	public String getProcessCurrentTime(String processName) 
	{
		if (process_current.containsKey(processName)){
			return process_current.get(processName).getTimeSinceStart();
		}
		else if (process_finished.containsKey(processName)){
			return process_finished.get(process_current).getTimeSinceStart();
		}
		else if (process_queue.containsKey(processName)){
			return "" + 0;
		}
		return null;
	}

	@Override
	public void deleteProcess(String processName) 
	{
		if (process_current.containsKey(processName))
			process_current.remove(processName);
		if (process_finished.containsKey(processName))
			process_finished.remove(processName);
		if (process_queue.containsKey(processName))
			process_queue.remove(processName);
		updateObserver("msg","alert","Delete Process", "process deleted.");
	}

	@Override
	public void finishProcess(String processID)
	{
		IProcess p=getProcessByID(processID);
		if(p == null)
			{
			updateObserver("display");
			return;
			}
		if(process_current.containsKey(processID))
		{
			process_current.remove(processID);
		}
		else if(process_queue.containsKey(processID))
		{
			process_queue.remove(processID);
		}
		if(!process_finished.containsKey(processID))
		{
			process_finished.put(processID, p);
		}
		p.finishProcess();
		updateObserver("display");
	}

	@Override
	public boolean isProcessFinished(String processName)
	{
		return process_finished.containsKey(processName);
	}

	@Override
	public HashMap<String, IProcess> getCurrentProcess() 
	{
		return process_current;
	}

	@Override
	public HashMap<String, IProcess> getFinishedtProcess()
	{
		return process_finished;
	}

	@Override
	public void addProcess(IProcess process) 
	{
		if(process_current.containsKey(process) || process_queue.containsKey(process))
		{
			updateObserver("pexist");
			return;
		}
		if (process.isStarted())
		{
			process_current.put(process.getProcessID(), process);
		}
		else if(process.isFinishedProcess())
		{
			process_finished.put(process.getProcessID(),process);
		}
		else 
		{
			process_queue.put(process.getProcessID(), process);
		}
		updateObserver("display");
	}

	@Override
	public String getProcessStartTime(String pID)
	{
		if (process_current.containsKey(pID))
			return process_current.get(pID).getTimeSinceStart();
		if (process_finished.containsKey(pID))
			return process_finished.get(pID).getTimeSinceStart();
		return null;
	}

	@Override
	public boolean isTaskFinished(String processName, String taskName)
	{
		if (process_current.containsKey(processName))
			return process_current.get(processName).getTask(taskName).isFinished();
		if (process_finished.containsKey(processName))
			return true;
		return false;
	}

	@Override
	public void finishTask(String processName, String taskName)
	{
		process_current.get(processName).getTask(taskName).finish();
		updateObserver("display");

	}

	@Override
	public void deleteTask(String processName, String taskName) 
	{
		if (process_current.containsKey(processName)) {
			process_current.get(processName).deleteTask(taskName);
		}
		updateObserver("display");

	}

	@Override
	public void addTask(String processName, ITask task)
	{
		if(process_queue.containsKey(processName))
			process_queue.get(processName).addTask(task);
		if (process_current.containsKey(processName))
			process_current.get(processName).addTask(task);
		updateObserver("display");
	}

	@Override
	public String getTaskCurrentTime(String processName, String taskName)
	{
		if (process_current.containsKey(processName))
			return "" + (currentTime - process_current.get(processName).getTask(taskName).getTimeStart());
		return null;
	}

	@Override
	public void startProcess(String processID) 
	{
		IProcess process = getProcessByID(processID);
		if(process == null)
			return;
		process.start();
		if(process_finished.containsKey(processID))
		{
			process_finished.remove(processID);
		}
		else if(process_queue.containsKey(processID))
		{
			process_queue.remove(processID);
		}
		if(!process_current.containsKey(processID))
		{
			process_current.put(processID, process);
		}
		updateObserver("display");
	}
	@Override
	public void freezeProcess(String processID)
	{
		IProcess p = getProcessByID(processID);
		if(p==null)
		{
			System.err.println("[Model]: freezeProcess = process is null");
			return;
		}
		if(process_current.containsKey(processID))
		{
			process_current.remove(processID);
		}
		else if(process_finished.containsKey(processID))
		{
			process_finished.remove(processID);
		}
		if(!process_queue.containsKey(processID))
		{
			p.freeze();
			process_queue.put(processID, p);
		}
		updateObserver("display");
		
	}
	@Override
	public String getTaskStartTime(String processName, String taskName) 
	{
		if (process_current.containsKey(processName))
			return "" + (process_current.get(processName).getTask(taskName).getTimeStart());
		return null;
	}

	private void asyncTimeUpdate() 
	{
		timer.scheduleAtFixedRate(new TimerTask()
		{
			@Override
			public void run() {
				while(keepUpdating){
					updateCurrentTime();
				}
			}
		}, 0, 1000);
	}

	private void updateCurrentTime()
	{
		currentTime = System.currentTimeMillis();
	}

	private void updateObserver(String... strings)
	{
		List<String> params = new LinkedList<String>();
		for (String s : strings)
			params.add(s);
		setChanged();
		notifyObservers(params);
	}

	@Override
	public int getNumOfProcess()
	{
		return process_current.size()+process_finished.size()+process_queue.size();
	}

	@Override
	public ArrayList<String> getAllNamesOfProecss() 
	{
		ArrayList<String> names=new ArrayList<>();
		for(String s: process_queue.keySet()){
			names.add(s);
		}
		for(String s: process_current.keySet()){
			names.add(s);
		}
		for(String s: process_finished.keySet()){
			names.add(s);
		}
		return names;
	}

	@Override
	public HashMap<String, IProcess> getQueuedProcess() 
	{
		return process_queue;
	}

	@Override
	public int getNumOfCurrentProcess() 
	{
		return process_current.size();
	}

	@Override
	public int getNumOfQueuedProcess()
	{
		return process_queue.size();
	}

	@Override
	public int getNumOfFinishedProcess() 
	{
		return process_finished.size();
	}

	@Override
	public IProcess getProcessByID(String processID)
	{
		if(process_current.containsKey(processID))
		{
			return process_current.get(processID);
		}
		if(process_queue.containsKey(processID))
		{
			return process_queue.get(processID);
		}
		if(process_finished.containsKey(processID))
		{
			return process_finished.get(processID);
		}
		return null;
	}

	@Override
	public void editProcess(String lastID, IProcess copy) 
	{
		HashMap<String, IProcess> list = findListByPID(lastID);
		if(list == null)
		{
			updateObserver("msg","error","Update Process","Cannot find process ID.");
			return;
		}
		IProcess p = list.get(lastID);
		list.remove(lastID);
		p.setName(copy.getProcessName());
		p.setReplaceComments(copy.getComments());
		if(copy.getTargetDays() != 0)
		{
			p.setTargetDays(copy.getTargetDays());
		}
		list.put(p.getProcessID(),p);
		updateObserver("display");
		updateObserver("msg","alert","Update Process","Process successfuly updated!");
	}

	@Override
	public HashMap<String, IProcess> findListByPID(String processID)
	{
		if(process_current.containsKey(processID))
		{
			return process_current;
		}
		else if(process_finished.containsKey(processID))
		{
			return process_finished;
		}
		if(process_queue.containsKey(processID))
		{
			return process_queue;
		}
		return null;
	}

	@Override
	public void sort(process_type processType, Sort_Type sortType) 
	{
		Comparator<IProcess> comparator  = comparator_factory.generate(sortType);
		ISorter sorter = new BasicSorter(comparator);
		switch(processType)
		{
		case CURRENT:
			lastSortedList= sorter.sort(process_current);
			break;
		case FINISHED:
			lastSortedList=sorter.sort(process_finished);
			break;
		case QUEUED:
			lastSortedList=sorter.sort(process_queue);
			break;
		default:
			break;
		}
		updateObserver("displaysorted");
	}
	@Override
	public void setSortedList(List<IProcess> list)
	{
		lastSortedList = list;
	}
	@Override
	public List<IProcess> getSortedList() 
	{
		return lastSortedList;
	}
	@Override
	public Collection<IProcess> getSearchedList()
	{
		return lastSearchedList;
	}
	@Override
	public Collection<IProcess> searchInProcess(String query) 
	{
		Collection<IProcess> result;
		result = searcher.search(process_current.values(), query);
		if(result == null)
		{
			result = new ArrayList<IProcess>();
		}
		Collection<IProcess> result_finished =searcher.search(process_finished.values(), query);
		if(result_finished != null)
		{
			result.addAll(result_finished);
		}
		Collection<IProcess> result_queue =searcher.search(process_queue.values(), query);
		if(result_queue != null)
		{
			result.addAll(result_queue);
		}
		lastSearchedList = result;
		updateObserver("displaysearched");
		return result;
	}
	
	
}
