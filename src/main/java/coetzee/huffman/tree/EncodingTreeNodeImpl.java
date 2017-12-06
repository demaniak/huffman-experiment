package coetzee.huffman.tree;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of= {"valueInternal","depth","encoding"},includeFieldNames=false)
@Slf4j
public class EncodingTreeNodeImpl implements EncodingTreeNode {
    private String valueInternal;
    private EncodingTreeNodeImpl leftNodeInternal;
    private EncodingTreeNodeImpl rightNodeInternal;
    private EncodingTreeNodeImpl parentInternal;
    private int depth;
    private long frequency;
    //Only present in leave nodes.
    private String encoding;

    public Optional<EncodingTreeNodeImpl> getLeftNode() {
        return Optional.ofNullable(leftNodeInternal);
    }

    public Optional<EncodingTreeNodeImpl> getRightNode() {
        return Optional.ofNullable(rightNodeInternal);
    }

    public Optional<EncodingTreeNodeImpl> getParent() {
        return Optional.ofNullable(parentInternal);
    }
    
    public void setParent (EncodingTreeNodeImpl p) {
        this.parentInternal = p;
    }
    
    public void setLeftNode (EncodingTreeNodeImpl l) {
        this.leftNodeInternal = l;
    }
    
    public void setRightNode (EncodingTreeNodeImpl r) {
        this.rightNodeInternal = r;
    }
    
    public void setValue (String val) {
        this.valueInternal = val;
    }

    public boolean isLeave() {
       return getValue().isPresent();
    }

    public Optional<String> getValue() {
        return Optional.ofNullable(valueInternal);
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public EncodingTreeNodeImpl addNode(char c) {
        EncodingTreeNodeImpl newNode = buildChildNode(null);
        if ('0' == c) {            
            leftNodeInternal =  leftNodeInternal != null ? leftNodeInternal :newNode;
            return leftNodeInternal;
        } else {
            rightNodeInternal = rightNodeInternal != null ? rightNodeInternal : newNode;
            return rightNodeInternal;
        }        
    }
    
    public EncodingTreeNodeImpl addNode(String c) {
        return addNode(c.charAt(0));
    }

   

    private EncodingTreeNodeImpl buildChildNode(String val) {
        return EncodingTreeNodeImpl.builder()
        .valueInternal(val)
        .parentInternal(this)
        .depth(getDepth()+1)                
        .build();
    }

    public long getFrequency() {
        return frequency;
    }

    public void setFrequency(long frequency) {
        this.frequency = frequency;
    }

    public EncodingTreeNodeImpl addLeaveFromEncoding(String encoding) {
        //@formatter:off
        EncodingTreeNodeImpl ret = EncodingTreeNodeImpl
                .builder()
                .encoding(encoding)
                .parentInternal(this)
                .depth(getDepth()+1)
                .valueInternal(encoding)
                .build();
        //@formatter:on
        
        trickleDownLeave(ret, this, encoding);
        
        return ret;
    }
    
    private void trickleDownLeave (EncodingTreeNodeImpl leaf, EncodingTreeNodeImpl parent, String remainingEncoding) {
        if (StringUtils.isAllBlank(remainingEncoding)) {
            log.warn ("We should not have reached this part");
            return;
        }
        
        String bit = remainingEncoding.substring(0, 1);
        String remainder = remainingEncoding.length()>1 ? remainingEncoding.substring(1) : "";      
        
        Side side = Side.fromBit(bit);
        
        if (StringUtils.isNotBlank(remainder)) {
            //We still have encoding left
            
            EncodingTreeNodeImpl nextParent = parent.getChildNode (side);
                  
            trickleDownLeave(leaf, nextParent, remainder);
            
        } else {
            //end of the road buckaroo
            leaf.setDepth(parent.getDepth()+1);
            switch (side) {
                case LEFT:
                    Optional<EncodingTreeNodeImpl> left= parent.getLeftNode();
                    if (left.isPresent()) {
                        left.get().setLeftNode(leaf);
                                                
                    } else {
                        log.trace("Adding left leaf {} to parent {}", leaf, parent);
                        parent.setLeftNode(leaf);
                    }
                    break;
                case RIGHT:
                    Optional<EncodingTreeNodeImpl> right = parent.getRightNode();
                    if (right.isPresent()) {
                        right.get().setRightNode(leaf);
                    } else {
                        log.trace("Adding right leaf {} to parent {}", leaf, parent);
                        parent.setRightNode( leaf);
                    }
                    break;
            }
            
            
        }
    }

    private EncodingTreeNodeImpl getChildNode(String bit) {
        return addNode(bit.charAt(0));
    }
    
    private EncodingTreeNodeImpl getChildNode(Side side) {
        return addNode(side.bit());
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

}
;