package controller.commands;

import java.util.LinkedList;

public interface ICommand {
	public void execute();
	public void init(LinkedList<String> params);
}
