package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.AIArt;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.AlbumArt;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.PaintingArt;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.PhotographicArt;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase.ProductUseCase;

public class CategoryResultViewModel extends ViewModel {

    private final ProductUseCase productUseCase;

    private final MutableLiveData<List<ProductInfoDto>> products;

    public CategoryResultViewModel(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;

        products = new MutableLiveData<>();
    }

    public LiveData<List<ProductInfoDto>> getProducts(Category category) {
        this.products.setValue(new ArrayList<>());
        productUseCase.listProductsByCategory(category).subscribe(productsFromRepo -> {
            products.setValue(productsFromRepo.stream().map(
                    it -> {
                        String specialField;
                        switch (category) {
                            case PHOTOGRAPHIC:
                                specialField = ((PhotographicArt) it).getCameraUsed();
                                break;
                            case AI:
                                specialField = ((AIArt) it).getGenerationPhrase();
                                break;
                            case ALBUM:
                                specialField = ((AlbumArt) it).getCreator();
                                break;
                            case PAINTING:
                                specialField = ((PaintingArt) it).getArtist();
                                break;
                            default:
                                specialField = "";
                        }


                        return new ProductInfoDto(it.getId(), it.getName(), it.getTagline(),
                                // TODO: Make isSaved return actual information
                                it.getCurrency(), it.getPrice(), it.getHeroImage(), false, specialField);
                    }).collect(Collectors.toList()));
        });

        return products;
    }
}
