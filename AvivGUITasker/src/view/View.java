 package view;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.process.IProcess;
import model.process.Process;
import model.process.sorting.BasicSorter;
import model.process.sorting.ComparatorFactory;
import model.process.sorting.ISorter;
import utils.AUtil;
import model.process.sorting.ComparatorGenerator.Sort_Type;


public class View extends Observable implements IView, Initializable 
{
	public enum process_type
	{
		FINISHED,QUEUED,CURRENT,SEARCHED,SORTED;
	}
	final String current_path="taskConfig/current.xml";
	final String queued_path="taskConfig/queded.xml";
	final String finished_path="taskConfig/finished.xml";
	String alertSaveCallbackAnswer;
	private static process_type listInFocus;
	private Timer timer;
	private static long currentTime;
	private Stage stage;
	private Collection<IProcess> process_finished, process_que, process_current;
	private LinkedList<String> new_process;
	private String processIDInFocus;
	private boolean dataLoaded, selectedFromFocus;
	@FXML
	private GItem gItem;
	@FXML
	private Button testButton;
	@FXML
	private TextField editTitleTxt;
	@FXML
	private TextArea editDescTXT;
	@FXML
	private Button submit;
	@FXML
	private CheckBox checkBoxStart;
	@FXML
	private Label pCommentLabel;
	@FXML
	private Label pTitleLabel;
	@FXML
	private Label pDescLabel;
	@FXML
	private Label pDurationLabel;
	@FXML
	private ProgressBar pProgress;
	@FXML 
	private TableView<IProcess> tableProcess;
	@FXML
	private DatePicker pDateInsert;
	@FXML
	private Button updateProcessButton;
	@FXML
	private String lastEditedPID;
	@FXML 
	private ListView<String> pListLabel;
	@FXML
	private TextField editTargetTxt;
	@FXML
	private Label progressPrecentLabel;
	@FXML
	private MenuButton sortingMenu;
	@FXML
	private TextField searchField;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		selectedFromFocus=false;
		dataLoaded=false;
		pProgress.setVisible(false);
		timer = new Timer();
		process_current = null;
		updateProcessButton.setOnAction((h)->onUpdateProcessButton());
		
		tableProcess.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent arg0) 
			{
				IProcess p=tableProcess.getSelectionModel().getSelectedItem();
				int index = tableProcess.getSelectionModel().getSelectedIndex();
				if(p == null)
					return;
				if(process_current.contains(p))
				{
					buildProcessPresentation(p.getProcessID(), process_current);
					//clearProcessFieldsInput(process_type.CURRENT);
					clearProcessFieldsInputNoRefresh();


				}
				else if(process_que.contains(p))
				{
					buildProcessPresentation(p.getProcessID(), process_que);
					//clearProcessFieldsInput(process_type.QUEUED);
					clearProcessFieldsInputNoRefresh();
				}
				else if(process_finished.contains(p))
				{
					buildProcessPresentation(p.getProcessID(), process_finished);
					//clearProcessFieldsInput(process_type.FINISHED);
					clearProcessFieldsInputNoRefresh();
				}
				tableProcess.getSelectionModel().select(index);
				tableProcess.getFocusModel().focus(index);
			}
			
		});	
			initializeSortingMenu();
	}
	// delete later
	// test do all sorting here on client side
	public void testMethodSorting(Collection<IProcess> list, Sort_Type sortType)
	{
		ComparatorFactory comparator_factory = new ComparatorFactory();
		Comparator<IProcess> comparator  = comparator_factory.generate(sortType);
		ISorter sorter = new BasicSorter(comparator);
		list = sorter.sort(list);
		updateMainList(list);
			
	}
	public void initializeSortingMenu()
	{
		sortingMenu.setOnMouseClicked((e)->
		{
			List<MenuItem> items = sortingMenu.getItems();
			for(int i=0; i<items.size(); ++i)
			{
				MenuItem item = items.get(i);
				item.setOnAction(a->
				{
					String listType = interperateListInFocus();
					if(listType != null)
					{
						String interpreted = interperateSortSelection(item.getText());
						updateObservers("sort",listType,interpreted);
					}
				});
			}
		});
	}
	private String interperateListInFocus() 
	{
		String list="";
		switch(listInFocus)
		{
		case CURRENT:
			list="current";
			break;
		case QUEUED:
			list="queue";
			break;
		case FINISHED:
			list = "finished";
			break;
		default:
			break;
		}
		
		if(list.equals(""))
			return null;
		return list;
	}
	private String interperateSortSelection(String sortType) 
	{
	
		String sort="";
		if(sortType.equals("First To Last"))
		{
			sort="bynewest";
		}
		else if(sortType.equals("Last To First"))
		{
			sort="byoldest";
		}
		else if(sortType.equals("Closest Dead Line"))
		{
			sort = "byfirstdeadline";
		}
		else if(sortType.equals("Most time Dead Line"))
		{
			sort ="bylastdeadline";
		}
		else if(sortType.equals("TO DO BY TITLE IN GUI"))
		{
			sort ="bytitle";
		}
		if(sort.equals(""))
			return null;
		return sort;
	}
	public void setGIteam(GItem gIteam) 
	{
		this.gItem = gIteam;
	}

	public void openningState()
	{
		String cmd="load";
		updateObservers(cmd,current_path, queued_path, finished_path);
	}


	public void buildProcessPresentation(String id, Collection<IProcess> list) 
	{
		if(list == null || list.size() <1)
			{
			 	return;
			}
		progressPrecentLabel.setText("");
		pProgress.setVisible(true);
		pProgress.setProgress(0);
		IProcess process = null;
		for (IProcess p : list) 
		{
			if (p.getProcessID().equals(id)) 
			{	
				pTitleLabel.setText(p.getProcessName());
				processIDInFocus=p.getProcessID();
				String comments = "";	
				if(p.getComments() != null)
				{

					ObservableList<String> items= FXCollections.observableArrayList();
					items.setAll(p.getComments());
					pListLabel.setItems(items);
				}
				pDurationLabel.setText("" + calculateTimeSinceStart(p));
				long daysLeft = p.getDaysLeftToTarget();
				if(daysLeft >=0)
				{
					long totalTargetDays = p.getTargetDays();
					if(totalTargetDays >0)
					{
						long daysUntillNow = totalTargetDays - p.getDaysLeftToTarget();
						double progress = ((double)daysUntillNow / (double)totalTargetDays);
						pProgress.setProgress(progress);
						String formattedString = String.format("%.02f", progress);
						progressPrecentLabel.setText(formattedString+ "%");
					}
				}
			}
		}

	}
	@Override
	public void setStage(Stage stage)
	{
		this.stage = stage;
		this.stage.setOnCloseRequest(e->
		{
			e.consume();
			handleExitSave();
		});

	}

	public void handleExitSave() 
	{
		AlertCallback callback = new AlertCallback();
		displayConfirmationgAlertMessage("Data Save","Save Work ?", callback);
		
		if(callback.getAnswer().equals("ok"))
		{
			onSaveButton();
			try 
			{
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{

				e.printStackTrace();
			}
			onExit();
		}
		else if(callback.getAnswer().equals("no"))
		{
			onExit();
		}
		else
		{
			return;
		}
	}
	@Override
	public void displayAlertMessage(String header,String msg)
	{
		Platform.runLater(()->
		{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(header);
		alert.setContentText(msg);
		alert.showAndWait();
		});

	}
	@Override
	public void displayInputAlertMessage(String header, String msg)
	{
		Platform.runLater(()->
		{
		TextInputDialog dialog = new TextInputDialog("text");
		dialog.setTitle("Text Input Dialog");
		dialog.setHeaderText(header);
		dialog.setContentText(msg);
		});
//		// Traditional way to get the response value.
//		Optional<String> result = dialog.showAndWait();
//		if (result.isPresent()){
//		    System.out.println("Your name: " + result.get());
//		}
//
//		// The Java 8 way to get the response value (with lambda expression).
//		result.ifPresent(name -> System.out.println("Your name: " + name));
//		
	}

	@Override
	public void displayErrorAlertMessage(String header, String msg) 
	{
		Platform.runLater(()->
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(header);
			alert.setContentText(msg);
			alert.showAndWait();
		});
		
	}
	@Override
	public void displayWarnningAlertMessage(String header, String msg) 
	{
		Platform.runLater(()->
		{
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText(header);
		alert.setContentText(msg);
		alert.showAndWait();
		});
		
	}
	@Override
	public IProcess displayConfirmationgAlertMessage(String header, String msg, CallBack alertCallBack) 
	{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Please Confirm");
			alert.setHeaderText(header);
			alert.setContentText(msg);
			ButtonType ok = new ButtonType("Ok",ButtonData.OK_DONE);
			ButtonType no = new ButtonType("No",ButtonData.NO);
			ButtonType cancel = new ButtonType("Cancel",ButtonData.CANCEL_CLOSE);
			alert.getButtonTypes().removeAll();
			alert.getButtonTypes().setAll(cancel,no,ok);
			alert.initOwner(stage);
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get().getButtonData() == ButtonData.OK_DONE )
			{
			    alertCallBack.execute("ok");
			} 
			else if (result.get().getButtonData() == ButtonData.NO)
			{
				alertCallBack.execute("no");
			}
			else 
			{
				alertCallBack.execute("cancel");
			}
			IProcess p = new Process();
			return p;
			
		
	}
	public String calculateTimeSinceStart(IProcess process)
	{
		return AUtil.getTotalTimeDaysHoursMinSec(process.getStartTimeMillis(), System.currentTimeMillis());
	}

	public void loadData()
	{
		dataLoaded=true;
		openningState();
	}

	public void onSaveButton()
	{
		
		if(dataLoaded)
			updateObservers("save", current_path, queued_path, finished_path);
		else
			displayErrorAlertMessage("Save Error","No data to save Amigo");
	}

	public void onFinishedProcess()
	{
		updateObservers("display");
		updateMainTable(process_finished);
		listInFocus = process_type.FINISHED;
	}

	public void onQueProcess() 
	{
		updateObservers("display");
		updateMainTable(process_que);
		listInFocus = process_type.QUEUED;
	}

	public void onCurrentProcess() 
	{
		updateObservers("display");
		updateMainTable(process_current);
		listInFocus = process_type.CURRENT;
	}

	private void updateMainList(Collection<IProcess> pros) 
	{
		ObservableList<String> items = FXCollections.observableArrayList();
		for (IProcess p : pros) 
		{
			if (p.isFinishedProcess()) 
			{
				items.add(p.getProcessName() + " finished "
						+ AUtil.getTotalTimeDaysHoursMinSec(p.getStartTimeMillis(), p.getFinishTimeMillis()));
			} else {
				items.add(p.getProcessName() + " Since: "
						+ AUtil.getTotalTimeDaysHoursMinSec(p.getStartTimeMillis(), System.currentTimeMillis()));
			}
		}
	}

	@Override
	public void updateLists(HashMap<String, IProcess> que, HashMap<String, IProcess> current,
			HashMap<String, IProcess> finished) 
	{
		Platform.runLater(new Runnable() 
		{
			@Override
			public void run() 
			{
				process_current = current.values();
				process_finished = finished.values();
				process_que = que.values();
			}
		});

	}

	private void updateObservers(String... strings) 
	{
		List<String> params = new LinkedList<String>();
		for (String s : strings)
		{
			params.add(s);
		}
		setChanged();
		notifyObservers(params);
	}

	public void onSubmitProcess() 
	{
		if(pDateInsert.getValue() != null)
		{
			LocalDate localDate = pDateInsert.getValue();
			System.out.println("LדLSLSLSLSS   "+localDate.toString());
			System.out.println("DAY: : : :"+ localDate.getDayOfMonth());
			System.out.println("MONTH : : : : "  + localDate.getMonthValue());
			System.out.println("YEAR:: : : " +localDate.getYear());
		}
		// make sure process name is unique 
		if(findProcessInLists(AUtil.encryptProcessTitleToID(editTitleTxt.textProperty().get())) != null)
		{
			displayErrorAlertMessage("Name error","Process Name already Taken");
			return;
		}
		boolean startProcess = checkBoxStart.isSelected();
		updateObservers("add", editTitleTxt.textProperty().get(), editDescTXT.textProperty().get(), 
				editTargetTxt.textProperty().get(),startProcess + "");
		
		clearProcessFieldsInput(getTypeInFocus());
	}

	public void updateMainTable(Collection<IProcess> pros)
	{
		if(pros==null)
		{
			return;
		}
		ObservableList<IProcess> data=FXCollections.observableArrayList();
		for (IProcess p : pros) 
		{
			data.add(p);
		}
		tableProcess.setEditable(true);
		TableColumn<IProcess,String> col_pTitle=new TableColumn<>("שם התהליך");
		TableColumn<IProcess,String> col_pComments=new TableColumn<>("הערות");
		TableColumn<IProcess,String> col_pTimeSinceStart=new TableColumn<>("זמן מההתחלה");
		TableColumn<IProcess,String> col_daysLeft = new TableColumn<>("ימים ליעד");
		col_pTitle.setCellValueFactory(new PropertyValueFactory<IProcess,String>("tableTitle"));
		col_pComments.setCellValueFactory(new PropertyValueFactory<IProcess,String>("tableComments"));
		col_pTimeSinceStart.setCellValueFactory(new PropertyValueFactory<IProcess,String>("tableTimeSinceStart"));
		col_daysLeft.setCellValueFactory(new PropertyValueFactory<IProcess,String>("tableDaysLeft"));
		tableProcess.getColumns().remove(0, tableProcess.getColumns().size());
		tableProcess.setStyle("-fx-alignment: CENTER-RIGHT;");
		// serious bug created
	//	tableProcess.setFixedCellSize(60.0);
	
		// colors for rows incase dead line late etc.
		initTableFactory(col_daysLeft);
		initTableFactory(col_pComments);
		initTableFactory(col_pTimeSinceStart);
		initTableFactory(col_pTitle);
		/* make sure the columns are apread even*/
		//nameCol.prefWidthProperty().bind(personTable.widthProperty().divide(4)); // w * 1/4
		col_pTitle.prefWidthProperty().bind(tableProcess.widthProperty().divide(4));
		col_pComments.prefWidthProperty().bind(tableProcess.widthProperty().divide(3));
		col_pTimeSinceStart.prefWidthProperty().bind(tableProcess.widthProperty().divide(5));
		col_daysLeft.prefWidthProperty().bind(tableProcess.widthProperty().divide(5));
		tableProcess.getColumns().addAll(col_pComments,col_daysLeft,col_pTimeSinceStart,col_pTitle);
		tableProcess.getColumns().get(0).setStyle("-fx-alignment: CENTER-RIGHT;");
		tableProcess.getItems().removeAll();
		tableProcess.setItems(data);
	}
	public void onStartProcessButton()
	{
		updateObservers("startprocess",processIDInFocus);
	}
	public void onFreezeProcessButton()
	{
		updateObservers("freezeprocess",processIDInFocus);
	}
	public void onFinishedProcessButton()
	{
		updateObservers("finishprocess",processIDInFocus);
	}
	
	public void onEditProcessButton()
	{
		selectedFromFocus = true;
		Collection<IProcess> list =findProcessInLists(processIDInFocus);
		if(list == null )
		{
			return;
		}
		IProcess p=null;
		for(IProcess pro: list)
		{
			if(pro.getProcessID().equals(processIDInFocus))
			{
				p=pro;
				break;
			}
		}
		if(p == null)
			return;
		editTitleTxt.setText(p.getProcessName());
		String c="";
		for(String comment : p.getComments())
			c+=comment;
		editDescTXT.setText(c);
		lastEditedPID=p.getProcessID();
	}
	
	public Collection<IProcess> findProcessInLists(String processID)
	{
		for(IProcess p : process_current)
		{
			if(p.getProcessID().equals(processID))
			{
				return process_current;
			}
		}
		for(IProcess p : process_finished)
		{
			if(p.getProcessID().equals(processID))
			{
				return process_finished;
			}
		}
		for(IProcess p : process_que)
		{
			if(p.getProcessID().equals(processID))
			{
				return process_que;
			}
		}
		return null;
	}
	
	public void onUpdateProcessButton()
	{
			Collection<IProcess> list=findProcessInLists(lastEditedPID);
			//check if it is an ACTUAL EXISTING PROCESS
			if(list == null)
			{
				return;
			}
			for(IProcess p: list)
			{
				if(p.getProcessID().equals(lastEditedPID))
				{  
					//lastID,currentTitle,currentDescription,targetDays
					
					updateObservers("editprocess",lastEditedPID, editTitleTxt.textProperty().get(),editDescTXT.textProperty().get(),
							editTargetTxt.textProperty().get());
					break;
				}
			}
			clearProcessFieldsInput(process_type.CURRENT);
	}
	
	private void clearProcessFieldsInput(process_type type)
	{
		editTitleTxt.setText("");
		editDescTXT.setText("");
		checkBoxStart.setSelected(false);
		editTargetTxt.setText("");
		if(type == null)
			onCurrentProcess();
		if(type == process_type.CURRENT)
			onCurrentProcess();
		else if(type == process_type.QUEUED)
			onQueProcess();
		else if(type == process_type.FINISHED)
			onFinishedProcess();	
	}
	private void clearProcessFieldsInputNoRefresh()
	{
		editTitleTxt.setText("");
		editDescTXT.setText("");
		checkBoxStart.setSelected(false);
		editTargetTxt.setText("");	
	}
	process_type getTypeInFocus()
	{
		Collection<IProcess> l =findProcessInLists(processIDInFocus);
		if(l == null)
			return null;
		if(l.equals(process_current))
		{
			return process_type.CURRENT;
		}
		else if(l.equals(process_finished))
		{
			return process_type.FINISHED;
		}
		else if(l.equals(process_que))
		{
			return process_type.QUEUED;
		}
		return null;
	}
	
	void initTableFactory(TableColumn<IProcess, String> col)
	{
		col.setCellFactory(column -> 
		{
		    return new TableCell<IProcess, String>()
		    {
		        @Override
		        protected void updateItem(String item, boolean empty) 
		        {
		            super.updateItem(item, empty); //This is mandatory

		            if (item == null || empty) { //If the cell is empty
		                setText(null);
		                setStyle("");
		            }
		            else
		            { //If the cell is not empty
		                setText(item); //Put the String data in the cell
		                //We get here all the info of the Person of this row
		                IProcess processCell = getTableView().getItems().get(getIndex());

		                if (processCell.isPastTarget())
		                {
		                    setTextFill(Color.WHITE); //The text in red
		                    setStyle("-fx-background-color: rgba(255, 80, 80,0.5)"); //The background of the cell in yellow
		                }
		                else 
		                {
		                    //Here I see if the row of this cell is selected or not
		                    if(getTableView().getSelectionModel().getSelectedItems().contains(processCell))
		                    {
		                    	setTextFill(Color.WHITE);
		                    }
		                       
		                    else
		                    {
		                    	setTextFill(Color.WHITE);
		                    }
		                        
		                }
		            }
		        }
		    };
		});
	}
	@Override
	public void updateSortedList(List<IProcess> sortedList) 
	{
		Platform.runLater(()->
		{
			updateMainTable(sortedList);
			listInFocus = process_type.SORTED;
		});
	}

	public void onSaveOS()
	{
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Level");
        
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Xml: ","*.xml"));
		fileChooser.setInitialDirectory(new File("./taskConfig/user_generated"));
		fileChooser.setTitle("For Current Process Data");
        File file1 = fileChooser.showSaveDialog(stage);
		fileChooser.setTitle("For Queued Process Data");
        File file2 = fileChooser.showSaveDialog(stage);
		fileChooser.setTitle("For Finished Process Data");
        File file3 = fileChooser.showSaveDialog(stage);
        if (file1 != null && file2!=null && file3!= null) 
        {
        	updateObservers("save", file1.getPath(), file2.getPath(), file3.getPath());
        }
        
	}
	public void onLoadOS()
	{
		 FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Level");
        
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Xml: ","*.xml"));
		fileChooser.setInitialDirectory(new File("./taskConfig/user_generated"));
		
		fileChooser.setTitle("For Current Process Data");
		File file1 = fileChooser.showOpenDialog(stage);
		fileChooser.setTitle("For Queued Process Data");
        File file2 = fileChooser.showOpenDialog(stage);
		fileChooser.setTitle("For Finished Process Data");
         File file3 = fileChooser.showOpenDialog(stage);

        if (file1 != null && file2!=null && file3!= null) 
        {
        	updateObservers("load", file1.getPath(), file2.getPath(), file3.getPath());
        }
        
	}
	public void onExit()
	{
		Platform.exit();
		System.exit(0);
	}
	public void onDeleteProcess()
	{
		updateObservers("delete",processIDInFocus);
	}
	@Override
	public void updateSearchedList(Collection<IProcess> searchedList) 
	{	Platform.runLater(()->
	{
		updateMainTable(searchedList);
		listInFocus = process_type.SEARCHED;
	});
	}	
	public void onSearchButton()
	{
		String query = searchField.textProperty().get();
		if(query.equals(""))
			return;
		updateObservers("search",query);
	}

	

}
