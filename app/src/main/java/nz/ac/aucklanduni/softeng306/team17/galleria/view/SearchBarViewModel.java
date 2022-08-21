package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase.SearchUseCase;

public class SearchBarViewModel extends ViewModel {

    private final SearchUseCase searchUseCase;
    private final MutableLiveData<List<String>> autoFillSuggestions = new MutableLiveData<>();

    public SearchBarViewModel(SearchUseCase searchUseCase) {
        this.searchUseCase = searchUseCase;

        System.out.println(this.searchUseCase.getAutocompleteTerms("", 10));
    }

    public LiveData<List<String>> getAutofill() {
        return autoFillSuggestions;
    }

    public void changeSearchQuery(String s) {
        if (s.trim().equals("")) {
            this.searchUseCase.listTopSearches(5).subscribe(this.autoFillSuggestions::setValue);
        } else {
            this.autoFillSuggestions.setValue(this.searchUseCase.getAutocompleteTerms(s, 5));
        }
    }

}
