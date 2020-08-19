package clientUIControllers;

import Entities.LendingHistoryRow;
import Entities.Subscriber;
import ServerClientRequests.Lend_ReturnABookRequest;
import ServerClientRequests.Lend_ReturnABookRequest.Type;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import DB_Communicator.Communicate;
import Entities.Book;

public class LendABook {
	@FXML
	private TextField bookRow;

	@FXML
	private TextField subscriberRow;

	@FXML
	private Button viewBookBth;

	@FXML
	private Button viewSubBtn;

	@FXML
	private TextField lendDateTxt;

	@FXML
	private TextField returnDateTxt;

	@FXML
	private Button lendBtn;

	static LendingHistoryRow lend;
	static Book selectedBook = null;
	static Subscriber selectedSubscriber = null;

	@FXML
	public void chooseABook(ActionEvent event) throws Exception {
		SearchABook.sourceFrameLend = this;
		SearchABook controller = (SearchABook) Utils.OpenFrame("/gui/SearchABook.fxml", "Search a book", this, event,
				false);
		SearchABook.setInstance(controller);
		viewBookBth.setDisable(false);

	}

	public void setLendAndReturnDates(boolean isPopular) {
		LocalDate now = LocalDate.now();
		lendDateTxt.setText(now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		returnDateTxt.setText(now.plusDays(isPopular ? 3 : 14).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
	}

	@FXML
	public void cancel(ActionEvent event) throws Exception {
		// Utils.OpenFrame("/gui/LibrarianOptions.fxml", "Librarian", this, event,
		// true);
		Utils.closeCurrentFrame(event);
	}

	@FXML
	public void chooseASubscriber(ActionEvent event) throws Exception {
		SearchASubscriber.sourceFrameLend = this;
		SubscriberDetails.addNewSubscriber = false;
		SearchASubscriber controller = (SearchASubscriber) Utils.OpenFrame("/gui/SearchASubscriber.fxml",
				"Search a subscriber", this, event, false);
		SearchASubscriber.setInstance(controller);
		viewSubBtn.setDisable(false);
	}

	@FXML
	public void lend(ActionEvent event) throws Exception {
		if (SearchASubscriber.selectedSubscriber == null || SearchABook.selectedBook == null)
			return;

		// if (SearchASubscriber.selectedSubscriber.isFreezedAccount()) {}
		else {
			if (selectedBook.getNumberOfCopies() == 0)
				return;
			for (LendingHistoryRow lhr : selectedSubscriber.getLendingHistory()) {
				if (lhr.getBook().getCatalogueNumber() == selectedBook.getCatalogueNumber()) {
					Utils.showAlertDialog(AlertType.ERROR, "Error", "Book already lent!",
							"Cannot lend the same book twice!");
					return;
				}
			}
			Lend_ReturnABookRequest lendingRequest = new Lend_ReturnABookRequest();
			lendingRequest.setBook(selectedBook);
			lendingRequest.setSubscriber(selectedSubscriber);
			lendingRequest.setRequestType(Type.LEND);
			Communicate.getInstance().sendRequestToServer(lendingRequest);
		}
	}

	/*
	 * @FXML public void viewLendedBooks(ActionEvent event) throws Exception {
	 * Utils.OpenFrame("/gui/SearchALend.fxml", "Search a lend", this, event, true);
	 * }
	 */

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
				SubscriberDetails.getInstance().getLogout().setVisible(false);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@FXML
	void viewBook(ActionEvent event) {
		if (selectedBook != null) {
			BookDetails.selectedBook = selectedBook;
			BookDetails controller;
			try {
				controller = (BookDetails) Utils.OpenFrame("/gui/BookDetails.fxml", "Book details", this, event, false);
				controller.getEditBtn().setVisible(false);
				BookDetails.setInstance(controller);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the bookRow
	 */
	public TextField getBookRow() {
		return bookRow;
	}

	/**
	 * @param bookRow the bookRow to set
	 */
	public void setBookRow(TextField bookRow) {
		this.bookRow = bookRow;
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
	 * @return the viewBookBth
	 */
	public Button getViewBookBth() {
		return viewBookBth;
	}

	/**
	 * @param viewBookBth the viewBookBth to set
	 */
	public void setViewBookBth(Button viewBookBth) {
		this.viewBookBth = viewBookBth;
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
	 * @return the selectedBook
	 */
	public static Book getSelectedBook() {
		return selectedBook;
	}

	/**
	 * @param selectedBook the selectedBook to set
	 */
	public static void setSelectedBook(Book selectedBook) {
		LendABook.selectedBook = selectedBook;
	}

	/**
	 * @return the selectedSubscriber
	 */
	public static Subscriber getSelectedSubscriber() {
		return selectedSubscriber;
	}

	/**
	 * @param selectedSubscriber the selectedSubscriber to set
	 */
	public static void setSelectedSubscriber(Subscriber selectedSubscriber) {
		LendABook.selectedSubscriber = selectedSubscriber;
	}

	/**
	 * @return the lendDateTxt
	 */
	public TextField getLendDateTxt() {
		return lendDateTxt;
	}

	/**
	 * @param lendDateTxt the lendDateTxt to set
	 */
	public void setLendDateTxt(TextField lendDateTxt) {
		this.lendDateTxt = lendDateTxt;
	}

	/**
	 * @return the returnDateTxt
	 */
	public TextField getReturnDateTxt() {
		return returnDateTxt;
	}

	/**
	 * @param returnDateTxt the returnDateTxt to set
	 */
	public void setReturnDateTxt(TextField returnDateTxt) {
		this.returnDateTxt = returnDateTxt;
	}

	/**
	 * @return the lendBtn
	 */
	public Button getLendBtn() {
		return lendBtn;
	}

	/**
	 * @param lendBtn the lendBtn to set
	 */
	public void setLendBtn(Button lendBtn) {
		this.lendBtn = lendBtn;
	}

}
