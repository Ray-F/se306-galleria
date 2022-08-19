package nz.ac.aucklanduni.softeng306.team17.galleria.di;

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
import nz.ac.aucklanduni.softeng306.team17.galleria.view.SavedProductsViewModel;

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

    /** Define {@link androidx.lifecycle.ViewModel}'s below here. */
    public CategoryResultViewModel categoryResultViewModel = new CategoryResultViewModel(productUseCase);
    public SavedProductsViewModel savedProductsViewModel = new SavedProductsViewModel(productUseCase);

}
