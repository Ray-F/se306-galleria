package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityMainBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;

public class MainActivity extends SearchBarActivity {

    private ActivityMainBinding binding;
    Stack<Intent> navigationHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        if (navigationHistory == null) {
            navigationHistory = new Stack<Intent>();
        }

        setContentView(binding.getRoot());

        Toolbar toolbar = (Toolbar) binding.topBarLayout.getRoot().getChildAt(0);

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