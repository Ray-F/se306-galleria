package nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product;

import java.io.File;
import java.util.List;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.CurrencyCode;

/**
 * A category of {@link Product} available to be sold on Galleria.
 */
public class PhotographicArt extends Product {

    /**
     * A string description of the camera that was used to capture this piece of art (e.g. Sony A7 IV).
     */
    private String cameraUsed;

    public PhotographicArt(String id, String name, String tagline, String desc, byte[] heroImage, List<byte[]> otherImages, float price, CurrencyCode currency, String backgroundColor, float rating, int views, int stockLevel, int totalReviews, Category category, String cameraUsed) {
        super(id, name, tagline, desc, heroImage, otherImages, price, currency, backgroundColor, rating, views, stockLevel, totalReviews, category);
        this.cameraUsed = cameraUsed;
    }

    public String getCameraUsed() {
        return cameraUsed;
    }

    @Override
    public String getSpecialField() {
        return getCameraUsed();
    }
}
