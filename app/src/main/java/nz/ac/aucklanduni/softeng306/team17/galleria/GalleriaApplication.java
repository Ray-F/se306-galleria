package nz.ac.aucklanduni.softeng306.team17.galleria;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import nz.ac.aucklanduni.softeng306.team17.galleria.data.ProductRepository;
import nz.ac.aucklanduni.softeng306.team17.galleria.di.DIProvider;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.CurrencyCode;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.AlbumArt;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.Product;

public class GalleriaApplication extends Application {

    public DIProvider diProvider;

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(getApplicationContext());

        diProvider = new DIProvider();


        // TODO: Uncomment this line and run application to populate data
        // populateDummyData();
    }

    private void populateDummyData() {
        // TODO: Usage Add image into r/drawable and reference their ID's where needed
        //  (after long description and inside Stream.of() for other images.
        //  Put the product inside the addProducts method below
        Product product1 = new AlbumArt("", "Test Example",
                                        "Test tagline", "Long description", toByteArr(R.drawable.galleria_logo),
                                        Stream.of(R.drawable.aigenerated, R.drawable.album).map(this::toByteArr).collect(Collectors.toList()),
                                        100, CurrencyCode.NZD, "#dddddd",
                                        4.7f, 200, 1, 3,
                                        Category.ALBUM, "Raymond Feng");

        Product product2 = new AlbumArt("", "Test Example no. 2",
                                        "Test tagline", "Long description", toByteArr(R.drawable.galleria_logo),
                                        Stream.of(R.drawable.aigenerated, R.drawable.album).map(this::toByteArr).collect(Collectors.toList()),
                                        100, CurrencyCode.NZD, "#dddddd",
                                        4.7f, 200, 1, 3,
                                        Category.ALBUM, "Raymond Feng");

        // TODO: Remember to add product into here
        addProducts(product1);
    }

    private byte[] toByteArr(int rId) {
        Bitmap image = BitmapFactory.decodeResource(getResources(), rId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        String base64 = Base64.getEncoder().encodeToString(byteArray).replaceFirst("data:image/jpeg;base64", "");
        return java.util.Base64.getDecoder().decode(base64);
    }


    private void addProducts(Product ... products) {
        ProductRepository productRepo = new ProductRepository(FirebaseFirestore.getInstance());

        Arrays.stream(products).forEach(productRepo::create);
    }



}
