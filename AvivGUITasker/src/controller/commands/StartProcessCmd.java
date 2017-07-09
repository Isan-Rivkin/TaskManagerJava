package controller.commands;

import java.util.LinkedList;

import model.process.IProcessManager;
import view.IView;

public class StartProcessCmd extends CommonCommand {

	private IProcessManager model;
	private IView view;
	private String processID;
	public StartProcessCmd(IProcessManager model , IView view) 
	{
		this.model=model;
		this.view=view;
	}
	@Override
	public void execute() 
	{
		model.startProcess(processID);
	}

	@Override
	public void init(LinkedList<String> params) 
	{
		processID=params.remove();
	}


}
