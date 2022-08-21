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
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;

public class SearchBarActivity extends AppCompatActivity {

    private SearchBarActivity context;

    private SimpleCursorAdapter adapter;

    private SearchBarViewModel searchBarViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);

        searchBarViewModel = ((GalleriaApplication) getApplication()).diProvider.searchBarViewModel;

        final String[] from = new String[] { "productName" };
        final int[] to = new int[] { android.R.id.text1 };

        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                                          null, from, to, CursorAdapter.NO_SELECTION) {
            @Override
            public void changeCursor(Cursor cursor) { super.swapCursor(cursor); }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.header_bar, menu);
        MenuItem menuItem = menu.findItem(R.id.searchBar);

        SearchView searchView = (SearchView) menuItem.getActionView();

        setUpSearchBar(searchView);

        return true;
    }

    private void setUpSearchBar(SearchView searchView) {
        searchView.setQueryHint("Search for products");
        searchView.setSuggestionsAdapter(adapter);

        AutoCompleteTextView searchAutoCompleteTextView = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchAutoCompleteTextView.setThreshold(0);

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

        searchBarViewModel.getAutofill().observe(context, data -> {
            final MatrixCursor c = new MatrixCursor(new String[] {BaseColumns._ID, "productName" });
            for (int i = 0; i < data.size(); i++) {
                c.addRow(new Object[] { i, data.get(i)});
            }
            adapter.changeCursor(c);
        });
    }

    protected void loadToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(view -> {
            Intent categoryIntent = new Intent(context, SavedProductsActivity.class);
            startActivity(categoryIntent);
        });
    }
}
