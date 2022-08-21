package nz.ac.aucklanduni.softeng306.team17.galleria.view.productdetail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.CurrencyCode;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ProductInfoDto;

public class ProductDetailDto extends ProductInfoDto {

    private List<Bitmap> allImages;
    private String description;
    private String backgroundColor;
    private float rating;
    private int views;
    private boolean isInStock;
    private int totalReviews;

    public ProductDetailDto(String id, String name, String tagline, CurrencyCode currencyCode, float price, byte[] heroImage, boolean isSaved, List<byte[]> otherImages, String description, String backgroundColor, float rating, int views, boolean isInStock, int totalReviews, String specialText) {
        super(id, name, tagline, currencyCode, price, heroImage, isSaved, specialText);
        this.allImages = otherImages.stream()
                .map((it) ->  BitmapFactory.decodeByteArray(it, 0, it.length))
                .collect(Collectors.toList());
        this.allImages.add(0, this.getHeroImage());
        this.description = description;
        this.backgroundColor = backgroundColor;
        this.rating = rating;
        this.views = views;
        this.isInStock = isInStock;
        this.totalReviews = totalReviews;
    }

    public List<Bitmap> getAllImages() {
        return allImages;
    }

    public String getDescription() {
        return description.replace("\\n", Objects.requireNonNull(System.getProperty("line.separator")));
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public boolean isInStock() {
        return isInStock;
    }

    /**
     * Format is string like '★ 4.2 (12 reviews)'.
     */
    public String getReviewString() {
        return String.format(Locale.ENGLISH, "★ %.1f (%d review%s)", this.rating, this.totalReviews, this.totalReviews != 0 ? "s" : "");
    }
}
