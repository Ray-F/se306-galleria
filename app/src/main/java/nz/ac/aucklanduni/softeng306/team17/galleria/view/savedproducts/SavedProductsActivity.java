package nz.ac.aucklanduni.softeng306.team17.galleria.view.savedproducts;

import android.os.Bundle;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivitySavedProductsBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.navigation.NavFactory;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.searchbar.TopBarActivity;

public class SavedProductsActivity extends TopBarActivity {

    ActivitySavedProductsBinding binding;

    SavedAdapter adapter;
    SavedProductsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySavedProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ((GalleriaApplication) getApplication()).diProvider.savedProductsViewModel;

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
        adapter.setOnItemClickListener((productId) -> new NavFactory(this).startProductDetail(productId));

        adapter.setOnUnsaveClickListener(viewModel::unsaveProduct);
    }
}