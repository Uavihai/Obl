package Legacy;

import Entities.MembershipStatus;
import OCSF.server.ConnectionToClient;
import ServerClientRequests.IBasicRequest;
import server.OBLServer;

public class StatusMembershipByIDRequest implements IBasicRequest {

	private String studentIDRequest;
	private MembershipStatus membershipStatusAnswer;

	/**
	 * Empty constructor
	 */
	public StatusMembershipByIDRequest() {
	}

	/**
	 * Constructor for requesting from server
	 * @param studentIDRequest student's ID required to find his/her membership
	 * status
	 */
	public StatusMembershipByIDRequest(String studentIDRequest) {
		this(studentIDRequest, null, 0, "");
	}
	/**
	 * Full constructor for reply from the server
	 * @param studentIDRequest student's ID
	 * @param membershipStatusAnswer student's status membership
	 * @param status reply status 1 if error, 0 otherwise
	 * @param errorMessage error message if there was an error
	 */
	public StatusMembershipByIDRequest(String studentIDRequest, MembershipStatus membershipStatusAnswer, int status,
			String errorMessage) {
		super(status, errorMessage);
		this.studentIDRequest = studentIDRequest;
		this.membershipStatusAnswer = membershipStatusAnswer;
	}
	/**
	 * @see IBasicRequest
	 */
	@Override
	public void handleRequest(ConnectionToClient clientConnection) {
		try {
			OBLServer.getInstance().proccessStudentStatusMembershipByIDRequest(this, clientConnection);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * @see IBasicRequest
	 */
	@Override
	public void updateUIPostAnswer() {
		try {
			OBLPrototypeGUIController.getUiControllerInstance().updateUIStudentMembershipByID(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

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
	 * @return the membershipStatusAnswer
	 */
	public MembershipStatus getMembershipStatusAnswer() {
		return membershipStatusAnswer;
	}

	/**
	 * @param membershipStatusAnswer the membershipStatusAnswer to set
	 */
	public void setMembershipStatusAnswer(MembershipStatus membershipStatusAnswer) {
		this.membershipStatusAnswer = membershipStatusAnswer;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StatusMembershipByIDRequest [studentIDRequest=" + studentIDRequest + ", membershipStatusAnswer="
				+ membershipStatusAnswer + "]";
	}

}
