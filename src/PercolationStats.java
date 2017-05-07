import edu.princeton.cs.algs4.StdRandom;

import edu.princeton.cs.algs4.StdStats;


/**
 * Created by LK on 2017/4/3.
 */
public class PercolationStats {

    private int number, trials;
    private double[] count;

    public PercolationStats(int number, int trials)    // perform trials independent experiments on an number-by-number grid
    {
        if (number <= 0 || trials <= 0)
            throw new java.lang.IllegalArgumentException();

        this.number = number;
        this.trials = trials;
        count = new double[this.trials];
        for (int i = 0; i < this.trials; ++i) {
            count[i] = 0;
            Percolation p = new Percolation(this.number);
            while (!p.percolates()) {
                int x = StdRandom.uniform(this.number) + 1;
                int y = StdRandom.uniform(this.number) + 1;
                if (!p.isOpen(x, y)) {
                    p.open(x, y);
                    ++count[i];
                }
            }
            count[i] /= (number * number);
        }
    }

    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(count);
    }

    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(count);
    }

    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        double mean = mean();
        double stddev = stddev();


        return mean - 1.96 * stddev / Math.sqrt(trials);
    }

    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        double mean = mean();
        double stddev = stddev();


        return mean + 1.96 * stddev / Math.sqrt(trials);
    }

    public static void main(String[] args)        // test client (described below)
    {
        if (args.length == 2) {
            int number = Integer.parseInt(args[0]);
            int trials = Integer.parseInt(args[1]);
            PercolationStats p = new PercolationStats(number, trials);
            System.out.println("mean                    = " + p.mean());
            System.out.println("stddev                  = " + p.stddev());
            System.out.println("95% confidence interval = [" + p.confidenceLo() + ", " + p.confidenceHi() + "]");
        }
    }
}