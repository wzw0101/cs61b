package hw2;

import org.junit.Test;
import static org.junit.Assert.*;

public class PercolationTest {
    @Test
    public void isOpenTest() {
        Percolation p = new Percolation(3);
        assertFalse(p.isOpen(2, 2));
        p.open(2, 2);
        assertTrue(p.isOpen(2, 2));

        assertFalse(p.isOpen(1, 2));
        p.open(1, 2);
        assertTrue(p.isOpen(1, 2));
    }

    @Test
    public void percolatesTest() {
        Percolation p = new Percolation(3);
        assertFalse(p.percolates());
        p.open(0, 1);
        assertFalse(p.percolates());
        p.open(0, 0);
        assertFalse(p.percolates());
        p.open(1, 1);
        assertFalse(p.percolates());
        p.open(2, 2);
        assertFalse(p.percolates());
        p.open(1, 2);
        assertTrue(p.percolates());
    }

    @Test
    public void isFullTest() {
        Percolation p = new Percolation(3);
        p.open(1, 1);
        assertFalse(p.isFull(1, 1));
        p.open(2, 1);
        assertFalse(p.isFull(2, 1));
        p.open(0, 0);
        assertTrue(p.isFull(0, 0));
        p.open(0, 1);
        assertTrue(p.isFull(0, 1));
        assertTrue(p.isFull(1, 1));
        assertTrue(p.isFull(2, 1));
    }
}
