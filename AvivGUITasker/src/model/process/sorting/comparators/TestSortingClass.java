package model.process.sorting.comparators;

import java.util.Comparator;
import java.util.List;

import model.process.CommonProcessManager;
import model.process.IProcess;
import model.process.sorting.BasicSorter;
import model.process.sorting.ComparatorFactory;
import model.process.sorting.ComparatorGenerator;
import model.process.sorting.ComparatorGenerator.Sort_Type;
import model.process.sorting.ISorter;

public class TestSortingClass {

	public static void main(String[] args) 
	{
		final String current_path="taskConfig/current.xml";
		final String queued_path="taskConfig/queded.xml";
		final String finished_path="taskConfig/finished.xml";
		CommonProcessManager model= new CommonProcessManager();
		List<IProcess> list=null;
		model.loadData(current_path, queued_path, finished_path);
		
		ComparatorGenerator factory = new ComparatorFactory();
		Comparator<IProcess> comparator  = factory.generate(Sort_Type.ByLastDeadLine);
		ISorter sorter = new BasicSorter(comparator);
		list = sorter.sort(model.getCurrentProcess());
		for(IProcess p : list)
		{
			System.out.println(""+p.getDaysLeftToTarget());
		}
	}

}
