package nz.ac.aucklanduni.softeng306.team17.galleria.view.shared;

import android.content.Context;
import android.content.Intent;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.categoryresult.CategoryResultActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.main.MainActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.productdetail.ProductDetailsActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.savedproducts.SavedProductsActivity;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.searchresult.SearchResultActivity;

public class NavFactory {

    public static String SEARCH_TERM_INTENT_KEY = "searchTerm";
    public static String PRODUCT_ID_INTENT_KEY = "productId";
    public static String CATEGORY_INTENT_KEY = "category";

    private final Context context;

    public NavFactory(Context context) {
        this.context = context;
    }

    public void startMain() {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    public void startCategoryResult(Category category) {
        Intent intent = new Intent(context, CategoryResultActivity.class);
        intent.putExtra(CATEGORY_INTENT_KEY, category);
        context.startActivity(intent);
    }

    public void startProductDetail(String productId) {
        Intent intent = new Intent(context, ProductDetailsActivity.class);
        intent.putExtra(PRODUCT_ID_INTENT_KEY, productId);
        context.startActivity(intent);
    }

    public void startSearchResult(String searchTerm) {
        Intent intent = new Intent(context, SearchResultActivity.class);
        intent.putExtra(SEARCH_TERM_INTENT_KEY, searchTerm);
        context.startActivity(intent);
    }

    public void startSavedProducts() {
        context.startActivity(new Intent(context, SavedProductsActivity.class));
    }

}
