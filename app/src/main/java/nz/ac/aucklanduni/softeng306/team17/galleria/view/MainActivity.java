package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;

import nz.ac.aucklanduni.softeng306.team17.galleria.R;

public class MainActivity extends SearchBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Link XML elements with code
        setContentView(R.layout.activity_main);
        AppBarLayout layout = findViewById(R.id.topBarLayout);

        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) layout.getChildAt(0);

        loadToolbar(toolbar);

//        Intent categoryIntent = new Intent(this, CategoryResultActivity.class);
//        categoryIntent.putExtra("productId", "QcVejefcac104q3pOWUu");
//        categoryIntent.putExtra("CATEGORY", Category.PHOTOGRAPHIC);
//        startActivity(categoryIntent);
    }

}