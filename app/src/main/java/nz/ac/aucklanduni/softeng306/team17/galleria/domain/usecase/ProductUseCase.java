package nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase;


import java.util.ArrayList;
import java.util.List;


import nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo.AbstractProductRepository;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.Product;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo.IUserRepository;    

public class ProductUseCase {
    private final AbstractProductRepository productRepo;
    private final IUserRepository userRepo;

    public ProductUseCase(AbstractProductRepository productRepo, IUserRepository userRepo) {
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

//    public String getProductById(String id) {
//        return productRepo.getById(id);
//    }

//    public ArrayList listAllProducts() {
//        return new ArrayList<Product>(productRepo.getListAllProducts());
//    }

//    public List<Product> listProductsByCategory(Category category)) {
//        return new ArrayList<Product>(productRepo.listByCategory(category));
//    }

    public List<Product> listBySearchString(String searchString) {
        return new ArrayList<Product>(productRepo.listSortByNameMatch(searchString));
    }

    public List<Product> listTopRatedProduct(int limit) {
        return new ArrayList<Product>(productRepo.listSortByRating(limit));
    }

    public List<Product> listSavedProductsByUser(String userId) {
        return new ArrayList<>(userRepo.getProductsByUser(userId));
    }
}