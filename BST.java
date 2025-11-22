/**
 * Ordinary (not self-balancing) Binary Search Tree implementation
 * used for computing k-mer distributions.
 *
 * This implementation is written entirely from scratch and does not use
 * any built-in BST, tree, or ordered map classes.
 */
public class BST {

    /** Root node of the BST. A null root represents an empty tree. */
    private BSTNode root;

    /**
     * Factory method for creating an empty BST instance.
     * This corresponds to the required create() operation.
     *
     * @return a new empty BST
     */
    public static BST create() {
        return new BST();
    }

    /** Private constructor used by the factory method. */
    private BST() {
        this.root = null;
    }

    /**
     * Searches for the given k-mer in the BST.
     *
     * @param kmer the k-mer string to search for
     * @return true if the k-mer is found in the tree; false otherwise
     */
    public boolean search(String kmer) {
        return searchNode(root, kmer) != null;
    }

    /**
     * Searches for the given k-mer and returns the corresponding node.
     *
     * @param kmer the k-mer string to search for
     * @return the BSTNode containing the k-mer, or null if not found
     */
    public BSTNode searchNode(String kmer) {
        return searchNode(root, kmer);
    }

    /**
     * Recursive helper for searching from a given node.
     *
     * @param node current node in the search
     * @param kmer the k-mer string to search for
     * @return the node containing the k-mer, or null if not found
     */
    private BSTNode searchNode(BSTNode node, String kmer) {
        if (node == null) {
            return null;
        }

        int cmp = kmer.compareTo(node.kmer);

        if (cmp == 0) {
            return node;
        } else if (cmp < 0) {
            return searchNode(node.left, kmer);
        } else { // cmp > 0
            return searchNode(node.right, kmer);
        }
    }

    /**
     * Inserts the given k-mer into the BST.
     * If the k-mer already exists, its count is incremented.
     * This corresponds to the required insert() operation.
     *
     * @param kmer the k-mer string to insert or increment
     */
    public void insert(String kmer) {
        root = insertOrIncrement(root, kmer);
    }

    /**
     * Recursive helper that inserts a new k-mer node or increments the count
     * of an existing node.
     *
     * @param node current node in the insertion traversal
     * @param kmer the k-mer string to insert or increment
     * @return the (possibly updated) node reference
     */
    private BSTNode insertOrIncrement(BSTNode node, String kmer) {
        if (node == null) {
            return new BSTNode(kmer);
        }

        int cmp = kmer.compareTo(node.kmer);

        if (cmp == 0) {
            // The k-mer already exists in this node: increment its count.
            node.count++;
        } else if (cmp < 0) {
            // Insert into the left subtree.
            node.left = insertOrIncrement(node.left, kmer);
        } else {
            // Insert into the right subtree.
            node.right = insertOrIncrement(node.right, kmer);
        }

        return node;
    }

    /**
     * Destroys the BST by disconnecting all its nodes.
     * In Java, this allows the garbage collector to reclaim the memory.
     * This corresponds to the required destroy() operation.
     */
    public void destroy() {
        destroyRecursive(root);
        root = null;
    }

    /**
     * Recursive helper that disconnects all nodes in the subtree rooted at node.
     *
     * @param node current node to be cleaned up
     */
    private void destroyRecursive(BSTNode node) {
        if (node == null) {
            return;
        }

        destroyRecursive(node.left);
        destroyRecursive(node.right);

        node.left = null;
        node.right = null;
    }

    /**
     * Performs an in-order traversal of the BST and prints each k-mer
     * together with its count. This is primarily used for debugging
     * or verifying correctness on small inputs.
     */
    public void inorderPrint() {
        inorderPrint(root);
    }

    /**
     * Recursive helper for in-order traversal.
     *
     * @param node current node in the traversal
     */
    private void inorderPrint(BSTNode node) {
        if (node == null) {
            return;
        }
        inorderPrint(node.left);
        System.out.println(node.kmer + "\t" + node.count);
        inorderPrint(node.right);
    }

    /**
     * Returns the root node of the BST.
     *
     * @return the root node, or null if the tree is empty
     */
    public BSTNode getRoot() {
        return root;
    }
}
