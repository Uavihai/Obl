package OBLControllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import server.OBLServer;

public class OBLServerGUIController {
	
	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;
	@FXML // fx:id="txtArea"
	private TextArea txtArea; // Value injected by FXMLLoader

	private static OBLServerGUIController uiControllerInstance;
	
	public void start(Stage primaryStage, OBLServerGUIController oblServerGUIController,String dbName)
			throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("/GUIFXML/OBLServerGUI.fxml").openStream());
		uiControllerInstance = loader.getController();
		Scene scene = new Scene(root);
		
		// scene.getStylesheets().add(getClass().getResource("/GUIFXML/OBLServerGUI.css").toExternalForm());
		// //keep this in case we need to use css file
		primaryStage.setTitle("Ort Braude Library Server");
		primaryStage.setScene(scene);

		primaryStage.show();
		OBLServer sv = new OBLServer(DEFAULT_PORT,dbName);

		try {
			sv.listen(); // Start listening for connections
		} catch (Exception ex) {
			OBLServerGUIController.getUiControllerInstance().addLineToTextArea("ERROR - Could not listen for clients!" + "\n" + ex);
		}
	}

	public void addLineToTextArea(String str) 
	{
		Platform.runLater(() -> {
			// Update UI here.
			this.txtArea.appendText(">"+str+"\n");
		});
	}

	/**
	 * Method returns instance of this class
	 * @return the uiControllerInstance
	 */
	public static OBLServerGUIController getUiControllerInstance() {
		return uiControllerInstance;
	}

}