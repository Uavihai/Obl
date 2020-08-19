package DB_Communicator;

import Entities.Subscriber;
import clientUIControllers.SearchABook;

public class Authenticate {
	private static Authenticate instance = null;
	
	public static enum UserType {
		NOT_SIGNED_IN,
		SUBSCRIBER,
		LIBRARIAN,
		MANAGER
	}
	
	private UserType loggedInAs;
	private Subscriber loggedInUser=null;
	private Authenticate () {
		this.loggedInAs = UserType.NOT_SIGNED_IN; 
	}
	
	public static Authenticate getInstance () {
		return instance == null ? instance = new Authenticate() : instance;
	}
	/**
	 * Method Logs the logged in user out
	 * @param searchABook TODO
	 */
	public void Logout(SearchABook searchABook) {
		loggedInAs=UserType.NOT_SIGNED_IN;
		loggedInUser=null;
		searchABook.getLoginBtn().setText("Login");
	}
	/**
	 * @return the loggedInAs
	 */
	public UserType getLoggedInAs() {
		return loggedInAs;
	}

	/**
	 * @param loggedInAs the loggedInAs to set
	 */
	public void setLoggedInAs(UserType signedInAs) {
		this.loggedInAs = signedInAs;
	}

	/**
	 * @return the loggedInUser
	 */
	public Subscriber getLoggedInUser() {
		return loggedInUser;
	}

	/**
	 * @param loggedInUser the loggedInUser to set
	 */
	public void setLoggedInUser(Subscriber loggedInUser) {
		this.loggedInUser = loggedInUser;
	}
	
}
