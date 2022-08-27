package nz.ac.aucklanduni.softeng306.team17.galleria.view.categoryresult;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nz.ac.aucklanduni.softeng306.team17.galleria.GalleriaApplication;
import nz.ac.aucklanduni.softeng306.team17.galleria.R;
import nz.ac.aucklanduni.softeng306.team17.galleria.comparators.NameAscendingComparator;
import nz.ac.aucklanduni.softeng306.team17.galleria.comparators.NameDescendingComparator;
import nz.ac.aucklanduni.softeng306.team17.galleria.comparators.PriceAscendingComparator;
import nz.ac.aucklanduni.softeng306.team17.galleria.comparators.PriceDescendingComparator;
import nz.ac.aucklanduni.softeng306.team17.galleria.comparators.ViewAscendingComparator;
import nz.ac.aucklanduni.softeng306.team17.galleria.comparators.ViewDescendingComparator;
import nz.ac.aucklanduni.softeng306.team17.galleria.databinding.ActivityListResultBinding;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.productdetail.ProductDetailsActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.searchbar.SearchBarActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.SimpleListInfoAdapter;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.SimpleListInfoAdapter.ListModeItemDecoration;

public class CategoryResultActivity extends SearchBarActivity {

    private ActivityListResultBinding binding;
    private CategoryResultViewModel viewModel;

    private SimpleListInfoAdapter listViewAdapter;
    private ArrayAdapter<CharSequence> spinnerAdapter;

    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityListResultBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        viewModel = ((GalleriaApplication) getApplication()).diProvider.categoryResultViewModel;
        listViewAdapter = new SimpleListInfoAdapter();
        binding.setViewmodel(viewModel);
        binding.setLifecycleOwner(this);

        super.onCreate(savedInstanceState);

        Bundle allKeys = getIntent().getExtras();
        category = (Category) allKeys.get("CATEGORY");
        navigationHistory = (ArrayList<Intent>) allKeys.get("NAVIGATION");

        Toolbar toolbar = binding.topBarLayout.toolbar;
        loadToolbar(toolbar, binding.secondaryToolbar);
        customizeToolbarByCategory(category);

        viewModel.fetchCategoryProducts(category);
        viewModel.getProducts().observe(this, listViewAdapter::setProducts);

        binding.ProductRecyclerView.setAdapter(listViewAdapter);

        listViewAdapter.setOnItemClickListener((productId) -> {
            Intent returnIntent = new Intent(this, CategoryResultActivity.class);
            returnIntent.putExtra("CATEGORY", category);
            navigationHistory.add(returnIntent);

            Intent productIntent = new Intent(this, ProductDetailsActivity.class);
            productIntent.putExtra("productId", productId);
            productIntent.putExtra("NAVIGATION", navigationHistory);

            startActivity(productIntent);
        });

        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.sort_options, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item);
        binding.sortSpinner.setAdapter(spinnerAdapter);

        int defaultPosition = spinnerAdapter.getPosition("Alphabetical");
        binding.sortSpinner.setSelection(defaultPosition);
        listViewAdapter.setProducts(viewModel.sortByComparator(new NameDescendingComparator()));


        // TODO: We need to extract this code out from Search and Category Result activity
        viewModel.getLayoutMode().observe(this, mode -> {
            listViewAdapter.setLayoutMode(mode);

            RecyclerView.LayoutManager layoutManager;
            int imageResource;
            RecyclerView.ItemDecoration itemDecoration;

            switch (mode) {
                case GRID:
                    layoutManager = new GridLayoutManager(this, 2);
                    imageResource = R.drawable.list_view_icon;
                    itemDecoration = new SimpleListInfoAdapter.ColumnModeItemDecoration(this, 2, 16);
                    break;
                case LIST:
                default:
                    layoutManager = new LinearLayoutManager(this);
                    itemDecoration = new ListModeItemDecoration(this, 16);
                    imageResource = R.drawable.grid_view_icon;
            }

            binding.ProductRecyclerView.setLayoutManager(layoutManager);
            if (binding.ProductRecyclerView.getItemDecorationCount() > 0) {
                binding.ProductRecyclerView.removeItemDecorationAt(0);
            }
            binding.ProductRecyclerView.addItemDecoration(itemDecoration);
            binding.ViewLayoutIcon.setIconResource(imageResource);
        });

        initModifierListeners();
    }

    private void initModifierListeners() {
        binding.SortIcon.setOnClickListener(view -> {
            System.out.println("Sorting button clicked!");
            binding.sortSpinner.setVisibility(View.VISIBLE);
            binding.sortSpinner.performClick();
        });

        binding.sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                switch (selectedItem) {
                    case "Lowest Price":
                        listViewAdapter.setProducts(viewModel.sortByComparator(new PriceAscendingComparator()));
                        break;
                    case "Highest Price":
                        listViewAdapter.setProducts(viewModel.sortByComparator(new PriceDescendingComparator()));
                        break;
                    case "Alphabetical":
                        listViewAdapter.setProducts(viewModel.sortByComparator(new NameAscendingComparator()));
                        break;
                    case "Reverse Alphabetical":
                        listViewAdapter.setProducts(viewModel.sortByComparator(new NameDescendingComparator()));
                        break;
                    case "Most Views":
                        listViewAdapter.setProducts(viewModel.sortByComparator(new ViewAscendingComparator()));
                        break;
                    case "Least Views":
                        listViewAdapter.setProducts(viewModel.sortByComparator(new ViewDescendingComparator()));
                        break;
                    default:
                        listViewAdapter.setProducts(viewModel.sortByComparator(new NameAscendingComparator()));
                }

                    binding.SortFilterText.setText("Sort By: " + selectedItem);

            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        binding.ViewLayoutIcon.setOnClickListener(view -> viewModel.toggleLayoutMode());
    }

    private void customizeToolbarByCategory(Category category) {
        switch (category) {
            case PHOTOGRAPHIC:
                customizeToolbar(R.color.darkestShadeGreen,
                                 R.color.darkestShadeGreen,
                                 R.color.mediumShadeGreen,
                                 "PHOTOGRAPHIC ART");
                break;
            case ALBUM:
                customizeToolbar(R.color.darkestShadeGray,
                                 R.color.darkestShadeGray,
                                 R.color.mediumShadeGray,
                                 "ALBUM COVER ART");
                break;
            case AI:
                customizeToolbar(R.color.darkestShadeOrange,
                                 R.color.darkestShadeOrange,
                                 R.color.mediumShadeOrange,
                                 "AI GENERATED ART");
                break;
            case PAINTING:
                customizeToolbar(R.color.darkestShadeBlue,
                                 R.color.darkestShadeBlue,
                                 R.color.mediumShadeBlue,
                                 "PAINTINGS");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + category);
        }
    }

}