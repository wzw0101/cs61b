package bearmaps;

import edu.princeton.cs.algs4.In;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {
    @Test
    public void testSize() {
        ArrayHeapMinPQ<Integer> ahm = new ArrayHeapMinPQ<>();
        assertEquals(0, ahm.size());
        ahm.add(Integer.valueOf(1), 1.0D);
        assertEquals(1, ahm.size());
        ahm.add(Integer.valueOf(2), 2.0D);
        assertEquals(2, ahm.size());
        ahm.removeSmallest();
        assertEquals(1, ahm.size());
    }

    @Test
    public void testGetSmallest() {
        ArrayHeapMinPQ<Integer> ahm = new ArrayHeapMinPQ<>();
        ahm.add(Integer.valueOf(5), 5.0D);
        ahm.add(Integer.valueOf(4), 4.0D);
        ahm.add(Integer.valueOf(3), 3.0D);
        ahm.add(Integer.valueOf(2), 2.0D);
        ahm.add(Integer.valueOf(1), 1.0D);
        assertEquals(Integer.valueOf(1), ahm.getSmallest());
    }

    @Test
    public void testRemoveSmallest() {
        ArrayHeapMinPQ<Integer> ahm = new ArrayHeapMinPQ<>();
        ahm.add(Integer.valueOf(5), 5.0D);
        ahm.add(Integer.valueOf(4), 4.0D);
        ahm.add(Integer.valueOf(3), 3.0D);
        ahm.add(Integer.valueOf(2), 2.0D);
        ahm.add(Integer.valueOf(1), 1.0D);
        assertEquals(Integer.valueOf(1), ahm.removeSmallest());
        assertEquals(Integer.valueOf(2), ahm.removeSmallest());
        assertEquals(Integer.valueOf(3), ahm.removeSmallest());
        assertEquals(Integer.valueOf(4), ahm.removeSmallest());
        assertEquals(Integer.valueOf(5), ahm.removeSmallest());
    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<Integer> ahm = new ArrayHeapMinPQ<>();
        ahm.add(Integer.valueOf(5), 5.0D);
        ahm.add(Integer.valueOf(4), 4.0D);
        ahm.add(Integer.valueOf(3), 3.0D);
        ahm.add(Integer.valueOf(2), 2.0D);
        ahm.add(Integer.valueOf(1), 1.0D);
        ahm.changePriority(Integer.valueOf(1), 6.0D);
        assertEquals(Integer.valueOf(2), ahm.getSmallest());
    }

    @Test
    public void testContains() {
        ArrayHeapMinPQ<Integer> ahm = new ArrayHeapMinPQ<>();
        ahm.add(Integer.valueOf(5), 5.0D);
        ahm.add(Integer.valueOf(4), 4.0D);
        ahm.add(Integer.valueOf(3), 3.0D);
        assertTrue(ahm.contains(Integer.valueOf(3)));
        assertFalse(ahm.contains(Integer.valueOf(6)));
    }
}
