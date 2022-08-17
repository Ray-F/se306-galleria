package nz.ac.aucklanduni.softeng306.team17.galleria.domain.model;

import java.util.ArrayList;
import java.util.List;

public class ProductDbo {

    private String id;
    private String name;
    private String tagline;
    private String description;
    private String heroImage;
    private List<String> otherImages;
    private float price;
    private CurrencyCode currency;
    private String backgroundColor;
    private float rating;
    private int views;
    private int stockLevel;
    private int totalReviews;

    public ProductDbo() {
        this.id = "999999";
        this.name = "Default Name";
        this.tagline = "default tagline";
        this.description = "default description";
        this.heroImage = "dummy_image";
        this.otherImages = new ArrayList<String>();
        this.price = 23.45f;
        this.currency = CurrencyCode.USD;
        this.backgroundColor = "#FFFFFF";
        this.rating = 58.5f;
        this.views = 100;
        this.stockLevel = 5;
        this.totalReviews = 8;
    }

    public ProductDbo(String id, String name, String tagline, String description, String heroImage,
                      List<String> otherImages, float price, CurrencyCode currency, String backgroundColor,
                      float rating, int views, int stockLevel, int totalReviews) {
        this.id = id;
        this.name = name;
        this.tagline = tagline;
        this.description = description;
        this.heroImage = heroImage;
        this.otherImages = otherImages;
        this.price = price;
        this.currency = currency;
        this.backgroundColor = backgroundColor;
        this.rating = rating;
        this.views = views;
        this.stockLevel = stockLevel;
        this.totalReviews = totalReviews;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeroImage() {
        return heroImage;
    }

    public void setHeroImage(String heroImage) {
        this.heroImage = heroImage;
    }

    public List<String> getOtherImages() {
        return otherImages;
    }

    public void setOtherImages(List<String> otherImages) {
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


}
