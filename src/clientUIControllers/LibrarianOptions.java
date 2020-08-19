package clientUIControllers;

import DB_Communicator.Authenticate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LibrarianOptions {

	@FXML
	private Button addSubscriberBtn;

	@FXML
	private Button searchSubscriberBtn;

	@FXML
	private Button lendABookBtn;

	@FXML
	private Button logOut;

	@FXML 
	private Button returnABookBtn;
	
	private static SearchABook sourceFrame;

	@FXML
	public void addSubscriber(ActionEvent event) throws Exception {
		SubscriberDetails.addNewSubscriber = true;
		Utils.OpenFrame("/gui/SubscriberDetails.fxml", "Subscriber details", this, event, false);
	}

	@FXML
	public void searchSubscriber(ActionEvent event) throws Exception {
		SubscriberDetails.addNewSubscriber = false;
		Utils.OpenFrame("/gui/SubscriberDetails.fxml", "Subscriber details", this, event, false);
	}

	@FXML
	public void lendABook(ActionEvent event) throws Exception {
		Utils.OpenFrame("/gui/LendABook.fxml", "Lend a book", this, event, false);
	}

	@FXML
	void Logout(ActionEvent event) {
		
		Authenticate.getInstance().Logout(sourceFrame);
		Utils.closeCurrentFrame(event);
	}
	@FXML 
	public void returnABook(ActionEvent event) throws Exception {
		Utils.OpenFrame("/gui/ReturnABook.fxml", "Return a book", this, event, false);
	}
	
	/**
	 * @return the sourceFrame
	 */
	public static SearchABook getSourceFrame() {
		return sourceFrame;
	}

	/**
	 * @param sourceFrame the sourceFrame to set
	 */
	public static void setSourceFrame(SearchABook sourceFrame) {
		LibrarianOptions.sourceFrame = sourceFrame;
	}

	
	
}
