package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.CurrencyCode;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.Product;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase.ProductUseCase;

public class ProductDetailsViewModel extends ViewModel {

    private final ProductUseCase productUseCase;

    private final MutableLiveData<ProductDetailDto> singleProduct;
    private String productId;

    private MutableLiveData<Boolean> productIsSaved;

    private Product product;

    public ProductDetailsViewModel(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;

        singleProduct = new MutableLiveData<>();
        productIsSaved = new MutableLiveData<>(false);
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public LiveData<ProductDetailDto> getProduct() {
        this.singleProduct.setValue(new ProductDetailDto("", "", "", CurrencyCode.NZD,
                                                         2f, new byte[1], false,
                                                         new ArrayList<>(), "",
                                                         "", 2f, 1,
                                                         false, 1, ""));


        productUseCase.isProductSaved(GalleriaApplication.DEV_USER, productId).subscribe(data -> {
            productIsSaved.setValue(data);
        });

        productUseCase.getProductById(this.productId).subscribe(product -> {
            this.product = product;
            singleProduct.setValue(new ProductDetailDto(
                    product.getId(), product.getName(), product.getTagline(),
                    product.getCurrency(), product.getPrice(), product.getHeroImage(), false, product.getOtherImages(),
                    product.getDesc(), product.getBackgroundColor(), product.getRating(),
                    product.getViews(), product.getStockLevel() > 0, product.getTotalReviews(), "")
            );
        });

        return singleProduct;
    }

    public LiveData<Boolean> isSavedProduct() {
        return this.productIsSaved;
    }

    public void toggleSaveProduct() {
        if (productIsSaved.getValue()) {
            productUseCase.unsaveProductToUser(GalleriaApplication.DEV_USER, product.getId());
        } else {
            productUseCase.saveProductToUser(GalleriaApplication.DEV_USER, product.getId());
        }
        productIsSaved.setValue(!productIsSaved.getValue());
    }
}
