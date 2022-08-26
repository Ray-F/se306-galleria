package nz.ac.aucklanduni.softeng306.team17.galleria.view.savedproducts;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ProductInfoDto;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.searchbar.SearchBarActivity;

public class SavedProductsActivity extends SearchBarActivity {

    SavedAdapter adapter;
    RecyclerView rvSaved;
    SavedProductsViewModel viewModel;
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_products);

        Bundle allKeys = getIntent().getExtras();
        navigationHistory = (ArrayList<Intent>) allKeys.get("NAVIGATION");

        appBarLayout = findViewById(R.id.topBarLayout);
        Toolbar toolbar = (Toolbar) appBarLayout.getChildAt(0);
        loadToolbar(toolbar);

        rvSaved = findViewById(R.id.SavedRecyclerView);

        adapter = new SavedAdapter();
        adapter.setNavigationHistory(navigationHistory);
        rvSaved.setAdapter(adapter);

        // Bind ViewModel
        viewModel = ((GalleriaApplication) getApplication()).diProvider.savedProductsViewModel;
        adapter.setViewModel(viewModel);

        // Default user
        String uuid = GalleriaApplication.DEV_USER;

        viewModel.getProducts(uuid)
                .observe(this, data -> {
                    adapter.setProducts(data);
                    adapter.notifyDataSetChanged();
                });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvSaved.setLayoutManager(layoutManager);

    }
}