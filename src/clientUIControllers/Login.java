package clientUIControllers;

import java.io.IOException;
import java.util.Timer;
import java.util.regex.Pattern;

import DB_Communicator.Authenticate;
import DB_Communicator.Communicate;
import Entities.Subscriber;
import ServerClientRequests.LoginRequest;
import client.ClientConsole;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login {
	@FXML
	private Button ChackDetailsUser;

	@FXML
	private PasswordField  EnterPassword;

	@FXML
	private TextField EnterUserName;

	@FXML
	private Button connectBtn;

	@FXML
    private Button cancelBtn;
	
	@FXML
	private Label serverMsg;

	@FXML
	private TextField ServerIPtxt;

	private ActionEvent callingEvent;

	private static Login instance;
	
	

	private static final Pattern PATTERN_IP = Pattern
			.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

	public void next(ActionEvent event) throws Exception {
		if (!EnterUserName.getText().isEmpty() && !EnterPassword.getText().isEmpty()) {
			callingEvent = event;
				Communicate.getInstance().authenticate(new LoginRequest(EnterUserName.getText(), EnterPassword.getText()));
			
		}
	}

	public void openAppropriateWindow(LoginRequest loginRequest) throws Exception {
		if (loginRequest.isSuccessful()) {
			Authenticate.getInstance().setLoggedInUser(loginRequest.getUser());
			Authenticate.getInstance().setLoggedInAs(loginRequest.getLoginAs());
			reOpenAppropritateWindowForUser();
			/*Platform.runLater(() -> {
				// Update UI here.
				SearchABook.getInstance().getLoginBtn().setText("Logged In As Librarian");
				});*/
			
		}
	}
	public static void reOpenAppropritateWindowForUser()
	{
		Subscriber loggedInUser=Authenticate.getInstance().getLoggedInUser();
		switch (Authenticate.getInstance().getLoggedInAs()) {
		case LIBRARIAN:
			Platform.runLater(() -> {
				// Update UI here.
				try {
					Utils.OpenFrame("/gui/LibrarianOptions.fxml", "Librarian", Login.getInstance(), Login.getInstance().callingEvent, true);
					SearchABook.getInstance().getLoginBtn().setText("Logged in as Librarian");
					LibrarianOptions.setSourceFrame(SearchABook.getInstance());
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
				});
			
			break;
		case SUBSCRIBER:
			Platform.runLater(() -> {
				// Update UI here.
				try {
					SubscriberDetails.addNewSubscriber=false;
					SubscriberDetails.selectedSubscriber=loggedInUser;
					Utils.OpenFrame("/gui/SubscriberDetails.fxml", "Subscriber", Login.getInstance(), Login.getInstance().callingEvent, true);
					SearchABook.getInstance().getLoginBtn().setText("Logged in as Subscriber");
					SubscriberDetails.setSourceFrame(SearchABook.getInstance());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				});
			break;
		case MANAGER:
			Platform.runLater(() -> {
				// Update UI here.
				try {
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
				});
			break;
		}
	}
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/gui/Login.fxml"));

		Scene scene = new Scene(root);
		primaryStage.setTitle("ORT-Braude Library");
		primaryStage.setScene(scene);

		primaryStage.show();
	}

	@FXML
	public void CoonectToServer(ActionEvent event) {
		String ip = ServerIPtxt.getText();
		ClientConsole clientConsole;
		if (!validate(ip))
			serverMsg.setText("Not An IP Address!");
		else {
			try {
				clientConsole = ClientConsole.startClientConnection(ip);
				// connection successful
				serverMsg.setText("Connection Successful!");
				Communicate.setClientConsole(clientConsole);
			} catch (IOException e) {
				// unable to connect
				serverMsg.setText("Unable to connect. Check your IP address!");
			}

		}
	}

    @FXML
    public void cancel(ActionEvent event) {
    	Utils.closeCurrentFrame(event);
    }
	private boolean validate(final String ip) {
		return PATTERN_IP.matcher(ip).matches() || ip.equals("localhost");
	}

	/**
	 * @return the instance
	 */
	public static Login getInstance() {
		return instance;
	}

	/**
	 * @param instance the instance to set
	 */
	public static void setInstance(Login instance) {
		Login.instance = instance;
	}

}
