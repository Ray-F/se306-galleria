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
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.PhotographicArt;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.Product;

public class GalleriaApplication extends Application {

    public DIProvider diProvider;

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(getApplicationContext());

        diProvider = new DIProvider();


        // TODO: Uncomment this line and run application to populate data
         populateDummyData();
    }

    private void populateDummyData() {
        // TODO: Usage Add image into r/drawable and reference their ID's where needed
        //  (after long description and inside Stream.of() for other images.
        //  Put the product inside the addProducts method below

        Product product1 = new PhotographicArt("", "Stargazing",
                "Location: Arto Marttinen @ Ghandruk, Nepal", "Nepal has the most amazing starry skies I have ever seen ✨. On a good night the sky is so clear that you can see the milky way bending over the horizon.",
                toByteArr(R.drawable.sg),
                Stream.of(R.drawable.sg2, R.drawable.sg3).map(this::toByteArr).collect(Collectors.toList()),
                130, CurrencyCode.NZD, "#dddddd",
                4.7f, 200, 1, 11,
                Category.PHOTOGRAPHIC, "Sony A7 IV, 28-60mm");

        Product product2 = new PhotographicArt("", "Matcha Ice Cream",
                "Location: Raymond Feng @ Taronga Zoo, Australia", "Matcha Ice Cream shaped like a snake. Taken by Raymond Feng at the Taronga Zoo in 2022, absolute perfect depection of a snake looking so very edible and lovely. One day it will turn into a snake and go over the horizon.",
                toByteArr(R.drawable.mic),
                Stream.of(R.drawable.mic2, R.drawable.mic3).map(this::toByteArr).collect(Collectors.toList()),
                140, CurrencyCode.NZD, "#dddddd",
                4.5f, 157, 1, 12,
                Category.PHOTOGRAPHIC, "Sony A7 IV, 28-60mm");

        Product product3 = new PhotographicArt("", "Winter in Carlow",
                "Location: Erol Ahmed @ Lauterbrunnen, Switzerland", "A photo taken in Lauterbrunnen, Switzerland during winter. In winter we mostly go for places where there is a lot of sun, and which is relatively high so that the sun feels warm.",
                toByteArr(R.drawable.wic),
                Stream.of(R.drawable.wic2, R.drawable.wic3).map(this::toByteArr).collect(Collectors.toList()),
                130, CurrencyCode.NZD, "#dddddd",
                4.2f, 121, 1, 51,
                Category.PHOTOGRAPHIC, "Sony A7 IV, 28-60mm");

        Product product4 = new PhotographicArt("", "Green/Blue",
                "Location: Andreas Gucklhorn @ Lake Breinz, Switzerland", "Photo taken in Switzerland showing the split between the forest and the seas a perfect contrast in colours with the green forest and blue blue sea.",
                toByteArr(R.drawable.gb),
                Stream.of(R.drawable.gb2, R.drawable.gb3).map(this::toByteArr).collect(Collectors.toList()),
                160, CurrencyCode.NZD, "#dddddd",
                4.3f, 219, 1, 32,
                Category.PHOTOGRAPHIC, "Sony A7 IV, 28-60mm");

        Product product5 = new PhotographicArt("", "Unspoilt Wilderness",
                "Location: David Marcu @ Ciucas Peak, Romania", "Photo taken in Romania in the Wilderness by David Marcu, don't you ever wonder what it would be like to cut away from the internet and live in the wilderness? I don't... I couldn't imagine.",
                toByteArr(R.drawable.uspoilt),
                Stream.of(R.drawable.unspoilt2, R.drawable.unspoilt3).map(this::toByteArr).collect(Collectors.toList()),
                190, CurrencyCode.NZD, "#dddddd",
                4.2f, 152, 1, 61,
                Category.PHOTOGRAPHIC, "Sony A7 IV, 28-60mm");



        // TODO: Remember to add product into here
        addProducts(product1, product2, product3, product4, product5);

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