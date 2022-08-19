package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.data.DataProvider;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.ProductInfoDto;

public class SavedProductsActivity extends AppCompatActivity {

    ArrayList<ProductInfoDto> products;
    SavedAdapter adapter;
    RecyclerView rvSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_products);

        rvSaved = (RecyclerView) findViewById(R.id.SavedRecyclerView);

        //Initialize Data
        products = DataProvider.generateData();

        adapter = new SavedAdapter(products);
        rvSaved.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvSaved.setLayoutManager(layoutManager);

    }
}