package nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.Category;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.Product;

/**
 * A {@link IRepository} for the {@link Product} domain model.
 */
public interface IProductRepository extends IRepository<Product> {

    /**
     * Lists all products with a partial name match.
     *
     * E.g. searching "ep" should return a product with name "Pepsi".
     */
    Single<List<Product>> listSortByNameMatch(String nameMatch);

    /**
     * List the top "limit" elements sorted by their rating (high to low).
     */
    Single<List<Product>> listSortByRating(int limit);

    /**
     * List the top "limit" elements sorted by their view count (high to low).
     */
    Single<List<Product>> listSortByView(int limit);

    /**
     * List all products that belong to a category.
     */
    Single<List<Product>> listByCategory(Category category);
}
