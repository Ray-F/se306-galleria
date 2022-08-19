package nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.reactivex.rxjava3.core.Single;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.SearchAutocompleteTerms;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.Product;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo.IProductRepository;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo.ISearchRepository;

/**
 * Handles any search related query.
 */
public class SearchUseCase {

    private final ISearchRepository searchRepo;
    private final SearchAutocompleteTerms searchTerms;
    private final IProductRepository productRepo;


    public SearchUseCase(ISearchRepository searchRepo, IProductRepository productRepo) {
        // Get past searches and add these to the model
        searchTerms = searchRepo.get();

        // Add all lower case product names as search terms
        productRepo.listAll().subscribe((products) -> {
            products.stream().map(it -> it.getName().toLowerCase(Locale.ROOT))
                    .forEach(searchTerms::addSearchTerm);
        });

        this.searchRepo = searchRepo;
        this.productRepo = productRepo;
    }

    /**
     * @return the top searches to populate.
     */
    public Single<List<String>> listTopSearches(int limit) {
        return searchRepo.getPopular(limit).map(ArrayList::new);
    }

    /**
     * @return list of searches that match the provided prefix.
     */
    public List<String> getAutocompleteTerms(String prefix, int limit) {
        return searchTerms.getMatchingTerms(prefix).stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * Returns a list of products (up to a limit) that match a search term.
     *
     * If limit is -1, all products are returned.
     */
    public Single<List<Product>> makeSearch(String searchTerm, int limit, String userId) {
        String lowercase = searchTerm.toLowerCase(Locale.ROOT);

        // Add the search term both in repository and our model
        this.searchRepo.create(lowercase, userId);
        this.searchTerms.addSearchTerm(lowercase);

        return Single.create(emitter -> {
            Stream<Product> products = this.productRepo.listSortByNameMatch(searchTerm).blockingGet().stream();

            if (limit > 0) {
                products = products.limit(limit);
            }

            emitter.onSuccess(products.collect(Collectors.toList()));
        });
    }
}
