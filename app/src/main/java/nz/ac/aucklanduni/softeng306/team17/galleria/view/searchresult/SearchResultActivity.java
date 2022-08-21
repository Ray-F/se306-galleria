package nz.ac.aucklanduni.softeng306.team17.galleria.view.searchresult;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityCategoryResultBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ListViewLayoutMode;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ProductInfoDto;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.searchbar.SearchBarActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.SimpleListInfoAdapter;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.productdetail.ProductDetailsActivity;

public class SearchResultActivity extends SearchBarActivity {

    private ActivityCategoryResultBinding binding;

    private String searchTerm;

    private SearchResultViewModel viewModel;

    private SimpleListInfoAdapter listViewAdapter;

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
        listViewAdapter = new SimpleListInfoAdapter();
        binding.ProductRecyclerView.setAdapter(listViewAdapter);

        listViewAdapter.setOnItemClickListener((productId) -> {
            Intent returnIntent = new Intent(this, SearchResultActivity.class);
            returnIntent.putExtra("searchTerm", searchTerm);
            navigationHistory.add(returnIntent);

            Intent productIntent = new Intent(this, ProductDetailsActivity.class);
            productIntent.putExtra("productId", productId);
            productIntent.putExtra("NAVIGATION", navigationHistory);

            startActivity(productIntent);
        });

        viewModel.getLayoutMode().observe(this, mode -> {
            listViewAdapter.setLayoutMode(mode);
            RecyclerView.LayoutManager layoutManager;
            int imageResource;

            switch (mode) {
                case GRID:
                    layoutManager = new GridLayoutManager(this, 2);
                    imageResource = R.drawable.list_view_icon;
                    break;
                case LIST:
                default:
                    layoutManager = new LinearLayoutManager(this);
                    imageResource = R.drawable.grid_view_icon;
            }

            binding.ProductRecyclerView.setLayoutManager(layoutManager);
            binding.ViewLayoutIcon.setImageResource(imageResource);
        });

        initModifierListeners();
    }

    private void initModifierListeners() {
        binding.SortIcon.setOnClickListener(view -> {
            System.out.println("Sorting butotn clicked!");
        });

        binding.ViewLayoutIcon.setOnClickListener(view -> viewModel.toggleLayoutMode());
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
