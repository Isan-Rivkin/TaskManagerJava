package model.search;

import java.util.ArrayList;
import java.util.Collection;

import model.process.IProcess;

public class ProcessSearcher implements Searcher
{

	@Override
	public Collection<IProcess> search(Collection<IProcess> list, String query) 
	{
		Collection<IProcess> result = new ArrayList<>();
		for(IProcess process : list)
		{
			if(inTitle(process, query) || inComments(process, query))
			{
				result.add(process);
			}
		}
		if(result.size() ==0 )
			return null;
		return result;
	}
	private boolean inTitle(IProcess p, String query)
	{
		if(p.getProcessName().contains(query))
		{
			return true;
		}
		return false;
	}
	private boolean inComments(IProcess p, String query)
	{
		for(String l : p.getComments())
		{
			if(l.contains(query))
			{
				return true;
			}
		}
		return false;
	}

}
