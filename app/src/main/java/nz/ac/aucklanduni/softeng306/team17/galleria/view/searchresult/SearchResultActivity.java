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
import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityListResultBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.productdetail.ProductDetailsActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.searchbar.SearchBarActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ProductInfoDto;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.SimpleListInfoAdapter;

public class SearchResultActivity extends SearchBarActivity {

    private ActivityListResultBinding binding;
    private SearchResultViewModel viewModel;

    private SimpleListInfoAdapter listViewAdapter;

    private String searchTerm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityListResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ((GalleriaApplication) getApplication()).diProvider.searchResultViewModel;
        listViewAdapter = new SimpleListInfoAdapter();

        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        super.onCreate(savedInstanceState);

        Bundle allKeys = getIntent().getExtras();
        searchTerm = allKeys.getString("searchTerm");
        navigationHistory = (ArrayList<Intent>) allKeys.get("NAVIGATION");

        Toolbar toolbar = (Toolbar) binding.topBarLayout.getRoot().getChildAt(0);
        loadToolbar(toolbar, binding.secondaryToolbar);
        customizeToolbar(R.color.darkestShadeGreen, R.color.darkestShadeGreen, "SEARCH: " + searchTerm);

        viewModel.enterSearch(searchTerm);
        viewModel.getSearchResults().observe(this, listViewAdapter::setProducts);
        viewModel.isSearchResultsEmpty().observe(this, binding::setSearchResultsEmpty);

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

        // TODO: We need to extract this code out from Search and Category Result activity
        viewModel.getLayoutMode().observe(this, mode -> {
            listViewAdapter.setLayoutMode(mode);

            RecyclerView.LayoutManager layoutManager;
            int imageResource;
            RecyclerView.ItemDecoration itemDecoration;

            switch (mode) {
                case GRID:
                    layoutManager = new GridLayoutManager(this, 2);
                    imageResource = R.drawable.list_view_icon;
                    itemDecoration = new SimpleListInfoAdapter.ColumnModeItemDecoration(this, 2,16);
                    break;
                case LIST:
                default:
                    layoutManager = new LinearLayoutManager(this);
                    itemDecoration = new SimpleListInfoAdapter.ListModeItemDecoration(this, 16);
                    imageResource = R.drawable.grid_view_icon;
            }

            binding.ProductRecyclerView.setLayoutManager(layoutManager);
            if (binding.ProductRecyclerView.getItemDecorationCount() > 0) {
                binding.ProductRecyclerView.removeItemDecorationAt(0);
            }
            binding.ProductRecyclerView.addItemDecoration(itemDecoration);
            binding.ViewLayoutIcon.setIconResource(imageResource);
        });

        initModifierListeners();
    }

    private void initModifierListeners() {
        binding.SortIcon.setOnClickListener(view -> {
            System.out.println("Sorting button clicked!");
        });

        binding.ViewLayoutIcon.setOnClickListener(view -> viewModel.toggleLayoutMode());
    }

    // Override options menu to not have the search menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

}
