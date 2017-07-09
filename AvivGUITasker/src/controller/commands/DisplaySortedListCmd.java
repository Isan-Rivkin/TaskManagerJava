package controller.commands;

import java.util.LinkedList;

import model.process.IProcessManager;
import view.IView;

public class DisplaySortedListCmd extends CommonCommand
{

	IProcessManager model;
	IView view;
	
	public DisplaySortedListCmd(IProcessManager model, IView view) 
	{
		this.model=model;
		this.view=view;
	}
	@Override
	public void execute()
	{
		view.updateSortedList(model.getSortedList());
	}

	@Override
	public void init(LinkedList<String> params) 
	{	
	}

}
