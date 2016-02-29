/**
 * Created by egor_hm on 7/3/15.
 */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int gridSize = 0;
    private boolean [] grid = null;
    private WeightedQuickUnionUF qu = null;
    private WeightedQuickUnionUF backwashQu = null;
    private int virtualTop = 0;
    private int virtualBottom = 0;

    public Percolation(int N) {
        this.qu = new WeightedQuickUnionUF(N * N + 2);
        this.backwashQu = new WeightedQuickUnionUF(N * N + 2);

        this.virtualBottom = N * N + 1;
        this.virtualTop = 0;
        this.gridSize = N;

        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("Index is less than 0");
        }

        grid = new boolean[N * N + 1];

        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                grid[xyTo1D(i, j)] = false;
            }
        }
    }
    private boolean isIndexOut(int i, int j) {
        if ((i < 1) || (i > gridSize) || (j < 1) || (j > gridSize)) {
            return true;
        }

        return false;
    }

    private int xyTo1D(int i, int j) {
        return (i - 1) * this.gridSize + j;
    }

    private void union(int i, int j, int i1, int j1) {
        if (!isIndexOut(i1, j1) && isOpen(i1, j1)) {
            int p = xyTo1D(i1, j1);
            int q = xyTo1D(i, j);
            this.qu.union(p, q);
            this.backwashQu.union(p, q);
        }
    }

    public void open(int i, int j) {
        if (isIndexOut(i, j))
            throw new IndexOutOfBoundsException("Out of bounds :(");

        grid[xyTo1D(i, j)] = true;

        //union with neighbors
        union(i, j, i - 1, j);
        union(i, j, i, j - 1);
        union(i, j, i + 1, j);
        union(i, j, i, j + 1);

        if (i == 1) {
            this.qu.union(virtualTop, xyTo1D(i, j));
            this.backwashQu.union(virtualTop, xyTo1D(i, j));
        }

        if (i == this.gridSize) this.qu.union(virtualBottom, xyTo1D(i, j));
    }

    public boolean isOpen(int i, int j) {
        if (isIndexOut(i, j))
            throw new IndexOutOfBoundsException("Out of bounds :(");

        return grid[xyTo1D(i, j)];

    }
    public boolean isFull(int i, int j) {
        if (isIndexOut(i, j))
            throw new IndexOutOfBoundsException("Out of bounds :(");

        return this.backwashQu.connected(xyTo1D(i, j), virtualTop);

    }
    public boolean percolates() {
        return this.qu.connected(virtualBottom, virtualTop);

    }

    public static void main(String[] args) {
        Percolation p = new Percolation(10);

        p.open(1, 1);
        p.open(1, 2);
        boolean a = p.isFull(1, 2);

        System.out.print(a);




    }
}
