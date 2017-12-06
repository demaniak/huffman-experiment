package coetzee.huffman.tree;

/**
 * 
 * Represents a Huffman encoding tree.
 * 
 *
 */
public interface EncodingTree {
    /**
     * 
     * @return the root of the tree. 
     */
    EncodingTreeNode getRoot ();
    
    /**
     * 
     * @param input the encoded string
     * @return the decodded string hopefully
     */
    String decode (String input);
}
