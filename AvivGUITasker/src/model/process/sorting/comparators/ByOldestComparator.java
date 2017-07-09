package model.process.sorting.comparators;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import model.process.IProcess;

public class ByOldestComparator implements Comparator<IProcess>
{

	@Override
	public int compare(IProcess o1, IProcess o2)
	{
		// TODO Auto-generated method stub
		return (int)(o1.getStartTimeMillis()-o2.getStartTimeMillis());
	}

}
