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

import java.util.ArrayList;

import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.data.DataProvider;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.ProductInfoDto;

public class CategoryResultActivity extends AppCompatActivity {

    ArrayList<ProductInfoDto> products;
    ProductRowAdapter adapter;
    RecyclerView rvProducts;
    TextView filterText;
    ImageView sortFilterButton;
    ImageView viewTypeButton;
    Context localContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_result);

        // Set default sort filter setting
        filterText = (TextView) findViewById(R.id.SortFilterText);
        filterText.setText("Sort By: New");
        sortFilterButton = (ImageView) findViewById(R.id.SortIcon);
        viewTypeButton = (ImageView) findViewById(R.id.ViewLayoutIcon);

        rvProducts = (RecyclerView) findViewById(R.id.ProductRecyclerView);

        //Initialize Data
        products = DataProvider.generateData();

        adapter = new ProductRowAdapter(products);
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