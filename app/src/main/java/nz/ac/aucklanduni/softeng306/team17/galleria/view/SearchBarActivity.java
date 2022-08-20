package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;

import nz.ac.aucklanduni.softeng306.team17.galleria.R;

public class SearchBarActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
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

//        MenuItem backMenuItem = menu.findItem(R.id.backButton);
//        System.out.println(backMenuItem);
//        backMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                Intent categoryIntent = new Intent(context, SavedProductsActivity.class);
//                categoryIntent.putExtra("productId", "QcVejefcac104q3pOWUu");
//                startActivity(categoryIntent);
//                return true;
//            }
//        });

        return true;
    }

    protected void loadToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("IM CLICKING THE DUMB BACK BUTTON");
                Intent categoryIntent = new Intent(context, SavedProductsActivity.class);
                startActivity(categoryIntent);
            }
        });
    }
}
