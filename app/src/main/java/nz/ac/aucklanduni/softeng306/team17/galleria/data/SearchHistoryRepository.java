package nz.ac.aucklanduni.softeng306.team17.galleria.data;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.SearchAutocompleteTerms;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo.ISearchRepository;

public class SearchHistoryRepository implements ISearchRepository {

    private final CollectionReference searches;

    public SearchHistoryRepository(FirebaseFirestore firestoreClient) {
        this.searches = firestoreClient.collection("Searches");
    }

    @Override
    public SearchAutocompleteTerms get() {
        return new SearchAutocompleteTerms(getAllSearchTerms());
    }

    @Override
    public Set<String> getPopular(int limit) {
        List<String> allTerms = getAllSearchTerms();

        Map<String, Long> searchTermCounter = allTerms.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return searchTermCounter.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    @Override
    public String create(String searchTerm) {
        return null;
    }


    private List<String> getAllSearchTerms() {
        return searches.get().getResult().getDocuments()
                .stream().map(it -> it.get("term", String.class))
                .collect(Collectors.toList());
    }
}
