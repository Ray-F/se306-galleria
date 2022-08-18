package nz.ac.aucklanduni.softeng306.team17.galleria.data;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.Single;
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
    public Single<List<Product>> listSortByNameMatch(String nameMatch) {
        return listAll().map((products) ->
            products.stream().filter(it -> it.isNameMatch(nameMatch)).collect(Collectors.toList())
        );
    }

    @Override
    public Single<List<Product>> listSortByRating(int limit) {
        return Single.create(emitter -> {
            productsCollection.orderBy(ProductDbo.RATING_KEY).limit(limit).get()
                    .addOnSuccessListener((res) -> {
                        List<Product> products = res.getDocuments().stream()
                                .map((it) -> Objects.requireNonNull(it.toObject(ProductDbo.class)).toModel())
                                .collect(Collectors.toList());
                        emitter.onSuccess(products);
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    @Override
    public Single<List<Product>> listByCategory(Category category) {
        return Single.create(emitter -> {
            System.out.println("Test");
            productsCollection.whereEqualTo(ProductDbo.CATEGORY_KEY, category).get()
                    .addOnSuccessListener((res) -> {
                        List<Product> products = res.getDocuments().stream()
                                .map((it) -> Objects.requireNonNull(it.toObject(ProductDbo.class)).toModel())
                                .collect(Collectors.toList());

                        System.out.println(products);
                        emitter.onSuccess(products);
                    })
                    .addOnFailureListener(System.out::println);
        });
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
    public Single<List<Product>> listAll() {
        return Single.create(emitter -> {
            productsCollection.get()
                    .addOnSuccessListener(res -> {
                        List<Product> products = res.getDocuments().stream()
                                .map((it) -> Objects.requireNonNull(it.toObject(ProductDbo.class)).toModel())
                                .collect(Collectors.toList());
                        emitter.onSuccess(products);
                    })
                    .addOnFailureListener(emitter::onError);
        });
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
