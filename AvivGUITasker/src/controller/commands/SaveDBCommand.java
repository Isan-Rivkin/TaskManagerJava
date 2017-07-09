package controller.commands;

import java.util.LinkedList;

import model.process.IProcessManager;

public class SaveDBCommand extends CommonCommand {
	private IProcessManager model;
	private String quePath,CurPath,finPath;
	public SaveDBCommand(IProcessManager model) {
		this.model=model;
	}

	@Override
	public void execute() {
		if(CurPath.length() > 0 && finPath.length() >0 && quePath.length()>0){
			model.saveData(CurPath, quePath, finPath);
		}
		
	}

	@Override
	public void init(LinkedList<String> params) {
		CurPath=params.removeFirst();
		quePath=params.removeFirst();
		finPath=params.removeFirst();

	}

}
