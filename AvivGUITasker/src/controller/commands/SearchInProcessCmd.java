package controller.commands;

import java.util.LinkedList;

import model.process.IProcessManager;

public class SearchInProcessCmd extends CommonCommand
{

	private IProcessManager model;
	private String query;
	public SearchInProcessCmd(IProcessManager model) 
	{
		this.model=model;
	}
	@Override
	public void execute()
	{
		model.searchInProcess(query);
	}

	@Override
	public void init(LinkedList<String> params) 
	{
		query=params.remove();
	}
	
}
