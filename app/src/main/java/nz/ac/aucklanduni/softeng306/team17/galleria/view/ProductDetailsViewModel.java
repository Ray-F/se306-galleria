package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase.ProductUseCase;

public class ProductDetailsViewModel extends ViewModel {

    private final ProductUseCase productUseCase;

    private final MutableLiveData<ProductDetailDto> singleProduct;

    public ProductDetailsViewModel(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;

        singleProduct = new MutableLiveData<>();
    }

    public LiveData<ProductDetailDto> getProduct(String productID) {
        productUseCase.getProductById(productID).subscribe(product -> {
            singleProduct.setValue(new ProductDetailDto(
                    product.getId(), product.getName(), product.getTagline(),
                    product.getCurrency(), product.getPrice(), product.getHeroImage(), product.getOtherImages(),
                    product.getDesc(), product.getBackgroundColor(), Float.toString(product.getRating()),
                    Integer.toString(product.getViews()), product.getStockLevel() > 0, Integer.toString(product.getTotalReviews())
            ));
        });

        return singleProduct;
    }
}
