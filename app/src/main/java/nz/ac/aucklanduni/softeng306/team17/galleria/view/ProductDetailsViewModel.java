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

    private final MutableLiveData<List<ProductInfoDto>> products;
    private final MutableLiveData<ProductInfoDto> singleProduct;

    public ProductDetailsViewModel(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;

        products = new MutableLiveData<>();
        singleProduct = new MutableLiveData<>();
    }

    public LiveData<List<ProductInfoDto>> getProducts(String uuid) {
        productUseCase.listProductsByCategory(Category.PHOTOGRAPHIC).subscribe(productsFromRepo -> {
            products.setValue(productsFromRepo.stream().map(it -> (
                    new ProductInfoDto(it.getId(), it.getName(), it.getTagline(),
                            it.getCurrency(), it.getPrice(), it.getHeroImage())
            )).collect(Collectors.toList()));
        });

        return products;
    }

    public LiveData<ProductInfoDto> getSingleProduct(String productID) {
        productUseCase.getProductById(productID).subscribe(product -> {
            singleProduct.setValue(new ProductInfoDto(product.getId(), product.getName(), product.getTagline(),
                    product.getCurrency(), product.getPrice(), product.getHeroImage()));
        });

        return singleProduct;
    }
}
