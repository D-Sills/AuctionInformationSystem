import Utilities.CustomList;
import model.AuctionLot;
import model.Bidder;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

import static Utilities.Validation.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidationTest {
		private Bidder bidder;
		private AuctionLot auctionLot;

		@Before
		public void setUp() throws MalformedURLException {
				CustomList<Bidder> bidderTest = new CustomList<>();
				CustomList<AuctionLot> auctionTest = new CustomList<>();
				bidder = new Bidder("Ragna", "wexford", "Y35E308","b");
				auctionLot = new AuctionLot("Es","cool?","wow", LocalDate.now(), 12.00, new URL("https://static.wikia.nocookie.net/blazblue/images/5/57/Es_%28Centralfiction%2C_Character_Select_Artwork%29.png/revision/latest?cb=20160425120259"));
				bidderTest.add(bidder);
				auctionTest.add(auctionLot);
		}

		@Test
		public void testValidEmail() {
				bidder.setEmail("hi@bye");
				assertTrue(validEmail(bidder.getEmail()));
				bidder.setEmail("hi@");
				assertFalse(validEmail(bidder.getEmail()));
				bidder.setEmail("hi@bye.com");
				assertTrue(validEmail(bidder.getEmail()));
				bidder.setEmail("h");
				assertFalse(validEmail(bidder.getEmail()));
		}

		@Test
		public void testValidPhone() {
				bidder.setPhone("089422 2561");
				assertTrue(validPhone(bidder.getPhone()));
				bidder.setPhone("089 422 2561");
				assertTrue(validPhone(bidder.getPhone()));
				bidder.setPhone("089-422-2561");
				assertTrue(validPhone(bidder.getPhone()));
				bidder.setPhone("089 422 25612");
				assertFalse(validPhone(bidder.getPhone()));
				bidder.setPhone("111 111 1111");
				assertTrue(validPhone(bidder.getPhone()));
		}

		@Test
		public void testValidPrice() {
				auctionLot.setStartingPrice(12.0);
				assertTrue(validPrice(String.valueOf(auctionLot.getStartingPrice())));
				System.out.println(2);
				auctionLot.setStartingPrice(Double.valueOf("hi"));
				assertFalse(validPrice(String.valueOf(auctionLot.getStartingPrice())));
		}
}
