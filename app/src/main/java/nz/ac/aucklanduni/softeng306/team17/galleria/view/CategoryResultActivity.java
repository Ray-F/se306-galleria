package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.data.DataProvider;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.ProductInfoDto;

public class CategoryResultActivity extends AppCompatActivity {

    ArrayList<ProductInfoDto> products;
    ProductRowAdapter adapter;
    RecyclerView rvProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_result);

        rvProducts = (RecyclerView) findViewById(R.id.ProductRecyclerView);

        //Initialize Data
        products = DataProvider.generateData();

        adapter = new ProductRowAdapter(products);
        rvProducts.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvProducts.setLayoutManager(layoutManager);
    }



}