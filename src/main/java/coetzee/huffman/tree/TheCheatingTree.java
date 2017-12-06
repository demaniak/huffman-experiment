package coetzee.huffman.tree;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TheCheatingTree implements EncodingTree {
    
    
    private final EncodingTreeNodeImpl root;
    private final Properties treeCheatInfo;
    private final List<EncodingTreeNodeImpl>  leaveNodes;

    public TheCheatingTree(String [] leaveNodes, Properties treeCheatInfo) {
        this.root = new EncodingTreeNodeImpl();        
        this.leaveNodes = Arrays.asList(leaveNodes).stream().map(this::buildLeave).collect(Collectors.toList());
        
        this.treeCheatInfo = treeCheatInfo;
    }
    
    private EncodingTreeNodeImpl buildLeave (String encoding) {

        return root.addLeaveFromEncoding (encoding);
    }

    public EncodingTreeNode getRoot() {
       return root;
    }

    public String decode(String input) {
        if (StringUtils.isBlank(input)) {
            return "";
        }
                
        StringBuilder resultBuf = new StringBuilder();
        char []  remainder = input.toCharArray();
        
        while (remainder != null && remainder.length >0) {
            EncodingTreeNode leave = decode (remainder, root);
            resultBuf.append(leave.getValue().get());
            remainder = Arrays.copyOfRange(remainder, leave.getDepth(), remainder.length);
        }
        
        
        return resultBuf.toString();
    }

    private EncodingTreeNode decode(char[] charArray, EncodingTreeNode curNode) {
       if (curNode == null) {
           return null;
       }
       
       if (curNode.isLeave()) {
           return curNode;
       }
       
       Optional<? extends EncodingTreeNode> childNod;
       if (charArray[curNode.getDepth()] == '0') {
           childNod = curNode.getLeftNode();
       } else {
           childNod = curNode.getRightNode();
       }
       
       if (childNod.isPresent()) {
           return decode(charArray, childNod.get());
       } else {
           log.warn ("No Child nodes present at depth {}", curNode.getDepth());
           return curNode;
       }
       
    }
    
}
