package nz.ac.aucklanduni.softeng306.team17.galleria.view.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityLoginBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.main.MainActivity;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        binding.continueAnonymouslyBtn.setOnClickListener(clickEvent -> {
            // Main
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("NAVIGATION", new ArrayList<>());
            startActivity(intent);
        });
    }

}
