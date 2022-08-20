package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityMainBinding;

public class MainActivity extends SearchBarActivity {

    private ActivityMainBinding binding;

    // Linking to the XML elements

    ViewPager imageViewPage;

    Button saveProductButton;
    LinearLayout buttonsSlide;

    // Activity state / information
    ViewPagerAdapter imageViewPageAdapter;
    ProductDetailsViewModel viewModel;

    ImageView[] dotView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        Toolbar toolbar = (Toolbar) binding.topBarLayout.getRoot().getChildAt(0);

        loadToolbar(toolbar);

        Intent categoryIntent = new Intent(this, SavedProductsActivity.class);
//        categoryIntent.putExtra("productId", "QcVejefcac104q3pOWUu");
        startActivity(categoryIntent);

        String productId = getIntent().getExtras().getString("productId");

        viewModel = ((GalleriaApplication) getApplication()).diProvider.productDetailsViewModel;

        imageViewPageAdapter = new ViewPagerAdapter(MainActivity.this, new ArrayList<>());
        imageViewPage.setAdapter(imageViewPageAdapter);

        viewModel.setProductId(productId);

    }

    private void createDots(int nImages) {
        dotView = new ImageView[nImages];

        // Set layout parameters for the dots
        LinearLayout.LayoutParams parameters = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        parameters.setMargins(4, 0, 4, 0);

        buttonsSlide.removeAllViews();
        for (int i = 0; i < nImages; i++) {
            dotView[i] = new ImageView(this);
            buttonsSlide.addView(dotView[i], parameters);
        }

        resetDotsWithActiveNumber(0);

        imageViewPageAdapter.notifyDataSetChanged();
    }

    private void resetDotsWithActiveNumber(int currentPosition) {
        Arrays.stream(dotView).forEach(it -> it.setImageResource(R.drawable.dot_for_viewpager));
        dotView[currentPosition].setImageResource(R.drawable.dot_for_viewpager_selected);
    }

    private void initListeners() {

        // Add image scroll listener
        imageViewPage.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                resetDotsWithActiveNumber(position);
            }
        });

        // Add saved product click button listener
        saveProductButton.setOnClickListener(view -> {
            viewModel.toggleSaveProduct();
        });
    }
}