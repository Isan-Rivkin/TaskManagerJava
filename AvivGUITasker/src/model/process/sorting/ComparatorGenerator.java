package model.process.sorting;

import java.util.Comparator;

import model.process.IProcess;

public interface ComparatorGenerator 
{
	enum Sort_Type
	{	
		ByTitle,ByFirstDeadLine, ByLastDeadLine,ByOldest,ByNewest;
	}
	public Comparator<IProcess> generate(Sort_Type type);
}
