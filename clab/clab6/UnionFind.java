import java.util.Arrays;

public class UnionFind {

    int[] a;

    public UnionFind() {}

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        a = new int[n];
        Arrays.fill(a, -1);
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex < 0 || vertex >= a.length) {
            throw new IllegalArgumentException("argument out of bounds exception: " + vertex);
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        validate(v1);
        return Math.abs(a[find(v1)]);
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        return a[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        validate(v1);
        validate(v2);
        return find(v1) == find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        validate(v1);
        validate(v2);
        if (connected(v1, v2)) {
            return;
        }
        int root1 = find(v1);
        int root2 = find(v2);
        if (Math.abs(a[root1]) <= Math.abs(a[root2])) {
            a[root2] += a[root1];
            a[root1] = root2;
        } else {
            a[root1] += a[root2];
            a[root2] = root1;
        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        int cp = vertex;
        int pp = parent(cp);
        while (pp >= 0) {
            cp = pp;
            pp = parent(cp);
        }

        if (vertex != cp) {
            a[vertex] = cp;
        }

        return cp;
    }

}
