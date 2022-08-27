package nz.ac.aucklanduni.softeng306.team17.galleria.view.categoryresult;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.softeng306.team17.galleria.comparators.NameDescendingComparator;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.AIArt;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.AlbumArt;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.PaintingArt;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.PhotographicArt;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase.ProductUseCase;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ListViewLayoutMode;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.LoadingViewModel;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ProductInfoDto;

public class CategoryResultViewModel extends LoadingViewModel {

    private final ProductUseCase productUseCase;

    private final MutableLiveData<List<ProductInfoDto>> products = new MutableLiveData<>(new ArrayList<>());

    // Default view should be list
    private final MutableLiveData<ListViewLayoutMode> layoutMode = new MutableLiveData<>(ListViewLayoutMode.LIST);

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

    public void toggleLayoutMode() {
        if (this.layoutMode.getValue() == ListViewLayoutMode.LIST) {
            this.layoutMode.setValue(ListViewLayoutMode.GRID);
        } else {
            this.layoutMode.setValue(ListViewLayoutMode.LIST);
        }
    }

    public List<ProductInfoDto> sortByComparator(Comparator comparator) {
        List<ProductInfoDto> prods = products.getValue();
        prods.sort(comparator);
        return prods;
    }

    public MutableLiveData<ListViewLayoutMode> getLayoutMode() {
        return layoutMode;
    }
}
