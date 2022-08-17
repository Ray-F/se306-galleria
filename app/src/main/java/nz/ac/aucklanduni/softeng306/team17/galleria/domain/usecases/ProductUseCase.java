package nz.ac.aucklanduni.softeng306.team17.galleria.domain.usecase;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
}

/**
 * Returns the product by the id provided.
 */
public String getProductById(String id) {
    return productRepo.getById(id);
}

public List<Product> listAllProducts() {
    return new ArrayList<>(productRepo.getListAllProducts());
}

public List<Product> listProductsByCategory(Category category)) {
    return new ArrayList<>(productRepo.listByCategory(category));
}

public List<Product> listBySearchString(String searchString) {
    return new ArrayList<>(productRepo.listBySearchString(searchString));
}

public List<Product> listTopRatedProduct() {
    return new ArrayList<>(productRepo.listSortByRating());
}

/**
 * Return all the products that have been saved by the user.
 */

public List<Product> listSavedProductsByUser(String userId) {
    return new ArrayList<>(userRepo.getProductsByUser(userId));
}