package UnitTests;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import DataBase.DataBaseController;
import Entities.Book;
import Entities.Subscriber;
import ServerClientRequests.Lend_ReturnABookRequest;
import ServerClientRequests.Lend_ReturnABookRequest.Type;

public class LendingBookTest {

	private Lend_ReturnABookRequest request,answer;
	@Before
	public void setUp() throws Exception {
		DataBaseController.connectToDB("LibraryDatabase");
		request=new Lend_ReturnABookRequest(new Book("", null,null,"",0, null, null, 5, 5, false, "", null),
				new Subscriber("", "", 2, 0, "", null, null, 0),Type.LEND,0,"");
		
	}

	@Test
	public void test() {
		try {
			answer=DataBaseController.handleBookLendRequest(request);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(answer);
		assertEquals(answer.getStatus(),0);
	}

}
