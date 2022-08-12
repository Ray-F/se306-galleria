package nz.ac.aucklanduni.softeng306.team17.galleria.domain.model;

public class ProductInfoDto {

    private String id;
    private String name;
    private String tagline;
    private CurrencyCode currencyCode;
    private float price;
    private int heroImage;


    public ProductInfoDto() {
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

    public CurrencyCode getCurrenyCode() {
        return currencyCode;
    }

    public void setCurrenyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPriceString() {
        return Float.toString(this.price) + this.currencyCode.toString();
    }

    public int getHeroImage() {
        return heroImage;
    }

    public void setHeroImage(int heroImage) {
        this.heroImage = heroImage;
    }

}
