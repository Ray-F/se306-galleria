package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;

import nz.ac.aucklanduni.softeng306.team17.galleria.R;

public class SearchBarActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        System.out.println("WHY IS THIS NOT WORKING");
        getMenuInflater().inflate(R.menu.header_bar, menu);

        MenuItem menuItem = menu.findItem(R.id.searchBar);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search for products");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                System.out.println("Submit ");

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                System.out.println("Change ");

                return false;
            }
        });

        return true;
    }

    protected void loadToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }
}
