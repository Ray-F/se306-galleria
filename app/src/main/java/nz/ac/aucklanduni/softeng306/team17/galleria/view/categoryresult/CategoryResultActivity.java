package nz.ac.aucklanduni.softeng306.team17.galleria.view.categoryresult;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityListResultBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.productdetail.ProductDetailsActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ColourTheme;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ListResultActivity;

public class CategoryResultActivity extends ListResultActivity {

    private CategoryResultViewModel categoryResultViewModel;

    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        categoryResultViewModel = ((GalleriaApplication) getApplication()).diProvider.categoryResultViewModel;

        binding.setViewmodel(categoryResultViewModel);
        binding.setLifecycleOwner(this);

        Bundle allKeys = getIntent().getExtras();
        category = (Category) allKeys.get("CATEGORY");
        navigationHistory = (ArrayList<Intent>) allKeys.get("NAVIGATION");

        // Load and customize the toolbar
        Toolbar toolbar = binding.topBarLayout.toolbar;
        loadToolbar(toolbar, binding.secondaryToolbar);
        ColourTheme theme = ColourTheme.getThemeByCategory(category);
        customizeToolbar(theme.dark, theme.normal, theme.light, category.displayName);

        categoryResultViewModel.fetchCategoryProducts(category);
        categoryResultViewModel.getProducts().observe(this, super::setProducts);

        super.setOnItemClickListener((productId) -> {
            Intent returnIntent = new Intent(this, CategoryResultActivity.class);
            returnIntent.putExtra("CATEGORY", category);
            navigationHistory.add(returnIntent);

            Intent productIntent = new Intent(this, ProductDetailsActivity.class);
            productIntent.putExtra("productId", productId);
            productIntent.putExtra("NAVIGATION", navigationHistory);

            startActivity(productIntent);
        });
    }

}