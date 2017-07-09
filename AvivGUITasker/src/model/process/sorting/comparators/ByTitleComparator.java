package model.process.sorting.comparators;

import java.util.Comparator;

import model.process.IProcess;

public class ByTitleComparator implements Comparator<IProcess>
{
	@Override
	public int compare(IProcess o1, IProcess o2) 
	{
		return o1.getProcessName().compareTo(o2.getProcessName());
	}

}
