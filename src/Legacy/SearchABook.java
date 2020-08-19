package clientUIControllers;

import java.io.IOException;
import java.math.MathContext;
import java.net.URL;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import DB_Communicator.Authenticate;
import DB_Communicator.Authenticate.UserType;
import DB_Communicator.Communicate;
import Entities.Book;
import ServerClientRequests.BookSearchRequest;
import ServerClientRequests.BookSearchRequest.SearchType;
import client.ClientConsole;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SearchABook implements Initializable {
	public static enum SearchBookBy {
		BY_NAME, BY_AUTHOR, BY_SUBJECT, BY_DESCRIPTION
	}

	@FXML
	private ListView<Book> booksList;

	@FXML
	private RadioButton byName;

	@FXML
	private RadioButton byAuthor;

	@FXML
	private RadioButton bySubject;

	@FXML
	private RadioButton byDescription;

	@FXML
	private TextField searchRow;

	@FXML
    private Button chooseABookBtn;
	
	@FXML
    private Button loginBtn;
	
	@FXML
    private Button cancelBtn;
	
	private ArrayList<RadioButton> properties;

	private RadioButton selectedProperty;

	static Book selectedBook;

	public static LendABook sourceFrame;

	private static SearchABook instance=null;

	@FXML
	private Button connectBtn;

	@FXML
	private Label serverMsg;

	@FXML
	private TextField ServerIPtxt;

	private ClientConsole clientConsole;
	
	private ArrayList<Book> foundBooks;
	
	private static final Pattern PATTERN_IP = Pattern
			.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

	@FXML
    void login(ActionEvent event) throws Exception {
		if(Authenticate.getInstance().getLoggedInAs()==UserType.NOT_SIGNED_IN)
			Login.setInstance((Login) Utils.OpenFrame("/gui/Login.fxml", "ORT-Braude Library", this, event, false));
		else {
			Login.getInstance().reOpenAppropritateWindowForUser();
		}
    }
	
	@FXML
	public void chooseABookBtn(ActionEvent event) throws Exception {
		if (this.booksList.getSelectionModel().getSelectedItem() == null)
			sourceFrame.getBookRow().setText("");
		else
		{
			sourceFrame.getBookRow().setText(selectedBook.getTitle());
			sourceFrame.setSelectedBook(selectedBook);
		}
		
		this.cancel(event);
	}


	@FXML
	public void searchBy(ActionEvent event) {
		this.selectedProperty.setSelected(false);
		(this.selectedProperty = ((RadioButton) event.getSource())).setSelected(true);
		this.searchRow.requestFocus();
	}

	private void search() {
		this.booksList.setItems(FXCollections.observableArrayList(new ArrayList<Book>()));
		String propertyStr = searchRow.getText();
		if (propertyStr.isEmpty())
			return;

		BookSearchRequest bookSearchRequest = new BookSearchRequest();

		SearchType searchType = null;

		if (this.selectedProperty == this.byName) {
			searchType = SearchType.Title;
			bookSearchRequest.setBookTitle(propertyStr);
		} else if (this.selectedProperty == this.byAuthor) {
			searchType = SearchType.Author;
			bookSearchRequest.setBookAuthor(propertyStr);
		} else if (this.selectedProperty == this.bySubject) {
			searchType = SearchType.Subject;
			bookSearchRequest.setBookSubject(propertyStr);
		} else if (this.selectedProperty == this.byDescription) {
			searchType = SearchType.FreeText;
			bookSearchRequest.setBookFreeText(propertyStr);
		}
		bookSearchRequest.setSearchType(searchType);
		Communicate.getInstance().searchBook(bookSearchRequest);

	}

	public void updateUIPostBookSearch(ArrayList<Book> matchBooks) {
		try {
			foundBooks = matchBooks;
			Platform.runLater(() -> {
				// Update UI here.
				booksList.setItems(FXCollections.observableArrayList(foundBooks));
				booksList.setCellFactory(param -> new ListCell<Book>() {
					@Override
					protected void updateItem(Book item, boolean empty) {
						super.updateItem(item, empty);

						if (empty || item == null || item.getTitle() == null) {
							setText(null);
						} else {
							setText(item.getTitle());
						}
					}
				});
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void searchBtn(ActionEvent event) {
		search();
	}

	@FXML
	void searchRowKeyResponse(KeyEvent event) {
		if (event.getCode().toString().equals("ENTER"))
			search();
	}

	@FXML
	public void getBookDetails(MouseEvent event) throws Exception {
		if (this.booksList.getSelectionModel().getSelectedItem() == null)
			return;
		selectedBook = booksList.getSelectionModel().getSelectedItem();

		if (event.getClickCount() == 2) {
			BookDetails.selectedBook=booksList.getSelectionModel().getSelectedItem();
			Utils.OpenFrame("/gui/BookDetails.fxml", "Book details", this, event, false);
		}
	}

	
	@FXML
	public void cancel(ActionEvent event) throws Exception {
		Utils.closeCurrentFrame(event);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.properties = new ArrayList<RadioButton>();
		this.properties.add(this.selectedProperty = this.byName);
		this.properties.add(this.byAuthor);
		this.properties.add(this.bySubject);
		this.properties.add(this.byDescription);
		if(Authenticate.getInstance().getLoggedInAs()!=Authenticate.UserType.NOT_SIGNED_IN)
			this.loginBtn.setVisible(false);
	}

	public void start(Stage primaryStage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		Parent root = loader.load(getClass().getResource("/gui/SearchABook.fxml").openStream());
		instance = loader.getController();
		Scene scene = new Scene(root);
		primaryStage.setTitle("ORT-Braude Library");
		primaryStage.setScene(scene);

		primaryStage.show();
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

	/**
	 * @return the instance
	 */
	public static SearchABook getInstance() {
		return instance;
		
	}

	/**
	 * @param instance the instance to set
	 */
	public static void setInstance(SearchABook instance) {
		SearchABook.instance = instance;
	}

	/**
	 * @return the booksList
	 */
	public ListView<Book> getBooksList() {
		return booksList;
	}

	/**
	 * @param booksList the booksList to set
	 */
	public void setBooksList(ListView<Book> booksList) {
		this.booksList = booksList;
	}

	/**
	 * @return the byName
	 */
	public RadioButton getByName() {
		return byName;
	}

	/**
	 * @param byName the byName to set
	 */
	public void setByName(RadioButton byName) {
		this.byName = byName;
	}

	/**
	 * @return the byAuthor
	 */
	public RadioButton getByAuthor() {
		return byAuthor;
	}

	/**
	 * @param byAuthor the byAuthor to set
	 */
	public void setByAuthor(RadioButton byAuthor) {
		this.byAuthor = byAuthor;
	}

	/**
	 * @return the bySubject
	 */
	public RadioButton getBySubject() {
		return bySubject;
	}

	/**
	 * @param bySubject the bySubject to set
	 */
	public void setBySubject(RadioButton bySubject) {
		this.bySubject = bySubject;
	}

	/**
	 * @return the byDescription
	 */
	public RadioButton getByDescription() {
		return byDescription;
	}

	/**
	 * @param byDescription the byDescription to set
	 */
	public void setByDescription(RadioButton byDescription) {
		this.byDescription = byDescription;
	}

	/**
	 * @return the searchRow
	 */
	public TextField getSearchRow() {
		return searchRow;
	}

	/**
	 * @param searchRow the searchRow to set
	 */
	public void setSearchRow(TextField searchRow) {
		this.searchRow = searchRow;
	}

	/**
	 * @return the properties
	 */
	public ArrayList<RadioButton> getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(ArrayList<RadioButton> properties) {
		this.properties = properties;
	}

	/**
	 * @return the selectedProperty
	 */
	public RadioButton getSelectedProperty() {
		return selectedProperty;
	}

	/**
	 * @param selectedProperty the selectedProperty to set
	 */
	public void setSelectedProperty(RadioButton selectedProperty) {
		this.selectedProperty = selectedProperty;
	}

	/**
	 * @return the connectBtn
	 */
	public Button getConnectBtn() {
		return connectBtn;
	}

	/**
	 * @param connectBtn the connectBtn to set
	 */
	public void setConnectBtn(Button connectBtn) {
		this.connectBtn = connectBtn;
	}

	/**
	 * @return the serverMsg
	 */
	public Label getServerMsg() {
		return serverMsg;
	}

	/**
	 * @param serverMsg the serverMsg to set
	 */
	public void setServerMsg(Label serverMsg) {
		this.serverMsg = serverMsg;
	}

	/**
	 * @return the serverIPtxt
	 */
	public TextField getServerIPtxt() {
		return ServerIPtxt;
	}

	/**
	 * @param serverIPtxt the serverIPtxt to set
	 */
	public void setServerIPtxt(TextField serverIPtxt) {
		ServerIPtxt = serverIPtxt;
	}

	/**
	 * @return the clientConsole
	 */
	public ClientConsole getClientConsole() {
		return clientConsole;
	}

	/**
	 * @param clientConsole the clientConsole to set
	 */
	public void setClientConsole(ClientConsole clientConsole) {
		this.clientConsole = clientConsole;
	}

	/**
	 * @return the foundBooks
	 */
	public ArrayList<Book> getFoundBooks() {
		return foundBooks;
	}

	/**
	 * @param foundBooks the foundBooks to set
	 */
	public void setFoundBooks(ArrayList<Book> foundBooks) {
		this.foundBooks = foundBooks;
	}

	/**
	 * @return the chooseABookBtn
	 */
	public Button getChooseABookBtn() {
		return chooseABookBtn;
	}

	/**
	 * @param chooseABookBtn the chooseABookBtn to set
	 */
	public void setChooseABookBtn(Button chooseABookBtn) {
		this.chooseABookBtn = chooseABookBtn;
	}

	/**
	 * @return the loginBtn
	 */
	public Button getLoginBtn() {
		return loginBtn;
	}

	/**
	 * @param loginBtn the loginBtn to set
	 */
	public void setLoginBtn(Button loginBtn) {
		this.loginBtn = loginBtn;
	}

	/**
	 * @return the cancelBtn
	 */
	public Button getCancelBtn() {
		return cancelBtn;
	}

	/**
	 * @param cancelBtn the cancelBtn to set
	 */
	public void setCancelBtn(Button cancelBtn) {
		this.cancelBtn = cancelBtn;
	}

	
}
