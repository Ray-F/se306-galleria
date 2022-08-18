package nz.ac.aucklanduni.softeng306.team17.galleria.data;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.Product;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo.IProductRepository;

public class ProductRepository implements IProductRepository {

    private final CollectionReference productsCollection;

    public ProductRepository(FirebaseFirestore firestoreClient) {
        super();
        this.productsCollection = firestoreClient.collection("Products");
    }

    @Override
    public List<Product> listSortByNameMatch(String nameMatch) {
        return listAll().stream().filter(it -> it.isNameMatch(nameMatch)).collect(Collectors.toList());
    }

    @Override
    public Single<List<Product>> listSortByRating(int limit) {
        return Single.create(emitter ->
            productsCollection.orderBy(ProductDbo.RATING_KEY).limit(limit).get()
                    .addOnSuccessListener((res) -> {
                        List<Product> products = res.getDocuments().stream()
                                .map((it) -> Objects.requireNonNull(it.toObject(ProductDbo.class)).toModel())
                                .collect(Collectors.toList());
                        emitter.onSuccess(products);
                    })
                    .addOnFailureListener(emitter::onError)
        );
    }

    @Override
    public List<Product> listByCategory(Category category) {
        List<DocumentSnapshot> docs = productsCollection
                .whereEqualTo(ProductDbo.CATEGORY_KEY, category)
                .get().getResult().getDocuments();

        return docs.stream()
                .map((it) -> Objects.requireNonNull(it.toObject(ProductDbo.class)).toModel())
                .collect(Collectors.toList());
    }

    @Override
    public Product get(String id) {
        DocumentSnapshot doc = productsCollection.document(id).get().getResult();

        if (doc.exists()) {
            return Objects.requireNonNull(doc.toObject(ProductDbo.class)).toModel();
        } else {
            return null;
        }
    }

    @Override
    public List<Product> listAll() {
        System.out.println("Before");
        try {
            List<DocumentSnapshot> docs = Tasks.await(productsCollection.get()).getDocuments();

            return docs.stream()
                    .map((it) -> Objects.requireNonNull(it.toObject(ProductDbo.class)).toModel())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<>();
        }
    }

    @Override
    public Product create(Product item) {
        ProductDbo dbo = ProductDbo.fromModel(item);

        // Using .document() to generate a new ID without having to re-query the same object.
        DocumentReference docRef = productsCollection.document();
        dbo.id = docRef.getId();
        Product createdProduct = dbo.toModel();

        docRef.set(dbo).getResult();

        return createdProduct;
    }
}
