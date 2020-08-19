package UnitTests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import DataBase.DataBaseConnection;
import DataBase.DataBaseController;
import Entities.LendingHistoryRow;
import clientUIControllers.SubscriberDetails;
import javafx.application.Platform;

public class SubscriberLendingHistoryTest {

	@Before
	public void setUp() throws Exception {
		DataBaseController.connectToDB("librarydatabase");
	}

	@Test
	public void testOpenLendingHistory()
	{
		try {
			ArrayList<LendingHistoryRow> lendingHistory = DataBaseController.getLendingHistory(4);
			System.out.println(lendingHistory);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
