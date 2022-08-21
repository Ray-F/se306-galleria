package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;


public class ProductDetailsActivity extends SearchBarActivity {

    // Links to XML elements
    ViewPager imageViewPage;
    TextView productDetailsName;
    TextView productDetailsPrice;
    TextView productReviewInfo;
    TextView productDetailsTagline;
    TextView productDetailDescription;
    TextView productDetailsStock;
    Button saveProductButton;
    LinearLayout buttonsSlide;

    // Activity state / information
    ViewPagerAdapter imageViewPageAdapter;
    ProductDetailsViewModel viewModel;

    ImageView[] dotView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linkElements();

        Bundle allKeys = getIntent().getExtras();
        navigationHistory = (ArrayList<Intent>) allKeys.get("NAVIGATION");

        Toolbar toolbar = (Toolbar) ((AppBarLayout) findViewById(R.id.topBarLayout)).getChildAt(0);
        switchSavedToBackButton(toolbar);
        loadToolbar(toolbar);

        imageViewPage.setAdapter(imageViewPageAdapter);

        // Get passed in product Id from intent
        String productId = getIntent().getExtras().getString("productId");

        viewModel = ((GalleriaApplication) getApplication()).diProvider.productDetailsViewModel;

        imageViewPageAdapter = new ViewPagerAdapter(ProductDetailsActivity.this, new ArrayList<>(),
                R.layout.product_details_slideview, R.id.imageViewMain);
        imageViewPage.setAdapter(imageViewPageAdapter);


        viewModel.setProductId(productId);
        loadViewModelData();

        initListeners();
    }

    private void linkElements() {
        // Link XML elements with code
        setContentView(R.layout.activity_product_detail);

        imageViewPage = findViewById(R.id.viewPagerMain);
        productDetailsName = findViewById(R.id.productDetailName);
        productDetailsPrice = findViewById(R.id.detailsItemPrice);
        productReviewInfo = findViewById(R.id.theReviewOfTheProduct);
        productDetailDescription = findViewById(R.id.productDetailDescription);
        productDetailsTagline = findViewById(R.id.productDetailsArtist);
        saveProductButton = findViewById(R.id.saveProductButton);
        productDetailsStock = findViewById(R.id.productDetailsStock);
        buttonsSlide = findViewById(R.id.threeDots);
    }

    private void loadViewModelData() {
        viewModel.getProduct().observe(
                this, data -> {
                    System.out.println("Single product returned successfully");

                    List<Bitmap> imagesInCarousel = data.getAllImages();

                    imageViewPageAdapter.setImages(imagesInCarousel);
                    createDots(imagesInCarousel.size());

                    productReviewInfo.setText(data.getReviewString());
                    productDetailsName.setText(data.getName());
                    productDetailsPrice.setText(data.getDisplayPrice());
                    productDetailDescription.setText(data.getDescription());
                    productDetailsTagline.setText(data.getTagline());
                    productDetailsStock.setText(data.isInStock() ? "In Stock" : "Out of Stock");

                }
        );

        viewModel.isSavedProduct().observe(
                this, data -> {
                    saveProductButton.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, data ? R.color.darkestShadeGray : R.color.darkestShadeGreen)));
                    saveProductButton.setText(data ? "Saved! (Click again to unsave)" : "SAVE Product");
                }
        );
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