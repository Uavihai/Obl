package clientUIControllers;

import java.sql.Date;
import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.generic.Select;

import DB_Communicator.Communicate;
import Entities.Book;
import Entities.LendingHistoryRow;
import Entities.Subscriber;
import ServerClientRequests.Lend_ReturnABookRequest;
import ServerClientRequests.Lend_ReturnABookRequest.Type;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ReturnABook {

	@FXML
	private TextField subscriberRow;

	@FXML
	private Button viewSubBtn;

	@FXML
	private Button selectBookBtn;

	static Subscriber selectedSubscriber = null;

	static Book bookToReturn;

	@FXML
	private Button selectBookToReturnBtn;

	@FXML
	private TextField bookDetails;

	@FXML
	private Button returnBtn;

	@FXML
	void chooseASubscriber(ActionEvent event) {
		SearchASubscriber.sourceFrameReturn = this;
		SubscriberDetails.addNewSubscriber = false;
		SearchASubscriber controller;
		try {
			controller = (SearchASubscriber) Utils.OpenFrame("/gui/SearchASubscriber.fxml", "Search a subscriber", this,
					event, false);
			SearchASubscriber.setInstance(controller);
			viewSubBtn.setDisable(false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void viewSubsciber(ActionEvent event) {
		if (selectedSubscriber != null) {
			SubscriberDetails.selectedSubscriber = selectedSubscriber;
			SubscriberDetails controller;
			try {
				controller = (SubscriberDetails) Utils.OpenFrame("/gui/SubscriberDetails.fxml", "Subscriber details",
						this, event, false);
				controller.getEditBtn().setVisible(false);
				SubscriberDetails.setInstance(controller);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void returnBook(ActionEvent event) {
		if (bookToReturn == null || selectedSubscriber == null)
			return;

		Lend_ReturnABookRequest returnBookRequest = new Lend_ReturnABookRequest();
		returnBookRequest.setBook(bookToReturn);
		returnBookRequest.setSubscriber(selectedSubscriber);
		returnBookRequest.setRequestType(Type.RETURN);
		Communicate.getInstance().sendRequestToServer(returnBookRequest);
	}

	/**
	 * @param selectedSubscriber the selectedSubscriber to set
	 */
	public static void setSelectedSubscriber(Subscriber selectedSubscriber) {
		ReturnABook.selectedSubscriber = selectedSubscriber;
	}

	@FXML
	public void selectBookToReturn(ActionEvent event) {
		if (selectedSubscriber != null) {
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

			TableColumn<LendingHistoryRow, Boolean> hasReturnedCol = new TableColumn<>("Returned?");
			hasReturnedCol.setCellValueFactory(new PropertyValueFactory("HasReturned"));

			TableColumn<LendingHistoryRow, Boolean> isLateCol = new TableColumn<>("Late?");
			isLateCol.setCellValueFactory(new PropertyValueFactory("IsLate"));

			TableColumn<LendingHistoryRow, Boolean> isLostCol = new TableColumn<>("Lost?");
			isLostCol.setCellValueFactory(new PropertyValueFactory("IsLost"));

			TableColumn<LendingHistoryRow, Boolean> isExtendedCol = new TableColumn<>("Extended?");
			isExtendedCol.setCellValueFactory(new PropertyValueFactory("IsExtended"));

			TableColumn<LendingHistoryRow, Boolean> libIDCol = new TableColumn<>("Librarian ID");
			isExtendedCol.setCellValueFactory(new PropertyValueFactory("IsExtended"));

			TableColumn<LendingHistoryRow, Date> newReturnDateCol = new TableColumn<>("New Return Date");
			newReturnDateCol.setCellValueFactory(new PropertyValueFactory("NewReturnDate"));

			TableColumn<LendingHistoryRow, Boolean> actionCol = new TableColumn<>("Action");
			actionCol.setSortable(false);

			table.getColumns().setAll(bookNameCol, lendingDateCol, expectedReturnDateCol, hasReturnedCol, isLateCol,
					isExtendedCol, newReturnDateCol, libIDCol, actionCol);

			ArrayList<LendingHistoryRow> lendingHistory = selectedSubscriber.getLendingHistory();
			ArrayList<LendingHistoryRow> unreturnedBooks = new ArrayList<>();
			for (LendingHistoryRow lhr : lendingHistory) {
				if (!lhr.isHasReturned())
					unreturnedBooks.add(lhr);
			}

			ObservableList<LendingHistoryRow> historyList = FXCollections.observableArrayList(unreturnedBooks);
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
			table.setRowFactory(tv -> {
				TableRow<LendingHistoryRow> row = new TableRow<>();
				row.setOnMouseClicked(clickEvent -> {
					if (!row.isEmpty() && clickEvent.getButton() == MouseButton.PRIMARY) {

						if (clickEvent.getClickCount() == 2) {
							bookToReturn = row.getItem().getBook();
							bookDetails.setText(bookToReturn.getTitle());
							newWindow.close();
						}
						if (clickEvent.getClickCount() == 1) {
							bookToReturn = row.getItem().getBook();
						}
					}
				});
				return row;
			});
			table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			Button selectBtn = new Button("Select");

			selectBtn.setOnAction((actionEvent) -> {
				bookDetails.setText(bookToReturn.getTitle());
				newWindow.close();
			});
			VBox vbox = new VBox(20);
			vbox.setAlignment(Pos.CENTER);
			vbox.getChildren().add(table);
			vbox.getChildren().add(selectBtn);
			newWindow.setScene(new Scene(vbox));
			newWindow.show();
		}
	}

	@FXML
	void cancel(ActionEvent event) {
		Utils.closeCurrentFrame(event);
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
	 * @return the subscriberRow
	 */
	public TextField getSubscriberRow() {
		return subscriberRow;
	}

	/**
	 * @param subscriberRow the subscriberRow to set
	 */
	public void setSubscriberRow(TextField subscriberRow) {
		this.subscriberRow = subscriberRow;
	}

	/**
	 * @return the viewSubBtn
	 */
	public Button getViewSubBtn() {
		return viewSubBtn;
	}

	/**
	 * @param viewSubBtn the viewSubBtn to set
	 */
	public void setViewSubBtn(Button viewSubBtn) {
		this.viewSubBtn = viewSubBtn;
	}

	/**
	 * @return the selectBookBtn
	 */
	public Button getSelectBookBtn() {
		return selectBookBtn;
	}

	/**
	 * @param selectBookBtn the selectBookBtn to set
	 */
	public void setSelectBookBtn(Button selectBookBtn) {
		this.selectBookBtn = selectBookBtn;
	}

	/**
	 * @return the selectedSubscriber
	 */
	public static Subscriber getSelectedSubscriber() {
		return selectedSubscriber;
	}

	/**
	 * @return the selectBookToReturnBtn
	 */
	public Button getSelectBookToReturnBtn() {
		return selectBookToReturnBtn;
	}

	/**
	 * @param selectBookToReturnBtn the selectBookToReturnBtn to set
	 */
	public void setSelectBookToReturnBtn(Button selectBookToReturnBtn) {
		this.selectBookToReturnBtn = selectBookToReturnBtn;
	}

	/**
	 * @return the bookDetails
	 */
	public TextField getBookDetails() {
		return bookDetails;
	}

	/**
	 * @param bookDetails the bookDetails to set
	 */
	public void setBookDetails(TextField bookDetails) {
		this.bookDetails = bookDetails;
	}

	/**
	 * @return the bookToReturn
	 */
	public static Book getBookToReturn() {
		return bookToReturn;
	}

	/**
	 * @param bookToReturn the bookToReturn to set
	 */
	public static void setBookToReturn(Book bookToReturn) {
		ReturnABook.bookToReturn = bookToReturn;
	}

}
