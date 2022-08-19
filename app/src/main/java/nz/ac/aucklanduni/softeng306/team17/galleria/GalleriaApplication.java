package nz.ac.aucklanduni.softeng306.team17.galleria;

import android.app.Application;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.TestOnly;

import java.util.ArrayList;

import nz.ac.aucklanduni.softeng306.team17.galleria.data.ProductRepository;
import nz.ac.aucklanduni.softeng306.team17.galleria.di.DIProvider;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.CurrencyCode;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.PhotographicArt;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.Product;

public class GalleriaApplication extends Application {

    public DIProvider diProvider;

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(getApplicationContext());

        diProvider = new DIProvider();


//        populateDummyData();
    }

    public void populateDummyData() {
        String base64 = "";
        byte[] heroImg = java.util.Base64.getDecoder().decode(base64);
        Product product = new PhotographicArt("", "White",
                                              "Here lay the misty mountains covered in snow", "Long description", heroImg,
                                              new ArrayList<>(), 100, CurrencyCode.NZD, "#dddddd",
                                              4.7f, 200, 1, 3,
                                              Category.PHOTOGRAPHIC, "Canon R5, 24-70mm F2.8");

        ProductRepository productRepo = new ProductRepository(FirebaseFirestore.getInstance());
        productRepo.create(product);
    }

}
