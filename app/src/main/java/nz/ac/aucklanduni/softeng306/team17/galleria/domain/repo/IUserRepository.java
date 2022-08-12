package nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo;

import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.User;

/**
 * {@link IRepository} for {@link User} domain model.
 */
public interface IUserRepository extends IRepository<User> {

    User getByEmail(String email);

}
