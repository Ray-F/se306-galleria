package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityMainBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;


public class MainActivity extends SearchBarActivity {

    ActivityMainBinding binding;
    CategoryResultAdapter adapter;
    ViewPagerAdapter mViewPageAdapter;
    MainActivityViewModel viewModel;
    ImageView[] dotView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIsNavActive(false);

        // Link XML elements with code
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // Don't recreate/replace stack if we have returned back to a new instance of Main Activity.
        if (navigationHistory == null) {
            navigationHistory = new ArrayList<Intent>();
        }
        setNavigationHistory(navigationHistory);

        setContentView(binding.getRoot());

        toolbar = (Toolbar) binding.topBarLayout.getRoot().getChildAt(0);
        switchBackToSavedButton(toolbar);
        loadToolbar(toolbar);

        adapter = new CategoryResultAdapter();
        binding.MainRecyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.MainRecyclerView.setLayoutManager(layoutManager);

        viewModel = ((GalleriaApplication) getApplication()).diProvider.mainActivityViewModel;

        viewModel.fetchFeaturedProducts();
        viewModel.getProducts().observe(this, data -> {
            adapter.setProducts(data);
            adapter.notifyDataSetChanged();
        });

        mViewPageAdapter = new ViewPagerAdapter(this, new ArrayList<>(),
                R.layout.main_activity_slideview, R.id.mainViewPagerMain);
        binding.mainViewPagerMain.setAdapter(mViewPageAdapter);
        binding.mainViewPagerMain.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                resetDotsWithActiveNumber(position);
            }
        });

        viewModel.fetchMostViewedProducts();
        viewModel.getMostViewedProductImages().observe(this, data -> {
            mViewPageAdapter.setImages(data);
            mViewPageAdapter.notifyDataSetChanged();
            createDots(data.size());
        });

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
                navigationHistory.add(new Intent(this, MainActivity.class));

                Intent categoryIntent = new Intent(this, CategoryResultActivity.class);
                categoryIntent.putExtra("CATEGORY", category);
                categoryIntent.putExtra("NAVIGATION", navigationHistory);
                startActivity(categoryIntent);
            });
        });
    }

    private void createDots(int nImages) {
        dotView = new ImageView[nImages];
        // Set layout parameters for the dots
        LinearLayout.LayoutParams parameters = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        parameters.setMargins(4, 0, 4, 0);

        binding.threeDots.removeAllViews();
        for (int i = 0; i < nImages; i++) {
            dotView[i] = new ImageView(this);
            binding.threeDots.addView(dotView[i], parameters);
        }

        if (nImages > 0) {
            resetDotsWithActiveNumber(0);
        }

    }

    private void resetDotsWithActiveNumber(int currentPosition) {
        Arrays.stream(dotView).forEach(it -> it.setImageResource(R.drawable.dot_for_viewpager));
        dotView[currentPosition].setImageResource(R.drawable.dot_for_viewpager_selected);
    }
}