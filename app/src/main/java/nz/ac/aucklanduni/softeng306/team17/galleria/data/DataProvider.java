package nz.ac.aucklanduni.softeng306.team17.galleria.data;


import java.util.ArrayList;
import java.util.Random;

import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.CurrencyCode;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.ProductInfoDto;

public class DataProvider {

    static String[] productNames = {"Stargazing", "Matcha Icecream", "Winter in Carlow", "Green Blue",
        "Unspoilt Wilderness", "Hidden Gem", "One More Test", "TestName1", "TestName2", "TestName3",
        "TestName4", "Red Pandas", "Porsche", "Pork Belly"};

    public static ArrayList<ProductInfoDto> generateData() {
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

}
