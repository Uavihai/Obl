package ServerClientRequests;

import DB_Communicator.Authenticate;
import DB_Communicator.Communicate;
import Entities.Subscriber;
import OCSF.server.ConnectionToClient;
import server.OBLServer;

public class LoginRequest implements IBasicRequest {

	private String userID;
	private String password;
	private Authenticate.UserType loginAs;
	private boolean isSuccessful;
	private Subscriber user;
	private String errorMessage;
	private int status;
	
	
	@Override
	public void handleRequest(ConnectionToClient clientConnection) {
		try {
			OBLServer.getInstance().handleLoginRequest(this, clientConnection);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void updateUIPostAnswer() {
		try {
			Communicate.getInstance().openWindowAfterLogin(this);
			//Login.getInstance().openAppropriateWindow(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor using fields
	 * @param userID
	 * @param password
	 */
	public LoginRequest(String username, String password) {
		this.userID = username;
		this.password = password;
	}

	/**
	 * Empty constructor
	 */
	public LoginRequest() {
	}

	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(String username) {
		this.userID = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the loginAs
	 */
	public Authenticate.UserType getLoginAs() {
		return loginAs;
	}

	/**
	 * @param loginAs the loginAs to set
	 */
	public void setLoginAs(Authenticate.UserType loginAs) {
		this.loginAs = loginAs;
	}

	/**
	 * @return the isSuccessful
	 */
	public boolean isSuccessful() {
		return isSuccessful;
	}

	/**
	 * @param isSuccessful the isSuccessful to set
	 */
	public void setSuccessful(boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
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
	 * @return the UserType
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param UserType the UserType to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}
	
	/**
	 * @return the user
	 */
	public Subscriber getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(Subscriber user) {
		this.user = user;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LoginRequest [userID=" + userID + ", password=" + password + ", loginAs=" + loginAs
				+ ", isSuccessful=" + isSuccessful + ", errorMessage=" + errorMessage + ", UserType=" + status + "]";
	}
	
}
