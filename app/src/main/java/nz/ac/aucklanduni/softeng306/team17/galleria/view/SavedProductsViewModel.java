package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase.ProductUseCase;

public class SavedProductsViewModel extends ViewModel {

    private final ProductUseCase productUseCase;

    private final MutableLiveData<List<ProductInfoDto>> products;

    public SavedProductsViewModel(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;

        products = new MutableLiveData<>();
    }

    public LiveData<List<ProductInfoDto>> getProducts(String uuid) {
        productUseCase.listProductsByCategory(Category.PHOTOGRAPHIC).subscribe(productsFromRepo -> {
            products.setValue(productsFromRepo.stream().map(it -> (
                    new ProductInfoDto(it.getId(), it.getName(), it.getTagline(),
                                       // TODO: Somehow get whether this product is saved by user or not
                                       it.getCurrency(), it.getPrice(), it.getHeroImage(), false)
            )).collect(Collectors.toList()));
        });

        return products;
    }
}
