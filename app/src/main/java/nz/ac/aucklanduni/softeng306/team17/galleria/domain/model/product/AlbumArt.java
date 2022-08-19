package nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product;

import java.io.File;
import java.util.List;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.CurrencyCode;

/**
 * A category of {@link Product} available to be sold on Galleria.
 */
public class AlbumArt extends Product {

    /**
     * The name of the musician behind the album.
     */
    private String creator;

    public AlbumArt(String id, String name, String tagline, String desc, byte[] heroImage, List<byte[]> otherImages, float price, CurrencyCode currency, String backgroundColor, float rating, int views, int stockLevel, int totalReviews, Category category, String creator) {
        super(id, name, tagline, desc, heroImage, otherImages, price, currency, backgroundColor, rating, views, stockLevel, totalReviews, category);
        this.creator = creator;
    }

    public String getCreator() {
        return creator;
    }
}
