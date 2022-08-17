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

    public ProductUseCase() {

    }
}
/**
 * Returns the product by the id provided.
 */
public List<Product> getProductById(String id) {
    return new ArrayList<>(productRepo.getProductById(id));
}