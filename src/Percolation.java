import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by LK on 2017/4/3.
 */
public class Percolation {

    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;
    private int number;

    private boolean[][] map;
    private int numberOfOpenSites;

    public Percolation(int number)                // create number-by-number grid, with all sites blocked
    {
        if (number <= 0)
            throw new java.lang.IllegalArgumentException();
        uf = new WeightedQuickUnionUF(number * number + 2);
        uf2 = new WeightedQuickUnionUF(number * number + 1);
        this.number = number;
        map = new boolean[this.number][this.number];
        for (int i = 0; i < this.number; ++i)
            for (int j = 0; j < this.number; ++j)
                map[i][j] = false;


        this.numberOfOpenSites = 0;

    }

    public void open(int row, int col)    // open site (row, col) if it is not open already
    {
        if (row < 1 || col < 1 || row > number || col > number)
            throw new IndexOutOfBoundsException();

        if (isOpen(row, col))
            return;
        map[row - 1][col - 1] = true;
        ++numberOfOpenSites;

        int q = (row - 1) * number + col;

        if (row - 2 >= 0) {
            if (map[row - 2][col - 1]) {
                uf.union(q - number, q);
                uf2.union(q - number, q);
            }
        } else {
            uf.union(0, q);
            uf2.union(0, q);
        }
        if (col - 2 >= 0) {

            if (map[row - 1][col - 2]) {
                uf.union(q - 1, q);
                uf2.union(q - 1, q);
            }
        }
        if (row < number) {
            if (map[row][col - 1]) {
                uf.union(q + number, q);
                uf2.union(q + number, q);
            }

        } else {
            uf.union(number * number + 1, q);
        }
        if (col < number) {

            if (map[row - 1][col]) {
                uf.union(q + 1, q);
                uf2.union(q + 1, q);
            }
        }

    }

    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {

        if (row < 1 || col < 1 || row > number || col > number)
            throw new IndexOutOfBoundsException();
        return map[row - 1][col - 1];
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        if (row < 1 || col < 1 || row > number || col > number)
            throw new IndexOutOfBoundsException();

        return uf2.connected(0, (row - 1) * number + col);


    }

    public int numberOfOpenSites()       // number of open sites
    {
        return numberOfOpenSites;
    }

    public boolean percolates()              // does the system percolate?
    {
        return uf.connected(0, number * number + 1);
    }

}