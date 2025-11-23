import java.util.Random;

/**
 * Combined runtime experiments for:
 *  - BST-based k-mer distribution
 *  - HashTable1 (DJB2)
 *  - HashTable2 (String.hashCode / polynomial rolling hash)
 *
 * Ensures that for each n and k, ALL THREE methods use the SAME DNA sequence.
 */
public class CombinedRuntimeExperiment {

    /** DNA alphabet */
    private static final char[] BASES = {'a', 'c', 'g', 't'};

    /**
     * Generate a random DNA string.
     */
    public static String generateRandomDNA(int length, Random rng) {
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = BASES[rng.nextInt(BASES.length)];
        }
        return new String(chars);
    }

    /**
     * Time the BST-based k-mer distribution.
     */
    public static double timeBST(String dna, int k) {
        long start = System.nanoTime();
        BST tree = BSTKmerDistribution.buildKmerTree(dna, k);
        long end = System.nanoTime();

        // cleanup to match destroy() requirement
        tree.destroy();

        return (end - start) / 1_000_000.0;  // ms
    }

    /**
     * Time HashTable1 (DJB2).
     * Returns [timeMs, collisions].
     */
    public static double[] timeHT1(String dna, int k) {
        long start = System.nanoTime();

        // adjust constructor as needed (capacity, etc.)
        HashTable1 ht = new HashTable1(dna.length());
        for (int i = 0; i <= dna.length() - k; i++) {
            ht.insert(dna.substring(i, i + k));
        }

        long end = System.nanoTime();
        double timeMs = (end - start) / 1_000_000.0;

        int collisions = ht.getCollisions();

        return new double[] { timeMs, collisions };
    }

    /**
     * Time HashTable2 (hashCode / polynomial rolling hash).
     * Returns [timeMs, collisions].
     */
    public static double[] timeHT2(String dna, int k) {
        long start = System.nanoTime();

        HashTable2 ht = new HashTable2(dna.length());
        for (int i = 0; i <= dna.length() - k; i++) {
            ht.insert(dna.substring(i, i + k));
        }

        long end = System.nanoTime();
        double timeMs = (end - start) / 1_000_000.0;

        int collisions = ht.getCollisions();

        return new double[] { timeMs, collisions };
    }

    /**
     * Run timing experiments for n in {10^4, 10^5, 10^6}
     * and k in {5, 6, 7}, using the SAME DNA for all three methods.
     */
    public static void runTimingExperiments() {
        int[] lengths = {10_000, 100_000, 1_000_000};
        int[] ks = {5, 6, 7};

        // Use current time–based seed: different sequence each time you run.
        Random rng = new Random();

        // Generate one big DNA string of max length
        int maxLength = 1_000_000;
        String baseDna = generateRandomDNA(maxLength, rng);

        System.out.println("BST + HashTable1 (DJB2) + HashTable2 (hashCode) k-mer distribution timing results");
        System.out.println("(Times are in milliseconds)\n");

        for (int n : lengths) {
            // For each n, use a prefix of the same base DNA
            String dna = baseDna.substring(0, n);

            System.out.println("String length n = " + n);
            int previewLength = Math.min(80, n);
            System.out.println("DNA prefix (first " + previewLength + " bases):");
            System.out.println(dna.substring(0, previewLength));
            System.out.println();

            String line = "+----+-----------+----------------+------------+---------------------+------------+";
            System.out.println(line);
            System.out.printf("| %2s | %9s | %14s | %10s | %19s | %10s |%n",
                    "k", "BST (ms)", "HT1_DJB2 (ms)", "HT1 coll", "HT2_hashCode (ms)", "HT2 coll");
            System.out.println(line);

            for (int k : ks) {
                double bstTime = timeBST(dna, k);
                double[] ht1Res = timeHT1(dna, k);
                double[] ht2Res = timeHT2(dna, k);

                double ht1Time = ht1Res[0];
                int ht1Coll = (int) ht1Res[1];

                double ht2Time = ht2Res[0];
                int ht2Coll = (int) ht2Res[1];

                System.out.printf("| %2d | %9.3f | %14.3f | %10d | %19.3f | %10d |%n",
                        k, bstTime, ht1Time, ht1Coll, ht2Time, ht2Coll);
            }

            System.out.println(line);
            System.out.println();
        }
    }

    /**
     * Entry point: run combined comparison.
     */
    public static void main(String[] args) {
        runTimingExperiments();
    }
}
