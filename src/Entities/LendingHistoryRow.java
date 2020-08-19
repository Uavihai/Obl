package Entities;

import java.io.Serializable;
import java.sql.Date;

public class LendingHistoryRow implements Serializable {

	private int userID;
	private Book book;
	private Date LendingDate;
	private Date expectedReturnDate;
	private Date actualReturnDate;
	private boolean hasReturned;
	private boolean isLate;
	private boolean isLost;
	private boolean isExtended;
	private Date newReturnDate;
	private int librarianExtendID;
	private String description;
	
	
	/**
	 * Constructor using fields
	 * @param userID
	 * @param book
	 * @param lendingDate
	 * @param expectedReturnDate
	 * @param actualReturnDate
	 * @param hasReturned
	 * @param isLate
	 * @param isLost
	 * @param isExtended
	 * @param newReturnDate
	 * @param librarianExtendID
	 * @param description
	 */
	public LendingHistoryRow(int userID, Book book, Date lendingDate, Date expectedReturnDate, Date actualReturnDate,
			boolean hasReturned, boolean isLate, boolean isLost, boolean isExtended, Date newReturnDate,
			int librarianExtendID, String description) {
		this.userID = userID;
		this.book = book;
		LendingDate = lendingDate;
		this.expectedReturnDate = expectedReturnDate;
		this.actualReturnDate = actualReturnDate;
		this.hasReturned = hasReturned;
		this.isLate = isLate;
		this.isLost = isLost;
		this.isExtended = isExtended;
		this.newReturnDate = newReturnDate;
		this.librarianExtendID = librarianExtendID;
		this.description = description;
	}
	/**
	 * Empty Constructor
	 */
	public LendingHistoryRow() {
	}
	/**
	 * @return the lendingDate
	 */
	public Date getLendingDate() {
		return LendingDate;
	}
	/**
	 * @param lendingDate the lendingDate to set
	 */
	public void setLendingDate(Date lendingDate) {
		LendingDate = lendingDate;
	}
	/**
	 * @return the expectedReturnDate
	 */
	public Date getExpectedReturnDate() {
		return expectedReturnDate;
	}
	/**
	 * @param expectedReturnDate the expectedReturnDate to set
	 */
	public void setExpectedReturnDate(Date expectedReturnDate) {
		this.expectedReturnDate = expectedReturnDate;
	}
	/**
	 * @return the actualReturnDate
	 */
	public Date getActualReturnDate() {
		return actualReturnDate;
	}
	/**
	 * @param actualReturnDate the actualReturnDate to set
	 */
	public void setActualReturnDate(Date actualReturnDate) {
		this.actualReturnDate = actualReturnDate;
	}
	/**
	 * @return the hasReturned
	 */
	public boolean isHasReturned() {
		return hasReturned;
	}
	/**
	 * @param hasReturned the hasReturned to set
	 */
	public void setHasReturned(boolean hasReturned) {
		this.hasReturned = hasReturned;
	}
	/**
	 * @return the isLate
	 */
	public boolean isLate() {
		return isLate;
	}
	/**
	 * @param isLate the isLate to set
	 */
	public void setLate(boolean isLate) {
		this.isLate = isLate;
	}
	/**
	 * @return the isLost
	 */
	public boolean isLost() {
		return isLost;
	}
	/**
	 * @param isLost the isLost to set
	 */
	public void setLost(boolean isLost) {
		this.isLost = isLost;
	}
	/**
	 * @return the isExtended
	 */
	public boolean isExtended() {
		return isExtended;
	}
	/**
	 * @param isExtended the isExtended to set
	 */
	public void setExtended(boolean isExtended) {
		this.isExtended = isExtended;
	}
	/**
	 * @return the newReturnDate
	 */
	public Date getNewReturnDate() {
		return newReturnDate;
	}
	/**
	 * @param newReturnDate the newReturnDate to set
	 */
	public void setNewReturnDate(Date newReturnDate) {
		this.newReturnDate = newReturnDate;
	}
	/**
	 * @return the librarianExtendID
	 */
	public int getLibrarianExtendID() {
		return librarianExtendID;
	}
	/**
	 * @param librarianExtendID the librarianExtendID to set
	 */
	public void setLibrarianExtendID(int librarianExtendID) {
		this.librarianExtendID = librarianExtendID;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}
	/**
	 * @param userID the userID to set
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
	/**
	 * @return the book
	 */
	public Book getBook() {
		return book;
	}
	/**
	 * @param book the book to set
	 */
	public void setBook(Book book) {
		this.book = book;
	}
	/**
	 * @param String name of the book
	 */
	public String getBookName() {
		return book.getTitle();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LendingHistoryRow [userID=" + userID + ", book=" + book + ", LendingDate=" + LendingDate
				+ ", expectedReturnDate=" + expectedReturnDate + ", actualReturnDate=" + actualReturnDate
				+ ", hasReturned=" + hasReturned + ", isLate=" + isLate + ", isLost=" + isLost + ", isExtended="
				+ isExtended + ", newReturnDate=" + newReturnDate + ", librarianExtendID=" + librarianExtendID
				+ ", description=" + description + "]";
	}
	
	
	
}
