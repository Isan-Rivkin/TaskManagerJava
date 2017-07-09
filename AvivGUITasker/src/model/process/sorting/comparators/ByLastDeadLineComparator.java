package model.process.sorting.comparators;

import java.util.Comparator;

import model.process.IProcess;

public class ByLastDeadLineComparator implements Comparator<IProcess> 
{

	@Override
	public int compare(IProcess o1, IProcess o2) 
	{
		return (int)(o1.getDaysLeftToTarget()-o2.getDaysLeftToTarget());
	}

}
