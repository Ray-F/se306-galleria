package nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase;
import java.util.ArrayList;
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
     * @return product by category.
     */
    public Single<List<Product>> listProductsByCategory(Category category) {
        return productRepo.listByCategory(category);
    }

    /**
     * @return top rated products.
     */
    public Single<List<Product>> listTopRatedProducts(int limit) {
        return productRepo.listSortByRating(limit);
    }

    /**
     * @return most viewed products.
     */
    public Single<List<Product>> listTopViewedProducts(int limit) {
        return productRepo.listSortByView(limit);
    }

    /**
     * @return products that have been saved by the user.
     */
    public Single<List<Product>> listSavedProductsByUser(String uuid) {
        Single<List<String>> productIdsProm = userRepo.getSavedProductsByUser(uuid);
        Single<List<Product>> allProductsProm = productRepo.listAll();

        return Single.zip(productIdsProm, allProductsProm, (productIds, allProducts) -> allProducts.stream()
                .filter(it -> productIds.contains(it.getId()))
                .collect(Collectors.toList()));
    }

    /**
     * Saves a product to a user's saved products.
     */
    public void saveProductToUser(String uuid, String targetId) {
        userRepo.getSavedProductsByUser(uuid).subscribe( data -> {
            data.add(targetId);
            userRepo.updateSavedProductsByUser(uuid, data);
        });
    }

    /**
     * Removes a product to a user's saved products.
     */
    public void unsaveProductToUser(String uuid, String targetId) {
        userRepo.getSavedProductsByUser(uuid).subscribe( data -> {
            data.remove(targetId);
            userRepo.updateSavedProductsByUser(uuid, data);
        });
    }

    /**
     * Checks if a product is saved by given user
     */
    public Single<Boolean> isProductSaved(String uuid, String targetId) {
        return userRepo.getSavedProductsByUser(uuid).map( data -> data.contains(targetId));
    }


}