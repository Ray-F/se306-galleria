package nz.ac.aucklanduni.softeng306.team17.galleria.view.categoryresult;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityListResultBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.searchbar.SearchBarActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.SimpleListInfoAdapter;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.productdetail.ProductDetailsActivity;

public class CategoryResultActivity extends SearchBarActivity {

    private CategoryResultViewModel viewModel;
    private SimpleListInfoAdapter adapter;

    private ActivityListResultBinding binding;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityListResultBinding.inflate(getLayoutInflater());

        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        Bundle allKeys = getIntent().getExtras();

        Category category = (Category) allKeys.get("CATEGORY");
        navigationHistory = (ArrayList<Intent>) allKeys.get("NAVIGATION");

        toolbar = (Toolbar) binding.topBarLayout.getRoot().getChildAt(0);
        switchSavedToBackButton(toolbar);
        loadToolbar(toolbar);

        binding.SortFilterText.setText("Sort By: New");

        setCategoryStyle(category);

        // Bind ViewModel
        viewModel = ((GalleriaApplication) getApplication()).diProvider.categoryResultViewModel;

        adapter = new SimpleListInfoAdapter();

        viewModel.fetchCategoryProducts(category);
        viewModel.getProducts().observe(this, data -> adapter.setProducts(data));

        viewModel.getLayoutMode()
                .observe(this, data -> {
                    adapter.setLayoutMode(data);

                    RecyclerView.LayoutManager layoutManager;
                    int imageResource;

                    switch (data) {
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
                    binding.ViewLayoutIcon.setIconResource(imageResource);
                });

        binding.ProductRecyclerView.setAdapter(adapter);

        binding.ViewLayoutIcon.setOnClickListener(it -> viewModel.toggleLayoutMode());

        binding.SortIcon.setOnClickListener(v -> {
            // Functionality goes here later;
            binding.SortFilterText.setText("Sort By: Price");
        });

        adapter.setOnItemClickListener((productId) -> {
            Intent returnIntent = new Intent(this, CategoryResultActivity.class);
            returnIntent.putExtra("CATEGORY", category);
            navigationHistory.add(returnIntent);

            Intent productIntent = new Intent(this, ProductDetailsActivity.class);
            productIntent.putExtra("productId", productId);
            productIntent.putExtra("NAVIGATION", navigationHistory);

            startActivity(productIntent);
        });
    }

    public void setCategoryStyle(Category category) {
        int statusBarColourResId;
        int colourResId;
        int secondaryColourResId;
        String title;

        switch (category) {
            case PHOTOGRAPHIC:
                statusBarColourResId = R.color.blackShadeGreen;
                colourResId = R.color.darkestShadeGreen;
                secondaryColourResId = R.color.mediumShadeGreen;
                title = "PHOTOGRAPHIC ART";
                break;
            case ALBUM:
                statusBarColourResId = R.color.blackShadeGray;
                colourResId = R.color.darkestShadeGray;
                secondaryColourResId = R.color.mediumShadeGray;
                title = "ALBUM COVER ART";
                break;
            case AI:
                statusBarColourResId = R.color.blackShadeOrange;
                colourResId = R.color.darkestShadeOrange;
                secondaryColourResId = R.color.mediumShadeOrange;
                title = "AI GENERATED ART";
                break;
            case PAINTING:
                statusBarColourResId = R.color.blackShadeBlue;
                colourResId = R.color.darkestShadeBlue;
                secondaryColourResId = R.color.mediumShadeBlue;
                title = "PAINTINGS";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + category);
        }

        getWindow().setStatusBarColor(ContextCompat.getColor(this, statusBarColourResId));
        toolbar.setBackgroundColor(ContextCompat.getColor(this, colourResId));
        binding.secondaryTopBar.setBackgroundColor(ContextCompat.getColor(this, secondaryColourResId));
        toolbar.setTitle(title);
    }

}