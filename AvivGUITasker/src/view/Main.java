package view;
	
import java.util.Calendar;
import java.util.Date;

import controller.IController;
import controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.process.CommonProcessManager;
import utils.AUtil;


public class Main extends Application 
{
	@Override
	public void start(Stage primaryStage) 
	{
		try {
			FXMLLoader fxml=new FXMLLoader();
			AnchorPane root = fxml.load(getClass().getResource("view.fxml").openStream());
			//second loader
			FXMLLoader loader=new FXMLLoader();
			//loader.setLocation(GItem.class.getResource("xmliteam.fxml"));
//			loader.load(GItem.class.getResource("xmliteam.fxml"));
//			GItem gItem =loader.getController(); 
			// ********************************
			// init view
			View gui=fxml.getController();
			//gui.setGIteam(gItem);
		    gui.setStage(primaryStage);
		    //init model
		    CommonProcessManager model= new CommonProcessManager();
		    //init controller
		    IController controller= new MainController(gui,model);
		    //init observables
		    gui.addObserver(controller);
		    model.addObserver(controller);
		    //start 
		   controller.start();
		    System.out.println("Im alive !");
		    //model.exit();
 
			//********************************
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
