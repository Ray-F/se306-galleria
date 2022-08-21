package nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.User;

/**
 * {@link IRepository} for {@link User} domain model.
 */
public interface IUserRepository extends IRepository<User> {

    Single<User> getByEmail(String email);

}
