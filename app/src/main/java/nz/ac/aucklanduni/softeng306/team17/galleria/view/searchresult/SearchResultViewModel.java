package nz.ac.aucklanduni.softeng306.team17.galleria.view.searchresult;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase.SearchUseCase;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ListViewLayoutMode;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.LoadingViewModel;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ProductInfoDto;

public class SearchResultViewModel extends LoadingViewModel {

    private final SearchUseCase searchUseCase;

    private final MutableLiveData<List<ProductInfoDto>> products = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isSearchResultsEmpty = new MutableLiveData<>();


    public SearchResultViewModel(SearchUseCase searchUseCase) {
        this.searchUseCase = searchUseCase;
    }

    public LiveData<List<ProductInfoDto>> getSearchResults() {
        return products;
    }

    public LiveData<Boolean> isSearchResultsEmpty() {
        return isSearchResultsEmpty;
    }

    public void enterSearch(String searchTerm) {
        UUID id = setIsLoading();
        searchUseCase.makeSearch(searchTerm, -1, "").subscribe(repoProducts -> {
            isSearchResultsEmpty.setValue(repoProducts.isEmpty());
            products.setValue(repoProducts.stream().map(ProductInfoDto::fromModel).collect(Collectors.toList()));
            setIsLoaded(id);
        });
    }

}
