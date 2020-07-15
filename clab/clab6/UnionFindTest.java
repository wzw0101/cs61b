import org.junit.Test;
import static org.junit.Assert.*;

public class UnionFindTest {
    @Test
    public void testSizeOf() {
        UnionFind uf = new UnionFind(16);
        assertEquals(1, uf.sizeOf(2));
        assertEquals(1, uf.sizeOf(3));
        uf.union(2, 3);
        assertEquals(2, uf.sizeOf(2));
        uf.union(4, 5);
        uf.union(3, 4);
        assertEquals(4, uf.sizeOf(4));
    }

    @Test
    public void testParent() {
        UnionFind uf = new UnionFind(8);
        assertEquals(-1, uf.parent(1));
        uf.union(1, 2);
        assertEquals(2, uf.parent(1));
        uf.union(3, 4);
        assertEquals(4, uf.parent(3));
        uf.union(1, 3);
        assertEquals(4, uf.parent(2));
    }

    @Test
    public void testFind() {
        UnionFind uf = new UnionFind(8);
        assertEquals(1, uf.find(1));
        uf.union(1, 2);
        assertEquals(2, uf.find(1));
        uf.union(3, 4);
        uf.union(1, 3);
        assertEquals(4, uf.find(1));
    }

    @Test
    public void testConnected() {
        UnionFind uf = new UnionFind(8);
        assertFalse(uf.connected(1, 3));
        uf.union(1, 2);
        assertTrue(uf.connected(1, 2));
        uf.union(3, 4);
        uf.union(2, 4);
        assertTrue(uf.connected(1, 3));
    }
}