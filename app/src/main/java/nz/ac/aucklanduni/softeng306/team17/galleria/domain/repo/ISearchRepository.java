package nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.SearchAutocompleteTerms;

/**
 * Repository for {@link SearchAutocompleteTerms}.
 */
public interface ISearchRepository {

    SearchAutocompleteTerms get();

    /**
     * Returns the most searched terms up to the limit.
     */
    Single<List<String>> getPopular(int limit);

    String create(String searchTerm, String userId);

}