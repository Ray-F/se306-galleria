package nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase;
import java.util.List;
import java.util.stream.Collectors;


import io.reactivex.rxjava3.core.Single;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo.IProductRepository;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.Product;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo.IUserRepository;

/**
 * Handle the product querying.
 */

public class ProductUseCase {
    private final IProductRepository productRepo;
    private final IUserRepository userRepo;

    public ProductUseCase(IProductRepository productRepo, IUserRepository userRepo) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    /**
     * @return the products by id.
     */
    public Single<Product> getProductById(String id) {
        return productRepo.get(id);
    }

    /**
     * @return all the products.
     */
    public Single<List<Product>> listAllProducts() {
         return productRepo.listAll();
    }

    /**
     * @return product by category.
     */
    public Single<List<Product>> listProductsByCategory(Category category) {
        return productRepo.listByCategory(category);
    }

    /**
     * @return product by search term..
     */
    public Single<List<Product>> listBySearchString(String searchString) {
        return productRepo.listSortByNameMatch(searchString);
    }

    /**
     * @return the top rated product.
     */
    public Single<List<Product>> listTopRatedProduct(int limit) {
        return productRepo.listSortByRating(limit);
    }

    /**
     * @return products that have been saved by the user.
     */
    public Single<List<Product>> listSavedProductsByUser(String userId) {
        Single<List<String>> productIdsProm = userRepo.getProductsByUser(userId);
        Single<List<Product>> allProductsProm = productRepo.listAll();

        return Single.create(emitter -> {
            List<String> productIds = productIdsProm.blockingGet();
            List<Product> allProducts = allProductsProm.blockingGet();

            List<Product> filteredProducts = allProducts.stream()
                    .filter(it -> productIds.contains(it.getId()))
                    .collect(Collectors.toList());

            emitter.onSuccess(filteredProducts);
        });
    }

    /**
     * Saves a product to a user's saved products.
     */
    public void saveProductToUser(Product product) {
        // TODO: Complete this use case
    }
}