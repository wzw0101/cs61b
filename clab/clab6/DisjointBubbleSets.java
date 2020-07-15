import java.util.Arrays;

public class DisjointBubbleSets extends UnionFind {

    public final int CAPACITY;

    /**
     * Construct a disjoint sets object from a bubble grid.\
     *
     * Initially, elements in the disjoint sets are set -1 if there is a bubble in the corresponding place of the grid,
     * or else they will be set the total number of  squares in the grid.
     *
     * @param grid
     */
    public DisjointBubbleSets(int[][] grid) {
        super();
        CAPACITY = grid.length * grid[0].length;
        a = new int[CAPACITY];
        initDs(grid);
    }

    public void initDs(int[][] grid) {
        int rLength = grid.length;
        int cLength = grid[0].length;
        for (int r = 0; r < rLength; r++) {
            for (int c = 0; c < cLength; c++) {
                a[r * cLength + c] = grid[r][c] == 1 ? -1 : CAPACITY;
            }
        }
    }

    public static void main(String[] args) {
        int[][] grid = {
                {0, 0, 0, 1},
                {1, 1, 1, 0},
                {1, 1, 0, 0},
                {0, 1, 1, 0}
        };

        DisjointBubbleSets dbs = new DisjointBubbleSets(grid);
        System.out.println(Arrays.toString(dbs.a));
    }
}
