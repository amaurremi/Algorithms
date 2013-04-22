public class Percolation {
    
    private final boolean[][] grid;
    private final WeightedQuickUnionUF ufGrid;
    private final WeightedQuickUnionUF ufGridWithoutBottomVirualSite;
    private final int n;
    private final int additionalUpperSite;
    private final int additionalLowerSite;
    
    // create N-by-N grid, with all sites blocked (false)
    public Percolation(int n) {
        this.n = n;
        grid = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
        ufGrid = new WeightedQuickUnionUF(n * n + 2);
        ufGridWithoutBottomVirualSite = new WeightedQuickUnionUF(n * n + 1);
            // N * N + 1 for upper additional site, 
            // N * N + 2 for lower additional site
        additionalUpperSite = n * n;
        additionalLowerSite = n * n + 1;
    }
    
    public static void main(String[] args) {
        int n = 10;
        Percolation p = new Percolation(n);
        System.out.println(p.percolates());
        p.open(1, 1);
        System.out.println(p.percolates());
        for (int i = 1; i < n; i++) {
            p.open(i, 2);
        }
        System.out.println(p.percolates());
        p.print();
    }
    
    private void print() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
       
    // open site (row i, column j) if it is not already    
    public void open(int i, int j) {
        grid[i - 1][j - 1] = true;
        int ij = getSite(i, j);
        if (i == 1) {
            ufGrid.union(ij, additionalUpperSite);
            ufGridWithoutBottomVirualSite.union(ij, additionalUpperSite);
        }
        if (i == n) {
            ufGrid.union(ij, additionalLowerSite);
        }
        union(ij, i - 1, j);
        union(ij, i + 1, j);
        union(ij, i, j - 1);
        union(ij, i, j + 1);
    }
    
    private void union(int ij, int i2, int j2) {
        int ij2 = getSite(i2, j2);
        if (ij2 > -1 && isOpen(i2, j2)) {
            ufGrid.union(ij, ij2);
            ufGridWithoutBottomVirualSite.union(ij, ij2);
        }
    }
    
    private int getSite(int i, int j) {
        if (i < 1 || j < 1 || i > n || j > n)
            return -1;
        return n * (i - 1) + j - 1;
    }
   
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        return grid[i - 1][j - 1];
    }
       
    // is site (row i, column j) full?    
    public boolean isFull(int i, int j) {
        return ufGridWithoutBottomVirualSite.
            connected(getSite(i, j), additionalUpperSite);
    }

    // does the system percolate?
    public boolean percolates() {
        return ufGrid.connected(additionalUpperSite, additionalLowerSite);
    }
}
