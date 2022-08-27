package nz.ac.aucklanduni.softeng306.team17.galleria.view.main;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase.ProductUseCase;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.LoadingViewModel;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ProductInfoDto;

public class MainActivityViewModel extends LoadingViewModel {

    private final ProductUseCase productUseCase;

    private final MutableLiveData<List<ProductInfoDto>> products;
    private final MutableLiveData<List<Bitmap>> mostViewedProductImages;

    public MainActivityViewModel(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;

        mostViewedProductImages = new MutableLiveData<>();
        products = new MutableLiveData<>();

        fetchFeaturedProducts();
        fetchMostViewedProducts();
    }

    public void fetchFeaturedProducts() {
        UUID id = setIsLoading();
        productUseCase.listTopRatedProducts(5).subscribe(productsFromRepo -> {
            products.setValue(productsFromRepo.stream().map(ProductInfoDto::fromModel).collect(Collectors.toList()));
            setIsLoaded(id);
        });
    }

    public void fetchMostViewedProducts() {
        UUID id = setIsLoading();
        productUseCase.listAllProducts().subscribe(productsFromRepo -> {
            mostViewedProductImages.setValue(productsFromRepo.stream()
                    .limit(5)
                    .map(ProductInfoDto::convertByteToBitMap)
                    .collect(Collectors.toList())
            );
            setIsLoaded(id);
        });
    }

    public LiveData<List<ProductInfoDto>> getProducts() {
        return products;
    }
    
    public LiveData<List<Bitmap>> getMostViewedProductImages() {
        return mostViewedProductImages;
    }
}
