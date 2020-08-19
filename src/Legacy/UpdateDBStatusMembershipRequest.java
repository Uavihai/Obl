/**
 * 
 */
package Legacy;

import Entities.MembershipStatus;
import OCSF.server.ConnectionToClient;
import ServerClientRequests.IBasicRequest;
import server.OBLServer;

/**
 * @author Rostik
 *
 */
public class UpdateDBStatusMembershipRequest extends IBasicRequest {

	private String studentIDRequest;
	private MembershipStatus newStatus;
	/**
	 * Empty Constructor
	 */
	public UpdateDBStatusMembershipRequest() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param status
	 * @param errorMessage
	 */
	public UpdateDBStatusMembershipRequest(int status, String errorMessage) {
		super(status, errorMessage);
		// TODO Auto-generated constructor stub
	}

	public UpdateDBStatusMembershipRequest(String studentIDRequest, MembershipStatus newStatus) {
		super(0,"");
		this.studentIDRequest = studentIDRequest;
		this.newStatus = newStatus;
	}

	public UpdateDBStatusMembershipRequest(String studentIDRequest,
			MembershipStatus newStatus,int status, String errorMessage) {
		super(status, errorMessage);
		this.studentIDRequest = studentIDRequest;
		this.newStatus = newStatus;
	}


	
	/**
	 * Method summons OBL Server's method when was sent Client -> Server
	 * @param clientConnection connection to the client it was sent from
	 * @see ServerClientRequests.IBasicRequest#handleRequest(OCSF.server.ConnectionToClient)
	 */
	@Override
	public void handleRequest(ConnectionToClient clientConnection) {
		try {
			OBLServer.getInstance().proccessUpdateStudentStatusMembershipByIDRequest(this, clientConnection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Method summons a UI-updating method in UI Controller when sent Server -> Client 
	 * @see ServerClientRequests.IBasicRequest#updateUIPostAnswer()
	 */
	@Override
	public void updateUIPostAnswer() {
		OBLPrototypeGUIController.getUiControllerInstance().postUpdatingDBMembershipByID(this);
		
	}

	/**
	 * @return the studentIDRequest
	 */
	public String getStudentIDRequest() {
		return studentIDRequest;
	}

	/**
	 * @param studentIDRequest the studentIDRequest to set
	 */
	public void setStudentIDRequest(String studentIDRequest) {
		this.studentIDRequest = studentIDRequest;
	}

	/**
	 * @return the newStatus
	 */
	public MembershipStatus getNewStatus() {
		return newStatus;
	}

	/**
	 * @param newStatus the newStatus to set
	 */
	public void setNewStatus(MembershipStatus newStatus) {
		this.newStatus = newStatus;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UpdateDBStatusMembershipRequest [studentIDRequest=" + studentIDRequest + ", newStatus=" + newStatus
				+ "]";
	}
	
}
