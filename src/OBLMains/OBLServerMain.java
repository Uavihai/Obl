package OBLMains;

import OBLControllers.OBLServerGUIController;
import javafx.application.Application;
import javafx.stage.Stage;
import server.OBLServer;

public class OBLServerMain extends Application {
	private static String dbName; 
	
	/**
	 * This method is responsible for the creation of the server instance (there is
	 * no UI in this phase).
	 *
	 * @param args[0] The port number to listen on. Defaults to 5555 if no argument
	 *        is entered.
	 */
	public static void main(String[] args) {

		try {
			dbName = args[0]; // Get database name from command line
		} catch (Throwable t) {
			dbName = "librarydatabase"; // Set database name to librarydatabase
		}
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		OBLServerGUIController oblServerGUIController = new OBLServerGUIController();
		try {
			oblServerGUIController.start(primaryStage, oblServerGUIController,dbName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
