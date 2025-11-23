/**
 * HashTable2 using Java's built-in String.hashCode()
 *
 * This implementation is structurally identical to HashTable1,
 * but instead of the custom djb2 hash function, it uses
 * kmer.hashCode() (a polynomial rolling hash) to compute indices.
 */

public class HashTable2
{
    /**
     * Node class for separate chaining
     * Stores a k-mer string and its count
     */
    private class Node
    {
        String kmer;
        int count;
        Node next;

        Node(String kmer)
        {
            this.kmer = kmer;
            this.count = 1;
            this.next = null;
        }
    }

    private int tableSize;
    private Node[] table;
    private int collisions;

    public HashTable2(int size)
    {
        this.tableSize = size;
        this.table = new Node[size];
        this.collisions = 0;

        for (int i = 0; i < size; i++)
        {
            table[i] = null;
        }
    }

    /**
     * Hash function using Java's built-in String.hashCode()
     */
    private int hash(String kmer)
    {
        int h = kmer.hashCode();
        return Math.abs(h) % tableSize;
    }

    /**
     * Insert operation using separate chaining
     */
    public void insert(String kmer)
    {
        int index = hash(kmer);

        if (table[index] == null)
        {
            table[index] = new Node(kmer);
        }
        else
        {
            collisions++;

            Node current = table[index];

            while (current != null)
            {
                if (current.kmer.equals(kmer))
                {
                    current.count++;
                    return;
                }

                if (current.next == null)
                {
                    break;
                }

                current = current.next;
            }

            current.next = new Node(kmer);
        }
    }

    /**
     * Returns number of collisions
     */
    public int getCollisions()
    {
        return collisions;
    }

    /**
     * Counts unique stored k-mers
     */
    public int getUniqueKmers()
    {
        int count = 0;

        for (int i = 0; i < tableSize; i++)
        {
            Node current = table[i];

            while (current != null)
            {
                count++;
                current = current.next;
            }
        }

        return count;
    }

    /**
     * Debug print
     */
    public void printTable()
    {
        System.out.println("HashTable2 Contents (using String.hashCode()):");
        System.out.println("==================================================");

        for (int i = 0; i < tableSize; i++)
        {
            if (table[i] != null)
            {
                System.out.print("Index " + i + ": ");

                Node current = table[i];

                while (current != null)
                {
                    System.out.print("[" + current.kmer + ":" + current.count + "]");

                    if (current.next != null)
                    {
                        System.out.print(" -> ");
                    }

                    current = current.next;
                }

                System.out.println();
            }
        }
    }
}
