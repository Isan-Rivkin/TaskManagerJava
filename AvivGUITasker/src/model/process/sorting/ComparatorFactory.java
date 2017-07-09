package model.process.sorting;

import java.util.Comparator;
import java.util.HashMap;

import model.process.IProcess;
import model.process.sorting.comparators.ByFirstDeadLineComparator;
import model.process.sorting.comparators.ByLastDeadLineComparator;
import model.process.sorting.comparators.ByNewestComparator;
import model.process.sorting.comparators.ByOldestComparator;
import model.process.sorting.comparators.ByTitleComparator;

public class ComparatorFactory implements ComparatorGenerator 
{
//	ByTitle,ByFirstDeadLine, ByLastDeadLine,ByOldest,ByNewest;
	private HashMap<Sort_Type, Generator> map;
	
	public ComparatorFactory() 
	{
		map=new HashMap<>();
		map.put(Sort_Type.ByTitle,new ByTitleGenerator());
		map.put(Sort_Type.ByNewest, new ByNewestGenerator());
		map.put(Sort_Type.ByOldest, new ByOldestGenerator());
		map.put(Sort_Type.ByFirstDeadLine, new ByFirstDeadLineGenerator());
		map.put(Sort_Type.ByLastDeadLine,new ByLastDeadLineGenerator());
	}
	
	@Override
	public Comparator<IProcess> generate(Sort_Type type) 
	{
		if(map.containsKey(type))
		{
			Comparator<IProcess> comp= map.get(type).generate();
			return comp;
		}
		else
		{
			return null;
		}
	}
	private interface Generator
	{
		Comparator<IProcess> generate();
	}
	private class ByTitleGenerator implements Generator
	{
		@Override
		public Comparator<IProcess> generate() 
		{
			return new ByTitleComparator();
		}		
	}
	private class ByFirstDeadLineGenerator implements Generator
	{

		@Override
		public Comparator<IProcess> generate()
		{
			return new ByFirstDeadLineComparator();
		}
		
	}
	private class ByLastDeadLineGenerator implements Generator
	{

		@Override
		public Comparator<IProcess> generate() {
			
			return new ByLastDeadLineComparator();
		}
		
	}
	private class ByNewestGenerator implements Generator
	{
		@Override
		public Comparator<IProcess> generate()
		{
			return new ByNewestComparator();
		}
	}
	private class ByOldestGenerator implements Generator
	{

		@Override
		public Comparator<IProcess> generate() 
		{
			return new ByOldestComparator();
		}
	}
	
}
