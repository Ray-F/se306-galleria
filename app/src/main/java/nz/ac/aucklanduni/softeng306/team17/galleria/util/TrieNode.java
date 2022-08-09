package nz.ac.aucklanduni.softeng306.team17.galleria.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Based on the implementation found online. <br/>
 *
 * Credit: @see <a href="https://www.techiedelight.com/implement-trie-data-structure-java">tech delight</a>
 */
// TODO: This class still needs to be finished for allowing search and printing of words.
public class TrieNode {

    private boolean isLeafNode;

    private Map<Character, TrieNode> children;


    public TrieNode() {
        isLeafNode = false;
        children = new HashMap<>();
    }

    public void insert(String entry) {
        TrieNode curr = this;

        for (char c : entry.toCharArray()) {
            // Ignore this warning - putIfAbsent means there will always be a child element for c.
            curr.children.putIfAbsent(c, new TrieNode());
            curr = curr.children.get(c);
        }

        curr.isLeafNode = true;
    }

    public Set<String> getAllSearchTerms() {

        performDfs(this);

        return new HashSet<>();
    }

    public Set<String> getAutocompleteFor(String current) {

        return new HashSet<>();
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    private static void performDfs(TrieNode entryNode) {
        for (Map.Entry<Character, TrieNode> child : entryNode.getChildren().entrySet()) {
            System.out.println(child.getKey());
            performDfs(child.getValue());
        }
    }

}
