package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Locale;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.CurrencyCode;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.Product;

public class ProductInfoDto {

    private final String id;
    private final String name;
    private final String tagline;
    private final String priceAsString;
    private final Bitmap heroImage;
    private final String specialText;
    private boolean isSaved;

    public ProductInfoDto(String id, String name, String tagline, CurrencyCode currencyCode, float price, byte[] heroImage, boolean isSaved, String specialText) {
        this.id = id;
        this.name = name;
        this.tagline = tagline;
        this.priceAsString = String.format(Locale.UK, "%.0f", price) + " " + currencyCode;
        this.isSaved = isSaved;
        this.specialText = specialText;

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

    public String getSpecialText() {
        return this.specialText;
    }

    public static Bitmap convertByteToBitMap(Product product) {
        return BitmapFactory.decodeByteArray(product.getHeroImage(), 0, product.getHeroImage().length);
    }

    public boolean getIsSaved() { return this.isSaved; }
    public void setIsSaved(boolean isSaved) { this.isSaved = isSaved; }

}
