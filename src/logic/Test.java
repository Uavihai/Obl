package logic;

import clientUIControllers.ConnectToServer;
import clientUIControllers.Login;
import clientUIControllers.SearchABook;
import javafx.application.Application;
import javafx.stage.Stage;

public class Test extends Application {

	ConnectToServer aFrame;
	public static void main(String args[]) throws Exception {
		launch(args);
	} // end main

	@Override
	public void start(Stage arg0) throws Exception {

		/*
		 * Login aFrame = new Login(); // create StudentFrame
		 * 
		 * aFrame.start(arg0);
		 */
		aFrame = new ConnectToServer(); // create StudentFrame
		  
		aFrame.start(arg0);

	}
	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		super.stop();
		
		try {
			aFrame.closeConnection();
		} catch (Exception e) {
		}
	}
}
