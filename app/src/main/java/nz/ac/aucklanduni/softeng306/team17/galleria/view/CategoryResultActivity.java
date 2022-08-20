package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;

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

    CategoryResultViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_result);

        Toolbar toolbar = (Toolbar) ((AppBarLayout) findViewById(R.id.topBarLayout)).getChildAt(0);
        loadToolbar(toolbar);

        // Bind ViewModel
        viewModel = ((GalleriaApplication) getApplication()).diProvider.categoryResultViewModel;


        adapter = new CategoryResultAdapter();

        // savedInstanceState.getParcelable("category")
        Category category = Category.PHOTOGRAPHIC;

        viewModel.getProducts(category)
                .observe(this, data -> {
                    adapter.setProducts(data);
                    adapter.notifyDataSetChanged();
                });

        // Set default sort filter setting
        filterText = findViewById(R.id.SortFilterText);
        filterText.setText("Sort By: New");
        sortFilterButton = findViewById(R.id.SortIcon);
        viewTypeButton = findViewById(R.id.ViewLayoutIcon);

        rvProducts = findViewById(R.id.ProductRecyclerView);
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



}