package nz.ac.aucklanduni.softeng306.team17.galleria.view.categoryresult;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.AIArt;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.AlbumArt;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.PaintingArt;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.PhotographicArt;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase.ProductUseCase;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ListViewLayoutMode;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ProductInfoDto;

public class CategoryResultViewModel extends ViewModel {

    private final ProductUseCase productUseCase;

    private final MutableLiveData<List<ProductInfoDto>> products = new MutableLiveData<>(new ArrayList<>());

    // Default view should be list
    private final MutableLiveData<ListViewLayoutMode> layoutMode = new MutableLiveData<>(ListViewLayoutMode.LIST);

    public CategoryResultViewModel(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    public LiveData<List<ProductInfoDto>> getProducts() {
        return products;
    }

    public void fetchCategoryProducts(Category category) {
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
    }

    public void toggleLayoutMode() {
        if (this.layoutMode.getValue() == ListViewLayoutMode.LIST) {
            this.layoutMode.setValue(ListViewLayoutMode.GRID);
        } else {
            this.layoutMode.setValue(ListViewLayoutMode.LIST);
        }
    }

    public List<ProductInfoDto> sortByPrice() {
        List<ProductInfoDto> prods = products.getValue();
        prods.sort(new CustomComparator());
        return prods;
    }

    class CustomComparator implements Comparator<ProductInfoDto> {

        public int compare(ProductInfoDto p1, ProductInfoDto p2) {
            Long price1 = Long.parseLong(p1.getPrice().substring(1, p1.getPrice().length()));
            Long price2 = Long.parseLong(p2.getPrice().substring(1, p2.getPrice().length()));
            if (price1 == price2) {
                return 0;
            } else if (price1 > price2) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    public MutableLiveData<ListViewLayoutMode> getLayoutMode() {
        return layoutMode;
    }
}
