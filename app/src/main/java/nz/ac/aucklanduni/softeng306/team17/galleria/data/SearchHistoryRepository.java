package nz.ac.aucklanduni.softeng306.team17.galleria.data;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.SearchAutocompleteTerms;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo.ISearchRepository;

public class SearchHistoryRepository implements ISearchRepository {

    private final CollectionReference searches;

    public SearchHistoryRepository(FirebaseFirestore firestoreClient) {
        this.searches = firestoreClient.collection("SearchHistory");
    }

    @Override
    public SearchAutocompleteTerms get() {
        SearchAutocompleteTerms searchAutocompleteTerms = new SearchAutocompleteTerms();
        getAllSearchTerms().subscribe(terms -> {
            terms.forEach(searchAutocompleteTerms::addSearchTerm);
        });
        return searchAutocompleteTerms;
    }

    @Override
    public Single<List<String>> getPopular(int limit) {
        return getAllSearchTerms().map(allTerms -> {
            Map<String, Long> searchTermCounter = allTerms.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            return searchTermCounter.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .limit(limit)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        });
    }

    @Override
    public String create(String searchTerm, String userId) {
        SearchHistoryDbo dbo = new SearchHistoryDbo();
        dbo.searchTerm = searchTerm;
        dbo.userId = userId;

        DocumentReference ref = searches.document();
        dbo.id = ref.getId();

        ref.set(dbo);

        return searchTerm;
    }

    private Single<List<String>> getAllSearchTerms() {
        return Single.create(emitter -> {
            searches.get()
                    .addOnSuccessListener((docs) -> {
                        List<String> searchTerms = docs.getDocuments().stream()
                                .map(it -> Objects.requireNonNull(it.toObject(SearchHistoryDbo.class)).searchTerm)
                                .collect(Collectors.toList());

                        emitter.onSuccess(searchTerms);
                    });
        });
    }
}
