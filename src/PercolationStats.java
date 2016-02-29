/**
 * Created by egor_hm on 7/13/15.
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int experimentsNum = 0;
    private double [] threshold = null;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException();

        this.experimentsNum = T;
        this.threshold = new double[T + 1];
        int a, b;
        a = StdRandom.uniform(1, N + 1);
        b = StdRandom.uniform(1, N + 1);

        for (int i  = 1; i <= T; i++) {
            Percolation perc = new Percolation(N);
            for (int j = 1; j <= (N*N); j++) {
                while (perc.isOpen(a, b)) {
                    a = StdRandom.uniform(1, N + 1);
                    b = StdRandom.uniform(1, N + 1);
                }
                perc.open(a, b);

                if (perc.percolates()) {
                    this.threshold[i] = (double) j / (double) (N * N);
                    break;
                }
            }
        }
    }
    public double mean() {
        return StdStats.mean(this.threshold, 1, this.experimentsNum);
    }
    public double stddev() {
        return StdStats.stddev(this.threshold, 1, this.experimentsNum);
    }
    public double confidenceLo() {
        return mean() - (1.96 * stddev()
                / Math.sqrt(this.experimentsNum));
    }
    public double confidenceHi() {
        return mean() + (1.96 * stddev()
                / Math.sqrt(this.experimentsNum));
    }

    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(200, 100);
        System.out.println(stats.mean());
        System.out.println(stats.stddev());
        System.out.println(stats.confidenceLo());
        System.out.println(stats.confidenceHi());
    }
}
