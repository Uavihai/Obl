package Legacy;

import java.io.IOException;

import OCSF.server.ConnectionToClient;
import ServerClientRequests.IBasicRequest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import server.OBLServer;

/*
 * Class made for to receive student's name from the server by student's ID
 * when sent from client to server field "studentName" is empty;
 * status field is 0 when data exists in the database and 1 otherwise
 * errorMessage will have the error message when there is an error
 */
public class StudentNameByIDRequest extends IBasicRequest {

	private String studentName;
	private String studentIDRequested;

	public StudentNameByIDRequest() {
		super();
	}
	/**
	 * Full constructor for reply from the server
	 * @param studentName student's name
	 * @param studentID student's ID
	 * @param status reply status 1 if error, 0 otherwise
	 * @param errorMessage error message if there was an error
	 */
	public StudentNameByIDRequest(String studentName, String studentID, int status, String errorMessage) {
		super(status, errorMessage);
		this.studentName = studentName;
		this.studentIDRequested = studentID;
	}

	/**
	 * Method summons OBL Server's method when was sent Client -> Server
	 * @param clientConnection connection to the client it was sent from
	 * @see ServerClientRequests.IBasicRequest#handleRequest(OCSF.server.ConnectionToClient)
	 */
	@Override
	public void handleRequest(ConnectionToClient clientConnection) {
		try {
			OBLServer.getInstance().proccessStudentNameByIDRequest(this, clientConnection);
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
		try {
			OBLPrototypeGUIController.getUiControllerInstance().updateUIStudentNameByID(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * Constructor for request from server
	 * @param studentID student's ID
	 */
	public StudentNameByIDRequest(String studentID) {
		this("", studentID, 0, "");
	}

	/**
	 * get for student's name
	 * @return student's name
	 */
	public String getStudentName() {
		return studentName;
	}

	/**
	 * set for student's name
	 * @param studentName
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	/**
	 * get for student's name
	 * @return
	 */
	public String getStudentID() {
		return studentIDRequested;
	}
	/**
	 * set for student's id
	 * @param studentID
	 */
	public void setStudentID(String studentID) {
		this.studentIDRequested = studentID;
	}

	@Override
	public String toString() {
		return "StudentNameByIDRequest [studentName=" + studentName + ", studentID=" + studentIDRequested + "]";
	}

}
