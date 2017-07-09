package controller.commands;

import java.util.HashMap;
import java.util.LinkedList;

import model.process.IProcess;
import model.process.IProcessManager;
import view.IView;

public class CmdUpdateProcessList extends CommonCommand{
	IProcessManager model;
	IView view;
	HashMap<String, IProcess> current,queue,finished;
	
	public CmdUpdateProcessList(IProcessManager model, IView view) {
		this.model=model;
		this.view=view;
	}

	@Override
	public void execute() {
		current=model.getCurrentProcess();
		queue=model.getQueuedProcess();
		finished=model.getFinishedtProcess();
		view.updateLists(queue, current, finished);
	}

	@Override
	public void init(LinkedList<String> params) {
	}
	
}
