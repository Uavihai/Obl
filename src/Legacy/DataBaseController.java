package Legacy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Entities.MembershipStatus;
import Legacy.StatusMembershipByIDRequest;
import Legacy.StudentNameByIDRequest;
import Legacy.UpdateDBStatusMembershipRequest;

public class DataBaseController {

	private static Connection conn = null;

	

	/**
	 * Method creates a singleton of DataBaseConnection class and connects to the Database
	 * 
	 */
	public static void connectToDB(String dbName) {
		DataBaseConnection dbconn = DataBaseConnection.getInstance(dbName);
		conn = dbconn.getConnection();
	}
	
	/**
	 * Method issues an sql query to the database to get student's name by his ID
	 * @param clientRequest request object from the client containing only student's ID
	 * @return  answer object to the client containing student's ID and name if exists or error otherwise
	 */
	/*
	public static StudentNameByIDRequest getStudentNameByID(StudentNameByIDRequest clientRequest) {
		StudentNameByIDRequest answer = new StudentNameByIDRequest();
		ResultSet result;
		try {
			PreparedStatement preparedStatement = conn
					.prepareStatement("" + "SELECT StudentName FROM student WHERE StudentID = ?");
			preparedStatement.setString(1, clientRequest.getStudentID());
			result = preparedStatement.executeQuery();
			if (result.next()) {
				answer.setStudentID(clientRequest.getStudentID());
				answer.setStudentName(result.getString("StudentName"));
				answer.setErrorMessage("");
				answer.setStatus(0);
			} else {
				answer.setErrorMessage("Student ID not found!\nPlease try again.");
				answer.setStatus(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return answer;
	}

*/
	/**
	 * Method issues a query to the database to get student's status membership by his ID
	 * @param clientRequest request object from the client containing only student's ID
	 * @return  answer object to the client containing student's membership status and ID if exists, error message otherwise
	 */
	/*
	public static StatusMembershipByIDRequest getStudentMembershipStatusByID(
			StatusMembershipByIDRequest clientRequest) {
		StatusMembershipByIDRequest answer = new StatusMembershipByIDRequest();
		ResultSet result;
		try {
			PreparedStatement preparedStatement = conn
					.prepareStatement("" + "SELECT StatusMembership FROM student WHERE StudentID = ?");
			preparedStatement.setString(1, clientRequest.getStudentIDRequest());
			result = preparedStatement.executeQuery();
			if (result.next()) {
				answer.setStudentIDRequest(clientRequest.getStudentIDRequest());
				answer.setMembershipStatusAnswer(MembershipStatus.valueOf(result.getString("StatusMembership")));
				answer.setErrorMessage("");
				answer.setStatus(0);
			} else {
				answer.setErrorMessage("Student ID not found!\nPlease try again.");
				answer.setStatus(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return answer;
	}
	*/
	/**
	 * Method issues an sql query to the database to update student's status membership by his ID
	 * @param clientRequest request object from the client containing only student's ID and the new membership status
	 * @return answer object to the client containing student's membership status and ID if exists, error message otherwise
	 */
	/*public static UpdateDBStatusMembershipRequest updateMemeStudentMembershipStatusByID(
			UpdateDBStatusMembershipRequest clientRequest) {
		UpdateDBStatusMembershipRequest answer=new UpdateDBStatusMembershipRequest(clientRequest.getStudentIDRequest(),clientRequest.getNewStatus());
		try {
			PreparedStatement preparedStatement = conn
					.prepareStatement("" + "UPDATE student SET StatusMembership = ? WHERE StudentID = ?");
			preparedStatement.setString(1, clientRequest.getNewStatus().name());
			preparedStatement.setString(2, clientRequest.getStudentIDRequest());
			if(preparedStatement.executeUpdate()==0)
			{
				answer.setStatus(1);
				answer.setErrorMessage("Student ID not found!\nPlease try again.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return answer;
	}*/
	
	//------------------------OLD CODE-----------------------------------
	/**
	 * This method connects to the DataBase.
	 */
	/*
	 * public static void connectToDB() { try {
	 * Class.forName("com.mysql.jdbc.Driver").newInstance(); } catch (Exception ex)
	 * { //handle any errors } try { conn =
	 * DriverManager.getConnection("jdbc:mysql://localhost/librarydatabase", "root",
	 * "root"); // Connection conn = //
	 * DriverManager.getConnection("jdbc:mysql://192.168.3.68/test","root","Root");
	 * System.out.println("SQL connection succeed"); // createTableCourses(conn); }
	 * catch (SQLException ex) { //handle any errors
	 * System.out.println("SQLException: " + ex.getMessage());
	 * System.out.println("SQLState: " + ex.getSQLState());
	 * System.out.println("VendorError: " + ex.getErrorCode()); } }
	 */
	/*
	 * public static ArrayList<Student> parsingTheData(ArrayList<String>
	 * rawUserData) { ArrayList<Student> students=new ArrayList<>();
	 * System.out.println(rawUserData); for(int i=0;i<rawUserData.size();i+=2) {
	 * students.add(new
	 * Student(rawUserData.get(i),Integer.parseInt(rawUserData.get(i+1)))); }
	 * System.out.println(students); return students; }
	 */
	/*
	 * public static void saveUserToDB(ArrayList<Student> studentsList) {
	 * for(Student student:studentsList) { try { PreparedStatement
	 * preparedStatement=conn.prepareStatement(""+
	 * "INSERT INTO student (StudentID, StudentName, StatusMembership, Operation, Freeze) VALUES(?,?,?,?,?)"
	 * ); preparedStatement.setInt(1, student.getID());
	 * preparedStatement.setString(2,student.getName());
	 * preparedStatement.setString(3,"Active"); preparedStatement.setInt(4,111);
	 * preparedStatement.setBoolean(5,false); preparedStatement.execute(); } catch
	 * (SQLException e) { // TODO Auto-generated catch block e.printStackTrace(); }
	 * } }
	 */
}
