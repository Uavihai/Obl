package ServerClientRequests;

import java.util.ArrayList;

import Entities.Subscriber;
import OCSF.server.ConnectionToClient;
import clientUIControllers.SearchASubscriber;
import server.OBLServer;

public class SubscriberManageRequest implements IBasicRequest {

	private ManageType requestType;
	private SearchBy searchBy;
	private ArrayList<Subscriber> subscribers;//could be as a request when updating, or as an answer when searching
	private int ID;
	private String firstName;
	private String lastName;
	private int status;
	private String errorMessage;
	
	@Override
	public void handleRequest(ConnectionToClient clientConnection) {
		try {
			switch(requestType)
			{
			case SEARCH:
			OBLServer.getInstance().handleSearchSubscriberRequest(this,clientConnection);
			break;
			case UPDATE:
				
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void updateUIPostAnswer() {
		try {
			switch(requestType)
			{
			case SEARCH:
				SearchASubscriber.getInstance().updateUiPostSubSearch(this);
			break;
			case UPDATE:
				
				break;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Constructor using fields
	 * @param requestType
	 * @param searchBy
	 * @param subscriber
	 * @param UserType
	 * @param errorMessage
	 */
	public SubscriberManageRequest(ManageType requestType, SearchBy searchBy, ArrayList<Subscriber> subscribers, int status,
			String errorMessage) {
		this.requestType = requestType;
		this.searchBy = searchBy;
		this.subscribers = subscribers;
		this.status = status;
		this.errorMessage = errorMessage;
	}
	

	/**
	 * Empty Constructor
	 */
	public SubscriberManageRequest() {
	}
	

	/**
	 * @return the requestType
	 */
	public ManageType getRequestType() {
		return requestType;
	}

	/**
	 * @param requestType the requestType to set
	 */
	public void setRequestType(ManageType requestType) {
		this.requestType = requestType;
	}

	/**
	 * @return the searchBy
	 */
	public SearchBy getSearchBy() {
		return searchBy;
	}

	/**
	 * @param searchBy the searchBy to set
	 */
	public void setSearchBy(SearchBy searchBy) {
		this.searchBy = searchBy;
	}

	/**
	 * @return the subscriber
	 */
	public ArrayList<Subscriber> getSubscribers() {
		return subscribers;
	}

	/**
	 * @param subscriber the subscriber to set
	 */
	public void setSubscribers(ArrayList<Subscriber> subscribers) {
		this.subscribers = subscribers;
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
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public enum ManageType{
		SEARCH,UPDATE;
	}
	public enum SearchBy{
		ID,FIRST_NAME,LAST_NAME;
	}
}
