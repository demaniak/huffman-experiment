package coetzee.huffman.tree;

public enum Side {
    RIGHT("1"), LEFT("0");
    
    private final String bit;
    
    private Side (String bit) {
        this.bit = bit;
    }
    
    public static Side fromBit (String bit) {
        if ("0".equals(bit)) {
            return LEFT;
        }
        return RIGHT;
    }
    
    public String bit () {
        return this.bit;
    }
}
