package nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo;

import java.util.List;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.User;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.Product;

/**
 * {@link IRepository} for {@link User} domain model.
 */
public interface IUserRepository extends IRepository<User> {

    User getByEmail(String email);

    public List<Product> getProductsByUser(String userId);
}
