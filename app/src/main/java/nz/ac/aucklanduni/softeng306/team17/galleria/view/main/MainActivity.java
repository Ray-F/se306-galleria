package nz.ac.aucklanduni.softeng306.team17.galleria.view.main;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityMainBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.searchbar.SearchBarActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.SimpleListInfoAdapter;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.SimpleListInfoAdapter.ListModeItemDecoration;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ViewPagerAdapter;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.categoryresult.CategoryResultActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.productdetail.ProductDetailsActivity;


public class MainActivity extends SearchBarActivity {

    ActivityMainBinding binding;
    SimpleListInfoAdapter featuredListViewAdapter;
    ViewPagerAdapter mViewPageAdapter;
    MainActivityViewModel viewModel;
    ImageView[] dotView;
    private Toolbar toolbar;

    Timer timer;
    int currentPage = 0;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        viewModel = ((GalleriaApplication) getApplication()).diProvider.mainActivityViewModel;

        super.onCreate(savedInstanceState);

        // Link XML elements with code
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        // Don't recreate/replace stack if we have returned back to a new instance of Main Activity.
        if (navigationHistory == null) {
            navigationHistory = new ArrayList<>();
        }
        setNavigationHistory(navigationHistory);

        setContentView(binding.getRoot());

        toolbar = (Toolbar) binding.topBarLayout.getRoot().getChildAt(0);
        loadToolbar(toolbar);
        customizeToolbar(toolbar);

        /*
         * Set up categories
         */
        initCategoryListeners();


        /*
         * Set up "Most viewed products"
         */
        mViewPageAdapter = new ViewPagerAdapter(this, new ArrayList<>(),
                                                R.layout.main_activity_slideview, R.id.mainViewPagerMain);
        binding.mainViewPagerMain.setAdapter(mViewPageAdapter);
        initScrollTimer();
        viewModel.fetchMostViewedProducts();
        viewModel.getMostViewedProductImages().observe(this, data -> {
            mViewPageAdapter.setImages(data);
            mViewPageAdapter.notifyDataSetChanged();
            createDots(data.size());
        });
        binding.mainViewPagerMain.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                resetDotsWithActiveNumber(position);
            }
        });

        /*
         * Set up "featured products"
         */
        featuredListViewAdapter = new SimpleListInfoAdapter();
        binding.FeaturedRecyclerView.setAdapter(featuredListViewAdapter);
        binding.FeaturedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Add spacing between main items
        binding.FeaturedRecyclerView.addItemDecoration(new ListModeItemDecoration(this, 16));
        viewModel.fetchFeaturedProducts();
        viewModel.getProducts().observe(this, featuredListViewAdapter::setProducts);
        featuredListViewAdapter.setOnItemClickListener((productId) -> {
            Intent returnIntent = new Intent(this, MainActivity.class);
            navigationHistory.add(returnIntent);

            Intent productIntent = new Intent(this, ProductDetailsActivity.class);
            productIntent.putExtra("productId", productId);
            productIntent.putExtra("NAVIGATION", navigationHistory);
            productIntent.putExtra("CATEGORY", Category.AI);

            startActivity(productIntent);
        });
    }

    private void initScrollTimer() {
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage == 3) {
                currentPage = 0;
            }
            binding.mainViewPagerMain.setCurrentItem(currentPage++, true);
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
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

    private void customizeToolbar(Toolbar toolbar) {
        switchToSavedButton(toolbar);
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