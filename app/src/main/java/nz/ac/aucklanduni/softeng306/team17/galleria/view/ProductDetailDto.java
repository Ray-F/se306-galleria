package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.List;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.CurrencyCode;

public class ProductDetailDto extends ProductInfoDto {

    private List<Bitmap> allImages;
    private String description;
    private String backgroundColor;
    private String rating;
    private String views;
    private boolean isInStock;
    private String totalReviews;

    public ProductDetailDto(String id, String name, String tagline, CurrencyCode currencyCode, float price, byte[] heroImage, List<byte[]> otherImages, String description, String backgroundColor, String rating, String views, boolean isInStock, String totalReviews) {
        super(id, name, tagline, currencyCode, price, heroImage);
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
        return description;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String getRating() {
        return rating;
    }

    public String getViews() {
        return views;
    }

    public boolean isInStock() {
        return isInStock;
    }

    public String getTotalReviews() {
        return totalReviews;
    }
}
