package coetzee.huffman;

import coetzee.huffman.tree.EncodingTree;
import coetzee.huffman.tree.EncodingTreeFactory;

public class HuffmanImpl implements Huffman {

    public String Decode(String archive, String[] dictionary) {
        EncodingTree encodingTree = EncodingTreeFactory.getTree(archive, dictionary);
        
        return encodingTree.decode(archive);
    }

}
