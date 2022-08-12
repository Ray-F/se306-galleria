package nz.ac.aucklanduni.softeng306.team17.galleria.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Based on the implementation found online. <br/>
 *
 * Credit: @see <a href="https://www.techiedelight.com/implement-trie-data-structure-java">tech delight</a>
 */
public class TrieNode {

    private boolean isLeafNode;

    private final Map<Character, TrieNode> children;


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

    public Set<String> getKeys() {
        return performDfs(new HashSet<>(), this, "");
    }

    /**
     * Goes down n levels using the chars[n] array to determine which child to traverse to.
     */
    public TrieNode traverseTo(char[] chars) {
        TrieNode parent = this;
        for (char c : chars) {
            parent = parent.children.get(c);

            if (parent == null) {
                return new TrieNode();
            }
        }

        return parent;
    }

    /**
     * Helper method to return all words formed from a parent entry node.
     */
    private static Set<String> performDfs(Set<String> result, TrieNode entryNode, String prev) {
        for (Map.Entry<Character, TrieNode> child : entryNode.children.entrySet()) {
            String current = prev + child.getKey();
            if (child.getValue().isLeafNode) {
                result.add(current);
            }
            performDfs(result, child.getValue(), current);
        }

        return result;
    }
}
