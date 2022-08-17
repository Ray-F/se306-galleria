package nz.ac.aucklanduni.softeng306.team17.galleria.domain.model;

import com.google.firebase.firestore.CollectionReference;

public class SearchHistoryRepository extends ProductRepository {

    private final CollectionReference searchHistory;

    public SearchHistoryRepository() {
        super();
        this.searchHistory = super.getDB().collection("SearchHistory");
    }

    public CollectionReference getProducts() {
        return this.searchHistory;
    }

}
