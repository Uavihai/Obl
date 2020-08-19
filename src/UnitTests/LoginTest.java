package UnitTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import DataBase.DataBaseController;
import ServerClientRequests.LoginRequest;
import junit.framework.Assert;

public class LoginTest {

	LoginRequest request;
	
	@Before
	public void setUp() throws Exception {
		DataBaseController.connectToDB("LibraryDatabase");
		request=new LoginRequest();
	}

	@Test
	public void testLogin() {
		request.setUserID("456789");
		request.setPassword("123456");
		LoginRequest answer=DataBaseController.login(request);
		System.out.println(answer);
		assertNotNull(answer);
	}

}
