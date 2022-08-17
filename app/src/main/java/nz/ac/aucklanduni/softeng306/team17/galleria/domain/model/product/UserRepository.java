package nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product;

import com.google.firebase.firestore.CollectionReference;

public class UserRepository extends FirestoreRepository {

    private final CollectionReference users;

    public UserRepository() {
        super();
        this.users = super.getDB().collection("Users");
    }

    public CollectionReference getUsers() {
        return this.users;
    }

}
