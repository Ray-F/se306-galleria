package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityMainBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;

public class MainActivity extends SearchBarActivity {

    private ActivityMainBinding binding;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // Don't recreate/replace stack if we have returned back to a new instance of Main Activity.
        if (navigationHistory == null) {
            navigationHistory = new ArrayList<Intent>();
        }
        setNavigationHistory(navigationHistory);

        setContentView(binding.getRoot());

        toolbar = (Toolbar) binding.topBarLayout.getRoot().getChildAt(0);
        removeBackButton(toolbar);
        loadToolbar(toolbar);

        initCategoryListeners();
    }

    private void initCategoryListeners() {
        Map<ImageView, Category> categoryIconMap = new HashMap<>();
        categoryIconMap.put(binding.aiGeneratedIcon, Category.AI);
        categoryIconMap.put(binding.albumsIcon, Category.ALBUM);
        categoryIconMap.put(binding.photographicIcon, Category.PHOTOGRAPHIC);
        categoryIconMap.put(binding.paintingsIcon, Category.PAINTING);


        categoryIconMap.forEach((icon, category) -> {
            icon.setOnClickListener((view) -> {
                navigationHistory.add(new Intent(this, MainActivity.class));

                Intent categoryIntent = new Intent(this, CategoryResultActivity.class);
                categoryIntent.putExtra("CATEGORY", category);
                categoryIntent.putExtra("NAVIGATION", navigationHistory);
                startActivity(categoryIntent);
            });
        });
    }

}