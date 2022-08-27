package nz.ac.aucklanduni.softeng306.team17.galleria.di;

import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.FirebaseFirestore;

import nz.ac.aucklanduni.softeng306.team17.galleria.data.ProductRepository;
import nz.ac.aucklanduni.softeng306.team17.galleria.data.SearchHistoryRepository;
import nz.ac.aucklanduni.softeng306.team17.galleria.data.UserRepository;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo.IProductRepository;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo.ISearchRepository;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo.IUserRepository;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase.ProductUseCase;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase.SearchUseCase;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.categoryresult.CategoryResultViewModel;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.main.MainActivityViewModel;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.productdetail.ProductDetailsViewModel;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.savedproducts.SavedProductsViewModel;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.searchbar.SearchBarViewModel;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.searchresult.SearchResultViewModel;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.shared.ListResultViewModel;

/**
 * Dependency injection container for providing prebuilt classes.
 */
public class DIProvider {

    private final FirebaseFirestore firebaseClient = FirebaseFirestore.getInstance();

    private IUserRepository userRepo = new UserRepository(firebaseClient);
    private IProductRepository productRepo = new ProductRepository(firebaseClient);
    private ISearchRepository searchRepo = new SearchHistoryRepository(firebaseClient);

    private ProductUseCase productUseCase = new ProductUseCase(productRepo, userRepo);
    private SearchUseCase searchUseCase = new SearchUseCase(searchRepo, productRepo);

    /** Define {@link ViewModel}'s below here. */
    public SavedProductsViewModel savedProductsViewModel = new SavedProductsViewModel(productUseCase);
    public ProductDetailsViewModel productDetailsViewModel = new ProductDetailsViewModel(productUseCase);
    public SearchBarViewModel searchBarViewModel = new SearchBarViewModel(searchUseCase);
    public MainActivityViewModel mainActivityViewModel = new MainActivityViewModel(productUseCase);

    public ListResultViewModel listResultViewModel = new ListResultViewModel();
    public CategoryResultViewModel categoryResultViewModel = new CategoryResultViewModel(productUseCase);
    public SearchResultViewModel searchResultViewModel = new SearchResultViewModel(searchUseCase);

}
