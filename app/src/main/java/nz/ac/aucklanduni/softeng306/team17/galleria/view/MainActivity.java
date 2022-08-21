package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityMainBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;


public class MainActivity extends SearchBarActivity {

    ActivityMainBinding binding;
    CategoryResultAdapter adapter;
    ViewPagerAdapter mViewPageAdapter;
    MainActivityViewModel viewModel;
    ImageView[] dotView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Link XML elements with code
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) binding.topBarLayout.getRoot().getChildAt(0);
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

        viewModel.fetchMostViewedProducts();
        viewModel.getMostViewedProductImages().observe(this, data -> {
            mViewPageAdapter.setImages(data);
            mViewPageAdapter.notifyDataSetChanged();
        });

//        ImageView imgView = (ImageView)findViewById(R.id.mostPopular);
//        imgView.setBackgroundResource(R.drawable.mostviewed);
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
    }

    private void resetDotsWithActiveNumber(int currentPosition) {
        Arrays.stream(dotView).forEach(it -> it.setImageResource(R.drawable.dot_for_viewpager));
        dotView[currentPosition].setImageResource(R.drawable.dot_for_viewpager_selected);
    }
}