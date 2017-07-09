package model.search;

import java.util.Collection;

import model.process.CommonProcessManager;
import model.process.IProcess;

public class SearchTest {

	public static void main(String[] args) 
	{
		CommonProcessManager model= new CommonProcessManager();
		final String current_path="taskConfig/current.xml";
		final String queued_path="taskConfig/queded.xml";
		final String finished_path="taskConfig/finished.xml";
		
		model.loadData(current_path, queued_path, finished_path);
		
		Collection<IProcess> c = model.searchInProcess("הערה");
		for(IProcess p : c )
		{
			System.out.println(""+p.getTableComments());
		}

	}

}
