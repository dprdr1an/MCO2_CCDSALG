import java.util.Random;

/**
 * Simple DNA String Generator
 * Generates random DNA strings containing A, C, G, T
 */
public class DNAGenerator {

    /**
     * Generate a random DNA string
     * @param length How long the DNA string should be
     * @return Random DNA string
     */
    public static String generateRandom(int length) {
        char[] bases = {'A', 'C', 'G', 'T'};
        Random random = new Random();
        StringBuilder dna = new StringBuilder();

        for (int i = 0; i < length; i++) {
            dna.append(bases[random.nextInt(4)]);
        }

        return dna.toString();
    }

    /**
     * Generate a random DNA string with a seed (for testing - same seed = same DNA)
     * @param length How long the DNA string should be
     * @param seed Random seed
     * @return Random DNA string
     */
    public static String generateRandom(int length, long seed) {
        char[] bases = {'A', 'C', 'G', 'T'};
        Random random = new Random(seed);
        StringBuilder dna = new StringBuilder();

        for (int i = 0; i < length; i++) {
            dna.append(bases[random.nextInt(4)]);
        }

        return dna.toString();
    }

    /*
    // Test it
    public static void main(String[] args) {
        // Generate some random DNA
        String dna1 = generateRandom(20);
        System.out.println("Random DNA: " + dna1);

        // Generate with seed (reproducible)
        String dna2 = generateRandom(20, 42);
        String dna3 = generateRandom(20, 42);
        System.out.println("DNA with seed 42: " + dna2);
        System.out.println("Same seed again:  " + dna3);
        System.out.println("Are they equal? " + dna2.equals(dna3));

        // Generate for MCO2 sizes
        for (int n : new int[]{10_000, 100_000, 1_000_000}) {
            String dna = generateRandom(n);
            System.out.println("Generated n=" + n + " (first 20: " + dna.substring(0, 20) + "...)");
        }
    }
    */
}