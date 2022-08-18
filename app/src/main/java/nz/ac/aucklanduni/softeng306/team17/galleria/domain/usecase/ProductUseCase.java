package nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase;
import java.util.List;


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
    public Product getProductById(String id) {
        return productRepo.get(id);
    }

    /**
     * @return all the products.
     */
    public List<Product> listAllProducts() {
         return productRepo.listAll();
    }

    /**
     * @return product by category.
     */
    public List<Product> listProductsByCategory(Category category) {
        return productRepo.listByCategory(category);
    }

    /**
     * @return product by search term..
     */
    public List<Product> listBySearchString(String searchString) {
        return productRepo.listSortByNameMatch(searchString);
    }

    /**
     * @return the top rated product.
     */
    public List<Product> listTopRatedProduct(int limit) {
        return productRepo.listSortByRating(limit);
    }

    /**
     * @return products that have been saved by the user.
     */
    public List<Product> listSavedProductsByUser(String userId) {
        return userRepo.getProductsByUser(userId);
    }
}