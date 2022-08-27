package nz.ac.aucklanduni.softeng306.team17.galleria.view.searchresult;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.comparators.NameAscendingComparator;
import nz.ac.aucklanduni.softeng306.team17.galleria.comparators.NameDescendingComparator;
import nz.ac.aucklanduni.softeng306.team17.galleria.comparators.PriceAscendingComparator;
import nz.ac.aucklanduni.softeng306.team17.galleria.comparators.PriceDescendingComparator;
import nz.ac.aucklanduni.softeng306.team17.galleria.comparators.ViewAscendingComparator;
import nz.ac.aucklanduni.softeng306.team17.galleria.comparators.ViewDescendingComparator;
import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityListResultBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.productdetail.ProductDetailsActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.searchbar.SearchBarActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ListResultActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ProductInfoDto;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.SimpleListInfoAdapter;

public class SearchResultActivity extends ListResultActivity {

    private SearchResultViewModel searchResultViewModel;

    private String searchTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchResultViewModel = ((GalleriaApplication) getApplication()).diProvider.searchResultViewModel;

        binding.setViewmodel(searchResultViewModel);
        binding.setLifecycleOwner(this);

        Bundle allKeys = getIntent().getExtras();
        searchTerm = allKeys.getString("searchTerm");
        navigationHistory = (ArrayList<Intent>) allKeys.get("NAVIGATION");

        Toolbar toolbar = (Toolbar) binding.topBarLayout.getRoot().getChildAt(0);
        loadToolbar(toolbar, binding.secondaryToolbar);
        customizeToolbar(R.color.darkestShadeGreen, R.color.darkestShadeGreen, "SEARCH: " + searchTerm);

        searchResultViewModel.enterSearch(searchTerm);
        searchResultViewModel.getSearchResults().observe(this, super::setProducts);
        searchResultViewModel.isSearchResultsEmpty().observe(this, binding::setSearchResultsEmpty);

        super.setOnItemClickListener((productId) -> {
            Intent returnIntent = new Intent(this, SearchResultActivity.class);
            returnIntent.putExtra("searchTerm", searchTerm);
            navigationHistory.add(returnIntent);

            Intent productIntent = new Intent(this, ProductDetailsActivity.class);
            productIntent.putExtra("productId", productId);
            productIntent.putExtra("NAVIGATION", navigationHistory);

            startActivity(productIntent);
        });

    }

    // Override options menu to not have the search menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

}
