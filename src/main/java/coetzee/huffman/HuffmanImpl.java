package coetzee.huffman;

import coetzee.huffman.tree.EncodingTree;
import coetzee.huffman.tree.EncodingTreeFactory;

/**
 * 
 * Default implementation of {@link Huffman}. Currently thin wrapper around a {@link EncodingTree} implementation.
 * 
 *
 */
public class HuffmanImpl implements Huffman {

    public String Decode(String archive, String[] dictionary) {
        EncodingTree encodingTree = EncodingTreeFactory.getTree( dictionary);
        
        return encodingTree.decode(archive);
    }

}
