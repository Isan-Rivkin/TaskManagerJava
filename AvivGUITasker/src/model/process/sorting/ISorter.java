package model.process.sorting;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import model.process.IProcess;

public interface ISorter 
{
	public List<IProcess> sort(HashMap<String,IProcess> process_list);
	public List<IProcess> sort(Collection<IProcess> process_list);
	public List<IProcess> sort(List<IProcess> l);

}
