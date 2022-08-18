package nz.ac.aucklanduni.softeng306.team17.galleria.domain.repo;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.DomainModel;
import nz.ac.aucklanduni.softeng306.team17.galleria.domain.model.product.Product;

/**
 * Interface repository to define the data entrypoint for infrastructure.
 *
 * NB: This interface is not public so that it cannot be implemented.
 */
interface IRepository<T extends DomainModel> {

    Single<T> get(String id);

    Single<List<T>> listAll();

    T create(T item);

}
