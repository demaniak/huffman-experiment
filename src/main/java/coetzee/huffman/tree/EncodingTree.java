package coetzee.huffman.tree;

public interface EncodingTree {
    /**
     * 
     * @return the root of the tree. 
     */
    EncodingTreeNode getRoot ();
    
    String decode (String input);
}
