package ServerClientRequests;

import java.util.ArrayList;

import DB_Communicator.Communicate;
import Entities.Book;
import OCSF.server.ConnectionToClient;
import clientUIControllers.SearchABook;
import server.OBLServer;

public class BookSearchRequest implements IBasicRequest{

	private String bookTitle;
	private String bookAuthor;
	private String bookSubject;
	private String bookFreeText;
	private SearchType searchType;
	private ArrayList<Book> bookListResult;
	private String errorMessage;
	private int status;
	@Override
	public void handleRequest(ConnectionToClient clientConnection) {
		try {
			OBLServer.getInstance().handleSearchBookRequest(this,clientConnection);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateUIPostAnswer() {
		try {
			Communicate.getInstance().updateUIPostBookSearch(this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Constructor using fields
	 * @param bookTitle
	 * @param bookAuthor
	 * @param bookSubject
	 * @param bookFreeText
	 * @param bookListResult
	 * @param errorMessage
	 * @param UserType
	 */
	public BookSearchRequest(String bookName, String bookWriter, String bookSubject, String bookFreeText,
			SearchType searchType, ArrayList<Book> bookListResult, String errorMessage, int status) {
		this.bookTitle = bookName;
		this.bookAuthor = bookWriter;
		this.bookSubject = bookSubject;
		this.bookFreeText = bookFreeText;
		this.searchType = searchType;
		this.bookListResult = bookListResult;
		this.errorMessage = errorMessage;
		this.status = status;
	}

	/**
	 * Empty Constructor
	 */
	public BookSearchRequest() {
		bookListResult=new ArrayList<>();
	}

	/**
	 * @return the bookTitle
	 */
	public String getBookTitle() {
		return bookTitle;
	}

	/**
	 * @param bookTitle the bookTitle to set
	 */
	public void setBookTitle(String bookName) {
		this.bookTitle = bookName;
	}

	/**
	 * @return the bookAuthor
	 */
	public String getBookAuthor() {
		return bookAuthor;
	}

	/**
	 * @param bookAuthor the bookAuthor to set
	 */
	public void setBookAuthor(String bookWriter) {
		this.bookAuthor = bookWriter;
	}

	/**
	 * @return the bookSubject
	 */
	public String getBookSubject() {
		return bookSubject;
	}

	/**
	 * @param bookSubject the bookSubject to set
	 */
	public void setBookSubject(String bookSubject) {
		this.bookSubject = bookSubject;
	}

	/**
	 * @return the bookFreeText
	 */
	public String getBookFreeText() {
		return bookFreeText;
	}

	/**
	 * @param bookFreeText the bookFreeText to set
	 */
	public void setBookFreeText(String bookFreeText) {
		this.bookFreeText = bookFreeText;
	}

	/**
	 * @return the bookListResult
	 */
	public ArrayList<Book> getBookListResult() {
		return bookListResult;
	}

	/**
	 * @param bookListResult the bookListResult to set
	 */
	public void setBookListResult(ArrayList<Book> bookListResult) {
		this.bookListResult = bookListResult;
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
	 * @return the searchType
	 */
	public SearchType getSearchType() {
		return searchType;
	}

	/**
	 * @param searchType the searchType to set
	 */
	public void setSearchType(SearchType searchType) {
		this.searchType = searchType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BookSearchRequest [bookTitle=" + bookTitle + ", bookAuthor=" + bookAuthor + ", bookSubject=" + bookSubject
				+ ", bookFreeText=" + bookFreeText + ", searchType=" + searchType + ", bookListResult=" + bookListResult
				+ ", errorMessage=" + errorMessage + ", UserType=" + status + "]";
	}

	public enum SearchType{
		Title,Author,Subject,FreeText;
	}
}
