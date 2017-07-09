package model.process.sorting;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import model.process.IProcess;
import utils.AUtil;


public class BasicSorter implements ISorter 
{

	private Comparator<IProcess> comparator;
    
	public BasicSorter(Comparator<IProcess> comparator) 
	{
    	this.comparator=comparator;
	}
	@Override
	public List<IProcess> sort(HashMap<String, IProcess> process_list) 
	{
			List<IProcess> l =AUtil.MapToList(process_list);
			return sort(l);
		
	}
	@Override
	public List<IProcess> sort(Collection<IProcess> process_list)
	{
		HashMap<String,IProcess> l = new HashMap<>();
		for(IProcess p : process_list)
		{
			l.put(p.getProcessID(),p);
		}
		return sort(l);
		
	}
	@Override
	public List<IProcess> sort(List<IProcess> l)
	{
		for(int i=0;i<l.size();++i)
		{
			for(int j=0;j<l.size()-1;++j)
			{
				IProcess temp;
				if(comparator.compare(l.get(j), l.get(j+1))<0)
				{
					temp = l.remove(j);
					l.add(j+1, temp);
				}
			}
		}
		return l;
	}


}
