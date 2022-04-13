import Utilities.CustomList;
import Utilities.SortingSearchMethods;
import model.Bidder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
/**
 * Test Class for the SortingSearchMethods class
 * @author Darren Sills
 */
public class SortingSearchTest {
    private CustomList<Bidder> biddersTest;
    private Bidder bidder0, bidder1, bidder2, bidder3, bidder4;

    @Before
    public void setUp() {
        biddersTest = new CustomList<>();
        bidder0 = new Bidder("Potemkin", "wexford", "123", "a@g");
        bidder1 = new Bidder("Chipp", "wexford", "123", "a@g");
        bidder2 = new Bidder("Ky", "wexford", "123", "a@g");
        bidder3 = new Bidder("Sol", "wexford", "123", "a@g");
        bidder4 = new Bidder("Ramlethal", "wexford", "123", "a@g");
    }

    @Test
    public void testBinarySearch() {
        biddersTest.add(bidder0);
        biddersTest.add(bidder1);
        biddersTest.add(bidder2);
        biddersTest.add(bidder3);
        biddersTest.add(bidder4);

        biddersTest.sort(biddersTest);
        System.out.println(biddersTest.toString());
        int a = SortingSearchMethods.binarySearch(biddersTest, bidder4);
        assertEquals(biddersTest.indexOf(bidder4), a);
        biddersTest.remove(bidder0);
        biddersTest.remove(bidder1);
        int b = SortingSearchMethods.binarySearch(biddersTest, bidder4);
        assertEquals(biddersTest.indexOf(bidder4), b);
        System.out.println(b);
    }

}