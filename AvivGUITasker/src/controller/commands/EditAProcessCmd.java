package controller.commands;

import java.util.LinkedList;

import model.process.IProcess;
import model.process.IProcessManager;
import model.process.Process;
import utils.AUtil;

public class EditAProcessCmd extends CommonCommand
{

	private IProcessManager model;
	String lastID,currentTitle,currentDescription,targetDays;
	public EditAProcessCmd(IProcessManager model) 
	{
		this.model=model;
	}
	@Override
	public void execute() 
	{
		IProcess copy=new Process();
		copy.setName(currentTitle);
		copy.addComment(currentDescription);
		if(AUtil.isNumber(targetDays))
		{
			copy.setTargetDays(Integer.parseInt(targetDays));
		}
		model.editProcess(lastID,copy); 
	}

	@Override
	public void init(LinkedList<String> params)
	{
		lastID=params.removeFirst();
		currentTitle=params.removeFirst();
		currentDescription=params.removeFirst();
		targetDays = params.removeFirst();
		//lastID,currentTitle,currentDescription,targetDays
	}

}
