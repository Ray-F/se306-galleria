package nz.ac.aucklanduni.softeng306.team17.galleria.view.savedproducts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase.ProductUseCase;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.LoadingViewModel;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ProductInfoDto;

public class SavedProductsViewModel extends LoadingViewModel {

    private final ProductUseCase productUseCase;

    private final MutableLiveData<List<ProductInfoDto>> products;

    public SavedProductsViewModel(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;

        products = new MutableLiveData<>();
    }


    public LiveData<List<ProductInfoDto>> getProducts(String uuid) {
        UUID id = setIsLoading();
        productUseCase.listSavedProductsByUser(uuid).subscribe(filteredProducts -> {
            products.setValue(filteredProducts.stream().map(ProductInfoDto::fromModel).collect(Collectors.toList()));
            setIsLoaded(id);
        });

        return products;
    }

    public void unsaveProduct(String productId) {
        productUseCase.unsaveProductToUser(GalleriaApplication.DEV_USER, productId);
        products.setValue(Objects.requireNonNull(products.getValue()).stream().filter((it) -> !it.getId().equals(productId)).collect(Collectors.toList()));
    }

}
