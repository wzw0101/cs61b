package hw2;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private int N;
    private int T;
    private double[] percolationFractions;
    private PercolationFactory pf;


    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0) throw new IllegalArgumentException("N should be greater than 0");
        if (T <= 0) throw new IllegalArgumentException("T should be greater than 0");

        this.N = N;
        this.T = T;
        this.pf = pf;
        percolationFractions = new double[T];

        doExperiments();
    }

    private void doExperiments() {
        final int SQUARENUMBER = N * N;
        for (int i = 0; i < T; i++) {
            Percolation p = pf.make(N);
            while(!p.percolates()) p.open(StdRandom.uniform(N), StdRandom.uniform(N));
            percolationFractions[i] = p.numberOfOpenSites() / (double) SQUARENUMBER;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationFractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolationFractions);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        double mean = mean();
        double stddev = stddev();
        return mean - 1.96 * Math.sqrt(stddev/T);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        double mean = mean();
        double stddev = stddev();
        return mean + 1.96 * Math.sqrt(stddev/T);
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        PercolationStats ps = new PercolationStats(400, 8000, new PercolationFactory());
        System.out.println(ps.mean());
        System.out.println(ps.stddev());
        System.out.println(ps.confidenceHigh());
        System.out.println(ps.confidenceLow());
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000.0);
    }
}
