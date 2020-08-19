package Entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;

public class Book implements Serializable{

	private String title;
	private ArrayList<String> authors;
	private ArrayList<String> subjects;
	private String shelfPosition;
	private int editionNumber;
	private Date printDate;
	private Date purchaseDate;
	private int catalogueNumber;
	private int numberOfCopies;
	private boolean isPopular;
	private String summery;
	private byte[] contentTable;
	
	
	/**
	 * @param title
	 * @param authors
	 * @param subject
	 * @param shelfPosition
	 * @param editionNumber
	 * @param printDate
	 * @param purchaseDate
	 * @param catalogueNumber
	 * @param numberOfCopies
	 * @param isPopular
	 * @param summery
	 * @param contentTable
	 */
	public Book(String title, ArrayList<String> writers, ArrayList<String> subjects, String shelfPosition,
			int editionNumber, Date printDate, Date purchaseDate, int catalogueNumber, int numberOfCopies,
			boolean isPopular, String summery, byte[] contentTable) {
		this.title = title;
		this.authors = writers;
		this.subjects = subjects;
		this.shelfPosition = shelfPosition;
		this.editionNumber = editionNumber;
		this.printDate = printDate;
		this.purchaseDate = purchaseDate;
		this.catalogueNumber = catalogueNumber;
		this.numberOfCopies = numberOfCopies;
		this.isPopular = isPopular;
		this.summery = summery;
		this.contentTable = contentTable;
	}

	/**
	 * Empty constructor
	 */
	public Book() {
		authors=new ArrayList<>();
		subjects=new ArrayList<>();
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the authors
	 */
	public ArrayList<String> getAuthors() {
		return authors;
	}
	/**
	 * @param authors the authors to set
	 */
	public void setAuthors(ArrayList<String> writers) {
		this.authors = writers;
	}
	/**
	 * @return the subject
	 */
	public ArrayList<String> getSubjects() {
		return subjects;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubjects(ArrayList<String> subjects) {
		this.subjects = subjects;
	}
	/**
	 * @return the shelfPosition
	 */
	public String getShelfPosition() {
		return shelfPosition;
	}
	/**
	 * @param shelfPosition the shelfPosition to set
	 */
	public void setShelfPosition(String shelfPosition) {
		this.shelfPosition = shelfPosition;
	}
	/**
	 * @return the editionNumber
	 */
	public int getEditionNumber() {
		return editionNumber;
	}
	/**
	 * @param editionNumber the editionNumber to set
	 */
	public void setEditionNumber(int editionNumber) {
		this.editionNumber = editionNumber;
	}
	/**
	 * @return the printDate
	 */
	public Date getPrintDate() {
		return printDate;
	}
	/**
	 * @param printDate the printDate to set
	 */
	public void setPrintDate(Date printDate) {
		this.printDate = printDate;
	}
	/**
	 * @return the purchaseDate
	 */
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	/**
	 * @param purchaseDate the purchaseDate to set
	 */
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	/**
	 * @return the catalogueNumber
	 */
	public int getCatalogueNumber() {
		return catalogueNumber;
	}
	/**
	 * @param catalogueNumber the catalogueNumber to set
	 */
	public void setCatalogueNumber(int catalogueNumber) {
		this.catalogueNumber = catalogueNumber;
	}
	/**
	 * @return the numberOfCopies
	 */
	public int getNumberOfCopies() {
		return numberOfCopies;
	}
	/**
	 * @param numberOfCopies the numberOfCopies to set
	 */
	public void setNumberOfCopies(int numberOfCopies) {
		this.numberOfCopies = numberOfCopies;
	}
	/**
	 * @return the summery
	 */
	public String getSummery() {
		return summery;
	}
	/**
	 * @param summery the summery to set
	 */
	public void setSummery(String summery) {
		this.summery = summery;
	}
	/**
	 * @return the contentTable
	 */
	public byte[] getContentTable() {
		return contentTable;
	}
	/**
	 * @param contentTable the contentTable to set
	 */
	public void setContentTable(byte[] contentTable) {
		this.contentTable = contentTable;
	}
	
	/**
	 * @return the isPopular
	 */
	public boolean isPopular() {
		return isPopular;
	}

	/**
	 * @param isPopular the isPopular to set
	 */
	public void setPopular(boolean isPopular) {
		this.isPopular = isPopular;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Book [title=" + title + ", authors=" + authors + ", subject=" + subjects + ", shelfPosition="
				+ shelfPosition + ", editionNumber=" + editionNumber + ", printDate=" + printDate + ", purchaseDate="
				+ purchaseDate + ", catalogueNumber=" + catalogueNumber + ", numberOfCopies=" + numberOfCopies
				+ ", summery=" + summery + ", contentTable=" + Arrays.toString(contentTable) + "]\n";
	}
	
}
