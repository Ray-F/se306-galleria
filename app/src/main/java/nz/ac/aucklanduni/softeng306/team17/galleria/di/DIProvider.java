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
import nz.ac.aucklanduni.softeng306.team17.galleria.view.CategoryResultViewModel;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.MainActivityViewModel;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.ProductDetailsViewModel;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.SavedProductsViewModel;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.SearchBarViewModel;
import nz.ac.aucklanduni.softeng306.team17.galleria.view.SearchResultViewModel;

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
    public CategoryResultViewModel categoryResultViewModel = new CategoryResultViewModel(productUseCase);
    public SavedProductsViewModel savedProductsViewModel = new SavedProductsViewModel(productUseCase);
    public ProductDetailsViewModel productDetailsViewModel = new ProductDetailsViewModel(productUseCase);
    public SearchBarViewModel searchBarViewModel = new SearchBarViewModel(searchUseCase);
    public MainActivityViewModel mainActivityViewModel = new MainActivityViewModel(productUseCase);
    public SearchResultViewModel searchResultViewModel = new SearchResultViewModel(searchUseCase);

}
