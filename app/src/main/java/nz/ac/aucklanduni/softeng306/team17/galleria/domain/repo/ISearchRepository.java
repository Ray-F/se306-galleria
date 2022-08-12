package nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo;

import java.util.Set;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.SearchAutocompleteTerms;

/**
 * {@link IRepository} for {@link SearchAutocompleteTerms}.
 */
public interface ISearchRepository extends IRepository<SearchAutocompleteTerms> {

    SearchAutocompleteTerms get();

    Set<String> getPopular(int limit);

    String create(String searchTerm);

}
