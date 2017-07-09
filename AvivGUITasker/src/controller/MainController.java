package controller;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import controller.commands.AlertMessageCommand;
import controller.commands.CmdNewProcessInput;
import controller.commands.CmdUpdateProcessList;
import controller.commands.DeleteProcessCmd;
import controller.commands.DisplaySearchCmd;
import controller.commands.DisplaySortedListCmd;
import controller.commands.EditAProcessCmd;
import controller.commands.FinishProcessCommand;
import controller.commands.FreezeProcessCmd;
import controller.commands.ICommand;
import controller.commands.LoadDBCommand;
import controller.commands.SaveDBCommand;
import controller.commands.SearchInProcessCmd;
import controller.commands.SortCommand;
import controller.commands.StartProcessCmd;
import model.process.IProcessManager;
import view.View;

public class MainController implements IController 
{
	
	private HashMap<String, ICommand> factory_cmd;
	private GeneralController controller;
	private View view;
	private IProcessManager model;
	
	
	public MainController(View view, IProcessManager model)
	{
		controller=new GeneralController();	
		this.view=view;
		this.model=model;
		initFactoryCmd();
	}
	private void initFactoryCmd()
	{
		factory_cmd=new HashMap<>();
		factory_cmd.put("display", new CmdUpdateProcessList(model,view));
		factory_cmd.put("load", new LoadDBCommand(view, model));
		factory_cmd.put("add",new CmdNewProcessInput(model));
		factory_cmd.put("save", new SaveDBCommand(model));
		factory_cmd.put("startprocess", new StartProcessCmd(model, view));
		factory_cmd.put("freezeprocess", new FreezeProcessCmd(model));
		factory_cmd.put("finishprocess", new FinishProcessCommand(model));
		factory_cmd.put("editprocess", new EditAProcessCmd(model));
		factory_cmd.put("sort", new SortCommand(model));
		factory_cmd.put("displaysorted", new DisplaySortedListCmd(model, view));
		factory_cmd.put("delete", new DeleteProcessCmd(model));
		factory_cmd.put("msg", new AlertMessageCommand(view));
		factory_cmd.put("displaysearched", new DisplaySearchCmd(model, view));
		factory_cmd.put("search", new SearchInProcessCmd(model));
	}
	@Override
	public void update(Observable arg0, Object arg1) 
	{
		LinkedList<String> params=(LinkedList<String>)arg1;
		String cmd = params.removeFirst();
		ICommand command=factory_cmd.get(cmd);
		if(command == null)
			return;
		command.init(params);
		controller.insertCommand(command);

	}
	public void start()
	{
		controller.start();
	}
	public void stop()
	{
		controller.stop();
	}
	

}
