package clientUIControllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import DB_Communicator.Communicate;
import clientUIControllers.SearchABook.SearchBookBy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import logic.Date;


public class LendDetails implements Initializable{
	private boolean editing = false;
	@FXML
    private TextField bookRow;

    @FXML
    private TextField subscriberRow;

    @FXML
    private DatePicker lendDate;

    @FXML
    private DatePicker returnDate;

    @FXML
    private Button editBtn;
    
    @FXML
    private Button viewSubscriberDetailsBtn;

    @FXML
    private Button viewBookDetailsBtn;
    
    @FXML
    public void cancel(ActionEvent event) throws Exception {
    	Utils.closeCurrentFrame(event);
    	Utils.OpenFrame("/gui/LibrarianOptions.fxml", "Librarian", this, event, true);
    }
    
    @FXML
    public void editLendDetails(ActionEvent event) throws Exception {
    	if (!this.editing) {
    		this.editBtn.setText("Update");
    		this.bookRow.setEditable(true);
    		this.subscriberRow.setEditable(true);
    		this.lendDate.setEditable(true);
    		this.returnDate.setEditable(true);
    		this.editing = true;
    	}
    	else {
    		//Communicate.getInstance().updateLendDetails(key, lend);
    	}
    }

    @FXML
    void viewBookDetails(ActionEvent event) throws Exception {
    	if (this.bookRow.getText() == "")
    		return;
    	//SearchABook.selectedBook = Communicate.getInstance().searchBook(SearchBookBy.BY_NAME, this.bookRow.getText()).get(0);
    	if (SearchABook.selectedBook == null)
    		Utils.OpenFrame("/gui/ItemNotFound.fxml", "Item not found", this, event, false);
		else
			Utils.OpenFrame("/gui/BookDetails.fxml", "Book details", this, event, false);
	}

    @FXML
    void viewSubscriberDetails(ActionEvent event) throws Exception {
    	/*
    	if (this.subscriberRow.getText() == "")
    		return;
    	SearchASubscriber.selectedSubscriber = Communicate.getInstance().searchSubscriber(SearchSubscriberBy.BY_ID, this.subscriberRow.getText()).get(0);
    	if (SearchASubscriber.selectedSubscriber == null)
    		Utils.OpenFrame("/gui/ItemNotFound.fxml", "Item not found", this, event, false);
    	else
    		Utils.OpenFrame("/gui/SubscriberDetails.fxml", "Subscriber details", this, event, false);
    		*/
    }
    
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {/*
		this.subscriberRow.setText(LendABook.lend.getSubscriber().getID());
		this.bookRow.setText(LendABook.lend.getBook().getTitle());
		
		Date d = LendABook.lend.getLendDate();
		this.lendDate.setValue(LocalDate.of(d.getYear(), d.getMonth(), d.getDate()));
		
		d = LendABook.lend.getReturnDate();
		this.returnDate.setValue(LocalDate.of(d.getYear(), d.getMonth(), d.getDate()));
		*/
	}
}
