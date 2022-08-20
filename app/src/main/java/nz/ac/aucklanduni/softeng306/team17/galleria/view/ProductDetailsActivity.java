package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;


public class ProductDetailsActivity extends AppCompatActivity {

    /**
     * Initializing the ViewPager Slider.
     * Initializing an array to store the images.
     * Creating an object of the ViewPageAdapter
     */

    ViewPager imageViewPage;
    TextView productDetailsName;
    TextView productDetailsPrice;
    TextView productReviewInfo;
    TextView productDetailsTagline;
    TextView productDetailDescription;
    TextView productDetailsStock;
    Button saveProductButton;

    LinearLayout buttonsSlide;
    int totalDots;
    ImageView[] dotView;

    List<Bitmap> imagesArray = new ArrayList<>();

    ViewPagerAdapter imageViewPageAdapter;
    ProductDetailsViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        imageViewPage = findViewById(R.id.viewPagerMain);
        productDetailsName = findViewById(R.id.productDetailName);
        productDetailsPrice = findViewById(R.id.detailsItemPrice);

        // Format should be string like '★ 4.2 (12 reviews)', compute within viewmodel
        productReviewInfo = findViewById(R.id.theReviewOfTheProduct);
        productDetailDescription = findViewById(R.id.productDetailDescription);
        productDetailsTagline = findViewById(R.id.productDetailsArtist);
        saveProductButton = findViewById(R.id.saveProductButton);

        // 'In stock' or 'Out of stock'
        productDetailsStock = findViewById(R.id.productDetailsStock);

        buttonsSlide = findViewById(R.id.threeDots);

        // We need a default bitmap and imagesArray must have at least one value at position 0 to load. Set it blank so unnoticed while true data loads.
        Bitmap defaultBitmap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.blank_icon);
        imagesArray.add(defaultBitmap);

        imageViewPageAdapter = new ViewPagerAdapter(ProductDetailsActivity.this, imagesArray);

        imageViewPage.setAdapter(imageViewPageAdapter);

        viewModel = ((GalleriaApplication) getApplication()).diProvider.productDetailsViewModel;

        // Will have to have a method of passing a value for this from other screen's (on click)
        viewModel.getProduct("QcVejefcac104q3pOWUu").observe(
                this, data -> {
                    System.out.println("Single product returned successfully");

                    imagesArray = new ArrayList<>();
                    imagesArray.addAll(data.getAllImages());
                    imageViewPageAdapter.setImages(imagesArray);

                    productDetailsName.setText(data.getName());
                    productDetailsPrice.setText(data.getDisplayPrice());
                    productDetailDescription.setText(data.getDescription());
                    productDetailsTagline.setText(data.getTagline());
                    productDetailsStock.setText(data.isInStock() ? "In Stock" : "Out of Stock");

                    imageViewPageAdapter.notifyDataSetChanged();
                }
        );

        totalDots = imageViewPageAdapter.getCount();

        dotView = new ImageView[totalDots];

        for (int i = 0; i < totalDots; i++) {
            dotView[i] = new ImageView(this);
            dotView[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dot_for_viewpager));

            LinearLayout.LayoutParams parameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            parameters.setMargins(4, 0, 4, 0);

            buttonsSlide.addView(dotView[i], parameters);
        }

        dotView[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dot_for_viewpager));

        imageViewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                /* Automatic import from OnPageChangeListener must have this method */
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < totalDots; i++) {
                    dotView[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dot_for_viewpager));
                }
                dotView[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.dot_for_viewpager));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /* Automatic import from OnPageChangeListener must have this method */
            }

        });

        saveProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Functionality goes here to add this product string to saved products list in database for a user?
                productDetailsName.setText("JUST SAVED THIS PRODUCT TEST");
            }
        });
    }
}