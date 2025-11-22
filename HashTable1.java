/**
 * djb2 Hash Function Implementation
 * Step-by-step implementation of the djb2 algorithm
 */
public class HashTable1 {
    private class Node {
        String kmer;      // The k-mer string (e.g., "ACG")
        int count;        // How many times we've seen this k-mer
        Node next;        // Link to next node in chain

        // Constructor
        Node(String kmer) {
            this.kmer = kmer;
            this.count = 1;        // First time seeing this k-mer
            this.next = null;      // No next node yet
        }
    }

    // Hash table properties
    private int tableSize;         // Size of the hash table
    private Node[] table;          // Array of linked lists
    private int collisions;        // Track number of collisions

    /**
     * Constructor - create empty hash table
     * @param size The size of the hash table (should be n = DNA length)
     */
    public HashTable1(int size) {
        this.tableSize = size;
        this.table = new Node[size];  // Create array of Nodes
        this.collisions = 0;

        // Initialize all slots to null (empty)
        for (int i = 0; i < size; i++) {
            table[i] = null;
        }
    }

    /**
     * Hash function: djb2 algorithm
     * @param kmer The k-mer to hash
     * @return Index in hash table (0 to tableSize-1)
     */
    private int hash(String kmer) {
        long hash = 5381;

        for (int i = 0; i < kmer.length(); i++) {
            char c = kmer.charAt(i);
            hash = hash * 33 + c;
        }

        return (int) (Math.abs(hash) % tableSize);
    }

    /**
     * Insert a k-mer into the hash table
     * @param kmer The k-mer to insert
     */
    public void insert(String kmer) {
        // Step 1: Calculate hash index
        int index = hash(kmer);

        // Step 2: Check if this slot is empty
        if (table[index] == null) {
            // Empty slot - create new node
            table[index] = new Node(kmer);
        } else {
            // Slot occupied - need to handle collision
            collisions++;

            // Step 3: Search through the chain for this k-mer
            Node current = table[index];

            while (current != null) {
                // If we find the k-mer, increment its count
                if (current.kmer.equals(kmer)) {
                    current.count++;
                    return;  // Done!
                }

                // If this is the last node, break
                if (current.next == null) {
                    break;
                }

                // Move to next node
                current = current.next;
            }

            // Step 4: K-mer not found in chain, add new node at end
            current.next = new Node(kmer);
        }
    }

    /**
     * Get the k-mer distribution (all k-mers and their counts)
     * @return Map of k-mer -> count
     */
    public java.util.Map<String, Integer> getDistribution() {
        java.util.Map<String, Integer> distribution = new java.util.HashMap<>();

        // Go through each slot in the table
        for (int i = 0; i < tableSize; i++) {
            Node current = table[i];

            // Go through each node in the chain
            while (current != null) {
                distribution.put(current.kmer, current.count);
                current = current.next;
            }
        }

        return distribution;
    }

    /**
     * Get the number of collisions that occurred
     * @return Number of collisions
     */
    public int getCollisions() {
        return collisions;
    }

    /**
     * Get the number of unique k-mers stored
     * @return Number of unique k-mers
     */
    public int getUniqueKmers() {
        int count = 0;

        for (int i = 0; i < tableSize; i++) {
            Node current = table[i];
            while (current != null) {
                count++;
                current = current.next;
            }
        }

        return count;
    }

    /**
     * Print the hash table (for debugging)
     */
    public void printTable() {
        System.out.println("Hash Table Contents:");
        System.out.println("=".repeat(50));

        for (int i = 0; i < tableSize; i++) {
            if (table[i] != null) {
                System.out.print("Index " + i + ": ");

                Node current = table[i];
                while (current != null) {
                    System.out.print("[" + current.kmer + ":" + current.count + "]");
                    if (current.next != null) {
                        System.out.print(" -> ");
                    }
                    current = current.next;
                }
                System.out.println();
            }
        }
    }
}
