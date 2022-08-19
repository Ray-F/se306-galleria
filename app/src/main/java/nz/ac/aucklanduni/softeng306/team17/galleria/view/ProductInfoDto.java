package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.CurrencyCode;

public class ProductInfoDto {

    private String id;
    private String name;
    private String tagline;
    private CurrencyCode currencyCode;
    private float price;
    private Bitmap heroImage;


    public ProductInfoDto(String id, String name, String tagline, CurrencyCode currencyCode, float price, byte[] heroImage) {
        this.id = id;
        this.name = name;
        this.tagline = tagline;
        this.currencyCode = currencyCode;
        this.price = price;

        this.heroImage = BitmapFactory.decodeByteArray(heroImage, 0, heroImage.length);
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

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Bitmap getHeroImage() {
        return heroImage;
    }

    public void setHeroImage(Bitmap heroImage) {
        this.heroImage = heroImage;
    }

}
