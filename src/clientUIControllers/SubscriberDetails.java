package clientUIControllers;

import java.net.URL;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.ResourceBundle;

import DB_Communicator.Authenticate.UserType;
import DB_Communicator.Authenticate;
import DB_Communicator.Communicate;
import Entities.Book;
import Entities.LendingHistoryRow;
import Entities.MembershipStatus;
import Entities.Subscriber;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class SubscriberDetails implements Initializable {
	private boolean editing = false;
	static boolean addNewSubscriber = false;

	@FXML
	private TextField firstName;

	@FXML
	private Button editBtn;

	@FXML
	private Label title;

	@FXML
	private TextField lastName;

	@FXML
	private TextField id;

	@FXML
	private TextField email;

	@FXML
	private TextField cellphone;

	@FXML
	private Button logout;
	/*
	 * @FXML private CheckBox isFreezed;
	 */
	@FXML
	private ComboBox<MembershipStatus> membershipStatus;

	public static Subscriber selectedSubscriber;

	private static SubscriberDetails instance;

	private static SearchABook sourceFrame;
	@FXML
	void cancel(ActionEvent event) {
		Utils.closeCurrentFrame(event);
	}

	@FXML
	void logout(ActionEvent event) {
		Authenticate.getInstance().Logout(sourceFrame);
		Utils.closeCurrentFrame(event);
	}

	@FXML
	void editSubscriberDetailsBtn(ActionEvent event) {
		if (!this.editing && !addNewSubscriber) {
			this.editBtn.setText("Save");
			this.editing = true;

			this.cellphone.setEditable(true);
			this.email.setEditable(true);
			if (selectedSubscriber.getType() == UserType.LIBRARIAN
					|| selectedSubscriber.getType() == UserType.MANAGER) {
				this.firstName.setEditable(true);
				this.lastName.setEditable(true);
				this.id.setEditable(true);
				if (selectedSubscriber.getType() == UserType.MANAGER)
					this.membershipStatus.setDisable(false);
			}

		} else {
			System.out.println("Saving...");
			/*
			 * selectedSubscriber = new Subscriber( this.firstName.getText(),
			 * this.lastName.getText(), this.id.getText(),
			 * Integer.parseInt(this.cellphone.getText()), this.email.getText());
			 */
			if (!addNewSubscriber) {
				// Communicate.getInstance().updateSubscriberDetails(SearchASubscriber.selectedSubscriber.getID(),
				// SearchASubscriber.selectedSubscriber);
			} else {
				// Communicate.getInstance().addSubscriber(SearchASubscriber.selectedSubscriber);
			}
			this.cancel(event);
		}
	}

	@FXML
	public void viewLendsHistory(ActionEvent event) {
		openLendingHistory();
	}

	public void openLendingHistory() {
		// New window (Stage)
		Stage newWindow = new Stage();
		newWindow.setTitle(
				"Lending History of " + selectedSubscriber.getFirstName() + " " + selectedSubscriber.getLastName());

		// create a table.
		final TableView<LendingHistoryRow> table = new TableView<>();

		// define the table columns.
		TableColumn<LendingHistoryRow, String> bookNameCol = new TableColumn<>("Book Name");
		bookNameCol.setCellValueFactory(new PropertyValueFactory("BookName"));

		TableColumn<LendingHistoryRow, Date> lendingDateCol = new TableColumn<>("Lending Date");
		lendingDateCol.setCellValueFactory(new PropertyValueFactory("LendingDate"));

		TableColumn<LendingHistoryRow, Date> expectedReturnDateCol = new TableColumn<>("Expected Return Date");
		expectedReturnDateCol.setCellValueFactory(new PropertyValueFactory("ExpectedReturnDate"));

		TableColumn<LendingHistoryRow, Date> actualReturnDateCol = new TableColumn<>("Actual Return Date");
		actualReturnDateCol.setCellValueFactory(new PropertyValueFactory("ActualReturnDate"));

		TableColumn<LendingHistoryRow, Boolean> hasReturnedCol = new TableColumn<>("Returned?");
		hasReturnedCol.setCellValueFactory(new PropertyValueFactory("HasReturned"));

		TableColumn<LendingHistoryRow, Boolean> isLateCol = new TableColumn<>("Late?");
		isLateCol.setCellValueFactory(new PropertyValueFactory("Late"));

		TableColumn<LendingHistoryRow, Boolean> isLostCol = new TableColumn<>("Lost?");
		isLostCol.setCellValueFactory(new PropertyValueFactory("Lost"));

		TableColumn<LendingHistoryRow, Boolean> isExtendedCol = new TableColumn<>("Extended?");
		isExtendedCol.setCellValueFactory(new PropertyValueFactory("Extended"));

		TableColumn<LendingHistoryRow, Boolean> libIDCol = new TableColumn<>("Librarian ID");
		isExtendedCol.setCellValueFactory(new PropertyValueFactory("IsExtended"));

		TableColumn<LendingHistoryRow, Date> newReturnDateCol = new TableColumn<>("New Return Date");
		newReturnDateCol.setCellValueFactory(new PropertyValueFactory("NewReturnDate"));

		TableColumn<LendingHistoryRow, Boolean> actionCol = new TableColumn<>("Action");
		actionCol.setSortable(false);

		table.getColumns().setAll(bookNameCol, lendingDateCol, expectedReturnDateCol, hasReturnedCol,
				actualReturnDateCol, isLateCol, isExtendedCol, newReturnDateCol, libIDCol, actionCol);
		ObservableList<LendingHistoryRow> historyList = FXCollections
				.observableArrayList(selectedSubscriber.getLendingHistory());
		table.setItems(historyList);
		// create a cell value factory with a details button for each row in the table.
		actionCol.setCellFactory(
				new Callback<TableColumn<LendingHistoryRow, Boolean>, TableCell<LendingHistoryRow, Boolean>>() {
					@Override
					public TableCell<LendingHistoryRow, Boolean> call(
							TableColumn<LendingHistoryRow, Boolean> personBooleanTableColumn) {
						return new ShowABook(newWindow, table);
					}
				});

		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		newWindow.setScene(new Scene(table));
		newWindow.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (addNewSubscriber) {
			this.title.setText("Add new subscriber");
			this.editBtn.setText("Add");
			this.firstName.setEditable(true);
			this.lastName.setEditable(true);
			this.id.setEditable(true);
			this.cellphone.setEditable(true);
			this.email.setEditable(true);
		} else {
			this.firstName.setText(selectedSubscriber.getFirstName());
			this.lastName.setText(selectedSubscriber.getLastName());
			this.id.setText("" + selectedSubscriber.getStudentID());
			this.cellphone.setText("" + selectedSubscriber.getCellphone());
			this.email.setText(selectedSubscriber.getEmail());
			this.membershipStatus.setItems(FXCollections.observableArrayList(MembershipStatus.Active,
					MembershipStatus.Frozen, MembershipStatus.Locked));
			this.membershipStatus.setValue(selectedSubscriber.getStatus());
		}
	}

	/** A table cell containing a button for details on each Book. */
	private class ShowABook extends TableCell<LendingHistoryRow, Boolean> {
		// a button for a specific person's details
		final Button viewBook = new Button("View Book");
		// pads and centers the button in the cell.
		final StackPane paddedButton = new StackPane();
		Book book;
		TableView table;

		/**
		 * AddPersonCell constructor
		 * 
		 * @param stage the stage in which the table is placed.
		 * @param table the table to which a new person can be added.
		 */
		ShowABook(final Stage stage, final TableView table) {
			paddedButton.setPadding(new Insets(3));
			paddedButton.getChildren().add(viewBook);
			viewBook.setOnAction(buttonHandler);
			this.table = table;
		}

		/** places an details button in the row only if the row is not empty. */
		@Override
		protected void updateItem(Boolean item, boolean empty) {
			super.updateItem(item, empty);
			if (!empty) {
				setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
				setGraphic(paddedButton);
			} else {
				setGraphic(null);
			}
		}

		EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// LendingHistoryRow
				// lr=(LendingHistoryRow)table.getSelectionModel().getSelectedItem();
				LendingHistoryRow lr = (LendingHistoryRow) ShowABook.this.getTableView().getItems()
						.get(ShowABook.this.getIndex());
				Book book = lr.getBook();
				BookDetails.selectedBook = book;
				try {
					BookDetails bookDetails = (BookDetails) Utils.OpenFrame("/gui/BookDetails.fxml", "Book details",
							this, event, false);
					bookDetails.getEditBtn().setDisable(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				event.consume();
			}
		};
	}

	/**
	 * @return the instance
	 */
	public static SubscriberDetails getInstance() {
		return instance;
	}

	/**
	 * @param instance the instance to set
	 */
	public static void setInstance(SubscriberDetails instance) {
		SubscriberDetails.instance = instance;
	}

	/**
	 * @return the editing
	 */
	public boolean isEditing() {
		return editing;
	}

	/**
	 * @param editing the editing to set
	 */
	public void setEditing(boolean editing) {
		this.editing = editing;
	}

	/**
	 * @return the firstName
	 */
	public TextField getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(TextField firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the editBtn
	 */
	public Button getEditBtn() {
		return editBtn;
	}

	/**
	 * @param editBtn the editBtn to set
	 */
	public void setEditBtn(Button editBtn) {
		this.editBtn = editBtn;
	}

	/**
	 * @return the title
	 */
	public Label getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(Label title) {
		this.title = title;
	}

	/**
	 * @return the lastName
	 */
	public TextField getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(TextField lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the id
	 */
	public TextField getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(TextField id) {
		this.id = id;
	}

	/**
	 * @return the email
	 */
	public TextField getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(TextField email) {
		this.email = email;
	}

	/**
	 * @return the cellphone
	 */
	public TextField getCellphone() {
		return cellphone;
	}

	/**
	 * @param cellphone the cellphone to set
	 */
	public void setCellphone(TextField cellphone) {
		this.cellphone = cellphone;
	}

	/**
	 * @return the membershipStatus
	 */
	public ComboBox<MembershipStatus> getMembershipStatus() {
		return membershipStatus;
	}

	/**
	 * @param membershipStatus the membershipStatus to set
	 */
	public void setMembershipStatus(ComboBox<MembershipStatus> membershipStatus) {
		this.membershipStatus = membershipStatus;
	}

	/**
	 * @return the logout
	 */
	public Button getLogout() {
		return logout;
	}

	/**
	 * @param logout the logout to set
	 */
	public void setLogout(Button logout) {
		this.logout = logout;
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
		SubscriberDetails.sourceFrame = sourceFrame;
	}

}
