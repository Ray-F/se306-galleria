package nz.ac.aucklanduni.softeng306.team17.galleria.data;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.User;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo.IUserRepository;

public class UserRepository implements IUserRepository {

    private final CollectionReference usersCollection;
    private final CollectionReference userSavedProductsCollection;

    public UserRepository(FirebaseFirestore firestoreClient) {
        super();
        this.usersCollection = firestoreClient.collection("Users");
        this.userSavedProductsCollection = firestoreClient.collection("SavedProducts");
    }

    @Override
    public User get(String id) {
        DocumentSnapshot doc = usersCollection.document(id).get().getResult();

        // Return new user if it exists
        if (doc.exists()) return Objects.requireNonNull(doc.toObject(UserDbo.class)).toModel();

        // Return null if user does not exist
        return null;
    }

    // TODO: Complete this method with the associated changes to DBO for saved users
    @Override
    public Single<List<String>> getProductsByUser(String userId) {
        return Single.create(emitter -> emitter.onSuccess(new ArrayList<>()));
    }

    @Override
    public Single<List<User>> listAll() {
        return Single.create(emitter -> {
            usersCollection.get()
                    .addOnSuccessListener((res) -> {
                        List<User> users = res.getDocuments().stream()
                                .map((it) -> Objects.requireNonNull(it.toObject(UserDbo.class)).toModel())
                                .collect(Collectors.toList());
                        emitter.onSuccess(users);
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    @Override
    public User create(User item) {
        UserDbo dbo = UserDbo.fromModel(item);

        // Using .document() to generate a new ID without having to re-query the same object.
        DocumentReference docRef = usersCollection.document();
        dbo.id = docRef.getId();
        User createdUser = dbo.toModel();


        docRef.set(dbo).addOnCompleteListener(res -> {
            System.out.println("Finished saving " + item.getId() + " to the DB.");
        });

        return createdUser;
    }

    @Override
    public Single<User> getByEmail(String email) {
        return Single.create(emitter -> {
            usersCollection.whereEqualTo(UserDbo.EMAIL_KEY, email).get()
                    .addOnSuccessListener(res -> {
                        if (!res.getDocuments().isEmpty()) {
                            User user = Objects.requireNonNull(res.getDocuments().get(0).toObject(UserDbo.class)).toModel();
                            emitter.onSuccess(user);
                        } else {
                            emitter.onSuccess(null);
                        }
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }
}
