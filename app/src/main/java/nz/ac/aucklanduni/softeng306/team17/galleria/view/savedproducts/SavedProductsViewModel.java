package nz.ac.aucklanduni.softeng306.team17.galleria.view.savedproducts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase.ProductUseCase;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ProductInfoDto;

public class SavedProductsViewModel extends ViewModel {

    private final ProductUseCase productUseCase;

    private final MutableLiveData<List<ProductInfoDto>> products;

    public SavedProductsViewModel(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;

        products = new MutableLiveData<>();
    }


    public LiveData<List<ProductInfoDto>> getProducts(String uuid) {
        productUseCase.listSavedProductsByUser(uuid).subscribe(filteredProducts -> {
            products.setValue(filteredProducts.stream().map(it -> (
                    new ProductInfoDto(it.getId(), it.getName(), it.getTagline(),
                            it.getCurrency(), it.getPrice(), it.getHeroImage(), true, "", it.getCategory(), it.getViews())
            )).collect(Collectors.toList()));
        });

        return products;
    }

    public void unsaveProduct(String productId) {
        productUseCase.unsaveProductToUser(GalleriaApplication.DEV_USER, productId);
    }

}
