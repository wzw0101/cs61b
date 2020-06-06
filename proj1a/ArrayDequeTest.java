import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayDequeTest {
    @Test
    public void addIsEmptySizeTest() {
        ArrayDeque<String> ad = new ArrayDeque<>();
        assertEquals(true, ad.isEmpty());

        ad.addFirst("front");
        assertEquals(1, ad.size());
        assertEquals(false, ad.isEmpty());

        ad.addLast("middle");
        assertEquals(2, ad.size());

        ad.addLast("back");
        assertEquals(3, ad.size());

        ad.printDeque();
    }

    @Test
    public void addRemoveTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        assertEquals(true, ad.isEmpty());

        ad.addFirst(10);
        assertEquals(false, ad.isEmpty());

        ad.addFirst(20);
        assertEquals(Integer.valueOf(10), ad.removeLast());
        assertEquals(Integer.valueOf(20), ad.removeLast());
        assertEquals(true, ad.isEmpty());

    }

    @Test
    public void compressTest() {
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        for (int i = 0; i < 9; i++) {
            ad.addFirst(i);
        }
        assertEquals(9, ad.size());
//        assertEquals(16, ad.capacity);
        for (int i = 0; i < 5; i++) {
            ad.removeFirst();
        }
        assertEquals(4, ad.size());
//        assertEquals(8, ad.capacity);
    }
}
