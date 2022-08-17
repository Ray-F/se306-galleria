package nz.ac.aucklanduni.softeng306.team17.galleria.data;


import android.util.Log;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.CurrencyCode;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.ProductInfoDto;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.ProductRepository;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.ProductAdapter;

public class DataProvider {

    private static final String TAG = "DatProvider";
    static String[] productNames = {"Stargazing", "Matcha Icecream", "Winter in Carlow", "Green Blue",
        "Unspoilt Wilderness", "Hidden Gem", "One More Test", "TestName1", "TestName2", "TestName3",
        "TestName4", "Red Pandas", "Porsche", "Pork Belly"};

    public static List<ProductInfoDto> generateData() {
        ArrayList<ProductInfoDto> products = new ArrayList<ProductInfoDto>();

        for (int i = 0; i < productNames.length; i++) {
            String tagline = "Description for artwork called " + productNames[i];
            float price = ((float) Math.random() * (400));
            CurrencyCode currency = CurrencyCode.USD;

            Random random = new Random();
            int image;
            if (random.nextBoolean()) {
                image = R.drawable.test_matcha;
            } else {
                image = R.drawable.test_raccoon;
            }

            ProductInfoDto fakeProduct = new ProductInfoDto(Integer.toString(i), productNames[i], tagline, currency, price, image);
            products.add(fakeProduct);
        }
        return products;
    }

    public static List<ProductInfoDto> generateDataLive(QueryCompleteListener<List<ProductInfoDto>> onQueryCompleteListener, ProductAdapter adapter) {
        ProductRepository productRepository = new ProductRepository();
        ArrayList<ProductInfoDto> products = new ArrayList<ProductInfoDto>();
        productRepository.getProducts()
                .get()
                .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                Map<String, Object> data = document.getData();
                                String id = data.get("id").toString();
                                String productName = data.get("name").toString();
                                String tagline = data.get("tagline").toString();
                                CurrencyCode currency = CurrencyCode.USD;
                                float price = Float.parseFloat(data.get("price").toString());
                                int image = R.drawable.test_raccoon;

                                products.add(new ProductInfoDto(id, productName, tagline, currency, price, image));
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                        onQueryCompleteListener.onComplete(products, adapter);
                });

        return products;

    }

}
