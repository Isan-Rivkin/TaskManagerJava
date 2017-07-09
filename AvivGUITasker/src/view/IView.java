package view;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.process.IProcess;

public interface IView  {
	public void setStage(Stage stage);
	public void displayAlertMessage(String header , String msg);
	public void displayErrorAlertMessage(String header ,String msg);
	public void displayWarnningAlertMessage(String header ,String msg);
	public IProcess displayConfirmationgAlertMessage(String header ,String msg, CallBack callback);
	public void displayInputAlertMessage(String header ,String msg);
	public void updateLists(HashMap<String,IProcess> que,HashMap<String,IProcess> current,HashMap<String,IProcess> finished);
	public void updateSortedList(List<IProcess> sortedList);
	public void updateSearchedList(Collection<IProcess> searchedList);

	
}
