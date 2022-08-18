package nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo;

import java.util.List;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.Product;

/**
 * A {@link ICachedRepository} for the {@link Product} domain model.
 */
public abstract class AbstractProductRepository extends ICachedRepository<Product> {

    /**
     * Lists all products with a partial name match.
     *
     * E.g. searching "ep" should return a product with name "Pepsi".
     */
    public abstract List<Product> listSortByNameMatch(String nameMatch);

    /**
     * List the top "limit" elements sorted by their rating (high to low).
     */
    public abstract List<Product>listSortByRating(int limit);

    /**
     * List all products that belong to a category.
     */
    public abstract List<Product> listByCategory(Category category);

}
