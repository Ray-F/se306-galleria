package nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product;

import java.io.File;
import java.util.List;
import java.util.Locale;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.CurrencyCode;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.DomainModel;

public abstract class Product implements DomainModel {

    /**
     * Unique identifier for the product.
     */
    private final String id;

    /**
     * Short name.
     */
    private String name;

    /**
     * Tagline to be displayed on all lists and other space-sensitive views.
     */
    private String tagline;

    /**
     * Long description to be used when viewing product details.
     */
    private String desc;

    /**
     * The hero image to be used as a featured image or on lists.
     */
    private byte[] heroImage;

    /**
     * The background colour of the product when featuring in product details (must be light coloured).
     */
    private String backgroundColor;

    /**
     * Other images to be visible on product details.
     */
    private List<byte[]> otherImages;

    /**
     * Price of the product, in {@link CurrencyCode}.
     */
    private float price;

    /**
     * Currency the product is sold as.
     */
    private CurrencyCode currency;

    /**
     * The rating of a product between 1 and 5.
     */
    private float rating;

    /**
     * The number of views a product has received in total (including duplicate views from the same customer).
     */
    private int views;

    /**
     * The total amount of stock available for this product.
     */
    private int stockLevel;

    /**
     * The total number of reviews (ratings) provided for this product.
     */
    private int totalReviews;

    /**
     * The category of this product.
     */
    private Category category;


    public Product(String id,
                   String name,
                   String tagline,
                   String desc,
                   byte[] heroImage,
                   List<byte[]> otherImages,
                   float price,
                   CurrencyCode currency,
                   String backgroundColor,
                   float rating,
                   int views,
                   int stockLevel,
                   int totalReviews,
                   Category category) {
        this.id = id;
        this.name = name;
        this.tagline = tagline;
        this.desc = desc;
        this.heroImage = heroImage;
        this.otherImages = otherImages;
        this.price = price;
        this.currency = currency;
        this.backgroundColor = backgroundColor;
        this.rating = rating;
        this.views = views;
        this.stockLevel = stockLevel;
        this.totalReviews = totalReviews;
        this.category = category;
    }


    /*
     * Default getters/setters
     */

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public byte[] getHeroImage() {
        return heroImage;
    }

    public void setHeroImage(byte[] heroImage) {
        this.heroImage = heroImage;
    }

    public List<byte[]> getOtherImages() {
        return otherImages;
    }

    public void setOtherImages(List<byte[]> otherImages) {
        this.otherImages = otherImages;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public CurrencyCode getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyCode currency) {
        this.currency = currency;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    public int getTotalReviews() {
        return totalReviews;
    }

    public void setTotalReviews(int totalReviews) {
        this.totalReviews = totalReviews;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Checks if the name of this product contains a match for the search term.
     */
    public boolean isNameMatch(String searchTerm) {
        return this.getName().toLowerCase(Locale.ROOT).contains(searchTerm.toLowerCase(Locale.ROOT));
    }
}
