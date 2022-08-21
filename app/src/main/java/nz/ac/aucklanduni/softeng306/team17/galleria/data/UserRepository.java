package nz.ac.aucklanduni.softeng306.team17.galleria.data;

import com.google.android.gms.tasks.OnSuccessListener;
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
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.Product;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo.IUserRepository;

public class UserRepository extends CachedRepository<User> implements IUserRepository {

    private final CollectionReference usersCollection;

    public UserRepository(FirebaseFirestore firestoreClient) {
        super();
        this.usersCollection = firestoreClient.collection("Users");
    }

    @Override
    public Single<User> get(String uuid) {
        return Single.create(emitter -> {
            User cached = getFromCacheOrNull(uuid);

            if (cached != null) {
                emitter.onSuccess(cached);
                return;
            }

            usersCollection.document(uuid).get()
                    .addOnSuccessListener((doc) -> {
                        if (doc.exists()) {
                            User user = Objects.requireNonNull(doc.toObject(UserDbo.class)).toModel();
                            addToCache(user.getId(), user);
                            emitter.onSuccess(user);
                        } else {
                            emitter.onError(new RuntimeException(String.format("User \"%s\" not found in DB.", uuid)));
                        }
                    })
                    .addOnFailureListener(System.out::println);
        });
    }

    @Override
    public Single<List<String>> getSavedProductsByUser(String uuid) {

        return Single.create(emitter -> {
            usersCollection.document(uuid).get().addOnSuccessListener((doc) -> {
                if (doc.exists()) {
                    UserDbo userDbo = Objects.requireNonNull(doc.toObject(UserDbo.class));
                    List<String> savedIds = userDbo.saved;
                    emitter.onSuccess(savedIds);
                } else {
                    emitter.onError(new RuntimeException(String.format("User \"%s\" not found in DB.", uuid)));
                }
            }).addOnFailureListener(emitter::onError);
        });
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
        addToCache(createdUser.getId(), createdUser);

        docRef.set(dbo);

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

    @Override
    public void updateSavedProductsByUser(String uuid, List<String> newSavedProducts) {
        usersCollection.document(uuid).update("saved", newSavedProducts)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        System.out.println("SUCCESSFULLY UPDATED SAVED PRODUCTS FOR " + uuid);
                    }
                });
    }
}
