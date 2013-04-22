public class PercolationStats {
    
    private double[] thresholds;
    
    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Argument too small");
        }
        thresholds = new double[T];
        int sites = N * N; 
        for (int i = 0; i < T; i++) {
            Percolation percolation = new Percolation(N);
            double openSites;
            for (openSites = 0; !percolation.percolates(); openSites++) {
                while (true) {
                    int k = random(1, N);
                    int l = random(1, N);
                    if (!percolation.isOpen(k, l)) {
                        percolation.open(k, l);
                        break;
                    }
                }
            }
            thresholds[i] = openSites / sites;
        }
    }
    
    private int random(int min, int max) {
        return (int) (min + Math.random() * ((max - min) + 1));
    }
   
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }
   
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }
   
    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(thresholds.length);
    }
   
    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(thresholds.length);
    }
   
    // test client, described below
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(
                        Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo() 
                           + ", " + stats.confidenceHi());
    }
}