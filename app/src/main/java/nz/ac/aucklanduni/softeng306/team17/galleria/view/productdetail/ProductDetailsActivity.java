package nz.ac.aucklanduni.softeng306.team17.galleria.view.productdetail;

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

import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Angle;
import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.core.models.Size;
import nl.dionsegijn.konfetti.xml.KonfettiView;
import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityMainBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityProductDetailBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.searchbar.SearchBarActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ViewPagerAdapter;


public class ProductDetailsActivity extends SearchBarActivity {

    ActivityProductDetailBinding binding;

    // Activity state / information
    ViewPagerAdapter imageViewPageAdapter;
    ProductDetailsViewModel viewModel;

    ImageView[] dotView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Link XML elements with code
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle allKeys = getIntent().getExtras();
        navigationHistory = (ArrayList<Intent>) allKeys.get("NAVIGATION");

        Toolbar toolbar = binding.topBarLayout.toolbar;
        loadToolbar(toolbar, null);

        binding.viewPagerMain.setAdapter(imageViewPageAdapter);

        // Get passed in product Id from intent
        String productId = getIntent().getExtras().getString("productId");

        viewModel = ((GalleriaApplication) getApplication()).diProvider.productDetailsViewModel;

        imageViewPageAdapter = new ViewPagerAdapter(ProductDetailsActivity.this, new ArrayList<>(),
                R.layout.product_details_slideview, R.id.imageViewMain);
        binding.viewPagerMain.setAdapter(imageViewPageAdapter);


        viewModel.setProductId(productId);
        loadViewModelData();

        initListeners();
    }

    private void loadViewModelData() {
        viewModel.getProduct().observe(
                this, data -> {
                    System.out.println("Single product returned successfully");

                    List<Bitmap> imagesInCarousel = data.getAllImages();

                    imageViewPageAdapter.setImages(imagesInCarousel);
                    createDots(imagesInCarousel.size());

                    binding.theReviewOfTheProduct.setText(data.getReviewString());
                    binding.productDetailName.setText(data.getName());
                    binding.detailsItemPrice.setText(data.getDisplayPrice());
                    binding.productDetailDescription.setText(data.getDescription());
                    binding.productDetailsArtist.setText(data.getTagline());
                    binding.productDetailsStock.setText(data.isInStock() ? "In Stock" : "Out of Stock");

                }
        );

        viewModel.isSavedProduct().observe(
                this, data -> {
                    binding.saveProductButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, data ? R.color.darkestShadeGray : R.color.darkestShadeGreen)));
                    binding.saveProductButton.setText(data ? "Saved! (Click again to unsave)" : "SAVE Product");
                }
        );
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

        resetDotsWithActiveNumber(0);

        imageViewPageAdapter.notifyDataSetChanged();
    }

    private void resetDotsWithActiveNumber(int currentPosition) {
        Arrays.stream(dotView).forEach(it -> it.setImageResource(R.drawable.dot_for_viewpager));
        dotView[currentPosition].setImageResource(R.drawable.dot_for_viewpager_selected);
    }

    private void initListeners() {

        // Add image scroll listener
        binding.viewPagerMain.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                resetDotsWithActiveNumber(position);
            }
        });

        // Add saved product click button listener
        binding.saveProductButton.setOnClickListener(view -> {
            if (binding.saveProductButton.getText().toString().toLowerCase(Locale.ROOT).equals("save product")) {
                EmitterConfig emitterConfig = new Emitter(2L, TimeUnit.SECONDS).perSecond(200);
                Party party = new PartyFactory(emitterConfig)
                        .angle(Angle.TOP)
                        .spread(90)
                        .setSpeedBetween(15f, 20f)
                        .timeToLive(1000L)
                        .sizes(new Size(12, 5f, 0.2f))
                        .position(0.0, 0, 1.0, 1)
                        .build();

                ((KonfettiView) findViewById(R.id.konfettiView)).start(party);
            }

            viewModel.toggleSaveProduct();
        });
    }
}