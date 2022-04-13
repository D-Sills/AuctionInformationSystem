import Utilities.CustomList;
import model.Bidder;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Test Class for the CustomList class
 * @author Darren Sills
 */
public class CustomListTest {
    private CustomList<Bidder> biddersTest;
    private Bidder bidder0, bidder1, bidder2, bidder3, bidder4, bidder5;

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
    public void testAdd() {
        assertEquals(0, biddersTest.size());
        biddersTest.add(bidder0);
        assertEquals(1, biddersTest.size());
        biddersTest.add(bidder1);
        assertEquals(2,biddersTest.size());
    }

    @Test
    public void testSet() {
        biddersTest.add(bidder0);
        biddersTest.add(bidder1);
        biddersTest.add(bidder2);
        biddersTest.add(bidder3);
        biddersTest.add(bidder4);
        System.out.println(biddersTest.toString());
        assertEquals(5, biddersTest.size());
        System.out.println(biddersTest.toString());
        assertEquals(bidder0, biddersTest.get(0));
        assertEquals(bidder1, biddersTest.get(1));
        biddersTest.set(0, bidder4);
        biddersTest.set(4, bidder2);
        biddersTest.set(2, bidder4);
        biddersTest.set(3, bidder4);
        System.out.println(biddersTest.toString());
        assertEquals(bidder4, biddersTest.get(3));
    }

    @Test
    public void testRemove() {
        biddersTest.add(bidder0);
        biddersTest.add(bidder1);
        biddersTest.remove(bidder0);
        assertEquals(1,biddersTest.size());
        assertTrue(biddersTest.contains(bidder1));
        biddersTest.remove(0);
        assertTrue(biddersTest.isEmpty());
    }

    @Test
    public void testGet() {
        biddersTest.add(bidder0);
        biddersTest.add(bidder1);
        assertEquals(biddersTest.get(0), bidder0);
        assertEquals(biddersTest.get(1), bidder1);
        biddersTest.remove(bidder0);
        assertEquals(bidder1,biddersTest.get(0));

        biddersTest.add(bidder0);
        assertEquals(0, biddersTest.indexOf(bidder1)); //quick indexof test
        assertEquals(1, biddersTest.indexOf(bidder0));

        biddersTest.clear();
        assertNull(biddersTest.get(0));
    }

    @Test
    public void testToString() {
        biddersTest.add(bidder0);
        biddersTest.add(bidder1);
        assertEquals(biddersTest.get(0).toString(), bidder0.toString());
        assertEquals(biddersTest.get(1).toString(), bidder1.toString());
    }

    @Test
    public void testIterator() {
        biddersTest.add(bidder0);
        biddersTest.add(bidder1);
        biddersTest.add(bidder1);
        biddersTest.add(bidder1);
        biddersTest.add(bidder1);
        biddersTest.add(bidder1);
        int i = 0;
        for (Bidder bidder: biddersTest) {
            i++;
        }
        assertEquals(i, biddersTest.size());
    }

    @Test
    public void toArray() {
        biddersTest.add(bidder0);
        biddersTest.add(bidder1);
        biddersTest.add(bidder2);
        biddersTest.add(bidder3);
        biddersTest.add(bidder4);

        Bidder[] a = new Bidder[biddersTest.size()];
        biddersTest.toArray(a);
        System.out.println(Arrays.toString(a) + "\n" + a.length);
        System.out.println(biddersTest);

        System.out.println(biddersTest);
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));
    }
}