package controller.commands;

import java.util.LinkedList;

import model.process.IProcessManager;
import view.IView;

public class LoadDBCommand extends CommonCommand {

	private IView view;
	private IProcessManager model;
	private String currentProcessPath,inQueueProcessPath,finishedProcessPath;

	public LoadDBCommand(IView view, IProcessManager model) {
		this.model=model;
		this.view=view;
	}
	@Override
	public void execute() {
		if(model == null)
			return;
		Thread t = new Thread(()->model.loadData(currentProcessPath, inQueueProcessPath, finishedProcessPath));
		t.start();
	}

	@Override
	public void init(LinkedList<String> params) 
	{
		this.currentProcessPath=params.removeFirst();
		this.inQueueProcessPath=params.removeFirst();
		this.finishedProcessPath=params.removeFirst();
	}

}
