/**
 * Represents a single node in an ordinary (non-balanced) Binary Search Tree.
 * Each node stores a k-mer string and the number of times it appears.
 */
public class BSTNode {

    /** The k-mer string (used as the key for ordering in the BST). */
    String kmer;

    /** The number of times this k-mer has been observed. */
    int count;

    /** Reference to the left child (k-mers that are lexicographically smaller). */
    BSTNode left;

    /** Reference to the right child (k-mers that are lexicographically greater). */
    BSTNode right;

    /**
     * Constructs a new BST node with the given k-mer and an initial count of 1.
     *
     * @param kmer the k-mer string to store in this node
     */
    public BSTNode(String kmer) {
        this.kmer = kmer;
        this.count = 1;
        this.left = null;
        this.right = null;
    }
}
