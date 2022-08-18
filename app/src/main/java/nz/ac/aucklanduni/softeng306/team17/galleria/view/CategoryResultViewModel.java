package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase.ProductUseCase;

public class CategoryResultViewModel extends ViewModel {

    private final ProductUseCase productUseCase;

    private MutableLiveData<List<ProductInfoDto>> products;

    public CategoryResultViewModel(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;

        products = new MutableLiveData<>();
    }

    public LiveData<List<ProductInfoDto>> getProducts(Category category) {
        productUseCase.listProductsByCategory(category).subscribe(productsFromRepo -> {
            System.out.print(productsFromRepo);
            products.setValue(productsFromRepo.stream().map(it -> {
                return new ProductInfoDto(it.getId(), it.getName(), it.getTagline(),
                                          it.getCurrency(), it.getPrice(), it.getHeroImage());
            }).collect(Collectors.toList()));
        });

        return products;
    }
}
