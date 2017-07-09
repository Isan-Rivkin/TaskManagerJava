package model.process.sorting.comparators;

import java.util.Comparator;

import model.process.IProcess;

public class ByFirstDeadLineComparator implements Comparator<IProcess> 
{

	@Override
	public int compare(IProcess o1, IProcess o2) 
	{
		return (int)(o2.getDaysLeftToTarget()-o1.getDaysLeftToTarget());
	}

}
