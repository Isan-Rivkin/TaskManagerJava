package model.process.sorting.comparators;

import java.util.Comparator;

import model.process.IProcess;

public class ByNewestComparator implements Comparator<IProcess>
{

	@Override
	public int compare(IProcess o1, IProcess o2) 
	{
		return (int)(o2.getStartTimeMillis()-o1.getStartTimeMillis());
	}

}
