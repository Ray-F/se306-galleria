package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nz.ac.aucklanduni.softeng306.team17.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;

public class CategoryResultActivity extends AppCompatActivity {

    ProductAdapter adapter;
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

        // Bind ViewModel
        viewModel = ((GalleriaApplication) getApplication()).diProvider.categoryResultViewModel;


        adapter = new ProductAdapter();

        // savedInstanceState.getParcelable("category")
        Category category = Category.ALBUM;

        viewModel.getProducts(category)
                .observe(this, data -> {
                    adapter.setProducts(data);
                });

        // Set default sort filter setting
        filterText = (TextView) findViewById(R.id.SortFilterText);
        filterText.setText("Sort By: New");
        sortFilterButton = (ImageView) findViewById(R.id.SortIcon);
        viewTypeButton = (ImageView) findViewById(R.id.ViewLayoutIcon);

        rvProducts = (RecyclerView) findViewById(R.id.ProductRecyclerView);
        rvProducts.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvProducts.setLayoutManager(layoutManager);

        sortFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Functionality goes here later;
                filterText.setText("Sort By: Price");
            }
        });

        viewTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.toggleDisplayLayoutMode();
                rvProducts.setLayoutManager(adapter.getmIsListViewEnabled() ?  new LinearLayoutManager(localContext) : new GridLayoutManager(localContext, 2));
                rvProducts.setAdapter(adapter);
                viewTypeButton.setImageResource(adapter.getmIsListViewEnabled() ? R.drawable.grid_view_icon : R.drawable.list_view_icon);
            }
        });


    }



}