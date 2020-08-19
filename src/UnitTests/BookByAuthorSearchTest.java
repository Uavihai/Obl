package UnitTests;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import DataBase.DataBaseController;
import ServerClientRequests.BookSearchRequest;
import ServerClientRequests.BookSearchRequest.SearchType;
import junit.framework.Assert;

public class BookByAuthorSearchTest {

	private BookSearchRequest bookSearchRequest;
	@Before
	public void setUp() throws Exception {
		DataBaseController.connectToDB("LibraryDatabase");
		bookSearchRequest=new BookSearchRequest();
	}

	@Test
	public void testBookSearchByAuthor() {
		bookSearchRequest.setBookAuthor("Leo Tolstoy");
		bookSearchRequest.setSearchType(SearchType.Author);
		BookSearchRequest request=DataBaseController.BookSearch(bookSearchRequest);
		System.out.println("By Author");
		System.out.println(request);
		assertNotNull(request);
	}
	@Test
	public void testBookSearchBySubject() {
		bookSearchRequest.setBookSubject("Drama");
		bookSearchRequest.setSearchType(SearchType.Subject);
		BookSearchRequest request=DataBaseController.BookSearch(bookSearchRequest);
		System.out.println("By Subject");
		System.out.println(request);
		assertNotNull(request);
	}
	@Test
	public void testBookSearchByTitle() {
		bookSearchRequest.setBookTitle("Harry Potter");
		bookSearchRequest.setSearchType(SearchType.Title);
		BookSearchRequest request=DataBaseController.BookSearch(bookSearchRequest);
		System.out.println("By Title");
		System.out.println(request);
		assertNotNull(request);
	}
	@Test
	public void testBookSearchByFreeText() {
		bookSearchRequest.setBookFreeText("Harry Potter");
		bookSearchRequest.setSearchType(SearchType.FreeText);
		BookSearchRequest request=DataBaseController.BookSearch(bookSearchRequest);
		System.out.println("By Free Text");
		System.out.println(request);
		assertNotNull(request);
	}
}
