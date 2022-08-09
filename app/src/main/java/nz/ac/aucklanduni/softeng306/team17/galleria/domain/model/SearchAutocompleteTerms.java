package nz.ac.aucklanduni.softeng306.team17.galleria.domain.model;

import java.util.Collection;
import java.util.Set;

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

    public void addSearchTerm(String term) {
        this.root.insert(term);
    }

    public Set<String> getMatchingTerms(String prefix) {
        return root.getAutocompleteFor(prefix);
    }
}
