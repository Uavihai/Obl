package Entities;

import java.io.Serializable;
import java.util.ArrayList;

import DB_Communicator.Authenticate;
import clientUIControllers.Utils;

public class Subscriber implements Serializable{
	private String firstName;
	private String lastName;
	private int cardID;
	private int studentID;
	private int cellphone;
	private String email;
	private MembershipStatus status;
	private Authenticate.UserType type;
	private int numberOfLateBooks;
	private ArrayList<LendingHistoryRow> lendingHistory;
	
	/**
	 * Constructor using fields
	 * @param firstName
	 * @param lastName
	 * @param cardID
	 * @param studentID
	 * @param cellphone
	 * @param email
	 * @param UserType
	 * @param type
	 * @param numberOfLateBooks
	 */
	public Subscriber(String firstName, String lastName, int studentID, int cellphone,
			String email, MembershipStatus status, DB_Communicator.Authenticate.UserType type, int numberOfLateBooks) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.studentID = studentID;
		this.cellphone = cellphone;
		this.email = email;
		this.status = status;
		this.type = type;
		this.numberOfLateBooks = numberOfLateBooks;
	}

	/**
	 * Empty constructor
	 */
	public Subscriber() {
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

	/**
	 * @return the cardID
	 */
	public int getCardID() {
		return cardID;
	}

	/**
	 * @param cardID the cardID to set
	 */
	public void setCardID(int cardID) {
		this.cardID = cardID;
	}

	/**
	 * @return the studentID
	 */
	public int getStudentID() {
		return studentID;
	}

	/**
	 * @param studentID the studentID to set
	 */
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	/**
	 * @return the cellphone
	 */
	public int getCellphone() {
		return cellphone;
	}

	/**
	 * @param cellphone the cellphone to set
	 */
	public void setCellphone(int cellphone) {
		this.cellphone = cellphone;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the UserType
	 */
	public MembershipStatus getStatus() {
		return status;
	}

	/**
	 * @param UserType the UserType to set
	 */
	public void setStatus(MembershipStatus status) {
		this.status = status;
	}

	/**
	 * @return the type
	 */
	public Authenticate.UserType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Authenticate.UserType type) {
		this.type = type;
	}

	/**
	 * @return the numberOfLateBooks
	 */
	public int getNumberOfLateBooks() {
		return numberOfLateBooks;
	}

	/**
	 * @param numberOfLateBooks the numberOfLateBooks to set
	 */
	public void setNumberOfLateBooks(int numberOfLateBooks) {
		this.numberOfLateBooks = numberOfLateBooks;
	}
	

	/**
	 * @return the lendingHistory
	 */
	public ArrayList<LendingHistoryRow> getLendingHistory() {
		return lendingHistory;
	}

	/**
	 * @param lendingHistory the lendingHistory to set
	 */
	public void setLendingHistory(ArrayList<LendingHistoryRow> lendingHistory) {
		this.lendingHistory = lendingHistory;
	}

	public String toString1 () {
		return Utils.join(new String[] {this.firstName, this.lastName, ""+this.studentID}, ("" + Utils.SEPARATOR));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Subscriber [firstName=" + firstName + ", lastName=" + lastName + ", cardID=" + cardID + ", studentID="
				+ studentID + ", cellphone=" + cellphone + ", email=" + email + ", status=" + status + ", type=" + type
				+ ", numberOfLateBooks=" + numberOfLateBooks + ", lendingHistory=" + lendingHistory + "]";
	}
	
	
}
