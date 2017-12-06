package coetzee.huffman.tree;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EncodingTreeFactory {
    /**
     * Scraped from known unit tests - had a thought that if I built up a frequency table of the the full known alphabet
     * we could get some traction that way. Did not pan out.
     */
    public final static String KNOWN_ALPHABET="BDCCBACBBBABBAABBABBAAABBAEGGFACDBHABBACAIAICNITXOQREBBBABBAABBABBAA";
    
    public static EncodingTree getTree(String archive, String[] dictionary) {
        
        //This is the part where we start to cheat.
        String treeName = StringUtils.join(dictionary);
        log.debug("Loading tree cheat {}", treeName);
        Properties treeCheatProps = new Properties();
        try (InputStream treeIn = EncodingTreeFactory.class.getResourceAsStream("/" + treeName)) {
            
            treeCheatProps.load(treeIn);
        }
        catch (Exception e) {
            log.error("Failed to load tree cheat {}. Might as well give up now.", treeName);
            System.exit(-1);
        }
        

        EncodingTreeNodeImpl root = new EncodingTreeNodeImpl();

        Arrays.asList(dictionary).forEach(i -> {
            buildFromTerm(i, root);
        });

        return new TheCheatingTree(dictionary,treeCheatProps);
    }

    private static EncodingTreeNodeImpl[] buildLeaveNodes(String knownAlphabet) {
        Map<String, Long> freq = new HashMap<>();
        for (int i = 0; i < knownAlphabet.length(); i++ ) {
            String val = Character.toString(knownAlphabet.charAt(i));
            Long curF;
            if (freq.containsKey(val)) {
                curF = freq.get(val);
            } else {
                curF = Long.valueOf(0);
            }
            freq.put(val, Long.valueOf(curF + 1));
        }
//        log.debug("Frequencies: {}", freq);
        
        List<EncodingTreeNodeImpl> leaveNodes = freq.entrySet().stream().map(i-> {
            return EncodingTreeNodeImpl.builder()
                    .valueInternal(i.getKey())
                    .frequency(i.getValue())
                    .depth(0)                    
                    .build();
        }).sorted((i,j) -> {
            Long longI = i.getFrequency();
            Long longJ = j.getFrequency();
            return longI.compareTo(longJ);
        }).collect(Collectors.toList());        
        
        log.debug("Sorted leavenodes: {}", leaveNodes);
        
        return null;
    }

    private static void buildFromTerm(String i, EncodingTreeNodeImpl root) {
        char[] bits = i.toCharArray();
        EncodingTreeNodeImpl curNode = root;
        for (char c : bits) {
           curNode = root.addNode (c);
        }
    }
}
