package nz.ac.aucklanduni.softeng306.team17.galleria.view.savedproducts;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivitySavedProductsBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.AdapterItemDecoration;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.NavFactory;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.searchbar.TopBarActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.SimpleListInfoAdapter;

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

        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        loadToolbar(binding.topBarLayout.toolbar, null);
        customizeToolbar(R.color.blackShadeGreen, R.color.darkestShadeGreen, "SAVED PRODUCTS");

        adapter = new SavedAdapter();
        binding.SavedRecyclerView.setAdapter(adapter);
        binding.SavedRecyclerView.addItemDecoration(new AdapterItemDecoration.ListModeItemDecoration(this, 16));

        // Default user
        String uuid = GalleriaApplication.DEV_USER;

        viewModel.getProducts(uuid).observe(this, products -> {
            binding.setEmptyResults(products.isEmpty());
            adapter.setProducts(products);
        });

        initListeners();
    }

    private void initListeners() {
        adapter.setOnItemClickListener((productId) -> new NavFactory(this).startProductDetail(productId));

        adapter.setOnUnsaveClickListener(viewModel::unsaveProduct);
    }
}