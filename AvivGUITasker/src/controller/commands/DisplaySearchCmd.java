package controller.commands;

import java.util.LinkedList;

import model.process.IProcessManager;
import view.IView;

public class DisplaySearchCmd extends CommonCommand
{
	IProcessManager model;
	IView view;
	
	public DisplaySearchCmd(IProcessManager model, IView view) 
	{
		this.model=model;
		this.view=view;
	}
	@Override
	public void execute() 
	{
		view.updateSearchedList(model.getSearchedList());
	}

	@Override
	public void init(LinkedList<String> params) {
		// TODO Auto-generated method stub
		
	}

}
