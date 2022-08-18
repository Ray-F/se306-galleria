package nz.ac.aucklanduni.softeng306.team17.galleria.data;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.User;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo.IUserRepository;

public class UserRepository implements IUserRepository {

    private final CollectionReference usersCollection;

    public UserRepository(FirebaseFirestore firestoreClient) {
        super();
        this.usersCollection = firestoreClient.collection("Users");
    }

    @Override
    public User get(String id) {
        DocumentSnapshot doc = usersCollection.document(id).get().getResult();

        // Return new user if it exists
        if (doc.exists()) return Objects.requireNonNull(doc.toObject(UserDbo.class)).toModel();

        // Return null if user does not exist
        return null;
    }

    @Override
    public List<User> listAll() {
        List<DocumentSnapshot> docs = usersCollection.get().getResult().getDocuments();

        return docs.stream()
                .map((it) -> Objects.requireNonNull(it.toObject(UserDbo.class)).toModel())
                .collect(Collectors.toList());
    }

    @Override
    public User create(User item) {
        UserDbo dbo = UserDbo.fromModel(item);

        // Using .document() to generate a new ID without having to re-query the same object.
        DocumentReference docRef = usersCollection.document();
        dbo.id = docRef.getId();
        User createdUser = dbo.toModel();

        docRef.set(dbo).getResult();

        return createdUser;
    }

    @Override
    public User getByEmail(String email) {
        List<DocumentSnapshot> docs = usersCollection.whereEqualTo(UserDbo.EMAIL_KEY, email)
                .get().getResult().getDocuments();

        if (docs.size() > 0) {
            return Objects.requireNonNull(docs.get(0).toObject(UserDbo.class)).toModel();
        }

        return null;
    }
}
