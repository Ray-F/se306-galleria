package nz.ac.aucklanduni.softeng306.team17.galleria.data;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.CurrencyCode;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.AIArt;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.AlbumArt;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.PaintingArt;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.PhotographicArt;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.Product;

public class ProductDbo {

    public static final String CATEGORY_KEY = "category";
    public static final String RATING_KEY = "rating";


    public String id;
    public String name;
    public String tagline;
    public String description;
    public String heroImage;
    public List<String> otherImages;
    public float price;
    public CurrencyCode currency;
    public String backgroundColor;
    public float rating;
    public int views;
    public int stock;
    public int totalReviews;

    public Category category;
    public String photographicCamera;
    public String paintingArtist;
    public String aiGenerationPhrase;
    public String albumCreator;

    // Empty constructor required for Firestore mapping
    public ProductDbo() {}

    private ProductDbo(String id, String name, String tagline, String description, String heroImage, List<String> otherImages, float price, CurrencyCode currency, String backgroundColor, float rating, int views, int stockLevel, int totalReviews, Category category, String photographicCamera, String paintingArtist, String aiGenerationPhrase, String albumCreator) {
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
        this.stock = stockLevel;
        this.totalReviews = totalReviews;
        this.category = category;
        this.photographicCamera = photographicCamera;
        this.paintingArtist = paintingArtist;
        this.aiGenerationPhrase = aiGenerationPhrase;
        this.albumCreator = albumCreator;
    }

    public Product toModel() {
        byte[] heroImage = Base64.getDecoder().decode(this.heroImage);
        List<byte[]> otherImages = this.otherImages.stream()
                .map((it) -> Base64.getDecoder().decode(it))
                .collect(Collectors.toList());

        switch (category) {
            case AI:
                return new AIArt(id, name, tagline, description, heroImage, otherImages, price,
                                 currency, backgroundColor, rating, views, stock, totalReviews,
                                 category, aiGenerationPhrase);
            case PAINTING:
                return new PaintingArt(id, name, tagline, description, heroImage, otherImages, price,
                                       currency, backgroundColor, rating, views, stock, totalReviews,
                                       category, paintingArtist);
            case ALBUM:

                return new AlbumArt(id, name, tagline, description, heroImage, otherImages, price,
                                    currency, backgroundColor, rating, views, stock, totalReviews,
                                    category, albumCreator);
            case PHOTOGRAPHIC:
                return new PhotographicArt(id, name, tagline, description, heroImage, otherImages, price,
                                           currency, backgroundColor, rating, views, stock, totalReviews,
                                           category, photographicCamera);
            default:
                throw new IllegalArgumentException("Category " + category + " not yet implemented!");
        }
    }

    public static ProductDbo fromModel(Product product) {
        String photographicCamera = null;
        String aiGenerationPhrase = null;
        String albumCreator = null;
        String paintingArtist = null;

        switch (product.getCategory()) {

            case PHOTOGRAPHIC:
                photographicCamera = ((PhotographicArt) product).getCameraUsed();
                break;
            case AI:
                aiGenerationPhrase = ((AIArt) product).getGenerationPhrase();
                break;
            case ALBUM:
                albumCreator = ((AlbumArt) product).getCreator();
                break;
            case PAINTING:
                paintingArtist = ((PaintingArt) product).getArtist();
                break;
        }

        return new ProductDbo(product.getId(), product.getName(), product.getTagline(), product.getDesc(),
                              Base64.getEncoder().encodeToString(product.getHeroImage()),
                              product.getOtherImages().stream().map((it) -> Base64.getEncoder().encodeToString(it)).collect(Collectors.toList()),
                              product.getPrice(), product.getCurrency(), product.getBackgroundColor(),
                              product.getRating(), product.getViews(), product.getStockLevel(), product.getTotalReviews(), product.getCategory(),
                              photographicCamera, paintingArtist, aiGenerationPhrase, albumCreator);
    }

}
