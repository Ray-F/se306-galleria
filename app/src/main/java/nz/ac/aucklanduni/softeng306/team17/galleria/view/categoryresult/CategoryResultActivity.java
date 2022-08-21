package nz.ac.aucklanduni.softeng306.team17.galleria.view.categoryresult;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ListViewLayoutMode;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.searchbar.SearchBarActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.SimpleListInfoAdapter;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.productdetail.ProductDetailsActivity;

public class CategoryResultActivity extends SearchBarActivity {

    SimpleListInfoAdapter adapter;
    RecyclerView rvProducts;
    TextView filterText;
    ImageView sortFilterButton;
    ImageView viewTypeButton;
    RelativeLayout secondaryTopBar;
    Toolbar toolbar;

    CategoryResultViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_result);

        Bundle allKeys = getIntent().getExtras();
        Category category = (Category) allKeys.get("CATEGORY");
        navigationHistory = (ArrayList<Intent>) allKeys.get("NAVIGATION");

        toolbar = (Toolbar) ((AppBarLayout) findViewById(R.id.topBarLayout)).getChildAt(0);
        switchSavedToBackButton(toolbar);
        loadToolbar(toolbar);

        // Set default sort filter setting
        secondaryTopBar = findViewById(R.id.secondaryTopBar);
        filterText = (TextView) findViewById(R.id.SortFilterText);
        filterText.setText("Sort By: New");
        sortFilterButton = (ImageView) findViewById(R.id.SortIcon);
        viewTypeButton = (ImageView) findViewById(R.id.ViewLayoutIcon);

        setCategoryStyle(category);

        // Bind ViewModel
        viewModel = ((GalleriaApplication) getApplication()).diProvider.categoryResultViewModel;

        adapter = new SimpleListInfoAdapter();

        viewModel.getProducts(category)
                .observe(this, data -> {
                    adapter.setProducts(data);
                });

        viewModel.getLayoutMode()
                .observe(this, data -> {
                    adapter.setLayoutMode(data);

                    RecyclerView.LayoutManager layoutManager;
                    int imageResource;

                    switch (data) {
                        case ListViewLayoutMode.GRID:
                            layoutManager = new GridLayoutManager(this, 2);
                            imageResource = R.drawable.list_view_icon;
                            break;
                        case ListViewLayoutMode.LIST:
                        default:
                            layoutManager = new LinearLayoutManager(this);
                            imageResource = R.drawable.grid_view_icon;
                    }

                    rvProducts.setLayoutManager(layoutManager);
                    viewTypeButton.setImageResource(imageResource);
                });

        rvProducts = (RecyclerView) findViewById(R.id.ProductRecyclerView);
        rvProducts.setAdapter(adapter);

        // TODO: see if we can replace this
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        viewTypeButton.setOnClickListener(it -> viewModel.toggleLayoutMode());

        sortFilterButton.setOnClickListener(v -> {
            // Functionality goes here later;
            filterText.setText("Sort By: Price");
        });


        adapter.setOnItemClickListener((productId) -> {
            Intent returnIntent = getIntent();
            navigationHistory.add(returnIntent);

            Intent productIntent = new Intent(this, ProductDetailsActivity.class);
            productIntent.putExtra("productId", productId);
            productIntent.putExtra("NAVIGATION", navigationHistory);

            startActivity(productIntent);
        });
    }

    public void setCategoryStyle(Category category) {
        int colourResId;
        int secondaryColourResId;
        String title;

        switch (category) {
            case PHOTOGRAPHIC:
                colourResId = R.color.darkestShadeGreen;
                secondaryColourResId = R.color.mediumShadeGreen;
                title = "PHOTOGRAPHIC ART";
                break;
            case ALBUM:
                colourResId = R.color.darkestShadeGray;
                secondaryColourResId = R.color.mediumShadeGray;
                title = "ALBUM COVER ART";
                break;
            case AI:
                colourResId = R.color.darkestShadeOrange;
                secondaryColourResId = R.color.mediumShadeOrange;
                title = "AI GENERATED ART";
                break;
            case PAINTING:
                colourResId = R.color.darkestShadeBlue;
                secondaryColourResId = R.color.mediumShadeBlue;
                title = "PAINTINGS";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + category);
        }

        toolbar.setBackgroundColor(ContextCompat.getColor(this, colourResId));
        secondaryTopBar.setBackgroundColor(ContextCompat.getColor(this, secondaryColourResId));
        toolbar.setTitle(title);
    }

}