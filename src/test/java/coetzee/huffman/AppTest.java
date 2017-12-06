package coetzee.huffman;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import coetzee.huffman.Huffman;
import coetzee.huffman.HuffmanImpl;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void test() {
        Huffman t = new HuffmanImpl();
        assertEquals("BDC", t.Decode("101101", new String[] { "00", "10", "01", "11" }));
        assertEquals("CBAC", t.Decode("10111010", new String[] { "0", "111", "10" }));
        assertEquals("BBBABBAABBABBAAABBA", t.Decode("0001001100100111001", new String[] { "1", "0" }));
        assertEquals("EGGFAC",
                t.Decode("111011011000100110", new String[] { "010", "00", "0110", "0111", "11", "100", "101" }));
        assertEquals("DBHABBACAIAIC", t.Decode("001101100101100110111101011001011001010",
                new String[] { "110", "011", "10", "0011", "00011", "111", "00010", "0010", "010", "0000" }));
        assertEquals("NITXOQRE",
                t.Decode("01001111010010010011001000001010001101001000010",
                        new String[] { "01101", "01110", "01001110", "0100110", "00010", "01000", "0101", "0000",
                                "001001", "111", "010011111", "1010", "100", "0100111101", "00101", "01100", "00011",
                                "010010", "1011", "0011", "1101", "0100111100", "01111", "001000", "1100" }));
        assertEquals("BBBABBAABBABBAA", t.Decode("000100110010011", new String[] { "1", "0" }));
    }
}
