package clientUIControllers;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import DB_Communicator.Communicate;
import client.ClientConsole;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ConnectToServer implements Initializable{

    @FXML
    private Button connectBtn;

    @FXML
    private TextField ServerIPtxt;

    @FXML
    private Label serverMsg;

    private ClientConsole clientConsole;
    private static final Pattern PATTERN_IP = Pattern
			.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    @FXML
    public void CoonectToServer(ActionEvent event) {
    	String ip = ServerIPtxt.getText();
    	connect(event,ip);
    }
    private void connect(Event event,String ip)
    {
		if (!validate(ip))
			serverMsg.setText("Not An IP Address!");
		else {
			try {
				clientConsole = ClientConsole.startClientConnection(ip);
				// connection successful
				Communicate.setClientConsole(clientConsole);
				SearchABook sab=(SearchABook)Utils.OpenFrame("/gui/SearchABook.fxml", "Search a book", this, event, true);
				SearchABook.setInstance(sab);
				sab.getChooseABookBtn().setVisible(false);
			} catch (IOException e) {
				// unable to connect
				serverMsg.setText("Unable to connect. Check your IP address!");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }

    @FXML
    public void connectWithKeyRelease(KeyEvent event) 
    {
    	if (event.getCode().toString().equals("ENTER")) {
    		if(ServerIPtxt.getText().isEmpty())
    			connect(event,"localhost");
    		else
    			connect(event,ServerIPtxt.getText());
    	}
    }
    
    public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/ConnectToServer.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("ORT-Braude Library");
		primaryStage.setScene(scene);
		
		primaryStage.show();

	}
    /**
	 * close connection with the server
     * @throws Exception 
	 */
	public void closeConnection() throws Exception {
		clientConsole.closeConnection();
	}

    /**
	 * Validate ip address
	 * 
	 * @param ip input from the user
	 * @return boolean indicating if ip is valid or not
	 */
	private boolean validate(final String ip) {
		return PATTERN_IP.matcher(ip).matches() || ip.equals("localhost");
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(() -> {
			ServerIPtxt.requestFocus();
		});
	}
}
