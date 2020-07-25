package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

public class Percolation {

    private WeightedQuickUnionUF wquuf;
    private int numberOfOpenSites;
    private int order;
    private boolean[] isOpens;
    private int tp;
    private int bp;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N)   {
        if (N <= 0) throw new IllegalArgumentException("N should not be less than or equal to 0");
        wquuf = new WeightedQuickUnionUF(N * N + 2);
        numberOfOpenSites = 0;
        order = N;
        isOpens = new boolean[order * order];
        Arrays.fill(isOpens, false);
        tp = order * order;
        bp = order * order  + 1;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        if (isOpen(row, col)) return;

        numberOfOpenSites++;
        int centre = rcTo1D(row, col);
        isOpens[centre] = true;

        int top;
        int bottom;
        int left;
        int right;

        if(order == 1) {
            wquuf.union(tp , centre);
            wquuf.union(bp, centre);
        } else {
            if (row == 0) {
                wquuf.union(tp , centre);
                bottom = rcTo1D(row + 1, col);
                if (isOpens[bottom]) wquuf.union(bottom, centre);
            } else if (row == order - 1)  {
                wquuf.union(bp, centre);
                top = rcTo1D(row - 1, col);
                if (isOpens[top]) wquuf.union(top, centre);
            } else {
                top = rcTo1D(row - 1, col);
                bottom = rcTo1D(row + 1, col);
                if (isOpens[top]) wquuf.union(top, centre);
                if (isOpens[bottom]) wquuf.union(bottom, centre);
            }

            if (col == 0) {
                right = rcTo1D(row, col + 1);
                if (isOpens[right]) wquuf.union(right, centre);
            } else if (col == order - 1) {
                left = rcTo1D(row, col - 1);
                if (isOpens[left]) wquuf.union(left, centre);
            } else {
                right = rcTo1D(row, col + 1);
                left = rcTo1D(row, col - 1);
                if (isOpens[right]) wquuf.union(right, centre);
                if (isOpens[left]) wquuf.union(left, centre);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return isOpens[rcTo1D(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int site = rcTo1D(row, col);
        return wquuf.connected(tp, site);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return wquuf.connected(tp, bp);
    }

    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args) {

    }

    private void validate(int i, int j) {
        if (i < 0 || i > order - 1) throw new IndexOutOfBoundsException("index: " + i + "out of bounds.");
        if (j < 0 || j > order - 1) throw new IndexOutOfBoundsException("index: " + j + "out of bounds.");
    }

    private int rcTo1D(int row, int col) {
        return row * order + col;
    }
}
