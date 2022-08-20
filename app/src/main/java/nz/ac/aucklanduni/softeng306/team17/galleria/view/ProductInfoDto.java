package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Locale;

import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.CurrencyCode;

public class ProductInfoDto {

    private final String id;
    private final String name;
    private final String tagline;
    private final String priceAsString;
    private final Bitmap heroImage;

    public ProductInfoDto(String id, String name, String tagline, CurrencyCode currencyCode, float price, byte[] heroImage) {
        this.id = id;
        this.name = name;
        this.tagline = tagline;
        this.priceAsString = String.format(Locale.UK, "%.2f", price) + " " + currencyCode;

        this.heroImage = BitmapFactory.decodeByteArray(heroImage, 0, heroImage.length);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTagline() {
        return tagline;
    }

    public String getDisplayPrice() {
        return priceAsString;
    }

    public Bitmap getHeroImage() {
        return heroImage;
    }

}
