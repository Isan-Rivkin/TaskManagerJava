package controller.commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import model.process.IProcess;
import model.process.IProcessManager;
import model.process.Process;

public class CmdNewProcessInput extends CommonCommand 
{

	int targetDay,targetMonth,targetYear;
	int submitDay,submitMonth,submitYear;
	String targetDate;
	String submitDate;
	LocalDate localDate;
	String title;
	String desc, startProcess,targetDays;
	IProcessManager model;
	boolean startP;
	public CmdNewProcessInput(IProcessManager model) 
	{
		startP=false;
		this.model=model;
	}
	@Override
	public void execute() 
	{
		IProcess process=new Process();
		process.setName(title);
		process.addComment(desc);
		int days=0;
		try 
		{
			 days = Integer.parseInt(targetDays);
			 process.setTargetDays(Integer.parseInt(targetDays));
		}
		catch(NumberFormatException e)
		{
			System.out.println("[CmdNewProcessInput]:Invalid Target days number.");
		}
		finally
		{
			process.addSubmitionDate(LocalDate.now());
			if(startP)
			{
				process.start();
			}
			model.addProcess(process);	
		}
	}

	@Override
	public void init(LinkedList<String> params) 
	{
		title=params.removeFirst();
		desc=params.removeFirst();
		targetDays=params.removeFirst();
		startProcess=params.removeFirst();

		if(startProcess.equals("true"))
		{
			startP=true;
		}
		else
		{
			startP=false;
		}
	}
	private void currentDateInit()
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		localDate = LocalDate.now();
		this.submitYear=localDate.getYear();
		this.submitMonth=localDate.getMonthValue();
		this.submitDay=localDate.getDayOfMonth();
		this.submitDate=dtf.format(localDate);
	//	System.out.println(dtf.format(localDate)); //2016/11/16	
	}

}
