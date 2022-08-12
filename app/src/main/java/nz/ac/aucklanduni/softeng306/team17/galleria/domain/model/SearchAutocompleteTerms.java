package nz.ac.aucklanduni.softeng306.team17.galleria.domain.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.softeng306.team17.galleria.util.TrieNode;

/**
 * The domain model to improve searching efficiency on all database terms.
 */
public class SearchAutocompleteTerms {

    /**
     * The underlying data structure behind getting autocomplete terms.
     */
    private final TrieNode root;


    public SearchAutocompleteTerms(Collection<String> words) {
        this.root = new TrieNode();

        words.forEach(this.root::insert);
    }

    /**
     * Add a search term for indexing.
     */
    public void addSearchTerm(String term) {
        this.root.insert(term);
    }

    /**
     * Return all matching terms with the provided prefix.
     */
    public Set<String> getMatchingTerms(String prefix) {
        TrieNode parent = root.traverseTo(prefix.toCharArray());

        return parent.getKeys().stream()
                .map(it -> prefix + it)
                .collect(Collectors.toSet());
    }
}
