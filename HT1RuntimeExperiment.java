import java.util.Random;

public class HT1RuntimeExperiment {

    private static final char[] BASES = {'a', 'c', 'g', 't'};

    public static String generateRandomDNA(int length, Random rng) {
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = BASES[rng.nextInt(BASES.length)];
        }
        return new String(chars);
    }

    /**
     * Helper:
     * Builds the k-mer distribution by inserting all possible k-mers
     * into your HashTable1 implementation.
     */
    public static void buildKmerDistribution(HashTable1 table, String dna, int k) {
        for (int i = 0; i <= dna.length() - k; i++) {
            String kmer = dna.substring(i, i + k);
            table.insert(kmer);
        }
    }

    /**
     * Times the HT1-based (djb2) k-mer distribution.
     */
    public static double timeKmerDistribution(String dna, int k) {
        long start = System.nanoTime();

        // Create hashtable of size n (as required)
        HashTable1 ht = new HashTable1(dna.length());

        // Build the k-mer distribution
        buildKmerDistribution(ht, dna, k);

        long end = System.nanoTime();
        return (end - start) / 1_000_000.0; // ms
    }

    public static void runTimingExperiments() {
        int[] lengths = {10_000, 100_000, 1_000_000};
        int[] ks = {5, 6, 7};

        Random rng = new Random(12345);

        System.out.println("HT1-based (djb2) k-mer distribution timing results");
        System.out.println("(Times in milliseconds)\n");

        for (int n : lengths) {
            String dna = generateRandomDNA(n, rng);

            System.out.println("String length n = " + n);

            int previewLength = Math.min(80, n);
            System.out.println("DNA prefix (first " + previewLength + " bases):");
            System.out.println(dna.substring(0, previewLength));

            System.out.println("k\tTime (ms)");

            for (int k : ks) {
                double elapsed = timeKmerDistribution(dna, k);
                System.out.printf("%d\t%.3f%n", k, elapsed);
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {
        runTimingExperiments();
    }
}

