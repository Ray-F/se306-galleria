package nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo;

import java.util.Set;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.SearchAutocompleteTerms;

/**
 * Repository for {@link SearchAutocompleteTerms}.
 */
public interface ISearchRepository {

    SearchAutocompleteTerms get();

    /**
     * Returns the most searched terms up to the limit.
     */
    Set<String> getPopular(int limit);

    String create(String searchTerm);

}