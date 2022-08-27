package nz.ac.aucklanduni.softeng306.team17.galleria.view.shared;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.comparators.NameAscendingComparator;
import nz.ac.aucklanduni.softeng306.team17.galleria.comparators.NameDescendingComparator;
import nz.ac.aucklanduni.softeng306.team17.galleria.comparators.PriceAscendingComparator;
import nz.ac.aucklanduni.softeng306.team17.galleria.comparators.PriceDescendingComparator;
import nz.ac.aucklanduni.softeng306.team17.galleria.comparators.ViewAscendingComparator;
import nz.ac.aucklanduni.softeng306.team17.galleria.comparators.ViewDescendingComparator;
import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityListResultBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.searchbar.SearchBarActivity;

public class ListResultActivity extends SearchBarActivity {

    private ListResultViewModel viewModel;
    private RecyclerView recyclerView;

    private SimpleListInfoAdapter listViewAdapter;
    protected ActivityListResultBinding binding;


    protected void setProducts(List<ProductInfoDto> products) {
        viewModel.getSortComparator().observe(this, comparator -> {
            products.sort(comparator);
            listViewAdapter.setProducts(products);
        });
    }

    protected void setOnItemClickListener(ProductClickListener listener) {
        listViewAdapter.setOnItemClickListener(listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.listViewAdapter = new SimpleListInfoAdapter();
        this.recyclerView = binding.ProductRecyclerView;

        viewModel = ((GalleriaApplication) getApplication()).diProvider.listResultViewModel;

        binding.ProductRecyclerView.setAdapter(listViewAdapter);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.sort_options, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
        binding.sortSpinner.setAdapter(spinnerAdapter);

        int defaultPosition = spinnerAdapter.getPosition("Alphabetical");
        binding.sortSpinner.setSelection(defaultPosition);

        setupLayoutChangeListener();
        setupSortChangeListener();
    }

    private void setupLayoutChangeListener() {
        // Set layout change button listener to toggle layout mode
        binding.ViewLayoutIcon.setOnClickListener(view -> viewModel.toggleLayoutMode());

        // Listen to layout mode changes
        viewModel.getLayoutMode().observe(this, mode -> {
            listViewAdapter.setLayoutMode(mode);

            RecyclerView.LayoutManager layoutManager;
            int imageResource;
            RecyclerView.ItemDecoration itemDecoration;

            switch (mode) {
                case GRID:
                    layoutManager = new GridLayoutManager(this, 2);
                    imageResource = R.drawable.list_view_icon;
                    itemDecoration = new SimpleListInfoAdapter.ColumnModeItemDecoration(this, 2,16);
                    break;
                case LIST:
                default:
                    layoutManager = new LinearLayoutManager(this);
                    itemDecoration = new SimpleListInfoAdapter.ListModeItemDecoration(this, 16);
                    imageResource = R.drawable.grid_view_icon;
            }

            recyclerView.setLayoutManager(layoutManager);
            if (recyclerView.getItemDecorationCount() > 0) {
                recyclerView.removeItemDecorationAt(0);
            }
            recyclerView.addItemDecoration(itemDecoration);
            binding.ViewLayoutIcon.setIconResource(imageResource);
        });
    }

    private void setupSortChangeListener() {
        binding.SortIcon.setOnClickListener(view -> {
            System.out.println("Sorting button clicked!");
            binding.sortSpinner.setVisibility(View.VISIBLE);
            binding.sortSpinner.performClick();
        });

        binding.sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();

                Map<String, Comparator<ProductInfoDto>> mapSortTextToComparator = Map.of(
                        "Lowest Price", new PriceAscendingComparator(),
                        "Highest Price", new PriceDescendingComparator(),
                        "Alphabetical", new NameAscendingComparator(),
                        "Reverse Alphabetical", new NameDescendingComparator(),
                        "Most Views", new ViewAscendingComparator(),
                        "Least Views", new ViewDescendingComparator()
                );

                viewModel.setComparator(mapSortTextToComparator.getOrDefault(selectedItem, new PriceAscendingComparator()));
                binding.SortFilterText.setText("Sort By: " + selectedItem);

            }

            // Deliberately left empty (when somewhere else is clicked, do nothing)
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


}
