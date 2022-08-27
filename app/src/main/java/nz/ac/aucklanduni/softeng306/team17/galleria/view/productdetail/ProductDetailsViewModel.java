package nz.ac.aucklanduni.softeng306.team17.galleria.view.productdetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.CurrencyCode;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.Product;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase.ProductUseCase;

public class ProductDetailsViewModel extends ViewModel {

    private final ProductUseCase productUseCase;

    private final MutableLiveData<ProductDetailDto> singleProduct = new MutableLiveData<>();

    private final MutableLiveData<Boolean> productIsSaved = new MutableLiveData<>(false);


    public ProductDetailsViewModel(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }


    public void fetchProduct(String productId) {
        productUseCase.isProductSaved(GalleriaApplication.DEV_USER, productId).subscribe(data -> {
            if (productIsSaved.getValue() != data) {
                productIsSaved.setValue(data);
            }
        });

        productUseCase.getProductById(productId).subscribe(product -> {
            singleProduct.setValue(new ProductDetailDto(
                    product.getId(), product.getName(), product.getTagline(),
                    product.getCurrency(), product.getPrice(), product.getHeroImage(), false, product.getOtherImages(),
                    product.getDesc(), product.getBackgroundColor(), product.getRating(),
                    product.getViews(), product.getStockLevel() > 0, product.getTotalReviews(), "", product.getCategory())
            );
        });
    }

    public LiveData<ProductDetailDto> getProduct() {
        return singleProduct;
    }

    public LiveData<Boolean> isSavedProduct() {
        return this.productIsSaved;
    }

    public void toggleSaveProduct() {
        if (productIsSaved.getValue()) {
            productUseCase.unsaveProductToUser(GalleriaApplication.DEV_USER, this.singleProduct.getValue().getId());
        } else {
            productUseCase.saveProductToUser(GalleriaApplication.DEV_USER, this.singleProduct.getValue().getId());
        }
        productIsSaved.setValue(!productIsSaved.getValue());
    }
}
