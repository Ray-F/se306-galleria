package nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public abstract class FirestoreRepository {

    private final FirebaseFirestore db;
    private CollectionReference products;
    private CollectionReference searchHistory;
    private CollectionReference users;
    private CollectionReference images;

    public FirestoreRepository() {
            this.db = FirebaseFirestore.getInstance();
            this.products = db.collection("Products");
            this.searchHistory = db.collection("SearchHistory");
            this.users = db.collection("Users");
            this.images = db.collection("Images");
        }

    public FirebaseFirestore getDb() {
        return this.db;
    }

    public CollectionReference getProducts() {
        return products;
    }

    public CollectionReference getSearchHistory() {
        return searchHistory;
    }

    public CollectionReference getUsers() {
        return users;
    }

    public CollectionReference getImages() {
        return images;
    }
    
}
