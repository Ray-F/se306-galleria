package nz.ac.aucklanduni.softeng306.team17;

import android.app.Application;

import com.google.firebase.FirebaseApp;

import nz.ac.aucklanduni.softeng306.team17.galleria.di.DIProvider;

public class GalleriaApplication extends Application {

    public DIProvider diProvider;

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(getApplicationContext());

        diProvider = new DIProvider();
    }

}
