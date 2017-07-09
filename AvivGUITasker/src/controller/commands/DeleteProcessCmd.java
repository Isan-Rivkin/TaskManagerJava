package controller.commands;

import java.util.LinkedList;

import model.process.IProcessManager;

public class DeleteProcessCmd extends CommonCommand
{

	String processID;
	IProcessManager model;
	
	public DeleteProcessCmd(IProcessManager model) 
	{
		this.model=model;
	}
	@Override
	public void execute() 
	{
		model.deleteProcess(processID);
	}

	@Override
	public void init(LinkedList<String> params) 
	{
		this.processID = params.remove();
	}

}
