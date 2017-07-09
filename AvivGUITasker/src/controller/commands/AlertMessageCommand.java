package controller.commands;

import java.util.LinkedList;

import view.IView;

public class AlertMessageCommand extends CommonCommand
{
	private String msg,header,type;	
	private final String alert="alert";
	private final String error="error";
	private final String warnning = "warnning";
	private final String input ="input";
	private IView view;
	public AlertMessageCommand(IView view) 
	{
		this.view=view;
	}
	@Override
	public void execute() 
	{
		switch(type)
		{
		case alert:
			view.displayAlertMessage(header, msg);
			break;
		case error:
			view.displayErrorAlertMessage(header, msg);
			break;
		case warnning:
			view.displayWarnningAlertMessage(header, msg);
			break;
		case input:
			view.displayInputAlertMessage(header, msg);
			break;
		default:
			break;
		}
		
	}

	@Override
	public void init(LinkedList<String> params)
	{
		type = params.removeFirst();
		header = params.removeFirst();
		msg = params.remove();
	}

}
