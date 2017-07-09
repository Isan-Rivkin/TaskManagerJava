package controller.commands;

import java.util.LinkedList;

import model.process.IProcessManager;

public class FinishProcessCommand extends CommonCommand 
{

	private IProcessManager model;
	private String processID;
	public FinishProcessCommand(IProcessManager model) 
	{
		this.model=model;
	}
	@Override
	public void execute() 
	{
		model.finishProcess(processID);
	}

	@Override
	public void init(LinkedList<String> params)
	{
		processID=params.remove();
	}
	
}
