import java.util.Random;

/**
 * Runs timing experiments for the BST-based k-mer distribution algorithm.
 *
 * Task 5 (BST contribution):
 *  - For random DNA strings of lengths n = 10^4, 10^5, 10^6
 *    and k = 5, 6, 7, this program measures the running time of the
 *    BST-based implementation.
 */
public class BSTRuntimeExperiment {

    /** Possible DNA bases used when generating random sequences. */
    private static final char[] BASES = {'a', 'c', 'g', 't'};

    /**
     * Generates a random DNA string of the given length over {a, c, g, t}.
     *
     * @param length number of characters in the DNA sequence
     * @param rng    random number generator
     * @return a random DNA string
     */
    public static String generateRandomDNA(int length, Random rng) {
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = BASES[rng.nextInt(BASES.length)];
        }
        return new String(chars);
    }

    /**
     * Runs a single timing experiment of the BST-based k-mer distribution
     * for a given DNA string and k.
     *
     * @param dna the DNA sequence to analyze
     * @param k   the k-mer length
     * @return elapsed time in milliseconds
     */
    public static double timeKmerDistribution(String dna, int k) {
        long start = System.nanoTime();
        BST tree = BSTKmerDistribution.buildKmerTree(dna, k);
        long end = System.nanoTime();

        // Clean up the tree (not strictly necessary, but matches the spec's destroy() requirement).
        tree.destroy();

        long elapsedNs = end - start;
        return elapsedNs / 1_000_000.0; // convert to milliseconds
    }

    /**
     * Runs the timing experiments for:
     *  n in {10^4, 10^5, 10^6} and k in {5, 6, 7}.
     */
    public static void runTimingExperiments() {
        int[] lengths = {10_000, 100_000, 1_000_000};
        int[] ks = {5, 6, 7};

        // Fixed seed so that random sequences are reproducible.
        Random rng = new Random(12345);

        System.out.println("BST-based k-mer distribution timing results");
        System.out.println("(Times are in milliseconds)\n");

        for (int n : lengths) {
            // Use a single random DNA string for all k for this length,
            // so that BST and hash-table timings are comparable.
            String dna = generateRandomDNA(n, rng);

            System.out.println("String length n = " + n);

            // Optional: show only a prefix so you can see a sample of the DNA sequence.
            int previewLength = Math.min(80, n);
            System.out.println("DNA prefix (first " + previewLength + " bases):");
            System.out.println(dna.substring(0, previewLength));

            System.out.println("k\tTime (ms)");

            for (int k : ks) {
                double elapsedMs = timeKmerDistribution(dna, k);
                System.out.printf("%d\t%.3f%n", k, elapsedMs);
            }

            System.out.println();
        }
    }

    /**
     * Entry point: runs the full timing experiments only.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        runTimingExperiments();
    }
}
