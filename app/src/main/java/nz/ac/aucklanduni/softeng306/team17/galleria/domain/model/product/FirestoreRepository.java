package nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product;

import com.google.firebase.firestore.FirebaseFirestore;

public abstract class FirestoreRepository {

    private final FirebaseFirestore db;

    public FirestoreRepository() {
            this.db = FirebaseFirestore.getInstance();
        }

    public FirebaseFirestore getDB() {
        return this.db;
    }

}
