public class PercolationStats {
    private double[] results;

    public PercolationStats(int N, int T) {
        if (N < 1 || T < 1) {
            throw new IllegalArgumentException(
                "PercolationsStats paremeters must be greater than zero"
            );
        }

        results = new double[T];

        for (int i = 0; i < T; i++) {
            int result = simulation(N);

            results[i] = (double) result / (N * N);
        }

    }

    private int simulation(int N) {
        int x = 0;
        Percolation simulation = new Percolation(N);

        while (!simulation.percolates()) {
            int i = StdRandom.uniform(N) + 1;
            int j = StdRandom.uniform(N) + 1;

            if (!simulation.isOpen(i, j)) {
                x++;
                simulation.open(i, j);
            }
        }
        return x;
    }

    public double mean() {
        return StdStats.mean(results);
    }

    public double stddev() {
        if (results.length == 1) {
            return Double.NaN;
        }

        return StdStats.stddev(results);
    }

    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(results.length));
    }

    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(results.length));
    }

    public static void main(String[] args) {
        StdOut.println("Enter a value for N");
        int N = StdIn.readInt();
        StdOut.println("Enter a value for T");
        int T = StdIn.readInt();

        PercolationStats pobj = new PercolationStats(N, T);

        System.out.println("mean = " + pobj.mean());
        System.out.println("stddev = " + pobj.stddev());
        System.out.println(
            "95% confidence interval = "
            + pobj.confidenceLo() + ", "
            + pobj.confidenceHi()
        );
    }
}