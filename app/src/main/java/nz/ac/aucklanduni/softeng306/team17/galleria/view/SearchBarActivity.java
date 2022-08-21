package nz.ac.aucklanduni.softeng306.team17.galleria.view;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import java.util.ArrayList;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;

public class SearchBarActivity extends AppCompatActivity {

    public ArrayList<Intent> navigationHistory;
    
    private SearchView searchView;

    private SearchBarViewModel searchBarViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

                // Construct intent to go to Search Result Activity
//                Intent intent = new Intent(context, ProductDetailsActivity.class);
//                intent.putExtra("productName", term);
//                startActivity(intent);

                return false;
            }
        });
    }

    private void setUpSearchBarQueryListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                System.out.println("Submit ");

                // Redirect user to SearchResultActivity with query.
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("searchString", s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchBarViewModel.changeSearchQuery(s);
                return false;
            }
        });
    }

    protected void loadToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> {
            if (toolbar.getNavigationIcon().equals(R.drawable.back_button)) {
                System.out.println("IM CLICKING THE DUMB BACK BUTTON");
                Intent previousIntent = resolveReturn();
                previousIntent.putExtra("NAVIGATION", navigationHistory);
                startActivity(previousIntent);
            } else {
                Intent savedIntent = new Intent(this, SavedProductsActivity.class);
                Intent returnIntent = new Intent(this, MainActivity.class);
                navigationHistory.add(returnIntent);
                savedIntent.putExtra("NAVIGATION", navigationHistory);
                startActivity(savedIntent);
            }
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

    protected void switchBackToSavedButton(Toolbar toolbar) {
        if (toolbar.getNavigationIcon().equals(R.drawable.back_button)) {
            toolbar.setNavigationIcon(R.drawable.white_heart);
        }
    }

    protected void switchSavedToBackButton(Toolbar toolbar) {
        if (toolbar.getNavigationIcon().equals(R.drawable.white_heart)) {
            toolbar.setNavigationIcon(R.drawable.back_button);
        }
    }
}
