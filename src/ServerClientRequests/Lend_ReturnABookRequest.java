package ServerClientRequests;

import Entities.Book;
import Entities.Subscriber;
import OCSF.server.ConnectionToClient;
import clientUIControllers.Utils;
import javafx.scene.control.Alert.AlertType;
import server.OBLServer;

public class Lend_ReturnABookRequest implements IBasicRequest {

	public enum Type {
		LEND, RETURN;
	}

	private Book book;
	private Subscriber subscriber;
	private Type requestType;
	private int status;
	private String errorMessage;

	@Override
	public void handleRequest(ConnectionToClient clientConnection) {
		try {
			switch (requestType) {
			case LEND:
				OBLServer.getInstance().handleLendingBookRequest(this, clientConnection);
				break;
			case RETURN:
				OBLServer.getInstance().handleReturnBookRequest(this, clientConnection);
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void updateUIPostAnswer() {
		if (status == 0) {
			switch (requestType) {
			case LEND:
				Utils.showAlertDialog(AlertType.INFORMATION, "Lend a book", "Success!", "Book was lent successfully!");
				break;
			case RETURN:
				Utils.showAlertDialog(AlertType.INFORMATION, "Return a book", "Success!",
						"Book was returned successfully!");
				break;
			}
		}
	}

	/**
	 * Constructor using fields
	 * 
	 * @param book
	 * @param subscriber
	 * @param status
	 * @param errorMessage
	 */
	public Lend_ReturnABookRequest(Book book, Subscriber subscriber, Type requestType, int status,
			String errorMessage) {
		this.book = book;
		this.subscriber = subscriber;
		this.status = status;
		this.errorMessage = errorMessage;
		this.requestType = requestType;
	}

	/**
	 * Empty Constructor
	 */
	public Lend_ReturnABookRequest() {
	}

	/**
	 * @return the book
	 */
	public Book getBook() {
		return book;
	}

	/**
	 * @param book the book to set
	 */
	public void setBook(Book book) {
		this.book = book;
	}

	/**
	 * @return the subscriber
	 */
	public Subscriber getSubscriber() {
		return subscriber;
	}

	/**
	 * @param subscriber the subscriber to set
	 */
	public void setSubscriber(Subscriber subscriber) {
		this.subscriber = subscriber;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the requestType
	 */
	public Type getRequestType() {
		return requestType;
	}

	/**
	 * @param requestType the requestType to set
	 */
	public void setRequestType(Type requestType) {
		this.requestType = requestType;
	}

}
