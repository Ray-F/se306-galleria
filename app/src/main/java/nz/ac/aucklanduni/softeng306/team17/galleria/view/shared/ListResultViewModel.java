package nz.ac.aucklanduni.softeng306.team17.galleria.view.shared;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Comparator;

import nz.ac.aucklanduni.softeng306.team17.galleria.comparators.NameAscendingComparator;

public class ListResultViewModel extends LoadingViewModel {

    // Default view should be list
    private final MutableLiveData<ListViewLayoutMode> layoutMode = new MutableLiveData<>(ListViewLayoutMode.LIST);

    private final MutableLiveData<Comparator<ProductInfoDto>> comparator = new MutableLiveData<>(new NameAscendingComparator());

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

    public void setComparator(Comparator<ProductInfoDto> comparator) {
        this.comparator.setValue(comparator);
    }

    public LiveData<ListViewLayoutMode> getLayoutMode() {
        return layoutMode;
    }

}
