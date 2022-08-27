package nz.ac.aucklanduni.softeng306.team17.galleria.view.searchresult;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.productdetail.ProductDetailsActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ListResultActivity;

public class SearchResultActivity extends ListResultActivity {

    private SearchResultViewModel searchResultViewModel;

    private String searchTerm;

    public static String SEARCH_TERM_INTENT_KEY = "searchTerm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchResultViewModel = ((GalleriaApplication) getApplication()).diProvider.searchResultViewModel;

        binding.setViewmodel(searchResultViewModel);
        binding.setLifecycleOwner(this);

        Bundle allKeys = getIntent().getExtras();
        searchTerm = allKeys.getString(SEARCH_TERM_INTENT_KEY);

        Toolbar toolbar = (Toolbar) binding.topBarLayout.getRoot().getChildAt(0);
        loadToolbar(toolbar, binding.secondaryToolbar);
        customizeToolbar(R.color.darkestShadeGreen, R.color.darkestShadeGreen, "SEARCH: " + searchTerm);

        // Load data and observe, observed data changes are set inside the super class for
        // populating the list activity
        searchResultViewModel.enterSearch(searchTerm);
        searchResultViewModel.getSearchResults().observe(this, super::setProducts);
        searchResultViewModel.isSearchResultsEmpty().observe(this, binding::setEmptyResults);

        super.setOnItemClickListener((productId) -> {
            Intent productIntent = new Intent(this, ProductDetailsActivity.class);
            productIntent.putExtra(ProductDetailsActivity.PRODUCT_ID_INTENT_KEY, productId);
            startActivity(productIntent);
        });

    }

    // Override options menu to not have the search menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

}
