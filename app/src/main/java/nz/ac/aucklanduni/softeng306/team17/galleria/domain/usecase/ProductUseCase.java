package nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase;
import java.util.List;


import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo.IProductRepository;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.Product;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo.IUserRepository;    

public class ProductUseCase {
    private final IProductRepository productRepo;
    private final IUserRepository userRepo;

    public ProductUseCase(IProductRepository productRepo, IUserRepository userRepo) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    public Product getProductById(String id) {
        return productRepo.get(id);
    }

    public List<Product> listAllProducts() {
        return productRepo.listAll();
    }

    public List<Product> listProductsByCategory(Category category) {
        return productRepo.listByCategory(category);
    }

    public List<Product> listBySearchString(String searchString) {
        return productRepo.listSortByNameMatch(searchString);
    }

    public List<Product> listTopRatedProduct(int limit) {
        return productRepo.listSortByRating(limit);
    }

    public List<Product> listSavedProductsByUser(String userId) {
        return userRepo.getProductsByUser(userId);
    }
}