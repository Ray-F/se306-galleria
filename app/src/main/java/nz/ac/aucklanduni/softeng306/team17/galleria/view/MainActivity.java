package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityMainBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;

public class MainActivity extends SearchBarActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        Toolbar toolbar = (Toolbar) binding.topBarLayout.getRoot().getChildAt(0);

        loadToolbar(toolbar);

        Intent categoryIntent = new Intent(this, SavedProductsActivity.class);
//        categoryIntent.putExtra("productId", "QcVejefcac104q3pOWUu");
        categoryIntent.putExtra("CATEGORY", Category.PHOTOGRAPHIC);
        startActivity(categoryIntent);
    }

}