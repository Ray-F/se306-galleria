package nz.ac.aucklanduni.softeng306.team17.galleria.view.categoryresult;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import java.util.Locale;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.NavFactory;
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

        category = (Category) getIntent().getExtras().get(NavFactory.CATEGORY_INTENT_KEY);

        // Load and customize the toolbar
        Toolbar toolbar = binding.topBarLayout.toolbar;
        loadToolbar(toolbar, binding.secondaryToolbar);
        ColourTheme theme = ColourTheme.getThemeByCategory(category);
        customizeToolbar(theme.dark, theme.normal, theme.light, category.displayName.toUpperCase(Locale.ENGLISH));

        // Load data and observe, observed data changes are set inside the super class for
        // populating the list activity
        categoryResultViewModel.fetchCategoryProducts(category);
        categoryResultViewModel.getProducts().observe(this, super::setProducts);

        super.setOnItemClickListener((productId) -> {
            new NavFactory(this).startProductDetail(productId);
        });
    }

}