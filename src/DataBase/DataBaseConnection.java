package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import OBLControllers.OBLServerGUIController;

public class DataBaseConnection {

	private static DataBaseConnection instance;
	private Connection conn;
	//private static String dbName;

	/**
	 * Constructor that connects to the database librarydatabase or if another name comes as an argument
	 */
	private DataBaseConnection(String dbName) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception ex) {
			/* handle the error */}
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/"+dbName+"?allowMultiQueries=true", "root", "Aa123456");
			// Connection conn =
			// DriverManager.getConnection("jdbc:mysql://192.168.3.68/test","root","Root");
			//OBLServerGUIController.getUiControllerInstance().addLineToTextArea("SQL connection succeed");
			// createTableCourses(conn);
		} catch (SQLException ex) {/* handle any errors */
			try {
				OBLServerGUIController.getUiControllerInstance().addLineToTextArea("SQLException: " + ex.getMessage());
				OBLServerGUIController.getUiControllerInstance().addLineToTextArea("SQLState: " + ex.getSQLState());
				OBLServerGUIController.getUiControllerInstance().addLineToTextArea("VendorError: " + ex.getErrorCode());
			} catch (Exception e) {
			}
		}
	}
	/**
	 * A static method returns a singleton instance of this class
	 * @return DataBaseConnection singleton instance of this class
	 */
	public static DataBaseConnection getInstance(String dbName) {
		if (instance == null)
		{
			//DataBaseConnection.dbName=dbName;
			instance = new DataBaseConnection(dbName);
		}
		return instance;
	}
	/**
	 * Getter returns the Connection object
	 * @return Connection object holding the connection to the database
	 */
	public Connection getConnection() {
		return conn;
	}
	/**
	 * @return the dbName
	 */
	/*
	public String getDbName() {
		return dbName;
	}
	*/
	/**
	 * @param dbName the dbName to set
	 */
	/*
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}*/

	//------------------------OLD CODE-----------------------------------
	/**
	 * This method connects to the DataBase.
	 */
	/*
	 * public static Connection connectToDB(String password) { Connection conn =
	 * null; try { Class.forName("com.mysql.jdbc.Driver"); } catch (Exception ex) {
	 * // handle the error }
	 * 
	 * 
	 * try { try { conn =
	 * DriverManager.getConnection("jdbc:mysql://localhost/librarydatabase", "root",
	 * password); } catch (NullPointerException ex) { conn =
	 * DriverManager.getConnection("jdbc:mysql://localhost/librarydatabase", "root",
	 * "root"); } // Connection conn = //
	 * DriverManager.getConnection("jdbc:mysql://192.168.3.68/test","root","Root");
	 * System.out.println("SQL connection succeed"); // createTableCourses(conn); }
	 * catch (SQLException ex) {// handle any errors
	 * System.out.println("SQLException: " + ex.getMessage());
	 * System.out.println("SQLState: " + ex.getSQLState());
	 * System.out.println("VendorError: " + ex.getErrorCode()); } return conn; }
	 */
}
