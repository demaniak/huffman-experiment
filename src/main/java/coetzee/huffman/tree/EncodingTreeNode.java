package coetzee.huffman.tree;

import java.util.Optional;

/**
 * 
 * Interface for tree nodes.
 *
 *
 */
public interface EncodingTreeNode {
    /**
     * 
     * @return the value of this node, if present (non-leave nodes do not have values)
     */
    Optional<String> getValue();
    
    /**
     * 
     * @return the 0 (left) child node, if present
     */
    Optional<? extends EncodingTreeNode> getLeftNode ();
    
    /**
     * 
     * @return the 1 (right) child node, if present
     */
    Optional<? extends EncodingTreeNode> getRightNode ();
    
    /**
     * 
     * @return the parent of this node. Root node will not have parents...just like Batman.
     */
    Optional<? extends EncodingTreeNode> getParent ();
    
    /**
     * 
     * @return true if this is a leave node.
     */
    boolean isLeave ();
    
    /**
     * 
     * @return how deep are we...or "how much of the bitstring have we used"
     */
    int getDepth ();
    
    /**
     * 
     * @return the usage frequency of this node (or the sum of it's children).
     */
    long getFrequency ();
}
