package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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

public class CategoryResultActivity extends SearchBarActivity {

    CategoryResultAdapter adapter;
    RecyclerView rvProducts;
    TextView filterText;
    ImageView sortFilterButton;
    ImageView viewTypeButton;
    Context localContext = this;
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
        addBackButton(toolbar);
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

        adapter = new CategoryResultAdapter();
        adapter.setCategory(category);
        adapter.setNavigationHistory(navigationHistory);

        viewModel.getProducts(category)
                .observe(this, data -> {
                    adapter.setProducts(data);
                    adapter.notifyDataSetChanged();
                });


        rvProducts = (RecyclerView) findViewById(R.id.ProductRecyclerView);
        rvProducts.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvProducts.setLayoutManager(layoutManager);

        sortFilterButton.setOnClickListener(v -> {
            // Functionality goes here later;
            filterText.setText("Sort By: Price");
        });

        viewTypeButton.setOnClickListener(v -> {
            adapter.toggleDisplayLayoutMode();
            rvProducts.setLayoutManager(adapter.getmIsListViewEnabled() ?  new LinearLayoutManager(localContext) : new GridLayoutManager(localContext, 2));
            rvProducts.setAdapter(adapter);
            viewTypeButton.setImageResource(adapter.getmIsListViewEnabled() ? R.drawable.grid_view_icon : R.drawable.list_view_icon);
        });


    }

    public void setCategoryStyle(Category category) {
        // Add implementation for category specific database calls.
        if (category.equals(Category.PHOTOGRAPHIC)) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.darkestShadeGreen));
            secondaryTopBar.setBackgroundColor(getResources().getColor(R.color.mediumShadeGreen));
        } else if (category.equals(Category.AI)) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.darkestShadeGray));
            secondaryTopBar.setBackgroundColor(getResources().getColor(R.color.mediumShadeGray));
        } else if (category.equals(Category.ALBUM)) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.darkestShadeOrange));
            secondaryTopBar.setBackgroundColor(getResources().getColor(R.color.mediumShadeOrange));
        } else if (category.equals(Category.PAINTING)) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.darkestShadeBlue));
            secondaryTopBar.setBackgroundColor(getResources().getColor(R.color.mediumShadeBlue));
        } else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.darkestShadeBlue));
            secondaryTopBar.setBackgroundColor(getResources().getColor(R.color.mediumShadeBlue));
        }
    }



}