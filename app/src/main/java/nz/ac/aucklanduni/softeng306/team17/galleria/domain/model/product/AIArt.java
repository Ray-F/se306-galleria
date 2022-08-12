package nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product;

import java.io.File;
import java.util.List;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.CurrencyCode;

/**
 * A category of {@link Product} available to be sold on Galleria.
 */
public class AIArt extends Product {

    /**
     * The phrase that was used to generate this piece of art.
     */
    private String generationPhrase;


    public AIArt(String id, String name, String tagline, String desc, File heroImage, List<File> otherImages, float price, CurrencyCode currency, String backgroundColor, float rating, int views, int stockLevel, int totalReviews, Category category, String generationPhrase) {
        super(id, name, tagline, desc, heroImage, otherImages, price, currency, backgroundColor, rating, views, stockLevel, totalReviews, category);
        this.generationPhrase = generationPhrase;
    }

    public String getGenerationPhrase() {
        return generationPhrase;
    }
}
