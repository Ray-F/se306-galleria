package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;


public class ProductDetailsActivity extends AppCompatActivity {

    /**
     * Initializing the ViewPager Slider.
     * Initializing an array to store the images.
     * Creating an object of the ViewPageAdapter
     */

    ViewPager imageViewPage;
    LinearLayout buttonsSlide;
    int totalDots;
    ImageView[] dotView;
    TextView text;

    int[] imagesArray;

    ViewPagerAdapter imageViewPageAdapter;
    ProductDetailsViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        viewModel = ((GalleriaApplication) getApplication()).diProvider.productDetailsViewModel;

        imageViewPage = (ViewPager) findViewById(R.id.viewPagerMain);
        buttonsSlide = (LinearLayout) findViewById(R.id.threeDots);

        imageViewPageAdapter = new ViewPagerAdapter(ProductDetailsActivity.this, imagesArray);

        imageViewPage.setAdapter(imageViewPageAdapter);

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
                //Must have method
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
                //Must have method
            }
        });
    }
}