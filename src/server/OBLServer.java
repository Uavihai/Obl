package server;
// This file contains material supporting section 3.7 of the textbook:

import java.io.IOException;
import java.sql.SQLException;

// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import DataBase.DataBaseController;
import OBLControllers.OBLServerGUIController;
import OCSF.server.*;
import ServerClientRequests.BookSearchRequest;
import ServerClientRequests.IBasicRequest;
import ServerClientRequests.Lend_ReturnABookRequest;
import ServerClientRequests.LoginRequest;
import ServerClientRequests.SubscriberManageRequest;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class OBLServer extends AbstractServer {
	// Class variables *************************************************
	private static OBLServer instance;
	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;
	// Constructors ****************************************************

	/**
	 * Constructs an instance of the echo server.
	 * 
	 * @param port The port number to connect on.
	 */
	public OBLServer(int port, String dbName) {
		super(port);
		DataBaseController.connectToDB(dbName);
		instance = this;
	}

	// Instance methods ************************************************

	/**
	 * This method handles any messages received from the client.
	 * 
	 * @param msg The message received from the client.
	 * @param The connection from which the message originated.
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		OBLServerGUIController.getUiControllerInstance()
				.addLineToTextArea("Message received: " + msg + " from " + client);// Append to server ui
		((IBasicRequest) msg).handleRequest(client);
	}

	/**
	 * Method searches for a book by a give category
	 * 
	 * @param bookSearchRequest request from client
	 * @param client            connection to the client
	 */
	public void handleSearchBookRequest(BookSearchRequest bookSearchRequest, ConnectionToClient client) {
		try {
			BookSearchRequest answer = DataBaseController.BookSearch(bookSearchRequest);
			client.sendToClient(answer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Method handles login
	 * 
	 * @param bookSearchRequest
	 * @param client
	 */
	public void handleLoginRequest(LoginRequest loginRequest, ConnectionToClient client) {
		
		try {
			LoginRequest answer = DataBaseController.login(loginRequest);
			client.sendToClient(answer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Method searches for a subscriber by given category
	 * 
	 * @param request
	 * @param client
	 */
	public void handleSearchSubscriberRequest(SubscriberManageRequest request, ConnectionToClient client) {
		
		try {
			SubscriberManageRequest answer = DataBaseController.searchSubscriber(request);
			client.sendToClient(answer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Method lends a book to a subscriber
	 * 
	 * @param bookSearchRequest
	 * @param client
	 */
	public void handleLendingBookRequest(Lend_ReturnABookRequest bookSearchRequest, ConnectionToClient client) {
		try {
		Lend_ReturnABookRequest answer = DataBaseController.handleBookLendRequest(bookSearchRequest);
			client.sendToClient(answer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Static method to return OBLServer instance
	 * 
	 * @return instance OBLServer instance
	 */
	public static OBLServer getInstance() {
		return instance;
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		OBLServerGUIController.getUiControllerInstance()
				.addLineToTextArea("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		OBLServerGUIController.getUiControllerInstance()
				.addLineToTextArea("Server has stopped listening for connections.");
	}

	public void handleReturnBookRequest(Lend_ReturnABookRequest lend_ReturnABookRequest, ConnectionToClient client) {
		try {
			Lend_ReturnABookRequest answer = DataBaseController.handleBookReturnRequest(lend_ReturnABookRequest);
			client.sendToClient(answer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method is responsible for the creation of the server instance (there is
	 * no UI in this phase).
	 *
	 * @param args[0] The port number to listen on. Defaults to 5555 if no argument
	 *        is entered.
	 */
	// ------------------------OLD CODE-----------------------------------
	/*
	 * public static void main(String[] args) { String dbName; try { dbName =
	 * args[0]; // Get port from command line } catch (Throwable t) {
	 * dbName="librarydatabase"; // Set port to 5555 }
	 * 
	 * OBLServer sv = new OBLServer(DEFAULT_PORT,dbName);
	 * 
	 * try { sv.listen(); // Start listening for connections } catch (Exception ex)
	 * { System.out.println("ERROR - Could not listen for clients!" + "\n" + ex); }
	 * }
	 */
}
//End of EchoServer class
