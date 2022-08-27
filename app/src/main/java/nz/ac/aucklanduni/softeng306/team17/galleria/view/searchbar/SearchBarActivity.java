package nz.ac.aucklanduni.softeng306.team17.galleria.view.searchbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import java.util.ArrayList;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.main.MainActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.savedproducts.SavedProductsActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.searchresult.SearchResultActivity;

public class SearchBarActivity extends AppCompatActivity {


    private SearchBarActivity instance;

    private SearchBarViewModel searchBarViewModel;

    private Toolbar toolbar;
    private RelativeLayout secondaryToolbar;
    private SearchView searchView;

    public ArrayList<Intent> navigationHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance = this;
        super.onCreate(savedInstanceState);
        searchBarViewModel = ((GalleriaApplication) getApplication()).diProvider.searchBarViewModel;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.header_bar, menu);
        MenuItem menuItem = menu.findItem(R.id.searchBar);

        searchView = (SearchView) menuItem.getActionView();

        setUpSearchBar();

        return true;
    }

    private void setUpSearchBar() {
        // Create cursor for search autocomplete functionality
        final String[] from = new String[] { "productName" };
        final int[] to = new int[] { android.R.id.text1 };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                                                              android.R.layout.simple_list_item_1,
                                                              null, from, to,
                                                              CursorAdapter.NO_SELECTION);

        // Set up LiveData listener to fill in data in suggestions whenever suggestions change
        searchBarViewModel.getAutofill().observe(this, data -> {
            final MatrixCursor c = new MatrixCursor(new String[] { BaseColumns._ID, "productName" });
            for (int i = 0; i < data.size(); i++) {
                c.addRow(new Object[] { i, data.get(i) });
            }
            adapter.changeCursor(c);
        });


        searchView.setQueryHint("Search for products");
        searchView.setSuggestionsAdapter(adapter);

        // Set search threshold to 0 (so all query sizes will return a result)
        ((AutoCompleteTextView) searchView.findViewById(androidx.appcompat.R.id.search_src_text))
                .setThreshold(0);

        setUpSearchBarQueryListener();
        setUpSearchBarAutocompleteClickListener();
    }

    private void setUpSearchBarAutocompleteClickListener() {
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Cursor cursor = (Cursor) searchView.getSuggestionsAdapter().getItem(position);
                int index = cursor.getColumnIndex("productName");
                String term = cursor.getString(index);
                cursor.close();

                Intent returnIntent = getIntent();
                navigationHistory.add(returnIntent);

                // Construct intent to go to Search Result Activity
                Intent intent = new Intent(instance, SearchResultActivity.class);
                intent.putExtra("searchTerm", term);
                intent.putExtra("NAVIGATION", navigationHistory);
                startActivity(intent);
                return false;
            }
        });
    }

    private void setUpSearchBarQueryListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent returnIntent = getIntent();
                navigationHistory.add(returnIntent);

                // Redirect user to SearchResultActivity with query.
                Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
                intent.putExtra("searchTerm", s);
                intent.putExtra("NAVIGATION", navigationHistory);
                startActivity(intent);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchBarViewModel.changeSearchQuery(s);
                return false;
            }
        });
    }

    protected void loadToolbar(Toolbar toolbar, RelativeLayout secondaryToolbar) {
        this.toolbar = toolbar;
        this.secondaryToolbar = secondaryToolbar;

        setSupportActionBar(this.toolbar);
        this.toolbar.setNavigationOnClickListener(view -> {
            Intent previousIntent = resolveReturn();
            previousIntent.putExtra("NAVIGATION", navigationHistory);
            startActivity(previousIntent);
        });
    }

    protected void setNavigationHistory(ArrayList<Intent> navHistory) {
        navigationHistory = navHistory;
    }

    private Intent resolveReturn() {
        if (navigationHistory.isEmpty()) {
            return new Intent(this, MainActivity.class);
        } else {
            return navigationHistory.remove(navigationHistory.size()-1);
        }
    }

    protected void customizeToolbar(int statusBarColourResId, int toolbarColourResId, String toolbarTitle) {
        getWindow().setStatusBarColor(ContextCompat.getColor(this, statusBarColourResId));
        toolbar.setBackgroundColor(ContextCompat.getColor(this, toolbarColourResId));
        toolbar.setTitle(toolbarTitle);
    }

    protected void customizeToolbar(int statusBarColourResId, int toolbarColourResId, int secondaryToolbarColourResId, String toolbarTitle) {
        customizeToolbar(statusBarColourResId, toolbarColourResId, toolbarTitle);

        if (secondaryToolbar != null) {
            secondaryToolbar.setBackgroundColor(ContextCompat.getColor(this, secondaryToolbarColourResId));
        }
    }
}
