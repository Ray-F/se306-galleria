package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        binding.continueAnonymouslyBtn.setOnClickListener(clickEvent -> {
            // Main
            Intent intent = new Intent(this, SearchResultActivity.class);
            intent.putExtra("searchTerm", "matcha");
            intent.putExtra("NAVIGATION", new ArrayList<>());
            startActivity(intent);
        });
    }

}
