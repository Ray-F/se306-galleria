package nz.ac.aucklanduni.softeng306.team17.galleria.view.shared;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityListResultBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.searchbar.TopBarActivity;

abstract public class ListResultActivity extends TopBarActivity {

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

        viewModel.getSortText().observe(this, sortText -> {
            int defaultPosition = spinnerAdapter.getPosition(sortText);
            binding.sortSpinner.setSelection(defaultPosition);
        });

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
                    itemDecoration = new AdapterItemDecoration.ColumnModeItemDecoration(this, 2, 16);
                    break;
                case LIST:
                default:
                    layoutManager = new LinearLayoutManager(this);
                    itemDecoration = new AdapterItemDecoration.ListModeItemDecoration(this, 16);
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
                viewModel.setSortText(selectedItem);
                binding.SortFilterText.setText("Sort By: " + selectedItem);
            }

            // Deliberately left empty (when somewhere else is clicked, do nothing)
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


}
