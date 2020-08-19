package DB_Communicator;

import java.util.ArrayList;

import DB_Communicator.Authenticate.UserType;
import Entities.Book;
import Entities.Subscriber;
import ServerClientRequests.BookSearchRequest;
import ServerClientRequests.IBasicRequest;
import ServerClientRequests.Lend_ReturnABookRequest;
import ServerClientRequests.LoginRequest;
import ServerClientRequests.SubscriberManageRequest;
import client.ClientConsole;
import clientUIControllers.Login;
import clientUIControllers.SearchABook;
import clientUIControllers.Utils;
import clientUIControllers.SearchABook.SearchBookBy;
import clientUIControllers.SearchALend.SearchLendBy;
import clientUIControllers.SearchASubscriber.SearchSubscriberBy;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
//import logic.Lend;

public final class Communicate {
	private static Communicate instance = null;
	
	public static Communicate getInstance () {
		return instance == null ? instance = new Communicate() : instance;
	}
	private static ClientConsole clientConsole;
	private Communicate () {}
	
	public void authenticate (LoginRequest loginRequest){
		clientConsole.sendRequestToServer(loginRequest);
		/*if (username.equals("lib") && password.equals("lib"))
			Authenticate.getInstance().setStatus(UserType.LIBRARIAN);
		else if (username.equals("sub") && password.equals("sub"))
			Authenticate.getInstance().setStatus(UserType.SUBSCRIBER);
		else
			Authenticate.getInstance().setStatus(UserType.LIBRARIAN);*/
	}
	/**
	 * Method sends the request object to the server
	 * @param bookSearchRequest
	 */
	public void sendRequestToServer (IBasicRequest request)
	{
		clientConsole.sendRequestToServer(request);
	}
	
	public void openWindowAfterLogin(LoginRequest loginRequest)
	{
		try {
			Login.getInstance().openAppropriateWindow(loginRequest);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void updateUIPostBookSearch(BookSearchRequest bookSearchRequest) 
	{
		SearchABook.getInstance().updateUIPostBookSearch(bookSearchRequest.getBookListResult());
	}
	/*
	public void searchSubscriber (SubscriberManageRequest request)
	{
		clientConsole.sendRequestToServer(request);
		// send request to server
		
	}
	*/
	/*
	public void lendABook(Lend_ReturnABookRequest request)
	{
		clientConsole.sendRequestToServer(request);
	}*/
	public ArrayList<Lend> searchLend(SearchLendBy property, String propertyStr) {
		
		ArrayList<Lend> ret = new ArrayList<Lend>();
		/*switch (property) {
		case BY_SUBSCRIBER_ID:
			ret.add(new Lend(new Subscriber(propertyStr, "lName1", "123456789", "0522222222", "fNamelName@gmail.com"), new Book(propertyStr, "Author1", "Thriller", "Interesting Book")));
			break;
		case BY_BOOK_NAME:
			ret.add(new Lend(new Subscriber(propertyStr, "lName1", "123456789", "0522222222", "fNamelName@gmail.com"), new Book(propertyStr, "Author1", "Thriller", "Interesting Book")));
			break;
		case BY_LEND_DATE:
			ret.add(new Lend(new Subscriber(propertyStr, "lName1", "123456789", "0522222222", "fNamelName@gmail.com"), new Book(propertyStr, "Author1", "Thriller", "Interesting Book")));
			break;
		case BY_RETURN_DATE:
			ret.add(new Lend(new Subscriber(propertyStr, "lName1", "123456789", "0522222222", "fNamelName@gmail.com"), new Book(propertyStr, "Author1", "Thriller", "Interesting Book")));
			break;
		}*/
		return ret;
	}

	public void updateBookDetails(String bookName, Book newDetails) {
		System.out.println("Update Book Details Function");
	}

	public void updateSubscriberDetails(String id, Subscriber selectedSubscriber) {
		System.out.println("Update Subscriber Details Function");
	}
	
	public void updateLendDetails(String key, Lend lend) {
		System.out.println("Update Lend Details Function");
	}

	/**
	 * @return the clientConsole
	 */
	public static ClientConsole getClientConsole() {
		return clientConsole;
	}

	/**
	 * @param clientConsole the clientConsole to set
	 */
	public static void setClientConsole(ClientConsole clientConsole) {
		Communicate.clientConsole = clientConsole;
	}

	public void addSubscriber(Subscriber selectedSubscriber) {
		
	}

	
}
