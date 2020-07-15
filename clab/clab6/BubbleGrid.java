public class BubbleGrid {
    private int[][] grid;
    private DisjointBubbleSets dbs;

    /* Create new BubbleGrid with bubble/space locations specified by grid.
     * Grid is composed of only 1's and 0's, where 1's denote a bubble, and
     * 0's denote a space. */
    public BubbleGrid(int[][] grid) {
        this.grid = grid;
        dbs = new DisjointBubbleSets(grid);
    }

    /* Returns an array whose i-th element is the number of bubbles that
     * fall after the i-th dart is thrown. Assume all elements of darts
     * are unique, valid locations in the grid. Must be non-destructive
     * and have no side-effects to grid. */
    public int[] popBubbles(int[][] darts) {
        int[] result = new int[darts.length];
        for (int i = 0; i < darts.length; i++) {
            result[i] = modifyDs(darts[i]);
        }
        return result;
    }

    private int modifyDs(int[] dart) {
        int x = dart[0];
        int y = dart[1];
        if (grid[x][y] == 0) {
            return 0;
        }
        grid[x][y] = 0;
        connectDsFromGrid(grid);
        return countBubbles();
    }

    private void connectDsFromGrid(int[][] grid) {
        dbs.initDs(grid);
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] != 0) {
                    int left = convertCoordinatesIntoIndex(r, c - 1);
                    int center = convertCoordinatesIntoIndex(r, c);
                    int up = convertCoordinatesIntoIndex(r - 1, c);
                    if (r != 0 && c != 0) {
                        if (grid[r - 1][c] == 1 && !dbs.connected(up, center)) {
                            dbs.union(up, center);
                        }
                        if (grid[r][c - 1] == 1 && !dbs.connected(left, center)) {
                            dbs.union(left, center);
                        }
                    } else if (r != 0) {
                        if (grid[r - 1][c] == 1 && !dbs.connected(up, center)) {
                            dbs.union(up, center);
                        }
                    } else if (c != 0) {
                        if (grid[r][c - 1] == 1 && !dbs.connected(left, center)) {
                            dbs.union(left, center);
                        }
                    }
                }
            }
        }
    }

    private int countBubbles() {
        int droppingBubbles = 0;
        int gridLength = grid.length;
        int gridWidth = grid[0].length;

        for (int r = 1; r < gridLength; r++) {
            for (int c = 0; c < gridWidth; c++) {
                if (grid[r][c] == 1) {
                    int index = convertCoordinatesIntoIndex(r, c);
                    if (dbs.find(index) >= gridLength) {
                        droppingBubbles++;
                        grid[r][c] = 0;
                    }
                }
            }
        }
        return droppingBubbles;
    }

    private int convertCoordinatesIntoIndex(int x, int y) {
        return x * grid[0].length + y;
    }
}
