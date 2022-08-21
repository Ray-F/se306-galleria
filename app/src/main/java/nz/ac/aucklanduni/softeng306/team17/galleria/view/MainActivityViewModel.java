package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase.ProductUseCase;

public class MainActivityViewModel extends ViewModel {

    private final ProductUseCase productUseCase;

    private final MutableLiveData<List<ProductInfoDto>> products;
    private final MutableLiveData<List<Bitmap>> mostViewedProductImages;

    public MainActivityViewModel(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;

        mostViewedProductImages = new MutableLiveData<>();
        products = new MutableLiveData<>();
    }

    public void fetchFeaturedProducts() {
        this.products.setValue(new ArrayList<>());
        productUseCase.listTopRatedProducts(5).subscribe(productsFromRepo -> {
            products.setValue(productsFromRepo.stream().map(it -> (
                    new ProductInfoDto(it.getId(), it.getName(), it.getTagline(),
                            // TODO: Make isSaved return actual information
                            it.getCurrency(), it.getPrice(), it.getHeroImage(), false, "")
            )).collect(Collectors.toList()));
        });
    }

    public void fetchMostViewedProducts() {
        this.mostViewedProductImages.setValue(new ArrayList<>());
        productUseCase.listAllProducts().subscribe(productsFromRepo -> {
            mostViewedProductImages.setValue(productsFromRepo.stream()
                    .limit(3)
                    .map(ProductInfoDto::convertByteToBitMap)
                    .collect(Collectors.toList())
            );
        });
    }

    public LiveData<List<ProductInfoDto>> getProducts() {
        return products;
    }
    
    public LiveData<List<Bitmap>> getMostViewedProductImages() {
        return mostViewedProductImages;
    }
}
