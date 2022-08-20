package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;

public class SavedProductsActivity extends AppCompatActivity {

    ArrayList<ProductInfoDto> products;
    SavedAdapter adapter;
    RecyclerView rvSaved;
    SavedProductsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_products);

        rvSaved = (RecyclerView) findViewById(R.id.SavedRecyclerView);

        adapter = new SavedAdapter();
        rvSaved.setAdapter(adapter);

        // Bind ViewModel
        viewModel = ((GalleriaApplication) getApplication()).diProvider.savedProductsViewModel;

        // Default user
        String uuid = "Raymond Fang";

        viewModel.getProducts(uuid)
                .observe(this, data -> {
                    System.out.println("When does this change!");
                    adapter.setProducts(data);
                    adapter.notifyDataSetChanged();
                });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvSaved.setLayoutManager(layoutManager);

    }
}