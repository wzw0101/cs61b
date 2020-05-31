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

        ad.removeFirst();
        assertEquals(true, ad.isEmpty());

    }
}
