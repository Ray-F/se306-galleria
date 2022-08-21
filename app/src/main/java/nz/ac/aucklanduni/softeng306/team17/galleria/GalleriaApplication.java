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
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.PaintingArt;
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
        Product product1 = new AlbumArt("", "Astroworld",
                                        "Astroworld", "Astroworld is the third studio album by American rapper Travis Scott. " +
                "It was released on August 3, 2018, through Cactus Jack Records and Grand Hustle Records, and distributed by Epic Records. " +
                "The album follows his second studio album Birds in the Trap Sing McKnight (2016), and his collaborative album Huncho Jack, Jack Huncho (2017) with Quavo.",
                toByteArr(R.drawable.galleria_logo),
                                        Stream.of(R.drawable.aigenerated, R.drawable.album).map(this::toByteArr).collect(Collectors.toList()),
                                        12.93f, CurrencyCode.NZD, "#dddddd",
                                        4.7f, 200, 1, 3,
                                        Category.ALBUM, "Raymond Feng");

        Product product2 = new AlbumArt("", "Test Example no. 2",
                                        "Test tagline", "Long description", toByteArr(R.drawable.galleria_logo),
                                        Stream.of(R.drawable.aigenerated, R.drawable.album).map(this::toByteArr).collect(Collectors.toList()),
                                        100, CurrencyCode.NZD, "#dddddd",
                                        4.7f, 200, 1, 3,
                                        Category.ALBUM, "Raymond Feng");

        Product product3 = new AlbumArt("", "Test Example no. 2",
                "Test tagline", "Long description", toByteArr(R.drawable.galleria_logo),
                Stream.of(R.drawable.aigenerated, R.drawable.album).map(this::toByteArr).collect(Collectors.toList()),
                100, CurrencyCode.NZD, "#dddddd",
                4.7f, 200, 1, 3,
                Category.ALBUM, "Raymond Feng");

        Product product4 = new AlbumArt("", "Test Example no. 2",
                "Test tagline", "Long description", toByteArr(R.drawable.galleria_logo),
                Stream.of(R.drawable.aigenerated, R.drawable.album).map(this::toByteArr).collect(Collectors.toList()),
                100, CurrencyCode.NZD, "#dddddd",
                4.7f, 200, 1, 3,
                Category.ALBUM, "Raymond Feng");

        Product product5 = new AlbumArt("", "Test Example no. 2",
                "Test tagline", "Long description", toByteArr(R.drawable.galleria_logo),
                Stream.of(R.drawable.aigenerated, R.drawable.album).map(this::toByteArr).collect(Collectors.toList()),
                100, CurrencyCode.NZD, "#dddddd",
                4.7f, 200, 1, 3,
                Category.ALBUM, "Raymond Feng");

        Product product6 = new AlbumArt("", "Test Example no. 2",
                "Test tagline", "Long description", toByteArr(R.drawable.galleria_logo),
                Stream.of(R.drawable.aigenerated, R.drawable.album).map(this::toByteArr).collect(Collectors.toList()),
                100, CurrencyCode.NZD, "#dddddd",
                4.7f, 200, 1, 3,
                Category.ALBUM, "Raymond Feng");

        Product product7 = new AlbumArt("", "Test Example no. 2",
                "Test tagline", "Long description", toByteArr(R.drawable.galleria_logo),
                Stream.of(R.drawable.aigenerated, R.drawable.album).map(this::toByteArr).collect(Collectors.toList()),
                100, CurrencyCode.NZD, "#dddddd",
                4.7f, 200, 1, 3,
                Category.ALBUM, "Raymond Feng");

        Product product8 = new AlbumArt("", "Test Example no. 2",
                "Test tagline", "Long description", toByteArr(R.drawable.galleria_logo),
                Stream.of(R.drawable.aigenerated, R.drawable.album).map(this::toByteArr).collect(Collectors.toList()),
                100, CurrencyCode.NZD, "#dddddd",
                4.7f, 200, 1, 3,
                Category.ALBUM, "Raymond Feng");

        Product product9 = new AlbumArt("", "Test Example no. 2",
                "Test tagline", "Long description", toByteArr(R.drawable.galleria_logo),
                Stream.of(R.drawable.aigenerated, R.drawable.album).map(this::toByteArr).collect(Collectors.toList()),
                100, CurrencyCode.NZD, "#dddddd",
                4.7f, 200, 1, 3,
                Category.ALBUM, "Raymond Feng");

        Product product10 = new AlbumArt("", "Test Example no. 2",
                "Test tagline", "Long description", toByteArr(R.drawable.galleria_logo),
                Stream.of(R.drawable.aigenerated, R.drawable.album).map(this::toByteArr).collect(Collectors.toList()),
                100, CurrencyCode.NZD, "#dddddd",
                4.7f, 200, 1, 3,
                Category.ALBUM, "Raymond Feng");


        // TODO: Remember to add product into here
        addProducts(product1, product2, product3, product4, product5, product6, product7, product8, product9, product10);

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
