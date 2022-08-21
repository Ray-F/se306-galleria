package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;
import java.util.Map;

import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityMainBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;

public class MainActivity extends SearchBarActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Link XML elements with code
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) binding.topBarLayout.getRoot().getChildAt(0);
        loadToolbar(toolbar);

        initCategoryListeners();
    }

    private void initCategoryListeners() {
        // Map each image to its related category
        Map<ImageView, Category> categoryIconMap = new HashMap<>();
        categoryIconMap.put(binding.aiGeneratedIcon, Category.AI);
        categoryIconMap.put(binding.albumsIcon, Category.ALBUM);
        categoryIconMap.put(binding.photographicIcon, Category.PHOTOGRAPHIC);
        categoryIconMap.put(binding.paintingsIcon, Category.PAINTING);

        // For each image, when it is clicked on, open the relevant category result view
        categoryIconMap.forEach((icon, category) -> {
            icon.setOnClickListener((view) -> {
                Intent categoryIntent = new Intent(this, CategoryResultActivity.class);
                categoryIntent.putExtra("category", category);
                startActivity(categoryIntent);
            });
        });
    }

}