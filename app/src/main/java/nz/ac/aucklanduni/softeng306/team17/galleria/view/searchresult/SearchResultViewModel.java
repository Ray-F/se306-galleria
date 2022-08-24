package nz.ac.aucklanduni.softeng306.team17.galleria.view.searchresult;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase.SearchUseCase;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ListViewLayoutMode;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ProductInfoDto;

public class SearchResultViewModel extends ViewModel {

    private final SearchUseCase searchUseCase;

    private final MutableLiveData<List<ProductInfoDto>> products = new MutableLiveData<>();


    // Default view should be list
    private final MutableLiveData<ListViewLayoutMode> layoutMode = new MutableLiveData<>(ListViewLayoutMode.LIST);

    public SearchResultViewModel(SearchUseCase searchUseCase) {
        this.searchUseCase = searchUseCase;
    }

    public LiveData<List<ProductInfoDto>> getSearchResults() {
        return products;
    }

    public void enterSearch(String searchTerm) {
        searchUseCase.makeSearch(searchTerm, -1, "").subscribe(repoProducts -> {
            products.setValue(repoProducts.stream().map(it -> (
                    new ProductInfoDto(it.getId(), it.getName(), it.getTagline(),
                                       // TODO: Somehow get whether this product is saved by user or not
                                       it.getCurrency(), it.getPrice(), it.getHeroImage(), false, "", it.getCategory())
                    )).collect(Collectors.toList()));
        });
    }

    public void toggleLayoutMode() {
        if (this.layoutMode.getValue() == ListViewLayoutMode.LIST) {
            this.layoutMode.setValue(ListViewLayoutMode.GRID);
        } else {
            this.layoutMode.setValue(ListViewLayoutMode.LIST);
        }
    }

    public MutableLiveData<ListViewLayoutMode> getLayoutMode() {
        return layoutMode;
    }

}
