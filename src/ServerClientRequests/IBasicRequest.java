package ServerClientRequests;

import java.io.Serializable;

import OCSF.server.ConnectionToClient;


public abstract interface IBasicRequest extends Serializable{

	/**
	 * Method summons OBL Server's method when was sent Client -> Server
	 * @param clientConnection connection to the client it was sent from
	 */
	public abstract void handleRequest(ConnectionToClient clientConnection);
	
	/**
	 * Method summons a UI-updating method in UI Controller when sent Server -> Client 
	 */
	public abstract void updateUIPostAnswer();
}
