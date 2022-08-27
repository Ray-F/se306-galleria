package nz.ac.aucklanduni.softeng306.team17.galleria.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;
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
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_top);
        });

        customizeStatusBar();
    }

    // Change view just for home
    public void customizeStatusBar() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
    }

}
