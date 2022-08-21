package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityCategoryResultBinding;

public class SearchResultActivity extends SearchBarActivity {

    private ActivityCategoryResultBinding binding;

    private String searchTerm;

    private SearchResultViewModel viewModel;

    private CategoryResultAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        viewModel = ((GalleriaApplication) getApplication()).diProvider.searchResultViewModel;

        super.onCreate(savedInstanceState);
        navigationHistory = (ArrayList<Intent>) getIntent().getExtras().get("NAVIGATION");

        binding = ActivityCategoryResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        searchTerm = getIntent().getExtras().getString("searchTerm");

        Toolbar toolbar = (Toolbar) binding.topBarLayout.getRoot().getChildAt(0);
        loadToolbar(toolbar);
        customizeToolbar(toolbar);

        viewModel.getSearchResults().observe(this, this::loadSearchResultData);
        viewModel.enterSearch(searchTerm);

        binding.ProductRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listViewAdapter = new CategoryResultAdapter();
        listViewAdapter.setNavigationHistory(navigationHistory);
        binding.ProductRecyclerView.setAdapter(listViewAdapter);

        initModifierListeners();
    }

    private void initModifierListeners() {
        binding.SortIcon.setOnClickListener(view -> {
            System.out.println("Clicked!");
        });

        binding.ViewLayoutIcon.setOnClickListener(view -> {
            System.out.println("Toggle view change button, clicked!");
        });
    }

    private void customizeToolbar(Toolbar toolbar) {
        toolbar.setTitle("Search: " + searchTerm);
    }
    // Override options menu to not have menu item.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    private void loadSearchResultData(List<ProductInfoDto> productInfos) {
        System.out.println(productInfos);
        listViewAdapter.setProducts(productInfos);
        listViewAdapter.notifyDataSetChanged();
    }

}
