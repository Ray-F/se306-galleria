package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent categoryIntent = new Intent(this, ProductDetailsActivity.class);
        categoryIntent.putExtra("productId", "QcVejefcac104q3pOWUu");
        startActivity(categoryIntent);
    }
}