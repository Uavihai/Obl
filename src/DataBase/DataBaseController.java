package DataBase;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Arrays;

import javax.annotation.Resource.AuthenticationType;

import DB_Communicator.Authenticate;
import Entities.Book;
import Entities.LendingHistoryRow;
import Entities.MembershipStatus;
import Entities.Subscriber;
import ServerClientRequests.BookSearchRequest;
import ServerClientRequests.Lend_ReturnABookRequest;
import ServerClientRequests.Lend_ReturnABookRequest.Type;
import ServerClientRequests.LoginRequest;
import ServerClientRequests.SubscriberManageRequest;
import ServerClientRequests.SubscriberManageRequest.ManageType;
import clientUIControllers.LendABook;

public class DataBaseController {

	private static Connection conn = null;
	private static String searchByAuthorQuery = "SELECT b.*, GROUP_CONCAT(DISTINCT ba.name) Author,GROUP_CONCAT(DISTINCT bs.name) Subject from book b,book_author ba,book_has_author bha, book_has_subject bhs, book_subject bs,\r\n"
			+ "(SELECT b.Catalogue_number as catNum from book b\r\n" + "    join\r\n"
			+ "    (book_author ba,book_has_author bha)\r\n" + "        ON\r\n"
			+ "      (ba.id = bha.author_id AND bha.Book_Catalogue_number=b.catalogue_number) WHERE MATCH(ba.name) AGAINST(?))  as foundCatNums\r\n"
			+ "     where foundCatNums.catNum=b.Catalogue_number and foundCatNums.catNum=bha.book_catalogue_number and ba.id=bha.author_id and foundCatNums.catNum=bhs.book_catalogue_number and bs.id=bhs.subject_id group by b.catalogue_number";
	// copied from MySQL workbench (works there)
	/*
	 * SELECT b.*, GROUP_CONCAT(DISTINCT ba.name) Author,GROUP_CONCAT(DISTINCT
	 * bs.name) Subject from book b,book_author ba,book_has_author bha,
	 * book_has_subject bhs, book_subject bs, (SELECT b.Catalogue_number as catNum
	 * from book b join (book_author ba,book_has_author bha) ON (ba.id =
	 * bha.author_id AND bha.Book_Catalogue_number=b.catalogue_number) WHERE
	 * ba.name='Leo Tolstoy') as foundCatNums where
	 * foundCatNums.catNum=b.Catalogue_number and
	 * foundCatNums.catNum=bha.book_catalogue_number and ba.id=bha.author_id and
	 * foundCatNums.catNum=bhs.book_catalogue_number and bs.id=bhs.subject_id group
	 * by b.catalogue_number
	 */
	private static String searchBySubjectQuery = "SELECT b.*, GROUP_CONCAT(DISTINCT ba.name) author,GROUP_CONCAT(DISTINCT bs.name) Subject from book b,book_author ba,book_has_author bha, book_has_subject bhs, book_subject bs,\r\n"
			+ "(SELECT b.Catalogue_number as catNum from book b\r\n" + "    join\r\n"
			+ "    (book_subject bs,book_has_subject bhs)\r\n" + "        ON\r\n"
			+ "      (bs.id = bhs.subject_id AND bhs.Book_Catalogue_number=b.catalogue_number) WHERE MATCH(bs.name) AGAINST(?)) as foundCatNums\r\n"
			+ "    where foundCatNums.catNum=b.Catalogue_number and foundCatNums.catNum=bhs.book_catalogue_number and bs.id=bhs.subject_id and foundCatNums.catNum=bha.book_catalogue_number and ba.id=bha.author_id group by b.catalogue_number";
	// copied from MySQL workbench (works there)
	/*
	 * SELECT b.*, GROUP_CONCAT(DISTINCT ba.name) author,GROUP_CONCAT(DISTINCT
	 * bs.name) Subject from book b,book_author ba,book_has_author bha,
	 * book_has_subject bhs, book_subject bs, (SELECT b.Catalogue_number as catNum
	 * from book b join (book_subject bs,book_has_subject bhs) ON (bs.id =
	 * bhs.subject_id AND bhs.Book_Catalogue_number=b.catalogue_number) WHERE
	 * bs.name='Drama') as foundCatNums where foundCatNums.catNum=b.Catalogue_number
	 * and foundCatNums.catNum=bhs.book_catalogue_number and bs.id=bhs.subject_id
	 * and foundCatNums.catNum=bha.book_catalogue_number and ba.id=bha.author_id
	 * group by b.catalogue_number
	 */
	private static String searchByTitleQuery = "SELECT b.*, GROUP_CONCAT(DISTINCT ba.name) author,GROUP_CONCAT(DISTINCT bs.name) Subject from book b,book_author ba,book_has_author bha, book_has_subject bhs, book_subject bs,\r\n"
			+ "(SELECT b.Catalogue_number as catNum from book b\r\n  WHERE MATCH(b.title) AGAINST(?)) as foundCatNums\r\n"
			+ "    where foundCatNums.catNum=b.Catalogue_number and foundCatNums.catNum=bhs.book_catalogue_number and bs.id=bhs.subject_id and foundCatNums.catNum=bha.book_catalogue_number and ba.id=bha.author_id group by b.catalogue_number";

	// copied from MySQL workbench (works there)
	/*
	 * SELECT b.*, GROUP_CONCAT(DISTINCT ba.name) author,GROUP_CONCAT(DISTINCT
	 * bs.name) Subject from book b,book_author ba,book_has_author bha,
	 * book_has_subject bhs, book_subject bs, (SELECT b.Catalogue_number as catNum
	 * from book b WHERE b.title='harry potter') as foundCatNums where
	 * foundCatNums.catNum=b.Catalogue_number and
	 * foundCatNums.catNum=bhs.book_catalogue_number and bs.id=bhs.subject_id and
	 * foundCatNums.catNum=bha.book_catalogue_number and ba.id=bha.author_id group
	 * by b.catalogue_number
	 */
	// search By Free Text Query
	private static String searchByFreeTextQuery = "SELECT b.*, GROUP_CONCAT(DISTINCT ba.name) author,GROUP_CONCAT(DISTINCT bs.name) Subject from book b,book_author ba,book_has_author bha, book_has_subject bhs, book_subject bs,\r\n"
			+ "(SELECT b.Catalogue_number as catNum from book b\r\n"
			+ "   WHERE MATCH(b.summery) AGAINST(?)) as foundCatNums\r\n"
			+ "    where foundCatNums.catNum=b.Catalogue_number and foundCatNums.catNum=bhs.book_catalogue_number and bs.id=bhs.subject_id and foundCatNums.catNum=bha.book_catalogue_number and ba.id=bha.author_id group by b.catalogue_number";

	// copied from MySQL workbench (works there)
	/*
	 * SELECT b.*, GROUP_CONCAT(DISTINCT ba.name) author,GROUP_CONCAT(DISTINCT
	 * bs.name) Subject from book b,book_author ba,book_has_author bha,
	 * book_has_subject bhs, book_subject bs, (SELECT b.Catalogue_number as catNum
	 * from book b WHERE MATCH(b.summery) AGAINST('harry potter')) as foundCatNums
	 * where foundCatNums.catNum=b.Catalogue_number and
	 * foundCatNums.catNum=bhs.book_catalogue_number and bs.id=bhs.subject_id and
	 * foundCatNums.catNum=bha.book_catalogue_number and ba.id=bha.author_id group
	 * by b.catalogue_number
	 */
	// lend a book query
	private static String lendABookQuery = "set @bookcatnum=?, @readerID=?;\r\n"
			+ "INSERT INTO lending_history (Reader_Card_ID,Book_Catalogue_number,Lending_date, Expected_return_date,Is_lost,irregular) \r\n"
			+ "VALUES (@readerID,@bookcatnum,NOW() ,\r\n"
			+ "CASE WHEN (SELECT b.is_popular as isPop FROM book b ,lending_history lh WHERE b.catalogue_number=@bookcatnum LIMIT 1) = '0'\r\n"
			+ "THEN DATE_ADD(curdate(), INTERVAL 14 DAY) ELSE DATE_ADD(curdate(), INTERVAL 3 DAY) END\r\n"
			+ ", 0,0);\r\n"
			+ "set @founNumCopies=(SELECT b.number_of_copies as founNumCopies from book b where b.catalogue_number=@bookcatnum);\r\n"
			+ "UPDATE book \r\n" + "SET \r\n" + "    number_of_copies = @founNumCopies - 1\r\n" + "WHERE\r\n"
			+ "    catalogue_number = @bookcatnum;";
	// copied from MySQL workbench (works there) NOTE: in the example both book

	/*
	 * set @bookcatnum=2, @readerID=1; INSERT INTO lending_history
	 * (Reader_Card_ID,Book_Catalogue_number,Lending_date,
	 * Expected_return_date,Is_lost,irregular) VALUES (@readerID,@bookcatnum,NOW() ,
	 * CASE WHEN (SELECT b.is_popular as isPop FROM book b ,lending_history lh WHERE
	 * b.catalogue_number=@bookcatnum) = '0' THEN DATE_ADD(curdate(), INTERVAL 14
	 * DAY) ELSE DATE_ADD(curdate(), INTERVAL 3 DAY) END , 0,0);
	 * set @founNumCopies=(SELECT b.number_of_copies as founNumCopies from book b
	 * where b.catalogue_number=@bookcatnum LIMIT 1); UPDATE book SET
	 * number_of_copies = @founNumCopies - 1 WHERE catalogue_number = @bookcatnum;
	 */

	// Login query
	private static String loginQuery = "{ CALL login(?,?,?,?)} ";
	// copied from MySQL workbench (works there)
	/*
	 * drop procedure if exists librarydatabase.login; DELIMITER // CREATE PROCEDURE
	 * login(IN userID INT, IN password INT, OUT UserType INT, OUT type VARCHAR(64))
	 * BEGIN IF (SELECT EXISTS(SELECT 1 FROM reader_card rc WHERE rc.user_ID=userID
	 * LIMIT 1)) THEN IF ((SELECT rc.password FROM reader_card rc WHERE
	 * rc.user_ID=userID)=password) THEN SELECT 0 INTO UserType; SELECT rc.Type INTO
	 * type FROM reader_card rc WHERE rc.user_ID=userID AND rc.password=password;
	 * ELSE SELECT 2 INTO UserType; END IF; ELSE SELECT 1 INTO UserType; END IF;
	 * END// DELIMITER ; CALL
	 * librarydatabase.login("4156789","123456",@UserType,@type);
	 * SELECT @UserType, @type;
	 */

	// get lending history of a subscriber query
	private static String lendingHistoryQuery = "SELECT lh.* FROM lending_history lh WHERE lh.Reader_Card_ID=\r\n"
			+ "(SELECT rc.card_ID FROM reader_card rc WHERE rc.user_ID=?)";
	// original query
	/*
	 * SELECT lh.* FROM lending_history lh WHERE lh.Reader_Card_ID= (SELECT
	 * rc.card_ID FROM reader_card rc WHERE rc.user_ID=?)
	 */

	// query to search a subscriber by id
	private static String subscriberSearchQuery = "SELECT rc.* FROM reader_card rc WHERE rc.user_id=?";
	// original query
	/* SELECT rc.* FROM reader_card rc WHERE rc.user_id="112233" */
	// Select a book by its catalog number
	private static String serachABookByCatNum = "SELECT b.*, GROUP_CONCAT(DISTINCT ba.name) author,GROUP_CONCAT(DISTINCT\r\n"
			+ "	  bs.name) Subject from book b,book_author ba,book_has_author bha,\r\n"
			+ "	  book_has_subject bhs, book_subject bs, (SELECT b.Catalogue_number as catNum\r\n"
			+ "	  from book b WHERE b.Catalogue_number=?) as foundCatNums\r\n"
			+ "	  where foundCatNums.catNum=b.Catalogue_number and\r\n"
			+ "	  foundCatNums.catNum=bhs.book_catalogue_number and bs.id=bhs.subject_id and\r\n"
			+ "	  foundCatNums.catNum=bha.book_catalogue_number and ba.id=bha.author_id group\r\n"
			+ "	  by b.catalogue_number";

	/*
	 * SELECT b.*, GROUP_CONCAT(DISTINCT ba.name) author,GROUP_CONCAT(DISTINCT
	 * bs.name) Subject from book b,book_author ba,book_has_author bha,
	 * book_has_subject bhs, book_subject bs, (SELECT b.Catalogue_number as catNum
	 * from book b WHERE b.Catalogue_number=1) as foundCatNums where
	 * foundCatNums.catNum=b.Catalogue_number and
	 * foundCatNums.catNum=bhs.book_catalogue_number and bs.id=bhs.subject_id and
	 * foundCatNums.catNum=bha.book_catalogue_number and ba.id=bha.author_id group
	 * by b.catalogue_number
	 */

	// search subscriber by ID
	private static String searchSubscriberByIDQuery = "SELECT rc.* FROM reader_card rc WHERE rc.user_ID=?";
	// original query
	/* SELECT rc.* FROM reader_card rc WHERE rc.user_ID=? */
	// search subscriber by first name
	private static String searchSubscriberByFirstNameQuery = "SELECT rc.* FROM reader_card rc WHERE MATCH(rc.First_name) AGAINST(?)";
	// original query
	/* SELECT rc.* FROM reader_card rc WHERE MATCH(rc.First_name) AGAINST(?) */

	// search subscriber by last name
	private static String searchSubscriberByLastNameQuery = "SELECT rc.* FROM reader_card rc WHERE MATCH(rc.Last_name) AGAINST(?)";

	// original query
	/* SELECT rc.* FROM reader_card rc WHERE MATCH(rc.Last_name) AGAINST(?) */

	// return a book query
	private static String returnABookQuery = "set @bookCatNum=?,@readerCardID=?;\r\n"
			+ "UPDATE lending_history ls, reader_card rc SET ls.actual_return_date=now(),ls.has_returned=1,rc.Membership_status='Active' where ls.Book_Catalogue_number=@bookCatNum AND ls.Reader_Card_ID=@readerCardID AND rc.card_id=@readerCardID;\r\n"
			+ "UPDATE book b SET b.Number_of_copies=b.Number_of_copies+1 WHERE b.Catalogue_number=@bookCatNum;\r\n"
			+ "UPDATE reader_card rc SET rc.Membership_status='Active' , rc.Number_of_late_books=(CASE WHEN rc.Number_of_late_books=0 THEN 0 ELSE rc.Number_of_late_books-1 END) WHERE rc.Card_ID=@readerCardID;";

	// original query
	/*
	 * set @bookCatNum=?,@readerCardID=?; UPDATE lending_history ls, reader_card rc
	 * SET
	 * ls.actual_return_date=now(),ls.has_returned=1,rc.Membership_status='Active'
	 * where ls.Book_Catalogue_number=@bookCatNum AND
	 * ls.Reader_Card_ID=@readerCardID AND rc.card_id=@readerCardID; UPDATE book b
	 * SET b.Number_of_copies=b.Number_of_copies+1 WHERE
	 * b.Catalogue_number=@bookCatNum; UPDATE reader_card rc SET
	 * rc.Membership_status='Active' , rc.Number_of_late_books=(CASE WHEN
	 * rc.Number_of_late_books=0 THEN 0 ELSE rc.Number_of_late_books-1 END) WHERE
	 * rc.Card_ID=@readerCardID;
	 */
	/**
	 * Method creates a singleton of DataBaseConnection class and connects to the
	 * Database
	 * 
	 */
	public static void connectToDB(String dbName) {
		DataBaseConnection dbconn = DataBaseConnection.getInstance(dbName);
		conn = dbconn.getConnection();
	}

	/**
	 * 
	 */
	public static BookSearchRequest BookSearch(BookSearchRequest bookSearchRequest) {
		BookSearchRequest answer = null;
		// ResultSet result = null;
		// Book newBook;
		switch (bookSearchRequest.getSearchType()) {
		case Author: {
			answer = processQueryAndReturnAnswer(searchByAuthorQuery, bookSearchRequest.getBookAuthor());
			break;
		}
		case Subject: {
			answer = processQueryAndReturnAnswer(searchBySubjectQuery, bookSearchRequest.getBookSubject());
			break;
		}
		case Title: {
			answer = processQueryAndReturnAnswer(searchByTitleQuery, bookSearchRequest.getBookTitle());
			break;
		}
		case FreeText: {
			answer = processQueryAndReturnAnswer(searchByFreeTextQuery, bookSearchRequest.getBookFreeText());
			break;
		}
		}
		return answer;
	}

	private static BookSearchRequest processQueryAndReturnAnswer(String query, String keyWord) {
		BookSearchRequest answer = new BookSearchRequest();
		ResultSet result = null;
		Book newBook;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(query);

			preparedStatement.setString(1, keyWord.toLowerCase());
			result = preparedStatement.executeQuery();
			boolean isEmpty = true;
			while (true) {
				if (result.next()) {
					isEmpty = false;
					newBook = getBookFromResultSet(result);
					answer.getBookListResult().add(newBook);
				} else {
					break;
				}
			}
			result.close();
			preparedStatement.close();
			if (!isEmpty) {
				answer.setErrorMessage("");
				answer.setStatus(0);
			} else {
				answer.setErrorMessage("No Results");
				answer.setStatus(1);
			}

		} catch (SQLException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		return answer;
	}

	/**
	 * Method returns a Book object based on the data in result set, assume
	 * result.next()==true
	 * 
	 * @param result a ResultSet object with data from the DB
	 * @return newBook a new book from the DB
	 */
	private static Book getBookFromResultSet(ResultSet result) {
		Book newBook = new Book();
		try {
			newBook.setCatalogueNumber(result.getInt("catalogue_number"));
			newBook.setTitle(result.getString("title"));
			newBook.setEditionNumber(result.getInt("Edition_number"));
			newBook.setPrintDate(result.getDate("Print_date"));
			newBook.setSummery(result.getString("Summery"));
			newBook.setNumberOfCopies(result.getInt("Number_of_copies"));
			newBook.setPurchaseDate(result.getDate("Purchase_date"));
			newBook.setShelfPosition(result.getString("Shelf_position"));
			newBook.setContentTable(result.getBytes("Table_of_Contents"));
			newBook.setPopular(result.getBoolean("Is_popular"));
			newBook.setAuthors(new ArrayList<>(Arrays.asList(result.getString("author").split(","))));
			newBook.setSubjects(new ArrayList<>(Arrays.asList(result.getString("Subject").split(","))));
			Blob contentTableBlob = result.getBlob("Table_of_Contents");
			try {
				newBook.setContentTable(contentTableBlob.getBytes(1, (int) contentTableBlob.length()));
			} catch (Exception e) {
				newBook.setContentTable(null);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newBook;
	}

	public static LoginRequest login(LoginRequest loginRequest) {
		ResultSet result = null;
		int status;
		String type;
		try {
			CallableStatement callableStatement = conn.prepareCall(loginQuery);
			callableStatement.setString(1, loginRequest.getUserID());
			callableStatement.setString(2, loginRequest.getPassword());
			callableStatement.registerOutParameter(3, java.sql.Types.INTEGER);
			callableStatement.registerOutParameter(4, java.sql.Types.LONGVARCHAR);
			result = callableStatement.executeQuery();
			status = callableStatement.getInt(3);
			type = callableStatement.getString(4);
			switch (status) {
			case 0:
				loginRequest.setLoginAs(Authenticate.UserType.valueOf(type));
				loginRequest.setSuccessful(true);
				loginRequest.setStatus(0);
				loginRequest.setErrorMessage("");
				// user has been found and password matches- can login
				PreparedStatement ps = conn.prepareStatement(subscriberSearchQuery);
				ps.setString(1, loginRequest.getUserID());
				ResultSet rs = ps.executeQuery();
				if (rs.next())
					loginRequest.setUser(getNewSubscriberFromResultSet(rs));
				break;
			case 1:
				loginRequest.setLoginAs(null);
				loginRequest.setSuccessful(false);
				loginRequest.setStatus(1);
				loginRequest.setErrorMessage("Incorrect ID and/or password!");
			}
			result.close();
			callableStatement.close();

		} catch (SQLException e) { // TODO Auto-generated catch block
			e.printStackTrace();
			// not sure the following is needed
			loginRequest.setLoginAs(null);
			loginRequest.setSuccessful(false);
			loginRequest.setStatus(1);
			loginRequest.setErrorMessage("Incorrect ID and/or password!");
		}
		return loginRequest;
	}

	/**
	 * Method constructs a new subscriber from a result set
	 * 
	 * @param ResultSet a query result set containing user's data
	 * @return Subscriber a new Subscriber object
	 * @throws SQLException
	 */
	private static Subscriber getNewSubscriberFromResultSet(ResultSet resultSet) throws SQLException {
		Subscriber user = new Subscriber();
		user.setCardID(resultSet.getInt(1));
		user.setStudentID(resultSet.getInt(2));
		user.setFirstName(resultSet.getString(4));
		user.setLastName(resultSet.getString(5));
		user.setCellphone(resultSet.getInt(6));
		user.setEmail(resultSet.getString(7));
		user.setStatus(MembershipStatus.valueOf(resultSet.getString(9)));
		user.setNumberOfLateBooks(resultSet.getInt(10));
		user.setType(Authenticate.UserType.valueOf(resultSet.getString(11)));
		user.setLendingHistory(getLendingHistory(user.getStudentID()));
		return user;
	}

	/**
	 * Build and return Lending history of a subscriber
	 * 
	 * @param int ID of user
	 * @return ArrayList list of lending Books
	 * @throws SQLException
	 * 
	 */
	public static ArrayList<LendingHistoryRow> getLendingHistory(int userID) throws SQLException {
		ArrayList<LendingHistoryRow> lendingHistory = new ArrayList<>();
		PreparedStatement ps = conn.prepareStatement(lendingHistoryQuery);
		PreparedStatement psBook = conn.prepareStatement(serachABookByCatNum);
		ResultSet resultBookSearch;
		ps.setInt(1, userID);
		ResultSet result = ps.executeQuery();
		LendingHistoryRow lendRow;
		Book book;
		while (result.next()) {
			lendRow = new LendingHistoryRow();
			lendRow.setUserID(userID);
			lendRow.setLendingDate(result.getDate(3));
			lendRow.setExpectedReturnDate(result.getDate(4));
			lendRow.setActualReturnDate(result.getDate(5));
			lendRow.setHasReturned(result.getBoolean(6));
			lendRow.setLate(result.getBoolean(7));
			lendRow.setLost(result.getBoolean(8));
			lendRow.setExtended(result.getBoolean(11));
			lendRow.setLibrarianExtendID(result.getInt(12));
			lendRow.setNewReturnDate(result.getDate(13));
			psBook.setInt(1, result.getInt(2));
			resultBookSearch = psBook.executeQuery();
			resultBookSearch.next();
			book = getBookFromResultSet(resultBookSearch);
			lendRow.setBook(book);
			lendingHistory.add(lendRow);
		}
		return lendingHistory;
	}

	/**
	 * Method searches for a subscriber
	 * 
	 * @param subscriberManagerRequest
	 * @return
	 * @throws SQLException
	 */
	public static SubscriberManageRequest searchSubscriber(SubscriberManageRequest subscriberManagerRequest)
			throws SQLException {
		SubscriberManageRequest answer = new SubscriberManageRequest();
		PreparedStatement ps = null;
		ArrayList<Subscriber> subscribers = new ArrayList<>();
		ResultSet result;
		Subscriber sub;

		switch (subscriberManagerRequest.getSearchBy()) {
		case ID:
			ps = conn.prepareStatement(searchSubscriberByIDQuery);
			ps.setInt(1, subscriberManagerRequest.getID());
			break;
		case FIRST_NAME:
			ps = conn.prepareStatement(searchSubscriberByFirstNameQuery);
			ps.setString(1, subscriberManagerRequest.getFirstName());
			break;
		case LAST_NAME:
			ps = conn.prepareStatement(searchSubscriberByLastNameQuery);
			ps.setString(1, subscriberManagerRequest.getLastName());
			break;
		}
		if (ps != null) {
			result = ps.executeQuery();
			while (result.next()) {
				sub = getNewSubscriberFromResultSet(result);
				subscribers.add(sub);
			}
			subscriberManagerRequest.setSubscribers(subscribers);
		}
		return subscriberManagerRequest;
	}

	/**
	 * Method executes lend a book query which lends a book to a subscriber
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public static Lend_ReturnABookRequest handleBookLendRequest(Lend_ReturnABookRequest request) throws SQLException {
		Lend_ReturnABookRequest answer = new Lend_ReturnABookRequest();
		PreparedStatement ps = conn.prepareStatement(lendABookQuery);
		ps.setInt(1, request.getBook().getCatalogueNumber());
		ps.setInt(2, request.getSubscriber().getCardID());
		ps.execute();
		answer.setBook(null);
		answer.setSubscriber(null);
		answer.setRequestType(Type.LEND);
		answer.setStatus(0);
		answer.setErrorMessage("NoErrors");

		return answer;
	}

	/**
	 * Method executes return a book query which returns a book from a subscriber
	 * 
	 * @param request
	 * @return
	 * @throws SQLException
	 */
	public static Lend_ReturnABookRequest handleBookReturnRequest(Lend_ReturnABookRequest request) throws SQLException {
		Lend_ReturnABookRequest answer = new Lend_ReturnABookRequest();
		PreparedStatement ps = conn.prepareStatement(returnABookQuery);
		ps.setInt(1, request.getBook().getCatalogueNumber());
		ps.setInt(2, request.getSubscriber().getCardID());
		ps.execute();
		answer.setBook(null);
		answer.setSubscriber(null);
		answer.setRequestType(Type.RETURN);
		answer.setStatus(0);
		answer.setErrorMessage("NoErrors");
		return answer;
	}
}
//----------------------OLD CODE--------------------------------------------------
/**
 * Method issues an sql query to the database to get student's name by his ID
 * 
 * @param clientRequest request object from the client containing only student's
 *                      ID
 * @return answer object to the client containing student's ID and name if
 *         exists or error otherwise
 */
/*
 * public static StudentNameByIDRequest
 * getStudentNameByID(StudentNameByIDRequest clientRequest) {
 * StudentNameByIDRequest answer = new StudentNameByIDRequest(); ResultSet
 * result; try { PreparedStatement preparedStatement = conn .prepareStatement(""
 * + "SELECT StudentName FROM student WHERE StudentID = ?");
 * preparedStatement.setString(1, clientRequest.getStudentID()); result =
 * preparedStatement.executeQuery(); if (result.next()) {
 * answer.setStudentID(clientRequest.getStudentID());
 * answer.setStudentName(result.getString("StudentName"));
 * answer.setErrorMessage(""); answer.setStatus(0); } else {
 * answer.setErrorMessage("Student ID not found!\nPlease try again.");
 * answer.setStatus(1); }
 * 
 * } catch (SQLException e) { // TODO Auto-generated catch block
 * e.printStackTrace(); } return answer; }
 */

/**
 * Method issues a query to the database to get student's UserType membership by
 * his ID
 * 
 * @param clientRequest request object from the client containing only student's
 *                      ID
 * @return answer object to the client containing student's membership UserType
 *         and ID if exists, error message otherwise
 */
/*
 * public static StatusMembershipByIDRequest getStudentMembershipStatusByID(
 * StatusMembershipByIDRequest clientRequest) { StatusMembershipByIDRequest
 * answer = new StatusMembershipByIDRequest(); ResultSet result; try {
 * PreparedStatement preparedStatement = conn .prepareStatement("" +
 * "SELECT StatusMembership FROM student WHERE StudentID = ?");
 * preparedStatement.setString(1, clientRequest.getStudentIDRequest()); result =
 * preparedStatement.executeQuery(); if (result.next()) {
 * answer.setStudentIDRequest(clientRequest.getStudentIDRequest());
 * answer.setMembershipStatusAnswer(MembershipStatus.valueOf(result.getString(
 * "StatusMembership"))); answer.setErrorMessage(""); answer.setStatus(0); }
 * else { answer.setErrorMessage("Student ID not found!\nPlease try again.");
 * answer.setStatus(1); }
 * 
 * } catch (SQLException e) { // TODO Auto-generated catch block
 * e.printStackTrace(); } return answer; }
 */
/**
 * Method issues an sql query to the database to update student's UserType
 * membership by his ID
 * 
 * @param clientRequest request object from the client containing only student's
 *                      ID and the new membership UserType
 * @return answer object to the client containing student's membership UserType
 *         and ID if exists, error message otherwise
 */
/*
 * public static UpdateDBStatusMembershipRequest
 * updateMemeStudentMembershipStatusByID( UpdateDBStatusMembershipRequest
 * clientRequest) { UpdateDBStatusMembershipRequest answer=new
 * UpdateDBStatusMembershipRequest(clientRequest.getStudentIDRequest(),
 * clientRequest.getNewStatus()); try { PreparedStatement preparedStatement =
 * conn .prepareStatement("" +
 * "UPDATE student SET StatusMembership = ? WHERE StudentID = ?");
 * preparedStatement.setString(1, clientRequest.getNewStatus().name());
 * preparedStatement.setString(2, clientRequest.getStudentIDRequest());
 * if(preparedStatement.executeUpdate()==0) { answer.setStatus(1);
 * answer.setErrorMessage("Student ID not found!\nPlease try again."); } } catch
 * (SQLException e) { // TODO Auto-generated catch block e.printStackTrace(); }
 * return answer; }
 */

// ------------------------OLD CODE-----------------------------------
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
