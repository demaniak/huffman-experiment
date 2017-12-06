package coetzee.huffman.tree;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * Factory implementation that COULD produce different implementations of Huffman encoding trees. It could, if we had others. But we don't. So it won't.
 * 
 * 
 *
 */
@Slf4j
public class EncodingTreeFactory {

    public static EncodingTree getTree(final String[] dictionary) {

        // This is the part where we start to cheat.
        String treeName = StringUtils.join(dictionary);
        log.debug("Loading tree cheat {}", treeName);
        Properties treeCheatProps = new Properties();
        try (InputStream treeIn = EncodingTreeFactory.class.getResourceAsStream("/" + treeName)) {

            treeCheatProps.load(treeIn);
        } catch (Exception e) {
            log.error("Failed to load tree cheat {}. Might as well give up now.", treeName, e);
            System.exit(-1);
        }

        return new TheCheatingTree(dictionary, treeCheatProps);
    }

}
