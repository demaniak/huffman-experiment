package coetzee.huffman.tree;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of= {"valueInternal","frequency"},includeFieldNames=false)
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
        EncodingTreeNodeImpl newNode = buildChildNode(Character.toString(c));
        if ('0' == c) {            
            leftNodeInternal =  leftNodeInternal != null ? leftNodeInternal :newNode;
        } else {
            rightNodeInternal = rightNodeInternal != null ? rightNodeInternal : newNode;
        }
        
        return newNode;
    }

   

    private EncodingTreeNodeImpl buildChildNode(String val) {
        return EncodingTreeNodeImpl.builder()
        .valueInternal(val + "(" + (getDepth()+1) + ")")
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
        
        return ret;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

}
;