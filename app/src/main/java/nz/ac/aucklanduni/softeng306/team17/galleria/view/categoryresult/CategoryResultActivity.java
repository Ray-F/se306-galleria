package nz.ac.aucklanduni.softeng306.team17.galleria.view.categoryresult;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityListResultBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.productdetail.ProductDetailsActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.searchbar.SearchBarActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.SimpleListInfoAdapter;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.SimpleListInfoAdapter.ListModeItemDecoration;

public class CategoryResultActivity extends SearchBarActivity {

    private ActivityListResultBinding binding;
    private CategoryResultViewModel viewModel;

    private SimpleListInfoAdapter listViewAdapter;

    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityListResultBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        viewModel = ((GalleriaApplication) getApplication()).diProvider.categoryResultViewModel;
        listViewAdapter = new SimpleListInfoAdapter();
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        super.onCreate(savedInstanceState);

        Bundle allKeys = getIntent().getExtras();
        category = (Category) allKeys.get("CATEGORY");
        navigationHistory = (ArrayList<Intent>) allKeys.get("NAVIGATION");

        Toolbar toolbar = binding.topBarLayout.toolbar;
        loadToolbar(toolbar, binding.secondaryToolbar);
        customizeToolbarByCategory(category);

        viewModel.fetchCategoryProducts(category);
        viewModel.getProducts().observe(this, listViewAdapter::setProducts);

        binding.ProductRecyclerView.setAdapter(listViewAdapter);

        listViewAdapter.setOnItemClickListener((productId) -> {
            Intent returnIntent = new Intent(this, CategoryResultActivity.class);
            returnIntent.putExtra("CATEGORY", category);
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
                    itemDecoration = new SimpleListInfoAdapter.ColumnModeItemDecoration(this, 2, 16);
                    break;
                case LIST:
                default:
                    layoutManager = new LinearLayoutManager(this);
                    itemDecoration = new ListModeItemDecoration(this, 16);
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

    private void customizeToolbarByCategory(Category category) {
        switch (category) {
            case PHOTOGRAPHIC:
                customizeToolbar(R.color.darkestShadeGreen,
                                 R.color.darkestShadeGreen,
                                 R.color.mediumShadeGreen,
                                 "PHOTOGRAPHIC ART");
                break;
            case ALBUM:
                customizeToolbar(R.color.darkestShadeGray,
                                 R.color.darkestShadeGray,
                                 R.color.mediumShadeGray,
                                 "ALBUM COVER ART");
                break;
            case AI:
                customizeToolbar(R.color.darkestShadeOrange,
                                 R.color.darkestShadeOrange,
                                 R.color.mediumShadeOrange,
                                 "AI GENERATED ART");
                break;
            case PAINTING:
                customizeToolbar(R.color.darkestShadeBlue,
                                 R.color.darkestShadeBlue,
                                 R.color.mediumShadeBlue,
                                 "PAINTINGS");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + category);
        }
    }

}