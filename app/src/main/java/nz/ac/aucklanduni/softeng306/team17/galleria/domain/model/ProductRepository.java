package nz.ac.aucklanduni.softeng306.team17.galleria.domain.model;

import com.google.firebase.firestore.CollectionReference;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.FirestoreRepository;

public class ProductRepository extends FirestoreRepository {

    private final CollectionReference products;

    public ProductRepository() {
        super();
        this.products = super.getDB().collection("Products");
    }

    public CollectionReference getProducts() {
        return this.products;
    }

}
