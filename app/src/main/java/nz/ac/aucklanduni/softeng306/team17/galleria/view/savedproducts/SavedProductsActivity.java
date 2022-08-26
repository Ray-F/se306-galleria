package nz.ac.aucklanduni.softeng306.team17.galleria.view.savedproducts;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivitySavedProductsBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.productdetail.ProductDetailsActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.searchbar.SearchBarActivity;

public class SavedProductsActivity extends SearchBarActivity {

    ActivitySavedProductsBinding binding;

    SavedAdapter adapter;
    SavedProductsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySavedProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ((GalleriaApplication) getApplication()).diProvider.savedProductsViewModel;

        Bundle allKeys = getIntent().getExtras();
        navigationHistory = (ArrayList<Intent>) allKeys.get("NAVIGATION");

        loadToolbar(binding.topBarLayout.toolbar, null);
        customizeToolbar(R.color.blackShadeGreen, R.color.darkestShadeGreen, "SAVED PRODUCTS");

        adapter = new SavedAdapter();
        binding.SavedRecyclerView.setAdapter(adapter);

        // Default user
        String uuid = GalleriaApplication.DEV_USER;

        viewModel.getProducts(uuid).observe(this, adapter::setProducts);

        initListeners();
    }

    private void initListeners() {
        adapter.setOnItemClickListener((productId) -> {
            Intent returnIntent = new Intent(this, SavedProductsActivity.class);
            navigationHistory.add(returnIntent);

            Intent productIntent = new Intent(this, ProductDetailsActivity.class);
            productIntent.putExtra("productId", productId);
            productIntent.putExtra("NAVIGATION", navigationHistory);

            startActivity(productIntent);
        });

        adapter.setOnUnsaveClickListener(viewModel::unsaveProduct);
    }
}