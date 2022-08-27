package nz.ac.aucklanduni.softeng306.team17.galleria.view.main;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityMainBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.savedproducts.SavedProductsActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.searchbar.TopBarActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.AdapterItemDecoration;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.NavFactory;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.SimpleListInfoAdapter;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ViewPagerAdapter;


public class MainActivity extends TopBarActivity {

    ActivityMainBinding binding;
    MainActivityViewModel viewModel;

    SimpleListInfoAdapter featuredListViewAdapter;
    ViewPagerAdapter mViewPageAdapter;

    ImageView[] dotView;

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
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        setContentView(binding.getRoot());

        Toolbar toolbar = binding.topBarLayout.toolbar;
        loadToolbar(toolbar, null);
        // Show "Saved Products" button on home page
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.white_heart, null));
        toolbar.setNavigationContentDescription("Saved Products");
        toolbar.setNavigationOnClickListener(view -> {
            Intent savedIntent = new Intent(this, SavedProductsActivity.class);
            startActivity(savedIntent);
        });

        /* Set up category click listeners */
        initCategoryListeners();


        /* Set up "Most viewed products" */
        mViewPageAdapter = new ViewPagerAdapter(this, new ArrayList<>(),
                                                R.layout.main_activity_slideview, R.id.mainViewPagerMain);
        binding.mainViewPagerMain.setAdapter(mViewPageAdapter);
        initScrollTimer();
        viewModel.getMostViewedProductImages().observe(this, data -> {
            mViewPageAdapter.setImages(data);
            createDots(data.size());
        });
        binding.mainViewPagerMain.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                resetDotsWithActiveNumber(position);
            }
        });

        /* Set up "featured products" */
        featuredListViewAdapter = new SimpleListInfoAdapter();
        binding.FeaturedRecyclerView.setAdapter(featuredListViewAdapter);
        // Add spacing between main items
        binding.FeaturedRecyclerView.addItemDecoration(new AdapterItemDecoration.ListModeItemDecoration(this, 16));

        viewModel.getProducts().observe(this, featuredListViewAdapter::setProducts);
        featuredListViewAdapter.setOnItemClickListener((productId) -> new NavFactory(this).startProductDetail(productId));
    }

    private void initScrollTimer() {
        /*After setting the adapter use the timer */
        final Handler handler = new Handler();
        final Runnable Update = () -> {
            if (currentPage == 5) {
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
        Map<ImageView, Category> categoryIconMap = Map.of(
                binding.aiGeneratedIcon, Category.AI,
                binding.albumsIcon, Category.ALBUM,
                binding.photographicIcon, Category.PHOTOGRAPHIC,
                binding.paintingsIcon, Category.PAINTING
        );

        // For each image, when it is clicked on, open the relevant category result view
        categoryIconMap.forEach((icon, category) -> icon.setOnClickListener((view) -> {
            NavFactory factory = new NavFactory(this);
            factory.startCategoryResult(category);
        }));
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