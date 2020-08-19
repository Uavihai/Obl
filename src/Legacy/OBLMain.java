package OBLMains;

import javafx.application.Application;
import javafx.stage.Stage;
import server.OBLServer;

public class OBLMain extends Application{
	
	
	/**
	 * Main class that starts the application
	 */
	private static String[] host;
	public static void main(String[] args) {
		host=args;
		launch(args);
	}

	/**
	 * 
	 */
	@Override
	public void start(Stage primaryStage) {
		//OBLPrototypeGUIController oblPrototypeGUIController=new OBLPrototypeGUIController();
		try {
			//Client ui controller init here
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
