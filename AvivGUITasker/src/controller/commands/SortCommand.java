package controller.commands;

import java.util.HashMap;
import java.util.LinkedList;

import model.process.IProcessManager;
import model.process.sorting.ComparatorGenerator.Sort_Type;
import utils.AUtil;
import view.View.process_type;

public class SortCommand extends CommonCommand
{

	private IProcessManager model;
	private String sort_type,listType;
	private HashMap<String,Sort_Type> map;
	
	public SortCommand(IProcessManager model) 
	{
		this.model=model;
		map= new HashMap<>();
		map.put("bytitle", Sort_Type.ByTitle);
		map.put("byfirstdeadline", Sort_Type.ByFirstDeadLine);
		map.put("bylastdeadline", Sort_Type.ByLastDeadLine);
		map.put("byoldest", Sort_Type.ByOldest);
		map.put("bynewest",Sort_Type.ByNewest);
	}
	@Override
	public void execute() 
	{
		model.sort(AUtil.ProcessStringToEnum(listType), map.get(sort_type));
	}

	@Override
	public void init(LinkedList<String> params) 
	{
		listType = params.removeFirst();
		sort_type = params.remove();
	}

}
