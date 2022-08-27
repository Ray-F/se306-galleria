package nz.ac.aucklanduni.softeng306.team17.galleria.view.shared;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Comparator;
import java.util.Map;


public class ListResultViewModel extends LoadingViewModel {

    private static final Map<String, Comparator<ProductInfoDto>> mapSortTextToComparator = Map.of(
            "Lowest Price", Comparator.comparing(ProductInfoDto::getPrice),
            "Highest Price", Comparator.comparing(ProductInfoDto::getPrice).reversed(),
            "Alphabetical", Comparator.comparing(ProductInfoDto::getName, String.CASE_INSENSITIVE_ORDER),
            "Reverse Alphabetical", Comparator.comparing(ProductInfoDto::getName, String.CASE_INSENSITIVE_ORDER).reversed(),
            "Most Views", Comparator.comparing(ProductInfoDto::getViews).reversed(),
            "Least Views", Comparator.comparing(ProductInfoDto::getViews)
    );

    // Default view should be list
    private final MutableLiveData<ListViewLayoutMode> layoutMode = new MutableLiveData<>(ListViewLayoutMode.LIST);


    private final MutableLiveData<String> sortText = new MutableLiveData<>("Alphabetical");
    private final MutableLiveData<Comparator<ProductInfoDto>> comparator = new MutableLiveData<>();

    public void toggleLayoutMode() {
        if (this.layoutMode.getValue() == ListViewLayoutMode.LIST) {
            this.layoutMode.setValue(ListViewLayoutMode.GRID);
        } else {
            this.layoutMode.setValue(ListViewLayoutMode.LIST);
        }
    }

    public LiveData<Comparator<ProductInfoDto>> getSortComparator() {
        return comparator;
    }

    public void setSortText(String sortText) {


        if (mapSortTextToComparator.containsKey(sortText)) {
            this.sortText.setValue(sortText);
            comparator.setValue(mapSortTextToComparator.get(sortText));
        } else {
            this.sortText.setValue("Alphabetical");
            comparator.setValue(mapSortTextToComparator.get("Alphabetical"));
        }
    }

    public LiveData<String> getSortText() {
        return sortText;
    }

    public LiveData<ListViewLayoutMode> getLayoutMode() {
        return layoutMode;
    }

}
