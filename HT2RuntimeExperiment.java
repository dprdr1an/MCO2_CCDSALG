import java.util.Random;

/**
 * Runs timing and collision experiments for the HashTable2-based
 * k-mer distribution algorithm.
 *
 * Task 5:
 *  - For random DNA strings of lengths n = 10^4, 10^5, 10^6
 *    and k = 5, 6, 7, this program measures:
 *      • Running time of the HT-based implementation
 *      • Collision counts
 *      • Number of unique k-mers stored
 */

public class HT2RuntimeExperiment
{
    /** DNA alphabet */
    private static final char[] BASES = {'a', 'c', 'g', 't'};

    /**
     * Generate a random DNA string.
     */
    public static String generateRandomDNA(int length, Random rng)
    {
        char[] chars = new char[length];

        for (int i = 0; i < length; i++)
        {
            chars[i] = BASES[rng.nextInt(BASES.length)];
        }

        return new String(chars);
    }

    /**
     * Times the k-mer distribution using HashTable2
     * and prints collision statistics.
     *
     * @param dna The DNA sequence
     * @param k   k-mer length
     * @return    Time in milliseconds
     */
    public static double timeKmerDistributionHT2(String dna, int k)
    {
        long start = System.nanoTime();

        // Hash table size = n (standard approach)
        HashTable2 ht = new HashTable2(dna.length());

        // Insert all k-mers into the hash table
        for (int i = 0; i <= dna.length() - k; i++)
        {
            ht.insert(dna.substring(i, i + k));
        }

        long end = System.nanoTime();

        double elapsedMs = (end - start) / 1_000_000.0;

        // Print stats specific to HashTable2
        System.out.println("    Collisions: " + ht.getCollisions());
        System.out.println("    Unique k-mers: " + ht.getUniqueKmers());

        return elapsedMs;
    }

    /**
     * Runs timing experiments for:
     * n ∈ {10^4, 10^5, 10^6}
     * k ∈ {5, 6, 7}
     */
    public static void runTimingExperiments()
    {
        int[] lengths = {10_000, 100_000, 1_000_000};
        int[] ks = {5, 6, 7};

        Random rng = new Random(12345);

        System.out.println("Hash Table (HT2, String.hashCode) k-mer distribution timing results");
        System.out.println("(Times in milliseconds)\n");

        for (int n : lengths)
        {
            String dna = generateRandomDNA(n, rng);

            System.out.println("String length n = " + n);
            System.out.println("k\tTime (ms)");

            for (int k : ks)
            {
                System.out.println("  k = " + k);
                double time = timeKmerDistributionHT2(dna, k);
                System.out.printf("    Time: %.3f ms\n\n", time);
            }

            System.out.println();
        }
    }

    /**
     * Entry point
     */
    public static void main(String[] args)
    {
        runTimingExperiments();
    }
}
