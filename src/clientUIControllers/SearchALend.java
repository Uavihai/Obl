package clientUIControllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DB_Communicator.Communicate;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class SearchALend implements Initializable{
	public static enum SearchLendBy {
		BY_SUBSCRIBER_ID,
		BY_BOOK_NAME,
		BY_LEND_DATE,
		BY_RETURN_DATE
	}
	
	 @FXML
    private ListView<String> lendsList;
	
	@FXML
    private RadioButton bySubscriber;

    @FXML
    private RadioButton byBook;

    @FXML
    private RadioButton byLendDate;

    @FXML
    private RadioButton byReturnDate;
    
    @FXML
    private TextField searchRow;
    
    private ArrayList<RadioButton> properties;
    
    private RadioButton selectedProperty;
    
    //static Lend selectedLend;

	

    @FXML
    public void searchBy(ActionEvent event) {
    	this.selectedProperty.setSelected(false);
    	(this.selectedProperty = ((RadioButton)event.getSource())).setSelected(true);
    	this.searchRow.requestFocus();
    }

    private void search () {/*
    	this.lendsList.setItems(FXCollections.observableArrayList(new ArrayList<String>()));
    	String propertyStr = searchRow.getText();
    	if (propertyStr.length() == 0)
    		return;

    	SearchLendBy sb = null;

    	if (this.selectedProperty == this.bySubscriber)
			sb = SearchLendBy.BY_SUBSCRIBER_ID;
		else if (this.selectedProperty == this.byBook)
			sb = SearchLendBy.BY_BOOK_NAME;
		else if (this.selectedProperty == this.byLendDate)
			sb = SearchLendBy.BY_LEND_DATE;
		else if (this.selectedProperty == this.byReturnDate)
			sb = SearchLendBy.BY_RETURN_DATE;
		
    	ArrayList<Lend> matchLends = Communicate.getInstance().searchLend(sb, propertyStr);
		
		ArrayList<String> LendsList = new ArrayList<String>();
		for (Lend i : matchLends)
			LendsList.add(i.toString());
		this.lendsList.setItems(FXCollections.observableArrayList(LendsList));*/
    }
    
    @FXML
    public void searchBtn(ActionEvent event) {
    	search();
    }

    @FXML
    void searchRowKeyResponse(KeyEvent event) {
    	if(event.getCode().toString().equals("ENTER"))
    		search();
    }
    
    @FXML
    void getLendDetails(MouseEvent event) throws Exception {
    	/*
    	if (this.lendsList.getSelectionModel().getSelectedItem() == null)
    		return;
    	ArrayList<Lend> selectedLends = Communicate.getInstance().searchLend(SearchLendBy.BY_SUBSCRIBER_ID, this.lendsList.getSelectionModel().getSelectedItem().split("" + Utils.SEPARATOR)[2]);
    	String bookName = this.lendsList.getSelectionModel().getSelectedItem().split("" + Utils.SEPARATOR)[3];
    	for (Lend i: selectedLends)
    		if (i.getBook().getTitle().equals(bookName)) {
    			selectedLend = i;
    			break;
    		}
    	if (selectedLend != null)
    		Utils.OpenFrame("/gui/LendDetails.fxml", "Lend details", this, event, false);
    		*/
    }
    
    
    @FXML
    public void cancel(ActionEvent event) throws Exception {
    	Utils.closeCurrentFrame(event);
    	Utils.OpenFrame("/gui/LendABook.fxml", "Lend a book", this, event, true);
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.properties = new ArrayList<RadioButton>();
		this.properties.add(this.selectedProperty = this.bySubscriber);
		this.properties.add(this.byBook);
		this.properties.add(this.byLendDate);
		this.properties.add(this.byReturnDate);
	}
}
