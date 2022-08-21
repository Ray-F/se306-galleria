package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase.SearchUseCase;

public class SearchResultViewModel extends ViewModel {

    private final SearchUseCase searchUseCase;

    private final MutableLiveData<List<ProductInfoDto>> products = new MutableLiveData<>();

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
                                       it.getCurrency(), it.getPrice(), it.getHeroImage(), false)
                    )).collect(Collectors.toList()));
        });
    }
}
