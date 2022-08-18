package nz.ac.aucklanduni.softeng306.team17.galleria.data;


import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.CurrencyCode;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.ProductAdapter;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.ProductInfoDto;

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
        ProductRepository productRepository = new ProductRepository(FirebaseFirestore.getInstance());

//        new Thread(() -> {
//
//            onQueryCompleteListener.onComplete(
//                    productRepository.listAll().stream().map((it -> {
//                        int image = R.drawable.test_raccoon;
//                        return new ProductInfoDto(it.getId(), it.getName(), it.getTagline(), it.getCurrency(), it.getPrice(), image);
//                    })).collect(Collectors.toList()),
//                    adapter
//            );
//        }).start();

//        TaskCompletionSource<List<ProductInfoDto>> t = new TaskCompletionSource<>();
//        t.setResult(productRepository.listAll().stream().map((it -> {
//            int image = R.drawable.test_raccoon;
//            return new ProductInfoDto(it.getId(), it.getName(), it.getTagline(), it.getCurrency(), it.getPrice(), image);
//        })).collect(Collectors.toList()));
//
//        t.getTask().addOnCompleteListener((task) -> {
//            List<ProductInfoDto> products = task.getResult();
//            onQueryCompleteListener.onComplete(products, adapter);
//        });

        return new ArrayList<>();
    }

}
