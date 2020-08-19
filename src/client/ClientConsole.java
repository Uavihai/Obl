package client;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.ArrayList;

import Entities.MembershipStatus;
import OCSF.client.ClientInterface;
import ServerClientRequests.BookSearchRequest;
import ServerClientRequests.IBasicRequest;
import ServerClientRequests.LoginRequest;
import client.*;

/**
 * This class constructs the UI for a chat client. It implements the chat
 * interface in order to activate the display() method. Warning: Some of the
 * code here is cloned in ServerConsole
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ClientConsole implements ClientInterface {
	// variables *************************************************

	/**
	 * The default port to connect on.
	 */
	final public static int DEFAULT_PORT = 5555;

	/**
	 * variable to OBLClient
	 */
	private OBLClient client;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the ClientConsole UI.
	 *
	 * @param host The host to connect to.
	 * @param port The port to connect on.
	 */
	public ClientConsole(String host, int port) throws IOException {
		client = new OBLClient(host, port, this);

	}

	/**
	 * constructor using DEFAULT_PORT
	 * 
	 * @param host
	 */
	public ClientConsole(String host) throws IOException {
		this(host, DEFAULT_PORT);
	}
	// Class methods ***************************************************

	/**
	 * This method is responsible for the creation of the Client UI.
	 *
	 * @param args[0] The host to connect to.
	 */
	public static ClientConsole startClientConnection(String host) throws IOException {
		ClientConsole client = new ClientConsole(host, DEFAULT_PORT);
		return client;
	}

	/**
	 * This method recieves BookSearchRequest object and sends a search request to the server
	 * 
	 * @param studentID student'd id
	 */
/*
	public void getSearchBook(BookSearchRequest bookSearchRequest) {
		client.handleMessageFromClientUI(bookSearchRequest);
	}
	*/
	/**
	 * Method sends the request to the server
	 * @param loginRequest
	 */
	public void sendRequestToServer(IBasicRequest request) {
		client.handleMessageFromClientUI(request);
	}
	/**
	 * 
	 */
	public void closeConnection() throws Exception
	{
		client.quit();
	}
	/*
	*//**
		 * This method receives student ID from UI controller and sends it to server to
		 * get his/her name
		 * 
		 * @param studentID student'd id
		 */
	/*
	 * public void getStudentNameByID(String studentID) { StudentNameByIDRequest
	 * studentNameByIDRequest=new StudentNameByIDRequest(studentID);
	 * client.handleMessageFromClientUI(studentNameByIDRequest); }
	 * 
	 *//**
		 * This method receives student ID from UI controller and sends it to server to
		 * get membership UserType
		 * 
		 * @param studentID student's ID
		 */
	/*
	 * public void getStudentMemebershipStatusByID(String studentID) {
	 * StatusMembershipByIDRequest statusRequest =new
	 * StatusMembershipByIDRequest(studentID);
	 * client.handleMessageFromClientUI(statusRequest); }
	 *//**
		 * This method receives student ID and new membership UserType from UI controller
		 * and sends it to server to update
		 * 
		 * @param studentID student's ID
		 * @param newStatus student's new membrship UserType
		 *//*
			 * public void updateMembershipStatusByID(String studentID, MembershipStatus
			 * newStatus) { UpdateDBStatusMembershipRequest updateStatus=new
			 * UpdateDBStatusMembershipRequest(studentID, newStatus);
			 * client.handleMessageFromClientUI(updateStatus); }
			 */

	/**
	 * This method overrides the method in the ChatIF interface. It displays a
	 * message onto the screen.
	 *
	 * @param message The string to be displayed.
	 */
	public void display(String message) {
	}
	// ------------------------OLD CODE-----------------------------------
	/*
	 * public static void main(String[] args) { String host = ""; int port = 0; //
	 * The port number
	 * 
	 * try { host = args[0]; } catch (ArrayIndexOutOfBoundsException e) { host =
	 * "localhost"; } ClientConsole chat = new ClientConsole(host, DEFAULT_PORT);
	 * chat.accept(); // Wait for console data }
	 */
	

}
//End of ConsoleChat class
