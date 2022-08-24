package nz.ac.aucklanduni.softeng306.team17.galleria.view.shared;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Locale;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.CurrencyCode;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.Product;

public class ProductInfoDto {

    private final String id;
    private final String name;
    private final String tagline;
    private final String price;
    private final String currency;
    private final String priceAsString;
    private final Bitmap heroImage;
    private final String specialText;
    private boolean isSaved;
    private final Category category;
    private final int views;

    public ProductInfoDto(String id, String name, String tagline, CurrencyCode currencyCode, float price, byte[] heroImage, boolean isSaved, String specialText, Category category, int views) {
        this.id = id;
        this.name = name;
        this.tagline = tagline;
        this.price = String.format(Locale.ENGLISH, "$%.0f", price);
        this.currency = currencyCode.toString();
        this.priceAsString = String.format(Locale.UK, "%.0f", price) + " " + currencyCode;
        this.isSaved = isSaved;
        this.specialText = specialText;
        this.category = category;
        this.views = views;

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

    public Category getCategory() {
        return this.category;
    }

    public int getViews() {
        return this.views;
    }

    public static Bitmap convertByteToBitMap(Product product) {
        return BitmapFactory.decodeByteArray(product.getHeroImage(), 0, product.getHeroImage().length);
    }

    public boolean getIsSaved() { return this.isSaved; }
    public void setIsSaved(boolean isSaved) { this.isSaved = isSaved; }

    public String getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }
}
