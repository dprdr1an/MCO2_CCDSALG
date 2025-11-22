/**
 * Implements the k-mer distribution algorithm using the BST data structure.
 *
 * Task 2: Given a DNA string and an integer k, this class builds a BST where
 * each node stores a distinct k-mer of length k and the number of times it occurs.
 */
public class BSTKmerDistribution {

    /**
     * Builds the k-mer distribution for the given DNA sequence using a BST.
     *
     * @param dna DNA sequence over the alphabet {a, c, g, t}
     * @param k   length of each k-mer
     * @return a BST containing all distinct k-mers and their counts
     */
    public static BST buildKmerTree(String dna, int k) {
        BST tree = BST.create();
        int n = dna.length();

        // Handle invalid or trivial cases: no k-mers can be formed.
        if (k <= 0 || n < k) {
            return tree;
        }

        // Slide a window of length k across the DNA string and insert each k-mer.
        for (int i = 0; i <= n - k; i++) {
            String kmer = dna.substring(i, i + k);
            tree.insert(kmer);
        }

        return tree;
    }
}
