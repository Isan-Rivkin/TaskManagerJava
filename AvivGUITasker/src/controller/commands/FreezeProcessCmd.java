package controller.commands;

import java.util.LinkedList;

import model.process.IProcessManager;

public class FreezeProcessCmd extends CommonCommand
{
	private IProcessManager model;
	private String processID;
	public FreezeProcessCmd(IProcessManager model)
	{
		this.model=model;
		this.processID="";
	}
	@Override
	public void execute() 
	{
		model.freezeProcess(processID);
	}

	@Override
	public void init(LinkedList<String> params)
	{
		processID=params.remove();

	}
}
