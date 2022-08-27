package nz.ac.aucklanduni.softeng306.team17.galleria.view.categoryresult;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase.ProductUseCase;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.LoadingViewModel;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ProductInfoDto;

public class CategoryResultViewModel extends LoadingViewModel {

    private final ProductUseCase productUseCase;

    private final MutableLiveData<List<ProductInfoDto>> products = new MutableLiveData<>(new ArrayList<>());
    
    public CategoryResultViewModel(ProductUseCase productUseCase) {
        this.productUseCase = productUseCase;
    }

    public LiveData<List<ProductInfoDto>> getProducts() {
        return products;
    }

    public void fetchCategoryProducts(Category category) {
        UUID id = setIsLoading();
        productUseCase.listProductsByCategory(category).subscribe(productsFromRepo -> {
            products.setValue(productsFromRepo.stream().map(ProductInfoDto::fromModel).collect(Collectors.toList()));
            setIsLoaded(id);
        });
    }
}
