// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import OCSF.client.*;
import ServerClientRequests.IBasicRequest;

import java.io.*;
import java.util.ArrayList;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class OBLClient extends AbstractClient {
	// Instance variables **********************************************

	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	private ClientInterface clientUI;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 * 
	 * @param host     The server to connect to.
	 * @param port     The port number to connect on.
	 * @param clientUI The interface type variable.
	 */

	public OBLClient(String host, int port, ClientInterface clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
		openConnection();
	}

	// Instance methods ************************************************

	/**
	 * This method handles all data that comes in from the server.
	 * 
	 * @param msg The message from the server.
	 */
	public void handleMessageFromServer(Object msg) {
		((IBasicRequest) msg).updateUIPostAnswer();
	}

	/**
	 * This method handles all data coming from the UI
	 * 
	 * @param message The message from the UI.
	 */
	public void handleMessageFromClientUI(Object objectToSend) {
		try {
			sendToServer(objectToSend);
		} catch (IOException e) {
			clientUI.display("Could not send message to server.  Terminating client.");
			System.out.println(e);
			quit();
		}
	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
//End of ChatClient class
